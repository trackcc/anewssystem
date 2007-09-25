
var Example = {
    init : function(){

        // basic record
        var Skill = Ext.data.Record.create([
            {name: 'id',          type: 'int'},
            {name: 'name',        type: 'string'},
            {name: 'description', type: 'string'},
            {name: 'rating',      type: 'int'}
        ]);

        // tree setup
        var skillsTreeLoader = new Ext.tree.TreeLoader({
            dataUrl : 'ddtree.json'
        });
/*
        skillsTreeLoader.on('beforeload', function(treeLoader, node) {
            this.baseParams.level = node.attributes.level;
        });
*/
        var skillsTree = new Ext.tree.TreePanel('tree-div', {
            animate         : true,
            loader          : skillsTreeLoader,
            containerScroll : true,
            rootVisible     : true,
            enableDD        : true,
            ddGroup         : 'TreeDD'/*,
            dropConfig: {
                appendOnly : true
            }*/
        });
        skillsTree.on('nodedragover', function(e) {
            console.error("tree");
        });
        skillsTree.on('beforenodedrop', function(e){
            console.error(e);
/*
            var s = e.data.selections, r = [];
            for(var i = 0, len = s.length; i < len; i++){
                var ticket = s[i].data.ticket; // s[i] is a Record from the grid
                if(!watchList.findChild('ticket', ticket)){ // <-- filter duplicates
                    r.push(new xt.TreeNode({ // build array of TreeNodes to add
                        allowDrop:false,
                        text: 'Ticket #' + ticket,
                        ticket: ticket,
                        action: 'view',
                        qtip: String.format('<b>{0}</b><br />{1}', s[i].data.summary, s[i].data.excerpt)
                    }));
                }
            }
            e.dropNode = r;  // return the new nodes to the Tree DD
            e.cancel = r.length < 1; // cancel if all nodes were duplicates
*/
        });

        // Set the root node
        var skillsTreeRoot = new Ext.tree.AsyncTreeNode({
            text      : 'Skills',
            draggable : true,
            id        : 'root'
        });
/*
        skillsTreeRoot.attributes.level = 'root';
        skillsTreeRoot.attributes.selectable = true;
        skillsTreeRoot.attributes.description = '';
*/
        skillsTree.setRootNode(skillsTreeRoot);

        // grid setup
        var skillsColumnModel = new Ext.grid.ColumnModel([{
            header    : "Skill name",
            dataIndex : 'name',
            width     : 160
        },{
            header    : "Rating",
            dataIndex : 'rating',
            width     : 100
        }]);
        skillsColumnModel.defaultSortable = true;

        var skillsDataStore = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url : 'ddtree2.json'
            }),
            reader: new Ext.data.JsonReader({
                root : 'result',
                id   : 'id'
            }, Skill)
        });
        var ds2 = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url : 'ddtree2.json'
            }),
            reader: new Ext.data.JsonReader({
                root : 'result',
                id   : 'id'
            }, Skill)
        });

        var skillsGrid = new Ext.grid.Grid('grid-div', {
            ds             : skillsDataStore,
            cm             : skillsColumnModel,
            enableDragDrop : true
        });
        var grid2 = new Ext.grid.Grid('grid-div2', {
            ds             : ds2,
            cm             : skillsColumnModel,
            enableDragDrop : true
        });
/*
        var dropTree = new Ext.dd.DropTarget(skillsTree.el, {
              ddGroup : 'TreeDD',

              notifyOver: function(dd, e, data) {
                  console.debug(" - tree");
                  return true;
              },

              notifyDrop : function(dd, e, data){
                  console.debug(" - tree");
              }
        });
*/
        grid2.on("dragover", function() {
            console.debug("dragover");
        });

        var dropGrid = new Ext.dd.DropTarget(skillsGrid.container, {
              ddGroup : 'TreeDD',
              notifyOver: function(dd, e, data) {
                  console.error(" -- grid");
                  return true;
              },
              notifyDrop : function(dd, e, data){
                  console.error(" -- grid");
                  return true;
                    //i want to know the id of the tree node ive dropped it on.
              }
        });

        var dropGrid2 = new Ext.dd.DropTarget(grid2.container, {
              ddGroup : 'TreeDD',
              notifyOver: function(dd, e, data) {
                  console.error(" -- -- grid2");
                  return true;
              },
              notifyDrop : function(dd, e, data){
                  console.error(" -- -- grid2");
                    //i want to know the id of the tree node ive dropped it on.
              }
        });



    var layout = Ext.BorderLayout.create({
      center: {
        margins : {left:1,top:1,right:1,bottom:1},
        panels  : [new Ext.GridPanel(skillsGrid)]
      }, south : {
        margins : {left:2,top:2,right:2,bottom:2},
        panels  : [new Ext.GridPanel(grid2)]
      }
    }, 'grid-panel');



        // Render the tree, and expand the root note
        //skillsTree.render();
        //skillsTreeRoot.expand();
        // render it
        skillsDataStore.load();
        skillsGrid.render();
        // render it
        grid2.render();

    }
};

Ext.onReady(Example.init, Example);
