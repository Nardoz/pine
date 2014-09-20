var express = require('express'),
	app = express(),
	port = 8001,
	tweetController = require('./src/controllers/tweet').tweetController;

app.get('/hello', function(req, res){
	res.send("hello Nico!");
});

app.get('/tweet/:id', tweetController.getById);

console.log('Starting server on port: ' + port);
app.listen(port);