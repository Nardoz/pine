var services = require('../services.js').services;

exports.tweetController = new TweetController();

function TweetController(){};

TweetController.prototype.getById = function(req, res){
	console.log("services from controller: " + this.services);
	services.DB.getTweetByUserId(req.params.uid, req.params.tid, function(err, data){
		if(err){
			res.send(err);	
		} else {
			res.send("ok: " + data.rowCount);	
		}	
	});
};
