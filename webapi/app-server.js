var express = require('express'),
	app = express(),
	port = 8001,
	tweetController = require('./src/controllers/tweet').tweetController;

app.get('/:uid', tweetController.getByUserId);

app.get('/:uid/:tid', tweetController.getById);

console.log('Starting server on port: ' + port);
app.listen(port);