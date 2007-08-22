2007-08-09 Lingo
----------------


大幅度优化，增加功能，自动生成的测试覆盖率达到100%

流程纪要，ant任务在ant目录下，用到的java源代码在src目录下
1.prepare部分.build-prepare.xml
1.1.根据手工制作的excel生成hsqldb的建表脚本
    ant任务：prepare-excel-hsqldb.xml
    java代码：anni.tools.GenHsqldb
    生成文件：target/test.sql
1.2.根据test.sql生成hsqldb数据库
    ant任务：prepare-hsqldb.xml
    java代码：无
    生成文件：target/test.properties,target/test.script
1.3.生成hibernate反向工程需要的hibernate.reveng.xml
    ant任务：prepare-excel-hibernate-reveng.xml
    java代码：anni.tools.GenHibernateReveng
    生成文件：target/hibernate.reveng.xml
1.4.根据hsqldb数据库和hibernate.reveng.xml生成pojo
    ant任务：prepare-hibernatetools.xml
    java代码：无
    生成文件：target/gen/**/domain/*.java
1.5.编译上一步生成的pojo，根据pojo重新生成符合checkstyle标准的pojo以及对应单元测试
    ant任务：prepare-compile-domain.xml
    java代码：anni.tools.GenDomain
    生成文件：target/${project.name}/src/main/java/**/domain/*.java
              target/${project.name}/src/test/java/**/domain/*.java
2.gen部分
2.1.复制maven2工程需要的文件，以及附属资源
    ant任务：gen-pom.xml
    java代码：无
    生成文件：target/${project.name}/**
2.2.复制package.html，生成manager类，controller类，对应单元测试，manager与controller配置文件
    ant任务：gen-manager.xml
    java代码：anni.tools.GenManager
    生成文件：target/${project.name}/src/main/java/**/manager/*.java
              target/${project.name}/src/main/java/**/web/*.java
              target/${project.name}/src/test/java/**/manager/*.java
              target/${project.name}/src/test/java/**/web/*.java
              target/${project.name}/src/main/java/**/package.html
2.3.生成freemarker模板，和commons-validator配置文件
    ant任务：gen-freemarker.xml
    java代码：anni.tools.GenFreemarker
    生成文件：target/${project.name}/src/main/webapp/**.ftl
              target/${project.name}/src/main/resources/validation/validation.xml
2.4.生成配置文件
    ant任务：gen-excel-xml.xml
    java代码：anni.tools.GenXml
    生成文件：target/${project.name}/src/main/resources/spring/applicationContext-hibernate.xml
              target/${project.name}/src/main/resources/spring/db/hibernate-mock.xml
              target/${project.name}/src/hibernate.cfg.xml
              target/${project.name}/src/main/native2ascii/validation.properties
              target/${project.name}/src/main/native2ascii/validation_zh_CN.properties
              target/${project.name}/src/main/webapp/WEB-INF/menu-config.xml
              target/${project.name}/src/main/webapp/freemarker/admin/menu.ftl
              target/${project.name}/src/test/resources/xls/export.xls
3.assemble部分


2007-06-12 Lingo
----------------


总结之前所有的代码生成器，汇总为以ant脚本为主体的完全代码生成器。
分为以下几个步骤：

1.excel的表结构需要手工制作
2.根据手工汇总的excel生成hsqldb数据库的sql脚本
3.根据sql脚本，生成hsqldb的数据库文件
4.根据excel生成hibernate.reveng.xml和i18n使用的properties文件
5.根据生成hsqldb数据库文件使用hibernatetools生成pojo
6.根据excel生成manager和webcontroller，另外复制三个package.html
7.根据模板生成一个maven2的工程结构，包括最基本的配置文件
8.根据pojo和excel生成index.jsp和freemarker
9.最后组装

