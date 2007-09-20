// vim: ts=2:sw=2:nu:fdc=4:nospell
/**
  * Ext.ux.InfoPanel Extension Class
	*
	* @author  Ing. Jozef Sakalos
	* @version $Id: Ext.ux.InfoPanel.GoogleSearch.js 65 2007-06-23 22:30:06Z jozo $
  *
  * @class Ext.ux.InfoPanel
  * @extends Ext.ux.InfoPanel
  * @constructor
	* @cfg {String} searchBtnText text to display on search button (defaults to 'Search')
	* @cfg {String} searchBtnIcon icon path to display on search button (defaults to null)
	* @cfg {Integer} searchTextSize search text size (defaults to 25)
	* @cfg {String/HTMLElement/Element} searchResultIframe iframe to display search results in (defaults to null)
	*/
Ext.ux.InfoPanel.GoogleSearch = function(el, config, content) {

	if(Ext.isGecko) {
		this.autoScroll = true;
	}

	// call parent constructor
	Ext.ux.InfoPanel.GoogleSearch.superclass.constructor.call(this, el, config);

	// create nicer Ext form
	var gsForm = this.body.select('form').item(0);

	// beautify search text input
	var gsText = gsForm.select('input[type=text]').item(0);
	gsText.addClass('x-form-text x-form-field');
	gsText.dom.size = this.searchTextSize;
	gsText.on('click', function(e) {e.stopEvent();});

	// remove original google button
	this.body.select('input[type=submit]').remove();

	// create new nicer button
	this.searchButton = new Ext.Button(gsForm, {
		text: this.searchBtnText
		, icon: this.searchBtnIcon
		, cls: this.searchBtnIcon ? 'x-btn-text-icon' : null
		, type: 'submit'
		, scope: this
		, handler: function() {

			if(this.searchResultIframe) {
				// disable submit
				gsForm.dom.onsubmit = function() { return false };

				// create google search URL
				var inputs = gsForm.select('input');
				var getPars = [];
				inputs.each(function(el) {
					if('radio' === el.dom.type && !el.dom.checked) {
						return;
					}
					getPars.push(el.dom.name + '=' + encodeURIComponent(el.dom.value));
				});
				var gsURL = gsForm.dom.action + '?' + getPars.join('&');

				// set iframe src attribute
				Ext.get(this.searchResultIframe).dom.src = gsURL;
			}
			else {
				gsForm.dom.submit();
			}

		} // end of handler
	});
};

// extend
Ext.extend(Ext.ux.InfoPanel.GoogleSearch, Ext.ux.InfoPanel, {
	// defaults
	searchBtnText: 'Search'
	, searchTextSize: 25
	, searchBtnIcon: null
	, searchResultIframe: null

});

// end of file
