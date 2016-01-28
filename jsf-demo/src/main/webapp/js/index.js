'use strict';
/**
 * Anonymous function, to use as a wrapper
 */


  // GLOBAL VARIABLES
  var taClient = null;
  var lastProfile = 'basic';
  var MIN_BAR_SLIDE_PERIOD = 500;

  /**
   * Smooth scroll to any DOM element
   * @param  {String} DOM element
   */
  function jumpTo(h,animate) {
	if (animate === undefined || animate) {
		$('html, body').animate({
			scrollTop: $(h).offset().top
		}, 500);
    }
    else {
    	$(h)[0].scrollIntoView();
    }
  }

  /**
   * Wrapper around the API
   */
  function loadTradeoffAnalytics(profile, themeName, callback, errCallback) {
    taClient = new TA.TradeoffAnalytics({
      dilemmaServiceUrl: 'demo/dilemmas',
      analyticsEventsUrl: 'demo/events',
      metadata: {
    	'app-call-context' : 'tradeoff-analytics-java',
     	'app-version' : '2015-09-17'
      },
      customCssUrl: 'https://ta-cdn.mybluemix.net/v1/modmt/styles/' + themeName + '.css',
      profile: profile
    }, 'taWidgetContainer');

    taClient.subscribe('afterError', errCallback);
    
    var topics = [ 'started', 'problemChanged', 'destroyed', 'doneClicked', 'optionClicked', 'X_finalDecisionChanged',
        'X_favoritesChanged', 'X_selectionChanged', 'X_filterChanged'/*, 'X_optionHovered'*/ ];
    topics.forEach(function(t){
      taClient.subscribe(t, function (e){
        console.log(JSON.stringify(e));
      });
    });
    
    taClient.start(callback);
  }

  function showTradeoffAnalytcsWidget(problem) {
    taClient.show(problem, onResultsReady, {
    	"dataset-name" : problem.subject
    });
  }

  function destroyTradeoffAnalytcsWidget(callback) {
    taClient.destroy(callback);
  }

  /**
   * Resizes the widget based on the parent DOM element size
   */
  function resizeToParent() {
    taClient.resize();
  }

  function onPageReady() {
  }

  function onPageLoad() {
    loadTradeoffAnalytics('basic', 'watson', onPageReady, onError);
  }

  function onAnalyzeClick() {
	    $.getJSON('problem', function(data) {
	    	showTradeoffAnalytcsWidget(data);
	      });
  }

  function onResultsReady() {
    resizeToParent();
    onPageReady();
    jumpTo('#taWidgetContainer');
  }

  function onError(error) {
	  alert(error)
  }
  window.onerror = onError;
  $(document).ready(onPageLoad);
  $('.analyze').click(onAnalyzeClick);
;
