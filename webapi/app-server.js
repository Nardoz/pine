var express = require('express'),
  	app = express(),
    config = require('./config'),
  	tweetController = require('./src/controllers/tweet');

app.use(express.static('../ui'));

app.get('/api/:screenName', tweetController.getByScreenName);
//app.get('/api/:screenName/:tweetId', tweetController.getByTweetId);

console.log('Starting API server on port: ' + config.app.port);

app.listen(config.app.port);