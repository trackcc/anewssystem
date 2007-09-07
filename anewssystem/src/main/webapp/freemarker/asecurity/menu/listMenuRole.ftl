<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!--DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"-->
<html>
  <head>
    <#include "/include/meta.ftl">
    <title>菜单权限</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/admin.css" />
    <style type="text/css">
        .treeCheckBox {
            height: 14px;
            margin: 0px;
            padding: 0px;
            border: 1px;
            vertical-align: middle;
        }
        .dojoTreeNodeLabelTitle {
            font-size: 12px;
            font-weight: bold;
        }
    </style>
    <script src="${ctx}/js/admin.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/js/dojo/0.4.3/dojo.js"></script>
    <script type="text/javascript">
        dojo.require("dojo.lang.*");
        dojo.require("dojo.widget.*");
        dojo.require("dojo.widget.Tree");
        dojo.require("dojo.event.*");

        dojo.lang.extend(dojo.widget.TreeNode, {
            value:"",
            ischecked:"",
            type:""
        });

        dojo.hostenv.writeIncludes();

        var nodes = document.getElementsByName("itemCheckBox");

        function pushValue() {
            var values = [];
            for (var i = 0; i < nodes.length; i++) {
                if (nodes[i].checked) {
                    var value = nodes[i].value;
                    if (value) {
                        values.push(value);
                    }
                }
            }
            document.menu.itemselect.value = values.join(",");
        }

        function addCheckBox(message) {
            if (document.all) {
                addCheckBoxForIE(message);
            } else {
                addCheckBoxForFirefox(message);
            }
        }

        function addCheckBoxForFirefox(message) {
            var node = message.source;

            var type = (node.type == 1) ? 'radio' : ((node.type == 2) ? 'checkbox' : '');
            var value = node.value;
            var checked = node.ischecked;
            var className = 'treeCheckBox';

            //var inputText = "<input type=\"" + type + "\" class=\"" + className + "\" name=\"itemCheckBox\" id=\"" + value + "\" value=\"" + value + "\" " + ((checked == "true") ? " checked" : "") + ">";
            var checkBoxItem = document.createElement("input");
            checkBoxItem.type = type;
            checkBoxItem.className = className;
            checkBoxItem.name = "itemCheckBox";
            checkBoxItem.id = value;
            checkBoxItem.value = value;

            checkBoxItem.onclick = function(ev) {
                selectChildren(ev);
            };
            if (checked == "true") {
                checkBoxItem.checked = "true";
            }

            //node.titleNode.insertAdjacentHTML("beforeBegin", inputText);
            node.titleNode.insertBefore(checkBoxItem, node.titleNode.firstChild);
        }

        function addCheckBoxForIE(message) {
            var node = message.source;
            var value = node.value;
            var checked = node.ischecked;
            var className = 'treeCheckBox';

            var inputText = "<input type=\"checkbox\" class=\"treeCheckBox\" name=\"itemCheckBox\" id=\"" + value + "\" value=\"" + value + "\" " + ((checked == "true") ? " checked" : "") + " onclick=\"selectChildren(event)\">";

            node.titleNode.insertAdjacentHTML("beforeBegin", inputText);
        }

        dojo.event.topic.subscribe("tree/createDOMNode", addCheckBox);

        function formSubmit() {
            if (document.menu.roleId.value == '') {
                alert("请选择角色");
                return false;
            }
            pushValue();
            document.menu.submit();
        }

        function selectChildren(ev) {

            if (document.all) {
                selectAllChildrenForIE(event.srcElement);
            } else {
                selectAllChildrenForFirefox(ev.target);
            }
        }
        /**
         * 选择一个根下面的所有叶子节点，写成单独的方法，是为了迭带使用.
         */
        function selectAllChildrenForIE(root) {
            try {

                var div = root.parentNode.parentNode;
                var index = 3;
                var childDiv = div.childNodes[index];
                while (childDiv.tagName != "div" && childDiv.tagName != "DIV") {
                    index++;
                    childDiv = div.childNodes[index];
                }
                childDiv = childDiv;

                for (var i = 0; i < childDiv.childNodes.length; i++) {
                    var child = childDiv.childNodes[i];

                    //var item = child.childNodes[index - 1].childNodes[1];
                    //alert(item.tagName + "," + item.className + "," + item.innerHTML);

                    var item = child.childNodes[index - 1].childNodes[1];
                    item.checked = root.checked;

                    //selectAllChildrenForIE(item);
                }
            } catch (e) {
                alert(e);
            }
        }
        /**
         * 选择一个根下面的所有叶子节点，写成单独的方法，是为了迭带使用.
         */
        function selectAllChildrenForFirefox(root) {
            try {

                var div = root.parentNode.parentNode.parentNode;
                var index = 3;
                var childDiv = div.childNodes[index];
                while (childDiv.tagName != "div" && childDiv.tagName != "DIV") {
                    index++;
                    childDiv = div.childNodes[index];
                }

                for (var i = 0; i < childDiv.childNodes.length; i++) {
                    var child = childDiv.childNodes[i];
                    var item = child.childNodes[index - 1].childNodes[1].firstChild;
                    item.checked = root.checked;

                    //selectAllChildrenForFireFox(item);
                }
            } catch (e) {
                alert(e);
            }
        }
    </script>
  </head>

  <body>

    <div id="titleDiv" class="pageTitle"> 菜单权限 </div>

    <div id="operationDiv">
      <#include "/include/messages.ftl">
    </div>

    <div id="tableDiv">
      <form name="menu" action="${ctx}/menu/saveMenuRole.htm" method="post">
        <table width="100%" height="100%" border="0" cellspacing="3" cellpadding="0">
          <tr>
            <td width="80%" valign="top">
              <table class="border" width="100%" cellSpacing="0" cellPadding="2" align="center">
                <tr>
                  <td class="left" width="20%">
                    选择角色:
                  </td>
                  <td class="right">
                    <select name="roleId" onchange="window.location='${ctx}/menu/listMenuRole.htm?roleId='+this.value;">
                      <option value="">先选择角色</option>
                        <#list roles as role>
                          <#if role.id?c=roleId>
                          <option value="${role.id}" selected>${role.name}</option>
                          <#else>
                          <option value="${role.id}">${role.name}</option>
                          </#if>
                        </#list>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td class="left" valign="top">选择菜单</td>
                  <input type="hidden" id="itemselect" name="itemselect">
                  <td class="right" height="400" valign="top">
                    <div dojoType="Tree" widgetId="tree" expandLevel="2">
                      ${treeText}
                    </div>
                  </td>
                </tr>
                <tr>
                  <td colspan="2" class="bottom">
                    <BUTTON onclick="formSubmit();" style="margin-right:60px">
                      确定
                    </BUTTON>
                    <BUTTON onclick="history.back();">
                      返回
                    </BUTTON>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </form>
    </div>
  </body>
</html>
