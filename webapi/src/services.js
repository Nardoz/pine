var cassandra = require('./services/cassandra');
console.log("cassandra DB from services: " + cassandra.DB);
exports.services = {
	DB : cassandra.DB
}