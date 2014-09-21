var cassandra = require('cassandra-driver'),
client = new cassandra.Client({contactPoints: ['192.168.10.77'], keyspace: "nardoz_pine"});


function DB(){
	client.connect(function(err){
		if(err){
			throw err;
		} else {
			console.log("Connection successfully");
			db2.getTweetByUserId("", "", function(){});
		}
	});

};

DB.prototype.getTweetByUserId = function(uid, tid, callback) {
	client.execute('SELECT * from tweet_stats where screen_name = ?',
		['cfkargentina'], function(err, result){

		if(err){
			callback(err, null);
		} else {
			debugger;
			console.log("rowCount: " + result.rows.length);
			callback(null, result.rows);
		}
	});
};

var db2 = new DB();
exports.DB = db2;


