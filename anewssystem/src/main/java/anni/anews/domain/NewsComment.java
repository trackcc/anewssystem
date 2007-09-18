package anni.anews.domain;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 *import org.compass.annotations.Searchable;
 *import org.compass.annotations.SearchableId;
 *import org.compass.annotations.SearchableProperty;
 */
import org.hibernate.annotations.GenericGenerator;


/**
 * NewsComment generated by Lingo.
 *
 * @author Lingo
 * @since 2007年08月16日 下午 23时13分00秒31
 */
@Entity
//@Searchable(alias = "a_news_comment")
@Table(name = "A_NEWS_COMMENT")
public class NewsComment implements Serializable {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * id. */
    //@SearchableId
    private Long id;

    /** * news. */
    private News news;

    /** * name. */
    //@SearchableProperty
    private String name;

    /** * content. */
    //@SearchableProperty
    private String content;

    /** * username. */
    //@SearchableProperty
    private String username;

    /** * updateDate. */
    private Date updateDate = null;

    /** * status. */
    private Integer status;

    /** * ip. */
    private String ip;

    /** * 构造方法. */
    public NewsComment() {
    }

    /** * @return id. */
    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    /** * @param id id. */
    public void setId(Long id) {
        this.id = id;
    }

    /** * @return news. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NEWS_ID")
    public News getNews() {
        return news;
    }

    /** * @param news news. */
    public void setNews(News news) {
        this.news = news;
    }

    /** * @return name. */
    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    /** * @param name name. */
    public void setName(String name) {
        this.name = name;
    }

    /** * @return content. */
    @Column(name = "CONTENT", length = 2000)
    @Lob
    public String getContent() {
        return content;
    }

    /** * @param content content. */
    public void setContent(String content) {
        this.content = content;
    }

    /** * @return username. */
    @Column(name = "USERNAME", length = 50)
    public String getUsername() {
        return username;
    }

    /** * @param username username. */
    public void setUsername(String username) {
        this.username = username;
    }

    /** * @return updateDate. */
    @Column(name = "UPDATE_DATE")
    public Date getUpdateDate() {
        if (updateDate != null) {
            return (Date) updateDate.clone();
        } else {
            return null;
        }
    }

    /** * @param updateDate updateDate. */
    public void setUpdateDate(Date updateDate) {
        if (updateDate != null) {
            this.updateDate = (Date) updateDate.clone();
        } else {
            this.updateDate = null;
        }
    }

    /** * @return status. */
    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    /** * @param status status. */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /** * @return ip. */
    @Column(name = "IP", length = 20)
    public String getIp() {
        return ip;
    }

    /** * @param ip ip. */
    public void setIp(String ip) {
        this.ip = ip;
    }
}
