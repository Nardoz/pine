function TweetController(){};

TweetController.prototype.getById = function(req, res){
	console.log("id: " + req.params.id);

var mock = {
"status":"ok",
"start_point": 1409529600,
"end_point": 1409530200,
"interval": "1m",
"data": [
{
"ts": 1409529600,
"rt_count": 99,
"reply_count": 25,
"people": [
{"screen_name":"ezegolub", "pic":"https://pbs.twimg.com/profile_images/1338920419/Photo_on_2010-09-22_at_09.23_bigger.jpg", "name":"Ezequiel Golub"},
{"screen_name":"alan_reid", "pic":"https://pbs.twimg.com/profile_images/417369059370360832/k182Fy0w_bigger.png", "name":"Alan Reid"},
{"screen_name":"arjones", "pic":"https://pbs.twimg.com/profile_images/378800000640809195/7dd28f195e5a34d8332938ea8256be99_bigger.jpeg","name":"Gustavo Arjones"}
]
},
{
"ts": 1409529660,
"rt_count": 65,
"reply_count": 35,
"people": [
{"screen_name":"alan_reid", "pic":"https://pbs.twimg.com/profile_images/417369059370360832/k182Fy0w_bigger.png", "name":"Alan Reid"},
{"screen_name":"arjones", "pic":"https://pbs.twimg.com/profile_images/378800000640809195/7dd28f195e5a34d8332938ea8256be99_bigger.jpeg","name":"Gustavo Arjones"},
{"screen_name": "palamago", "pic":"https://pbs.twimg.com/profile_images/481091836220411905/zf4CJUlk_bigger.jpeg", "name":"El desaparecido"}
]
},
{
"ts": 1409529780,
"rt_count": 75,
"reply_count": 42,
"people": [
{"screen_name":"arjones", "pic":"https://pbs.twimg.com/profile_images/378800000640809195/7dd28f195e5a34d8332938ea8256be99_bigger.jpeg","name":"Gustavo Arjones"},
{"screen_name": "palamago", "pic":"https://pbs.twimg.com/profile_images/481091836220411905/zf4CJUlk_bigger.jpeg", "name":"El desaparecido"},
{"screen_name":"ezegolub", "pic":"https://pbs.twimg.com/profile_images/1338920419/Photo_on_2010-09-22_at_09.23_bigger.jpg", "name":"Ezequiel Golub"}
]
},
{
"ts": 1409529900,
"rt_count": 35,
"reply_count": 45,
"people": [
{"screen_name": "palamago", "pic":"https://pbs.twimg.com/profile_images/481091836220411905/zf4CJUlk_bigger.jpeg", "name":"El desaparecido"},
{"screen_name":"ezegolub", "pic":"https://pbs.twimg.com/profile_images/1338920419/Photo_on_2010-09-22_at_09.23_bigger.jpg", "name":"Ezequiel Golub"},
{"screen_name":"arjones", "pic":"https://pbs.twimg.com/profile_images/378800000640809195/7dd28f195e5a34d8332938ea8256be99_bigger.jpeg","name":"Gustavo Arjones"}
]
},
{
"ts": 1409530020,
"rt_count": 45,
"reply_count": 35,
"people": [
{"screen_name": "palamago", "pic":"https://pbs.twimg.com/profile_images/481091836220411905/zf4CJUlk_bigger.jpeg", "name":"El desaparecido"},
{"screen_name":"alan_reid", "pic":"https://pbs.twimg.com/profile_images/417369059370360832/k182Fy0w_bigger.png", "name":"Alan Reid"},
{"screen_name":"arjones", "pic":"https://pbs.twimg.com/profile_images/378800000640809195/7dd28f195e5a34d8332938ea8256be99_bigger.jpeg","name":"Gustavo Arjones"}
]
},
{
"ts": 1409530140,
"rt_count": 55,
"reply_count": 45,
"people": [
{"screen_name":"ezegolub", "pic":"https://pbs.twimg.com/profile_images/1338920419/Photo_on_2010-09-22_at_09.23_bigger.jpg", "name":"Ezequiel Golub"},
{"screen_name":"alan_reid", "pic":"https://pbs.twimg.com/profile_images/417369059370360832/k182Fy0w_bigger.png", "name":"Alan Reid"},
{"screen_name":"arjones", "pic":"https://pbs.twimg.com/profile_images/378800000640809195/7dd28f195e5a34d8332938ea8256be99_bigger.jpeg","name":"Gustavo Arjones"}
]
}
 
]
}

	res.send(mock);
};

exports.tweetController = new TweetController();
