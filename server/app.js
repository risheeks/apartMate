var net = require('net');
var firebase = require('firebase'); //for database
var sockets = [];
var loginMap = new Map();
var app = firebase.initializeApp(    {databaseURL: "https://apartmate-3.firebaseio.com",}
);

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
    loginMap.set(email, password);
    if(check == false)
     sock.write('REGISTER SUCCESS\n');
};


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
        if (idx != -1) {
            delete sockets[idx];
        }
    });
});

var svraddr = '10.186.41.164';
var svrport = 9900;

svr.listen(svrport, svraddr);
console.log('Server Created at ' + svraddr + ':' + svrport + '\n');