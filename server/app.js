/*
*   Initializing variables with libraries and dependencies for the server side
*
 */
var net = require('net');
var firebase = require('firebase'); //for database
var sockets = [];
var loginMap = new Map();
var groupMap = new Map();
var app = firebase.initializeApp(    {databaseURL: "https://apartmate-3.firebaseio.com",}
);
var socketMap = new Map();
var nodemailer = require('nodemailer')
var chores = "";
/*
Transporter for the email client initialized with auth info
 */
var transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: 'apartmate123@gmail.com',
        pass: 'adrian@123SOL'
    }
});
/*
Cryptr library for encrypt and decrypt
 */
const Cryptr = require('cryptr');
const cryptr = new Cryptr('apartmate');
/*
Initialize loginMap with existing users
 */
const dbLoginRef = firebase.database().ref("Login").orderByKey();
dbLoginRef.once("value")
    .then(function (snapshot) {
        snapshot.forEach(function (childSnapshot) {
            var key = childSnapshot.key;
            loginMap.set(key, childSnapshot.val());
        })
    });
const dbGroupRef = firebase.database().ref("Groups").orderByKey();
dbGroupRef.once("value")
    .then(function (snapshot) {
        snapshot.forEach(function (childSnapshot) {
            var key = childSnapshot.key;
            console.log(key+" "+childSnapshot.val().Members)
            groupMap.set(key, childSnapshot);
        })
    });


/*
*Function to process user login with password encryption
 */
var processLogin = function (data,sock) {
    var email = data.toString().split(" ")[1];
    var password = data.toString().split(" ")[2];
    var c = true;
    loginMap.forEach(function (value,key) {
        console.log(value.Email)
        if(value.Email == email) {

            if(cryptr.decrypt(value.Password) == password) {
                c = false;
            }
        }
    });
    setTimeout(function () {
        if(c == true) {
            sock.write('LOGIN FAILURE\n');
        }
        else {
            socketMap.set(data.toString().split(" ")[1], sock);
            sock.write('LOGIN SUCCESS\n');
        }
    },700)
};
/*==================================================================================================================
                                      Register Function
                                that deals with incoming requests
 ===================================================================================================================*/
var processRegister = function (data,sock) {
    var email = data.toString().split(" ")[1];
    var password = data.toString().split(" ")[2];
    var firstName = data.toString().split(" ")[3];
    var lastName = data.toString().split(" ")[4];

    var check = false;
    loginMap.forEach(function (value,key) {
        console.log(key)
       if(value.Email == email) {
            check = true;
           sock.write('REGISTER ACCOUNT_EXISTS\n');         //Communicate with client
       }
    });
    //Refresh the login map with newly updated data from Firebase
    const dbLoginRef = firebase.database().ref("Login").orderByKey();
    dbLoginRef.once("value")
        .then(function (snapshot) {
            snapshot.forEach(function (childSnapshot) {
                var key = childSnapshot.key;
                loginMap.set(key, childSnapshot.val());
            })
        });
    setTimeout(function () {

    var mailOptions = {
        from: 'apartmate123@gmail.com',
        to: email.toString(),
        subject: 'Welcome to ApartMate!',
        text: 'Your account has been verified!\nEnjoy using our app!'
    };
    transporter.sendMail(mailOptions, function(error, info){
        if (error) {
            console.log(error);
        } else {
            console.log('Email sent: ' + info.response);
        }setTimeout(function () {

                var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/Email");
                ref.set(email);
                var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/Password");
                ref.set(cryptr.encrypt(password));
                var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/FirstName");
                ref.set(firstName);
                var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/LastName");
                ref.set(lastName);
                var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/LatestAchievement");
                ref.set("");
                var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/GreatestAchievement");
                ref.set("");
                var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/TotalAmountDue");
                ref.set("0");
                var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/Interests");
                ref.set("I like reading books");
            sock.write('REGISTER SUCCESS\n');
            const dbLoginRef = firebase.database().ref("Login").orderByKey();
            dbLoginRef.once("value")
                .then(function (snapshot) {
                    snapshot.forEach(function (childSnapshot) {
                        var key = childSnapshot.key;
                        loginMap.set(key, childSnapshot.val());
                    })
                });

        },200)

    });

    },600);
};

/*======================================================================================================================
                    Reset Password function to update teh database with the new password
 =======================================================================================================================*/
var resetPassword = function (data,sock) {
    var email = data.toString().split(" ")[1];
    var newPassword = data.toString().split(" ")[2];
    var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/Password");
    ref.set(cryptr.encrypt(newPassword));
    sock.write('RESET_PASSWORD SUCCESS\n');
    const dbLoginRef = firebase.database().ref("Login").orderByKey();
    dbLoginRef.once("value")
        .then(function (snapshot) {
            snapshot.forEach(function (childSnapshot) {
                var key = childSnapshot.key;
                loginMap.set(key, childSnapshot.val());
            })
        });
}
/*
*   Checks if a user exists in the database
*
 */
var checkUser = function (data,sock) {
    var email = data.toString().split(" ")[1];
    var check = false;
    loginMap.forEach(function (value,key) {
        if(value.Email == email) {
            check = true;
        }
    });

    setTimeout(function () {
    if(check == false){
        sock.write('CHECK_USER ACCOUNT_DNE\n')
    }
    else
    {
        sock.write('CHECK_USER ACCOUNT_EXISTS\n');
    }
    },400);
}

/*
*   Send message to the client for chatting
*
*/

var sendMessage = function (data,sock) {
    var senderEmail = data.toString().split(" ")[1];
    var receiverEmail =  data.toString().split(" ")[2];
    var messageText =  data.toString().substr(data.toString().indexOf(receiverEmail)+receiverEmail.length+1);
    var check = true;
    socketMap.forEach(function (value, key) {
        if(key == receiverEmail)
        {
            check = false;
            value.write('RECEIVE_MESSAGE '+senderEmail+' '+messageText+'\n');
        }
    });
}
/*
*
* checks password against the actual password
 */
var checkPassword = function (data,sock) {
    var email = data.toString().split(" ")[1];
    var password = data.toString().split(" ")[2];
    var c = true;
    loginMap.forEach(function (value,key) {
        console.log(value.Email)
        if(value.Email == email) {
            if(cryptr.decrypt(value.Password) == password) {
                c = false;
            }
        }
    });
    setTimeout(function () {
        if(c == true) {
            sock.write('CHECK_PASSWORD INCORRECT_PASSWORD\n');
        }
        else {
            socketMap.set(data.toString().split(" ")[1], sock);
            sock.write('CHECK_PASSWORD SUCCESS\n');
        }
    },580)
}
/*
*
* function to send an email with a randomly generated password and updates the database
 */
var forgotPassword = function (data,sock) {
    var email = data.toString().split(" ")[1];
    var tempPass = Math.random().toString(36).substring(2, 15);
    var mailOptions = {
        from: 'apartmate123@gmail.com',
        to: email.toString(),
        subject: 'Temporary Password - ApartMate',
        text: 'Here\'s a temporary password for your ApartMate account: '+tempPass +'\nPlease change it later!',
    };
    transporter.sendMail(mailOptions, function(error, info) {
        if (error) {
            console.log(error);
        } else {
            console.log('Email sent: ' + info.response);
        }
    });
    var check = true;
    loginMap.forEach(function (value,key) {
        if(value.Email == email) {
            var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/Password");
            check = false;
            ref.set(cryptr.encrypt(tempPass));
            const dbLoginRef = firebase.database().ref("Login").orderByKey();
            dbLoginRef.once("value")
                .then(function (snapshot) {
                    snapshot.forEach(function (childSnapshot) {
                        var key = childSnapshot.key;
                        loginMap.set(key, childSnapshot.val());
                    })
                });
            sock.write('FORGOT_PASSWORD SUCCESS\n');
            }

    });
        if(check == true){
            sock.write('FORGOT_PASSWORD INVALID_EMAIL\n');
        }

}
/*
*
*   Function to edit profile given input from the client. Updates fields in the database
*
 */
var editProfile = function (data,sock) {
    var email = data.toString().split(";")[1];
    var firstName = data.toString().split(";")[2];
    var lastName = data.toString().split(";")[3];
    var latestA = data.toString().split(";")[4];
    var greatestA = data.toString().split(";")[5];
    var interests = data.toString().split(";")[6];
    var emergencyContacts = data.toString().split(";")[7];
    var birthday = data.toString().split(";")[8];

    var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/FirstName");
    ref.set(firstName);
    var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/LastName");
    ref.set(lastName);
    var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/LatestAchievement");
    ref.set(latestA);
    var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/GreatestAchievement");
    ref.set(greatestA);
     var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/Interests");
     ref.set(interests);
    var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/EmergencyContact");
    ref.set(emergencyContacts);
    var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/Birthday");
    ref.set(birthday);

}
/*
*
* Returns profile to the client with First Name, Last Name, Email, Latest Achievement, and Greatest Achievement
 */
var getProfile = function (data, sock) {
    var firstName;
    var lastName;
    var latestA;
    var greatestA;
    var email;

    socketMap.forEach(function (value, key) {
        if(value == sock)
        {
            email = key.toString();
        }
    });
    const dbLoginRef = firebase.database().ref("Login").orderByKey();
    dbLoginRef.once("value")
        .then(function (snapshot) {
            snapshot.forEach(function (childSnapshot) {
                var key = childSnapshot.key;
                loginMap.set(key, childSnapshot.val());
            })
        });
    setTimeout(function () {
        loginMap.forEach(function (value,key) {
            if(value.Email == email) {
                firstName = value.FirstName;
                lastName = value.LastName;
                latestA = value.LatestAchievement;
                greatestA = value.GreatestAchievement;
            }
        });
    },150)

    setTimeout(function () {
    console.log('RECEIVE_PROFILE;'+email.toString()+';'+firstName.toString()+';'+lastName.toString()+';'+latestA.toString()+';'+greatestA)
    sock.write('RECEIVE_PROFILE;'+email.toString()+';'+firstName.toString()+';'+lastName.toString()+';'+latestA.toString()+';'+greatestA+'\n');
    },0000)

}


var createGroup = function(data,sock){
    var email = data.toString().split(";")[1];
    var groupName = data.toString().split(";")[2];
    var checkGroup = false;
    checkUser = false;
    groupMap.forEach(function (value,key) {
       if(key == groupName)
       {
           console.log("equal")
           checkGroup = true;
       }
    });
    loginMap.forEach(function (value,key) {
        if(value.Email == email) {
            checkUser = true;
        }
    });
    setTimeout(function () {
        if(checkGroup == false && checkUser == true) {
            var ref = firebase.database().ref("Groups/" + groupName + "/Members");
            ref.set(email+";");
            var ref = firebase.database().ref("Groups/" + groupName + "/Chores");
            ref.set("A:B:C:D:E");
            var ref = firebase.database().ref("Groups/" + groupName + "/GroceryList");
            ref.set("Bananas:1 dozen");
            var ref = firebase.database().ref("Groups/" + groupName + "/ShareablePossessions");
            ref.set(email+":Refrigerator");
            var ref = firebase.database().ref("Groups/" + groupName + "/UnshareablePossessions");
            ref.set(email+":Clothing");
            var ref = firebase.database().ref("Groups/" + groupName + "/Interests");
            ref.set(email+":I like reading books and listening to music");
            var ref = firebase.database().ref("Groups/" + groupName + "/EmergencyContact");
            ref.set(email+":911");
            var ref = firebase.database().ref("Login/" + email.split("@")[0] + "/Group");
            ref.set(groupName);
            sock.write('CREATE_GROUP SUCCESS\n');
            dbGroupRef.once("value")
                .then(function (snapshot) {
                    snapshot.forEach(function (childSnapshot) {
                        var key = childSnapshot.key;
                        console.log(key+" "+childSnapshot.val().Members)
                        groupMap.set(key, childSnapshot);
                    })
                });
        }
        else if(checkUser == false)
        {
            sock.write('CREATE_GROUP INVALID_USER\n')
        }
        else
        {
            sock.write('CREATE_GROUP GROUP_EXISTS\n')
        }
    },300);

    //var ref = firebase.database().ref("Groups/" + email.toString().split("@")[0]+"/GroupName/"+groupName.toString()+"/Users");
    //ref.set(email);
}

var addToGroup = function(data,sock){
    var email = data.toString().split(";")[1];
    var groupName = data.toString().split(";")[2];
    var ref1 = firebase.database().ref("Groups/" + groupName + "/Members");
    var ids;
    ref1.on("value",function (snapshot) {
        ids = snapshot.val();
    });
    setTimeout(function () {
        var r =  firebase.database().ref("Groups/" + groupName + "/Members");
        r.set(ids+email+";");
        var ref2 = firebase.database().ref("Login/" + email.split("@")[0] + "/Group");
        ref2.set(groupName);
        dbGroupRef.once("value")
            .then(function (snapshot) {
                snapshot.forEach(function (childSnapshot) {
                    var key = childSnapshot.key;
                    console.log(key+" "+childSnapshot.val().Members)
                    groupMap.set(key, childSnapshot);
                })
            });

    },300);


    sock.write('ADD_GROUP SUCCESS\n')

}

var sendGroupMessage = function(data,sock)
{
    var senderEmail = data.toString().split(";")[1];
    var groupName =  data.toString().split(";")[2];
    var messageText =  data.toString().substr(data.toString().indexOf(groupName)+groupName.length+1);
    var check = true;
    var emails;
    groupMap.forEach(function (value,key) {
        console.log("Key: "+key)
        if(key == groupName) {
             emails = value.val().Members.toString().split(";");
            console.log(emails)
        }
    });
    setTimeout(function () {
        emails.forEach(function (val) {
            socketMap.forEach(function (value, key) {
                if(key == val)
                {
                    check = false;
                    value.write('RECEIVE_GROUPM;'+senderEmail+';'+groupName+';'+messageText+'\n');
                }
            });
        })
    },300)
}

var addChore = function (data,sock) {
    var groupName = data.toString().split(";")[1];
    var choreName = data.toString().split(";")[2];
    var assignee = data.toString().split(";")[3];
    var choreDescription = data.toString().split(";")[4];
    var choreDate = data.toString().split(";")[5];
    var choreTime = data.toString().split(";")[6];

    var ref1 = firebase.database().ref("Groups/" + groupName + "/Chores");
    var chores;
    ref1.on("value",function (snapshot) {
        chores = snapshot.val();
    });
    setTimeout(function () {
        if(chores == null)
            chores="";
        var r =  firebase.database().ref("Groups/" + groupName + "/Chores");
        console.log(chores+choreName+":"+assignee+":"+choreDescription+":"+choreDate+":"+choreTime+";")
        r.set(chores+choreName+":"+assignee+":"+choreDescription+":"+choreDate+":"+choreTime+";");
    },300);
    sock.write('ADD_CHORE SUCCESS\n');

}

var addGroceryItem = function (data,sock) {
    var groupName = data.toString().split(";")[1];
    var itemName = data.toString().split(";")[2];
    var quantity = data.toString().split(";")[3];

    var ref1 = firebase.database().ref("Groups/" + groupName + "/GroceryList");
    var groceryList;
    ref1.on("value",function (snapshot) {
        groceryList = snapshot.val();
    });
    setTimeout(function () {
        var r =  firebase.database().ref("Groups/" + groupName + "/GroceryList");
        if(groceryList == null)
            groceryList = "";
        r.set(groceryList+";"+itemName+":"+quantity+";");
    },300);
    sock.write('ADD_GROCERYITEM SUCCESS\n');

}

var addShareablePossession = function (data,sock) {
    var groupName = data.toString().split(";")[1];
    var email = data.toString().split(";")[2];
    var possession = data.toString().split(";")[3];
    var ref1 = firebase.database().ref("Groups/" + groupName + "/ShareablePossessions");
    var groceryList;
    ref1.on("value",function (snapshot) {
        groceryList = snapshot.val();
    });
    setTimeout(function () {
        var r =  firebase.database().ref("Groups/" + groupName + "/ShareablePossessions");
        if(groceryList == null)
            groceryList = "";
        r.set(groceryList+";"+email+":"+possession+";");
    },300);
    sock.write('ADD_SHAREABLEPOSSESSION SUCCESS\n');

}

var addInterest = function (data,sock) {
    var groupName = data.toString().split(";")[1];
    var email = data.toString().split(";")[2];
    var interest = data.toString().split(";")[3];
    var ref1 = firebase.database().ref("Groups/" + groupName + "/Interests");
    var groceryList;
    ref1.on("value",function (snapshot) {
        groceryList = snapshot.val();
    });
    setTimeout(function () {
        var r =  firebase.database().ref("Groups/" + groupName + "/Interests");
        if(groceryList == null)
            groceryList = "";
        r.set(groceryList+";"+email+":"+interest+";");
    },300);
    sock.write('ADD_INTEREST SUCCESS\n');

}

var addUnshareablePossessions = function (data,sock) {
    var groupName = data.toString().split(";")[1];
    var email = data.toString().split(";")[2];
    var possession = data.toString().split(";")[3];
    var ref1 = firebase.database().ref("Groups/" + groupName + "/UnshareablePossessions");
    var groceryList;
    ref1.on("value",function (snapshot) {
        groceryList = snapshot.val();
    });
    setTimeout(function () {
        var r =  firebase.database().ref("Groups/" + groupName + "/UnshareablePossessions");
        if(groceryList == null)
            groceryList = "";
        r.set(groceryList+";"+email+":"+possession+";");
    },300);
    sock.write('ADD_UNSHAREABLEPOSSESSION SUCCESS\n');

}

var addEmergency = function (data,sock) {
    var groupName = data.toString().split(";")[1];
    var email = data.toString().split(";")[2];
    var emergency = data.toString().split(";")[3];
    var ref1 = firebase.database().ref("Groups/" + groupName + "/EmergencyContacts");
    var groceryList;
    ref1.on("value",function (snapshot) {
        groceryList = snapshot.val();
    });
    setTimeout(function () {
        var r =  firebase.database().ref("Groups/" + groupName + "/EmergencyContacts");
        if(groceryList == null)
            groceryList = "";
        r.set(groceryList+email+":"+emergency+";");
    },300);
    sock.write('ADD_EMERGENCY SUCCESS\n');
}

var getChoresList = function (data,sock) {
  //  var groupName = data.toString().split(";")[1];
 //   var msg = 'GET_CHORESLIST;SUCCESS;' +  '\n'
   // setTimeout(function () {
        sock.write('GET_CHORESLIST;SUCCESS;' + chores + '\n');
    //},100);
}

var getGroceryList = function (data,sock) {
    var groupName = data.toString().split(";")[1];
    var ref1 = firebase.database().ref("Groups/" + groupName + "/GroceryList");
    var groceryList;
    ref1.on("value",function (snapshot) {
        groceryList = snapshot.val();
    });
    setTimeout(function () {
        if (groceryList == null) {
            groceryList = "";
        }
        sock.write('GET_GROCERYLIST SUCCESS ' + groceryList + '\n');
    },200);

}

var getTotalDue = function (data,scok) {
    var email = data.toString().split(";")[1];
    var ref1 = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/TotalAmountDue");
    var totalAmount;
    ref1.on("value",function (snapshot) {
        totalAmount = snapshot.val();
    });


    setTimeout(function () {
        sock.write('GET_TOTALDUE SUCCESS '+totalAmount+'\n')
    },200)

}

var getInterests = function (data,sock) {
    var email = data.toString().split(";")[1];
    var ref1 = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/Interests");
    var interests;
    ref1.on("value",function (snapshot) {
        interests = snapshot.val();
    });


    setTimeout(function () {
        sock.write('GET_INTERESTS;SUCCESS;'+interests+';\n')
    },200)
}

var getGroupMembers = function (data,sock) {
    var groupName = data.toString().split(";")[1];
    var ref1 = firebase.database().ref("Groups/" + groupName + "/Members");
    var members;
    ref1.on("value",function (snapshot) {
        members = snapshot.val();

    });
    setTimeout(function () {
        sock.write('GET_GROUPMEMBERS;SUCCESS;'+members+'\n')

    },100)
    }


var addEvent = function (data,sock) {
    var email = data.toString().split(";")[1];
    var date = data.toString().split(";")[2];
    var name = data.toString().split(";")[3];
    var type = data.toString().split(";")[4];
    var description = data.toString().split(";")[5];
    var ref2 = firebase.database().ref("Login/" + email.split("@")[0] + "/Events");
    var events;
    ref2.on("value",function (snapshot) {
        events = snapshot.val();
    });


    setTimeout(function () {
        if(events == null)
            events = "";
        ref2.set(events+date+":"+name+":"+type+":"+description+";");
        sock.write('ADD_EVENT;SUCCESS\n')
    },200)
}

var getEvents = function (data,sock) {
    var email = data.toString().split(";")[1];
    var ref1 = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/Events");
    var events;
    ref1.on("value",function (snapshot) {
        events = snapshot.val();
    });


    setTimeout(function () {
        sock.write('GET_EVENTS;SUCCESS;'+events+'\n')
    },200)
}

var getGroup = function (data,sock) {
    var email = data.toString().split(";")[1];
    var ref1 = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/Group");
    var groupName;
    ref1.on("value",function (snapshot) {
        groupName = snapshot.val();
    });

    setTimeout(function () {
        console.log("in"+groupName)
        sock.write('GET_GROUP;SUCCESS;'+groupName+'\n')
        var ref2 = firebase.database().ref("Groups/" + groupName + "/Chores");
        ref2.on("value",function (snapshot) {
            chores = snapshot.val();
        });
    },200)
}

var addReceipt = function (data,sock) {
    var groupName = data.toString().split(";")[1];
    var owedTo = data.toString().split(";")[2];
    var title = data.toString().split(";")[3];
    var amount = data.toString().split(";")[4];
    var members = data.toString().split(";")[5];
    var numMembers = (members.toString().split(",")).length;
    var memberArray = members.toString().split(",");
    var totalOwed;
    var ref1 = firebase.database().ref("Groups/" + groupName + "/Receipts");
    var receipts;
    ref1.on("value",function (snapshot) {
        receipts = snapshot.val();
    });
    setTimeout(function () {
        if(receipts == null)
            receipts="";
        var r =  firebase.database().ref("Groups/" + groupName + "/Receipts");
        r.set(receipts+title+":"+amount+":"+members+";");
        memberArray.forEach(function(element) {
            if(element != owedTo)
            {
                var ref1 = firebase.database().ref("Login/" + (element.toString().split("@")[0]).trim() + "/TotalAmountDue");
                var amoun="lol";
                ref1.on("value",function (snapshot) {
                    amoun = snapshot.val();
                });
                setTimeout(function () {
                    if(amoun == null)
                    {
                        amoun = 0;
                    }
                    console.log(amoun)
                    console.log(amount/numMembers)
                    amoun = parseInt(amoun)+(amount/numMembers);
                    console.log(amoun)
                    ref1.set(amoun);
                },500)
            }
            else
            {
                var ref2 = firebase.database().ref("Login/" + element.toString().split("@")[0] + "/TotalAmountDue");
                var amoun1="lol";
                ref2.on("value",function (snapshot) {
                    amoun1 = snapshot.val();
                });
                setTimeout(function () {
                    amoun1 = parseInt(amoun1)-amount+(amount/numMembers);
                    ref2.set(amoun1);
                },300)
            }
        });
    },300);
    //sock.write('ADD_CHORE SUCCESS\n');
}
/*
*  Starts the server with sockets listening for different input commands
 */
var svr = net.createServer(function(sock) {
    function processRequest(data,sock){
        var command = data.toString().split(" ")[0];
        if(command == "LOGIN")
        {
            processLogin(data,sock);
        }
        else if(command == "REGISTER")
        {
           processRegister(data,sock)
        }
        else if(command == "RESET_PASSWORD"){
            resetPassword(data,sock)
        }
        else if(command == "CHECK_USER"){
            checkUser(data,sock);
        }
        else if(command == "SEND_MESSAGE"){
            sendMessage(data,sock);
        }
        else if(command == "CHECK_PASSWORD"){
            checkPassword(data,sock);
        }
        else if(command == "FORGOT_PASSWORD"){
            forgotPassword(data,sock);
        }
        else if(command == "GET_PROFILE"){
            getProfile(data,sock);
        }
        else if(!(command.toString().indexOf(";") > -1))
        {
        //    sock.write('INVALID_COMMAND\n')
        }
        var command2 = data.toString().split(";")[0];
        if(command2 == "EDIT_PROFILE"){
            editProfile(data,sock);

        }
        else if(command2 == "CREATE_GROUP")
        {
            createGroup(data,sock);
        }
        else if(command2 == "ADD_GROUP")
        {
            addToGroup(data,sock);
        }
        else if(command2 == "SEND_GROUPM")
        {
            sendGroupMessage(data,sock);
        }
        else if(command2 == "ADD_CHORE")
        {
            addChore(data,sock);
        }
        else if(command2 == "ADD_GROCERYITEM")
        {
            addGroceryItem(data,sock);
        }
        else if(command2 == "GET_CHORESLIST")
        {
            getChoresList(data,sock);
        }
        else if(command2 == "GET_GROCERYLIST")
        {
            getGroceryList(data,sock);
        }
        else if(command2 == "GET_TOTALDUE")
        {
            getTotalDue(data,sock);
        }
        else if(command2 == "GET_INTERESTS")
        {
            getInterests(data,sock);
        }
        else if(command2 == "GET_GROUPMEMBERS")
        {
            getGroupMembers(data,sock);
        }
        else if(command2 == "ADD_EVENT")
        {
            addEvent(data,sock);
        }
        else if(command2 == "GET_EVENTS")
        {
            getEvents(data,sock);
        }
        else if(command2 == "GET_GROUP")
        {
            getGroup(data,sock)
        }
        else if(command2 == "ADD_RECEIPT")
        {
            addReceipt(data,sock);
        }
        else if(command2 == "ADD_SHAREABLEPOSSESSIONS")
        {
            addShareablePossession(data,sock);
        }
        else if(command2 == "ADD_UNSHAREABLEPOSSESSIONS")
        {
            addUnshareablePossessions(data,sock);
        }
        else if(command2 == "ADD_INTEREST")
        {
            addInterest(data,sock);
        }
        else if(command2 == "ADD_EMERGENCY")
        {
            addEmergency(data,sock);
        }



    }
    console.log('Connected: ' + sock.remoteAddress + ':' + sock.remotePort);
    sockets.push(sock);

    sock.write('Welcome to the server!\n');

    sock.on('data', function(data) {
        console.log("received: " + data.toString())
        processRequest(data,sock);
        /* for (var i=0; i<sockets.length ; i++) {
             if (sockets[i] != sock) {
                 if (sockets[i]) {
                     sockets[i].write(data);
                 }
             }
         }*/
    });

    sock.on('end', function() {
        console.log('Disconnected: ' + sock.remoteAddress + ':' + sock.remotePort);
        var idx = sockets.indexOf(sock);
        socketMap.forEach(function (value, key) {
            if(value == sock)
            {
                socketMap.delete(key);
            }
        })
        if (idx != -1) {
            delete sockets[idx];
        }
    });
});

var svraddr = '10.186.93.103';
var svrport = 9910;

svr.listen(svrport, svraddr);
console.log('Server Created at ' + svraddr + ':' + svrport + '\n');
