/* ************************************************************
:: Template Name: Nookx - Multivendor Marketplace HTML Template.
:: Template Author Dreamguys
:: Version: 1.0.0
*************************************************************/

(function ($) {
    "use strict";

	//sidebar open and close

	$(document).on('click', '#open_navSidebar', function() {
		$('#offcanvas_menu').css('width', '250px');
	});

	$(document).on('click', '#close_navSidebar', function() {
		$('#offcanvas_menu').css('width', '0px');
	});

	// jquery ui range - price range
  
	var $priceFrom = $('.price-ranges .from'),
	$priceTo = $('.price-ranges .to');

	if ($('.price-range').length > 0) {
		$(".price-range").slider({
			range: true,
			min: 0,
			max: 500,
			values: [30, 300],
			slide: function (event, ui) {
				$priceFrom.text("$" + ui.values[0]);
				$priceTo.text("$" + ui.values[1]);
			}
		});
	}

	//Single Product Slider
  
	if ($('.single-pdt-slider').length > 0) {
		$('.single-pdt-slider').slick({
			slidesToShow: 1,
			slidesToScroll: 1,
			arrows: false,
			fade: false,
			adaptiveHeight: true,
			infinite: false,
			useTransform: true,
			speed: 400,
			cssEase: 'cubic-bezier(0.77, 0, 0.18, 1)',
			asNavFor: '.slider-thumb'
		});
		$('.slider-thumb').slick({
			slidesToShow: 4,
			slidesToScroll: 1,
			asNavFor: '.single-pdt-slider',
			dots: false,
			focusOnSelect: true,
			prevArrow: $(".nav-left"),
			nextArrow: $(".nav-right"),
			infinite: false,
			responsive: [{
				breakpoint: 1024,
				settings: {
					slidesToShow: 4,
					slidesToScroll: 4,
				}
			}, {
				breakpoint: 640,
				settings: {
					slidesToShow: 4,
					slidesToScroll: 4,
				}
			}, {
				breakpoint: 420,
				settings: {
					slidesToShow: 3,
					slidesToScroll: 3,
				}
			}]
		});
	}


	//Single Product Slider
  
	if ($('.single-pdt-vertical-slider').length > 0) {
		$('.single-pdt-vertical-slider').slick({
			slidesToShow: 1,
			slidesToScroll: 1,
			arrows: false,
			fade: false,
			vertical: true,
			adaptiveHeight: false,
			infinite: false,
			useTransform: true,
			speed: 400,
			cssEase: 'cubic-bezier(0.77, 0, 0.18, 1)',
			asNavFor: '.vertical-thumb'
		});
		$('.vertical-thumb').slick({
			slidesToShow: 4,
			slidesToScroll: 1,
			asNavFor: '.single-pdt-vertical-slider',
			dots: false,
			vertical: true,
			focusOnSelect: true,
			prevArrow: $(".nav-left"),
			nextArrow: $(".nav-right"),
			infinite: false
		});
	}


	//Slick Carousel Controllers
	if ($('.testimonial-reel').length > 0) {
		$('.testimonial-reel').slick({
			centerMode: true,
			centerPadding: "40px",			
			slidesToShow: 3,
			dots: true,
			infinite: true,
  			arrows: false,
  			lazyLoad: "ondemand",
			responsive: [{
				breakpoint: 1024,
				settings: {
					slidesToShow: 3,
					centerPadding: "0px",	
					centerMode: true,
				}
			}, {
				breakpoint: 640,
				settings: {
					slidesToShow: 1
				}
			}, {
				breakpoint: 420,
				settings: {
					slidesToShow: 1,
					centerPadding: "0px",	
				}
			}]
		});	
	}


	// Text Editor
	
	if ($('.text-editor').length > 0) {
		$('.text-editor').summernote({
			height: 140, // set editor height
			minHeight: null, // set minimum height of editor
			maxHeight: null, // set maximum height of editor
			focus: false // set focus to editable area after initializing summernote
		});
	}

    // Countdown
    if ($('#countdown').length > 0) { 
        $('#countdown').countdown({
            render: function(data) {
                if (data.years >= 1) {
                    var $days = (data.years*365)+data.days;
                } else {
                    var $days = data.days;
                }
                $(this.el).html(
                    '<div class="day bg-black-opacity p-3 mr-0 mr-sm-3 mr-md-2 font-lg font-extra-bold border-theme text-theme d-inline-block">' + this.leadingZeros($days) + ' <h3>Days</h3></div>'+
                    '<div class="hour bg-black-opacity p-3 mt-4 mt-sm-0 mt-md-0 mr-0 mr-sm-3 mr-md-2 font-lg font-extra-bold border-theme text-theme d-inline-block">' + this.leadingZeros(data.hours, 2) + ' <h3>Hours</h3></div>'+
                    '<div class="min bg-black-opacity p-3 mt-4 mt-sm-0 mt-md-0 mr-0 mr-md-2 font-lg font-extra-bold border-theme text-theme d-inline-block">' + this.leadingZeros(data.min, 2) + ' <h3>Minutes</h3></div>'+
                    '<div class="sec bg-black-opacity p-3 mt-4 mt-md-0 font-lg font-extra-bold border-theme text-theme d-inline-block">' + this.leadingZeros(data.sec, 2) + ' <h3>Seconds</</h3></div>'
                );
            }
        });
    }
    
	// Open navbarSide when button is clicked
	$('#navbarSideButton').on('click', function() {
		$('#navbarSide').addClass('reveal');
		$('.overlay').show();
	});

	// Close navbarSide when the outside of menu is clicked
	$('.overlay').on('click', function(){
		$('#navbarSide').removeClass('reveal');
		$('.overlay').hide();
	});

	if ($('#colorPanel').length > 0) { 
		$('#colorPanel').ColorPanel({
		    styleSheet: '#cpswitch'
		    , animateContainer: '.home-wrapper'
		    , colors: {
		        '#ca970b': 'assets/css/style.css'
		        , '#0075ad': 'assets/css/blue.css'
		        , '#db3570': 'assets/css/pink.css'
		        , '#1fc0a0': 'assets/css/green.css'
		        , '#ff9801': 'assets/css/orange.css'
		       
		    , }
		});
	}

})(jQuery);