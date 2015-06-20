var db = require('../services.js').Database;


function TweetController() {};


TweetController.prototype.getByScreenName = function(req, res) {
	
	db.getRetweets({ screenName: req.params.screenName }, function(err, retweets) {

		var start = end = 0;

		retweets.forEach(function(item) {
			
			if(start === 0) {
				start = item.ts;
			}

			if(end < item.ts) {
				end = item.ts;
			}
		});

		res.send({
			status: 'ok',
			start_point: start,
			end_point: end,
			interval: '1m',
			data: retweets
		});
		
	});

}


module.exports = new TweetController();
