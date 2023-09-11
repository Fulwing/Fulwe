$(function(){
	
	/* Morris Area Chart */
	
	window.mA = Morris.Area({
	    element: 'morrisArea',
	    data: [
	        { y: '2013', a: 80, b: 60 },
	        { y: '2014', a: 240, b: 100 },
	        { y: '2015', a: 100, b: 120 },
	        { y: '2016', a: 120, b: 140 },
	        { y: '2017', a: 80, b: 100 },
	        { y: '2018', a: 100, b: 120 },
	        { y: '2019', a: 60, b: 80 },
	    ],
	    xkey: 'y',
	    ykeys: ['a', 'b'],
	    labels: ['Sales', 'Views'],
	    lineColors: ['#ca970b', '#050521'],
	    lineWidth: 0,
     	fillOpacity: 0.7,
	    gridTextSize: 10,
	    hideHover: 'false',
	    resize: true,
		redraw: true
	});
	$(window).on("resize", function(){
		mA.redraw();
		mL.redraw();
	});
	
	/* Morris Donut Chart */
	
	window.mL = Morris.Donut({
		element: 'top-sale-product',
		data: [
			{label: "Electrical", value: 77},
			{label: "Clothes", value: 50},
			{label: "Health", value: 23}
		],
		backgroundColor: '#ffffff',
		labelColor: '#ca970b',
			colors: [
				'#ca970b',
				'#050521',
				'#ca970b'
			],
		formatter: function (x) { return x + "%"}	  
	});	
	$(window).on("resize", function(){
		mA.redraw();
		mL.redraw();
	});	
	
	/* Morris Line Chart */
	
	window.mL = Morris.Line({
	    element: 'morrisLine',
	    data: [
	        { y: '2015', a: 100, b: 30},
	        { y: '2016', a: 20,  b: 60},
	        { y: '2017', a: 90,  b: 120},
	        { y: '2018', a: 50,  b: 80},
	        { y: '2019', a: 120,  b: 150},
	    ],
	    xkey: 'y',
	    ykeys: ['a', 'b'],
	    labels: ['Sales', 'Views'],
	    lineColors: ['#ca970b', '#050521'],
	    lineWidth: 1,
	    gridTextSize: 10,
	    hideHover: 'auto',
	    resize: true,
		redraw: true
	});
	$(window).on("resize", function(){
		mA.redraw();
		mL.redraw();
	});	

});
