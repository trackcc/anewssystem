# extjs混合java制作的新闻发布系统。 #
# A news publishing system with extjs and java. #

QQ群号：3038490

# 2007-10-23： #
# 新闻发布0.0.2-M3发布 #
**https://sourceforge.net/project/showfiles.php?group_id=204197**

新闻发布0.0.2-M3发布
（milestone里程碑3）

后台是java的，请使用jdk-1.5以上版本，和tomcat-5.5以上版本。

项目首页：http://code.google.com/p/anewssystem/
下载地址：https://sourceforge.net/project/showfiles.php?group_id=204197

发布的anews-0.0.2-M3.war是已经打包好的程序，可以直接放在tomcat下运行，自带了嵌入式数据库hsqldb，无需额外配置：）

anews-0.0.2-M3.zip是eclipse工程包，svn下的工程是maven2工程，有些包在公网上是找不到的，目前还没有明确哪些包需要额外提供，所以先用eclipse工程吧，那里边是全的。不过对于eclipse的使用经验太少，如果发现什么问题，请尽快反馈给我。echo\_o@163.com

这个版本主要是bug清除和结构重构，删除了一些无用的jar依赖库和源代码，另外有一些增加的功能：

  * 新闻部分的上传图片功能，为了美化上传按钮，把type=file隐藏掉后，放上一个假按钮。
  * 修改管理新闻的布局部分，不是使用dialog而是依靠可收缩的south放置编辑新闻的form。
  * Ext.lingo.JsonGrid修改了ie下checkbox显示错位的问题，添加翻页后保存选中信息的功能。
  * 修改了jcaptcha显示的图片，现在文字更容易辨认了，建议来自（下一道彩虹4612462）
  * 修正了dialog标题错位的问题，建议来自（/aiq伊扬贝尔4046921）
  * 添加了一个项目跟踪面板，记录修改的问题，地址在http://localhost:8080/anews/tracker/index.htm


之前的0.0.2-M2中完成了权限模块的基本功能，包括

  * 照抄springside-2.0中权限模块的RBAC模型
  * 资源分为URL与METHOD两种，分别使用filter和aop拦截，实现权限管理
  * 角色与资源多对多关联
  * 角色与菜单也是多对多关联，实现不同角色显示不同菜单
  * 用户与角色多对多关联，一个用户会拥有多个角色中的资源
  * 提供extjs与acegi对接，密码MD5加密，可选择cookie自动登录，登录支持图片验证码


之前的0.0.2-M1中演示的JsonTree和JsonGrid的使用，这两个封装基本实现了最基本的tree与grid功能，而且在后台封装了对应的java类，前后台一起继承使用，结果就是大量的减少代码总量，看着干净。

树形实现的功能：

  * 异步读取节点（不过管理分类的时候需要读取所有节点）
  * 双击节点编辑节点内容
  * 拖拽排序
  * 右键弹出菜单，进行详细配置

grid实现的功能：

  * 分页
  * checkbox全选，多选
  * 按字段搜索
  * 弹出对话框，进行新增或修改数据



# 截图20070913： #
参考土豆同志的Ext.DataGrid制作，参考网址：http://forgetdavi.javaeye.com/
![http://anewssystem.googlecode.com/files/screencut.jpg](http://anewssystem.googlecode.com/files/screencut.jpg)