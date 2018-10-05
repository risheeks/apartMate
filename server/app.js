/*
*   Initializing variables with libraries and dependencies for the server side
*
 */
var net = require('net');
var firebase = require('firebase'); //for database
var sockets = [];
var loginMap = new Map();
var app = firebase.initializeApp(    {databaseURL: "https://apartmate-3.firebaseio.com",}
);
var socketMap = new Map();
var nodemailer = require('nodemailer')

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

    var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/FirstName");
    ref.set(firstName);
    var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/LastName");
    ref.set(lastName);
    var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/LatestAchievement");
    ref.set(latestA);
    var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/GreatestAchievement");
    ref.set(greatestA);
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
    },310)

    setTimeout(function () {

    console.log('RECEIVE_PROFILE;'+email.toString()+';'+firstName.toString()+';'+lastName.toString()+';'+latestA.toString()+';'+greatestA)
    sock.write('RECEIVE_PROFILE;'+email.toString()+';'+firstName.toString()+';'+lastName.toString()+';'+latestA.toString()+';'+greatestA+'\n');
    },850)

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
        else
        {
            sock.write('INVALID_COMMAND\n')
        }
        var command2 = data.toString().split(";")[0];
        if(command2 == "EDIT_PROFILE"){
            editProfile(data,sock);

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

var svraddr = '10.186.33.252';
var svrport = 9910;

svr.listen(svrport, svraddr);
console.log('Server Created at ' + svraddr + ':' + svrport + '\n');