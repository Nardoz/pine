var app = $.sammy(function() {
    // include the plugin and alias mustache() to ms()
    this.use('Mustache', 'ms');
    
    this.get('/', function() {
      // set local vars
      this.title = 'Hello!'
      this.name = "eze"
      // render the template and pass it through mustache
      this.partial('templates/index.ms');
    });
  });

  $(function() {
    app.run()
  });