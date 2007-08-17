
var treecombo = function(){
  // return a public interface
  return {
    init : function(){
      var el = Ext.get('category_field').dom;
      var config = {
        title: '新闻分类',
        rootId: 0,
        height:200,
        dataTag: 'newdistrict',
        treeHeight: 150,
        beforeSelect: function(){}
      };
      var object = new Ext.form.TreeField({
        id: el.id,
        name : el.id,
        allowBlank: false,
        width: 200,
        treeConfig: config
      });
      //if(不是EditorGrid && 不是Form) object.applyTo(el.id);
      object.applyTo(el.id);
    }
  }
}();

Ext.onReady(treecombo.init, treecombo, true);
