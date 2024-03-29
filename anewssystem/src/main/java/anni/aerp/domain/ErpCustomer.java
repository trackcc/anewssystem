package anni.aerp.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import anni.core.grid.LongGridBean;

import org.hibernate.annotations.GenericGenerator;


/**
 * ErpCustomer generated by Lingo.
 *
 * @author Lingo
 * @since 2007年10月25日 下午 22时23分30秒750
 */
@Entity
@Table(name = "A_ERP_CUSTOMER")
public class ErpCustomer extends LongGridBean {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * id. */
    private Long id;

    /** * name. */
    private String name;

    /** * code. */
    private String code;

    /** * type. */
    private String type;

    /** * zip. */
    private String zip;

    /** * leader. */
    private String leader;

    /** * fax. */
    private String fax;

    /** * linkMan. */
    private String linkMan;

    /** * email. */
    private String email;

    /** * tel. */
    private String tel;

    /** * homepage. */
    private String homepage;

    /** * province. */
    private String province;

    /** * city. */
    private String city;

    /** * town. */
    private String town;

    /** * address. */
    private String address;

    /** * descn. */
    private String descn;

    /** * source. */
    private String source;

    /** * rank. */
    private String rank;

    /** * status. */
    private String status;

    /** * inputMan. */
    private String inputMan;

    /** * inputTime. */
    private Date inputTime;

    /** * 构造方法. */
    public ErpCustomer() {
    }

    /** * @return id. */
    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    /** * @param id Long. */
    public void setId(Long id) {
        this.id = id;
    }

    /** * @return name. */
    @Column(name = "NAME", length = 50)
    public String getName() {
        return name;
    }

    /** * @param name String. */
    public void setName(String name) {
        this.name = name;
    }

    /** * @return code. */
    @Column(name = "CODE", length = 50)
    public String getCode() {
        return code;
    }

    /** * @param code String. */
    public void setCode(String code) {
        this.code = code;
    }

    /** * @return type. */
    @Column(name = "TYPE", length = 50)
    public String getType() {
        return type;
    }

    /** * @param type String. */
    public void setType(String type) {
        this.type = type;
    }

    /** * @return zip. */
    @Column(name = "ZIP", length = 50)
    public String getZip() {
        return zip;
    }

    /** * @param zip String. */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /** * @return leader. */
    @Column(name = "LEADER", length = 50)
    public String getLeader() {
        return leader;
    }

    /** * @param leader String. */
    public void setLeader(String leader) {
        this.leader = leader;
    }

    /** * @return fax. */
    @Column(name = "FAX", length = 50)
    public String getFax() {
        return fax;
    }

    /** * @param fax String. */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /** * @return linkMan. */
    @Column(name = "LINK_MAN", length = 50)
    public String getLinkMan() {
        return linkMan;
    }

    /** * @param linkMan String. */
    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    /** * @return email. */
    @Column(name = "EMAIL", length = 50)
    public String getEmail() {
        return email;
    }

    /** * @param email String. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** * @return tel. */
    @Column(name = "TEL", length = 50)
    public String getTel() {
        return tel;
    }

    /** * @param tel String. */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /** * @return homepage. */
    @Column(name = "HOMEPAGE", length = 50)
    public String getHomepage() {
        return homepage;
    }

    /** * @param homepage String. */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    /** * @return province. */
    @Column(name = "PROVINCE", length = 50)
    public String getProvince() {
        return province;
    }

    /** * @param province String. */
    public void setProvince(String province) {
        this.province = province;
    }

    /** * @return city. */
    @Column(name = "CITY", length = 50)
    public String getCity() {
        return city;
    }

    /** * @param city String. */
    public void setCity(String city) {
        this.city = city;
    }

    /** * @return town. */
    @Column(name = "TOWN", length = 50)
    public String getTown() {
        return town;
    }

    /** * @param town String. */
    public void setTown(String town) {
        this.town = town;
    }

    /** * @return address. */
    @Column(name = "ADDRESS", length = 50)
    public String getAddress() {
        return address;
    }

    /** * @param address String. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** * @return descn. */
    @Column(name = "DESCN", length = 2000)
    @Lob
    public String getDescn() {
        return descn;
    }

    /** * @param descn String. */
    public void setDescn(String descn) {
        this.descn = descn;
    }

    /** * @return source. */
    @Column(name = "SOURCE", length = 50)
    public String getSource() {
        return source;
    }

    /** * @param source String. */
    public void setSource(String source) {
        this.source = source;
    }

    /** * @return rank. */
    @Column(name = "RANK", length = 50)
    public String getRank() {
        return rank;
    }

    /** * @param rank String. */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /** * @return status. */
    @Column(name = "STATUS", length = 50)
    public String getStatus() {
        return status;
    }

    /** * @param status String. */
    public void setStatus(String status) {
        this.status = status;
    }

    /** * @return inputMan. */
    @Column(name = "INPUT_MAN", length = 50)
    public String getInputMan() {
        return inputMan;
    }

    /** * @param inputMan String. */
    public void setInputMan(String inputMan) {
        this.inputMan = inputMan;
    }

    /** * @return inputTime. */
    @Column(name = "INPUT_TIME")
    public Date getInputTime() {
        if (inputTime != null) {
            return (Date) inputTime.clone();
        } else {
            return null;
        }
    }

    /** * @param inputTime Date. */
    public void setInputTime(Date inputTime) {
        if (inputTime == null) {
            this.inputTime = null;
        } else {
            this.inputTime = (Date) inputTime.clone();
        }
    }
}
