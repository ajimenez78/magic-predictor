#!/bin/env node
//  OpenShift sample Node application
var express = require('express');
var app = express();
var server = require('http').Server(app);
var io = require('socket.io')(server);


app.disable('x-powered-by');

app.use(function (req, res, next) {
        res.setHeader('Access-Control-Allow-Origin', "http://"+req.headers.host+':8000');

        res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
        res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
        next();
    }
);

/**
 *  Define the sample application.
 */
var magicPrediction = {

    //  Set the environment variables we need.
    ip : process.env.OPENSHIFT_NODEJS_IP || "127.0.0.1",
    port : process.env.OPENSHIFT_NODEJS_PORT || 8000,
    receiver : null,
};

server.listen(magicPrediction.port, magicPrediction.ip);

app.get('/', function(req, res) {
	res.sendfile(__dirname + '/index.html');
});

app.get('/socket.io.js', function(req, res) {
	res.sendfile(__dirname + '/socket.io.js');
});

app.get('/receiver', function(req, res) {
	res.sendfile(__dirname + '/receiver.html');
});

app.get('/sender', function(req, res) {
	res.sendfile(__dirname + '/sender.html');
});

io.on('connection', function(socket){
	console.log('Connection received');

	socket.on('start listening', function() {
	    magicPrediction.receiver = socket;
	    console.log('Start listening received');
	});

	socket.on('message', function(message) {
		if (magicPrediction.receiver) {
		    magicPrediction.receiver.emit('message', message);
		    console.log('We have receiver');
		}
		console.log('Message received: %s', message );
	});

	socket.on('stop listening', function() {
		if (socket == magicPrediction.receiver) {
		    magicPrediction.receiver = null;
		}
		console.log('Stop listening received');
	});
});
