var Connection = require('cassandra-client').Connection,
	conn = new Connection({
		host: "127.0.0.1",
		port: 9160,
		keyspace: "nardoz_pine",
		cql_version: '2.0.0'
	});

function DB(){
	conn.connect(function(err){
		if(err){
			throw err;
		} else {
			console.log("Connection successfully");
		}
	});

};

DB.prototype.getTweetByUserId = function(uid, tid, callback){
	conn.execute('SELECT * from tweet_stats where screen_name = ?',
		['arjones'], function(err, rows){
		if(err){
			callback(err, null);
		} else {
			console.log("rowCount: " + rows.rowCount());
			callback(null, rows);
		}
	});
};

exports.DB = new DB();