var express = require('express'),
	app = express(),
	port = 8001,
	tweetController = require('./src/controllers/tweet').tweetController;

app.get('/api/:uid', tweetController.getByUserId);

app.get('api/:uid/:tid', tweetController.getByTweetId);

app.use(express.static('../ui'));



console.log('Starting server on port: ' + port);
app.listen(port);