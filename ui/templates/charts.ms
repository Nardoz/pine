

{{#input}}
  <form action="#/search" method="post">
    <input type="text" name="query" value="{{input}}">
    <input type="submit" value="Enviar">
  </form>
{{/input}}
{{^input}}
  <h1>
    {{screenName}}
  <h1>

  {{#tweetId}}
     <h5>Tweet ID: {{tweetId}}</h5>
  {{/tweetId}}
{{/input}}

<div id="chart"></div>
<div id="pics"></div>