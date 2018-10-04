var net = require('net');
var firebase = require('firebase'); //for database
var sockets = [];
var loginMap = new Map();
var app = firebase.initializeApp(    {databaseURL: "https://apartmate-3.firebaseio.com",}
);
var socketMap = new Map();

const dbLoginRef = firebase.database().ref("Login").orderByKey();
dbLoginRef.once("value")
    .then(function (snapshot) {
        snapshot.forEach(function (childSnapshot) {
            var key = childSnapshot.key;
            loginMap.set(key, childSnapshot.val());
        })
    });

var processLogin = function (data,sock) {
    var email = data.toString().split(" ")[1];
    var password = data.toString().split(" ")[2];
    var c = true;
    loginMap.forEach(function (value,key) {
        console.log(value.Email)
        if(value.Email == email) {
            if(value.Password == password) {
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
    },580)
};

var processRegister = function (data,sock) {
    var email = data.toString().split(" ")[1];
    var password = data.toString().split(" ")[2];
    var check = false;
    loginMap.forEach(function (value,key) {
        console.log(key)
       if(value.Email == email) {
            check = true;
           sock.write('REGISTER ACCOUNT_EXISTS\n');
       }
    });

    var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/Email");

    ref.set(email);
    var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/Password");

    ref.set(password);
    const dbLoginRef = firebase.database().ref("Login").orderByKey();
    dbLoginRef.once("value")
        .then(function (snapshot) {
            snapshot.forEach(function (childSnapshot) {
                var key = childSnapshot.key;
                loginMap.set(key, childSnapshot.val());
            })
        });    if(check == false)
     sock.write('REGISTER SUCCESS\n');
};

var resetPassword = function (data,sock) {
    var email = data.toString().split(" ")[1];
    var newPassword = data.toString().split(" ")[2];
    var ref = firebase.database().ref("Login/" + email.toString().split("@")[0] + "/Password");
    ref.set(newPassword);
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
    if(check == true)
    {
        sock.write('SEND_MESSAGE USER_DISCONNECTED\n');
    }

}

var checkPassword = function (data,sock) {
    var email = data.toString().split(" ")[1];
    var password = data.toString().split(" ")[2];
    var c = true;
    loginMap.forEach(function (value,key) {
        console.log(value.Email)
        if(value.Email == email) {
            if(value.Password == password) {
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
        else
        {
            sock.write('INVALID_COMMAND\n')
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
                socketMap.remove(key);
            }
        })
        if (idx != -1) {
            delete sockets[idx];
        }
    });
});

var svraddr = '10.186.41.164';
var svrport = 9900;

svr.listen(svrport, svraddr);
console.log('Server Created at ' + svraddr + ':' + svrport + '\n');