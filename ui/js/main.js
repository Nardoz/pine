var app = $.sammy('#main', function() {

  this.use('Mustache', 'ms');
  
  this.get('/', function() {
    this.title = 'Hello!'
    this.name = "eze"
    this.partial('templates/index.ms');
  });

  this.get('/:screen_name', function() {

    this.screenName = this.params.screen_name;
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
});

$(function() {
  app.run()
});