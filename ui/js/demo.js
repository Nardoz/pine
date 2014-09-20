

var demoChart = function() {

  var data = {mentions:[],rts:[]};

  $.getJSON('data.json', function(response){

    //Nest real values
    var nested = {};
    $.each(response.data,function(i,e){
      nested[e.ts] = e;
    });

    //Interval
    var interval;
    switch(response.interval){
      case '1m':
        interval = 60;
      default:
        interval = 60;
    }

    //Iterate and complete zero values
    for (var s = response.start_point; s < response.end_point; s+=interval) {
      if(typeof(nested[s])!="undefined"){
        data.rts.push(nested[s].rt_count);
        data.mentions.push(nested[s].reply_count);
      } else {
        data.rts.push(0);
        data.mentions.push(0);
      }
    };

    renderChart(data, response.start_point, interval);

  });

};

var renderChart = function (data, start_point, interval){



  $('#chart').highcharts({
    credits: false,
    legend: {
      verticalAlign: 'top'
    },
    chart: {
      type: 'spline'
    },
    title: {
      text: ''
    },
    xAxis: {
      type: 'datetime',
      labels: {
        overflow: 'justify'
      }
    },
    yAxis: {
      title: {
        text: 'Tweets'
      },
      min: 0,
      minorGridLineWidth: 0,
      gridLineWidth: 0,
      alternateGridColor: null,
/*      plotBands: [{ // Light air
        from: 0.3,
        to: 1.5,
        color: 'rgba(68, 170, 213, 0.1)',
        label: {
          text: 'Light air',
          style: {
            color: '#606060'
          }
        }
      }, { // Light breeze
        from: 1.5,
        to: 3.3,
        color: 'rgba(0, 0, 0, 0)',
        label: {
          text: 'Light breeze',
          style: {
            color: '#606060'
          }
        }
      }, { // Gentle breeze
        from: 3.3,
        to: 5.5,
        color: 'rgba(68, 170, 213, 0.1)',
        label: {
          text: 'Gentle breeze',
          style: {
            color: '#606060'
          }
        }
      }, { // Moderate breeze
        from: 5.5,
        to: 8,
        color: 'rgba(0, 0, 0, 0)',
        label: {
          text: 'Moderate breeze',
          style: {
            color: '#606060'
          }
        }
      }, { // Fresh breeze
        from: 8,
        to: 11,
        color: 'rgba(68, 170, 213, 0.1)',
        label: {
          text: 'Fresh breeze',
          style: {
            color: '#606060'
          }
        }
      }, { // Strong breeze
        from: 11,
        to: 14,
        color: 'rgba(0, 0, 0, 0)',
        label: {
          text: 'Strong breeze',
          style: {
            color: '#606060'
          }
        }
      }, { // High wind
        from: 14,
        to: 15,
        color: 'rgba(68, 170, 213, 0.1)',
        label: {
          text: 'High wind',
          style: {
            color: '#606060'
          }
        }
      }]*/
    },
    tooltip: {
      valueSuffix: ' tweets'
    },
    plotOptions: {
      spline: {
        lineWidth: 4,
        states: {
          hover: {
            lineWidth: 5
          }
        },
        marker: {
          enabled: false
        },
        pointInterval: interval*1000, // one hour
        pointStart: start_point
      }
    },
    series: [{
      name: 'Mentions',
      data: data.mentions

    }, {
      name: 'Retweets',
      data: data.rts
    }],
    navigation: {
      menuItemStyle: {
        fontSize: '10px'
      }
    }
  });



};
