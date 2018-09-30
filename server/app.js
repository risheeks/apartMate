var net = require('net');
var sockets = [];

var svr = net.createServer(function(sock) {
    console.log('Connected: ' + sock.remoteAddress + ':' + sock.remotePort);
    sockets.push(sock);

    sock.write('Welcome to the server!\n');

    sock.on('data', function(data) {
        for (var i=0; i<sockets.length ; i++) {
            if (sockets[i] != sock) {
                if (sockets[i]) {
                    sockets[i].write(data);
                }
            }
        }
    });

    sock.on('end', function() {
        console.log('Disconnected: ' + sock.remoteAddress + ':' + sock.remotePort);
        var idx = sockets.indexOf(sock);
        if (idx != -1) {
            delete sockets[idx];
        }
    });
});


