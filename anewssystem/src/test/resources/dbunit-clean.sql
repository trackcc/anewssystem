delete from a_news_newstag;
delete from a_news;
update a_news_category set parent_id=null;
delete from a_news_category;

delete from A_SECURITY_ROLE_USER;
delete from A_SECURITY_MENU_ROLE;
delete from A_SECURITY_RESOURCE_ROLE;
update A_SECURITY_MENU set parent_id=null;
delete from A_SECURITY_MENU;
delete from A_SECURITY_USER;
update A_SECURITY_DEPT set parent_id=null;
delete from A_SECURITY_DEPT;
