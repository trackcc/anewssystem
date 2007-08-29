Ext.namespace('Tipos');
Tipos.DD = {
  init: function() {
    var dropEls = Ext.get('container').query('.slot');
    //结合实际makeup 这里循环了两次
    for(var i = 0; i < dropEls.length; i++) {
      new Ext.dd.DDTarget(dropEls[i]);
    }

    var dragEls = Ext.get('container').query('.block');
    for(var i = 0; i < dragEls.length; i++) {
      new Tipos.DDList(dragEls[i]);
    }
/*
    // Add a new slot
    Ext.get('add-slot').on('click', function(e) {
      e.preventDefault();
      // Insert the new block in the first slot.
      var new_slot = Ext.DomHelper.insertFirst('container', {
        tag:'div',
        cls:'slot'
      }, true);
      new Ext.dd.DDTarget(new_slot);
    });
*/
    Ext.select('.add-block').on('click', function(e) {
        e.preventDefault();
        var id = e.target.id;
        var name = e.target.innerHTML;
        var newId = id + "_" + new Date().getTime();
        var newBlock = Ext.DomHelper.insertFirst("slot1", {
          tag: "div",
          cls: "block"
        }, true);
        newBlock.dom.id = "block" + newId;

        newBlock.dom.innerHTML = "<div class='operation'>[<a href='#' class='add-line' id='addLine" + newId + "' onclick='addLine(event)'>+</a>][<a href='#' class='delete-line' id='deleteLine" + newId + "' onclick='deleteLine(event)'>-</a>][<a href='#' class='delete-block' id='deleteBlock" + newId + "' onclick='deleteBlock(event)'>x</a>]</div>\r\n" +
            "<div class='title'>" + name + "</div>\r\n" +
            "<div class='content'>\r\n" +
            "<p>&nbsp;新闻</p>\r\n" +
            "<p>&nbsp;新闻</p>\r\n" +
            "<p>&nbsp;新闻</p>\r\n" +
            "<p>&nbsp;新闻</p>\r\n" +
            "<p>&nbsp;新闻</p>\r\n" +
            "</div>\r\n" +
            "<div class='foot'>更多</div>";
        new Tipos.DDList(newBlock);
    });
    Ext.select(".add-line").on("click", addLine);
    Ext.select(".delete-line").on("click", deleteLine);
    Ext.select(".delete-block").on("click", deleteBlock);
  }
};
    function addLine(e) {
        var id = e.target.id;
        var contents = Ext.get("block" + id.substring(7)).query(".content");
        for(var i = 0; i < contents.length; i++) {
          var content = Ext.DomHelper.insertFirst(contents[i], {
            tag: "p"
          }, true);
          content.dom.innerHTML = "&nbsp;新闻"
        }
    }
    function deleteLine(e) {
        var id = e.target.id;
        var contents = Ext.get("block" + id.substring(10)).query(".content");
        //[0].query("p")[0];
        var item = Ext.get(contents[0]).query("p")[0];
        Ext.get(item).remove();
    }
    function deleteBlock(e) {
        var id = e.target.id;
        Ext.get("block" + id.substring(11)).remove();
    }

Tipos.DDList = function(id, sGroup, config) {
  Tipos.DDList.superclass.constructor.call(this, id, sGroup, config);

  // The proxy is slightly transparent
  Ext.get(this.getDragEl()).setStyle('opacity', 0.67);

  this.goingUp = false;
  this.lastY = 0;
};

Ext.extend(Tipos.DDList, Ext.dd.DDProxy, {
  startDrag: function(x, y) {
    // make the proxy look like the source element
    //得到这个被拖动的元素。因为我们不直接对这个元素拖放，而是通过代理proxy,所以把这个元素隐藏
    var dragEl = Ext.get(this.getDragEl());
    var clickEl = Ext.get(this.getEl());
    clickEl.setStyle('visibility', 'hidden');

    //dragEl.dom.innerHTML = clickEl.dom.innerHTML;
    //取出样式，组成字符串
    var padding = clickEl.getPadding('t') + 'px '
      + clickEl.getPadding('r') + 'px '
      + clickEl.getPadding('b') + 'px '
      + clickEl.getPadding('l') + 'px';

    dragEl.dom.innerHTML = '<div style="padding:' + padding + '">' + clickEl.dom.innerHTML + '</div>';
    //一取一应用
    dragEl.setStyle(clickEl.getStyles('background-color', 'color', 'padding'));
    dragEl.setStyle('border', '1px solid gray');
  },

  endDrag: function(e) {
    var srcEl = Ext.get(this.getEl());
    var proxy = Ext.get(this.getDragEl());

    // Hide the proxy and show the source element when finished with the animation
    //当动画完成后，隐藏proxy，显示源。
    var onComplete = function() {
      proxy.setStyle('visibility', 'hidden');
      srcEl.setStyle('visibility', '');
    };

    // Show the proxy element and animate it to the src element's location
    // shift()、easeOut实际上是“proxy”缩小到src的尺寸的动画（视觉上）。最后执行回调
    proxy.setStyle('visibility', '');
    proxy.shift({
      x: srcEl.getX(),
      y: srcEl.getY(),
      easing: 'easeOut',
      duration: .2,
      callback: onComplete,
      scope: this
    });
  },

  onDragDrop: function(e, id) {
    // DragDropMgr is a singleton that tracks the element interaction for all DragDrop items in the window.
    // DragDropMgr是一个单例，负责跟踪window内所有拖放元素间的相互影响
    // Generally, you will not call this class directly
    // 一般来说，你不会直接调用这个类,
    // but it does have helper methods that could be useful in your DragDrop implementations.
    // 但是它带有辅助性的方法，会在你拖放的实现中，发挥其用处。
    // *******关于DDM的属性和方法，还需更多的研究******

    var DDM = Ext.dd.DragDropMgr;
    var pt = e.getPoint();
    var region = DDM.getLocation(DDM.getDDById(id));

    if(e.browserEvent.type == 'mousedown') {
      // Check to see if we are over the source element's location.  We will
      // append to the bottom of the list once we are sure it was a drop in
      // the negative space (the area of the list without any list items)
      //判断我们是否在“源”元素所处的位置的上面。
      //如果我们在空白的地方（没有任何items列表的地方）“放下”，就在这个列表的底部新添加。
      if (!region.intersect(pt)) {
        var destEl = Ext.get(id);//目标元素
        var srcEl = Ext.get(this.getEl());//"源"元素
        var destDD = DDM.getDDById(id);//返回 drag drop对象

        if(destEl.is('div.block')) {
          if (this.goingUp) {
            srcEl.insertBefore(destEl);
          } else {
            srcEl.insertAfter(destEl);
          }
        } else {
          destEl.appendChild(srcEl);
        }

        destDD.isEmpty = false;//我在API中找不到isEmpty属性:(
          DDM.refreshCache();
        }
      }
    },

    onDrag: function(e) {
      // Keep track of the direction of the drag for use during onDragOver
      //为在onDragOver过程中，保持对拖动方向的跟踪
      var y = e.getPageY();

      if (y < this.lastY) {
        this.goingUp = true;
      } else if (y > this.lastY) {
        this.goingUp = false;
      }
      this.lastY = y;
    },

    onDragOver: function(e, id) {
      //乍一看，和上面的代码相似，但主要区别是，上面的情况是你的鼠标键还没松开，而这个是决定“放在这里”，鼠标键松开的。
      var DDM = Ext.dd.DragDropMgr;
      var srcEl = Ext.get(this.getEl());
      var destEl = Ext.get(id);

      // We are only concerned with list items, we ignore the dragover
      // notifications for the list.我们只关心列表items，忽略其他的通知列表的dragover
      if (destEl.is('div.block')) {
        if (this.goingUp) {
          // insert above上方插入
          srcEl.insertBefore(destEl);
        } else {
          // insert below下方插入
          srcEl.insertAfter(destEl);
        }

        DDM.refreshCache();
      } else if (destEl.is('div.slot')) {
        destEl.appendChild(srcEl);
        DDM.refreshCache();
      }
    }
});

Ext.onReady(Tipos.DD.init, Tipos.DD, true);