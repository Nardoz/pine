function TweetController(){};

TweetController.prototype.getById = function(req, res){
	console.log("id: " + req.params.id);
	res.send("id from TweetController: " + req.params.id);
};

exports.tweetController = new TweetController();
