

var Pine = function() {};

Pine.prototype.renderCharts = function(data) {
  demoChart(data);
};

Pine.prototype.renderPics = function(data) {
	
	var avatars = [];

	//Nest
	var nested = {};
	$.each(data.data,function(i,e){
		nested[e.ts] = e;
	});

	//Interval
	var interval;
	switch(data.interval){
	case '1m':
	  interval = 60;
	default:
	  interval = 60;
	}

	//Iterate and complete zero values
	for (var s = data.start_point; s < data.end_point; s+=interval) {
		if(typeof(nested[s])!="undefined"){
		  avatars.push(nested[s].people);
		} else {
		  avatars.push([]);
		}
	};

    return avatars;
};
