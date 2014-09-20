function get_user_or_tweet(text) { 
	if (text.match(/^(\w+|_*)$/) && text.length <= 15) { 
		return {screen_name: text};
	} else { 
		var match = text.match(/^https:\/\/twitter\.com\/(.+?)\/status\/(.+)$/)
		if (match) { 
			return {screen_name: match[1], tweet_id: match[2]};
		}
	}
}

var app = $.sammy(function() {
	 this.debug = true;
	// include the plugin and alias mustache() to ms()
	this.use('Mustache', 'ms');

	this.get('/', function(context) {
		this.load('templates/index.ms').then(function(partial) { 
			context.partials = {contents:partial};
			// render the template and pass it through mustache
			context.partial('templates/template.ms');
		});
	});

	this.post('#/search', function(context) {
		var params = get_user_or_tweet(this.params.query);
		console.log(params);
		this.load('templates/graph.ms').then(function(partial) { 
			context.partials = {contents:partial};
			// render the template and pass it through mustache
			context.partial('templates/template.ms');
		});
	});
	this.post('#/search', function(context) {
		var params = get_user_or_tweet(this.params.query);
		console.log(params);
		this.load('templates/graph.ms').then(function(partial) { 
			context.partials = {contents:partial};
			// render the template and pass it through mustache
			context.partial('templates/template.ms');
		});
	});


});

$(function() {
	app.run()
});
