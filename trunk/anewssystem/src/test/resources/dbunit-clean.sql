delete from a_news_newstag;
delete from a_news;
update a_news_category set parent_id=null;
delete from a_news_category;
