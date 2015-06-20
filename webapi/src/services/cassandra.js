var config = require('../../config'),
		cassandra = require('cassandra-driver'),
		client = new cassandra.Client(config.cassandra);


function Database() {

	client.connect(function(err){
		if(err){
			throw err;
		} else {
			console.log('Successfully connected to Cassandra');
		}
	});

};


Database.prototype.getRetweets = function(args, callback) {

	var column = 'screen_name';
	var param  = args.screenName;

	if(args && args.tweetId) {
		tweetId = 'tweet_id';
		param  = args.tweetId;
	} 

	client.execute('SELECT * from rts_tweet_stats where ' + column + ' = ? LIMIT 1000', [ param ], function(err, result) {

		if(err){
			callback(err, null);
		} else {

			var tweets = result.rows.map(function(item) {

				return {
					ts: parseInt(item.created_at.toString()),
					rt_count: item.count,
					reply_count: 0,
					people: []
				};

			});

			callback(null, tweets);
		}
	});
};


exports.Database = new Database();
