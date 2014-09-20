function get_user_or_tweet(text) { 
	if (text.match(/^(\w+|_*)$/) && text.length <= 15) { 
		return {screen_name: text};
	} else { 
		var match = text.match(/^https:\/\/twitter\.com\/(.+?)\/status\/(.+)$/)
		if (match) { 
			return {screen_name: match[1], tweet_id: match[2]};
		}
	} 

  return false;
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
  this.use('Mustache', 'ms');
  
  this.get('/', function() {
    this.title = 'Hello!'
    this.name = "eze"
    this.partial('templates/index.ms');
  });

  this.get('/:screen_name(\\/)?([0-9]+)?', function() {

    this.screenName = this.params.screen_name;
    this.tweetId = this.params.splat[1];
    
    var pine = new Pine();
    var that = this;

    this.partial('templates/charts.ms').then(function() {
      this.load('data.json').then(function(data) {

        pine.renderCharts(data);

        var avatars = data.data.map(function(item) {
          return item.people;
        });

        that.load('templates/avatars.ms').then(function(tpl) {
          $('#pics').append(Mustache.to_html(tpl, { avatars: avatars }));
        });

      });
      
    });
  });
	
	this.post('#/search', function(context) {
		var params = get_user_or_tweet(this.params.query);

    if(params && params.screen_name !== undefined && params.tweet_id !== undefined) {
      this.redirect('#/' + params.screen_name + '/' + params.tweet_id);
    } 
    else if(params && params.screen_name !== undefined) {
      this.redirect('#/' + params.screen_name);
    } 
    else {
      this.redirect('#/');
    }
	});
});

$(function() {
  app.run()
});
