package anni.aerp.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import anni.core.grid.LongGridBean;

import org.hibernate.annotations.GenericGenerator;


/**
 * ErpOrder generated by Lingo.
 *
 * @author Lingo
 * @since 2007年10月25日 下午 22时23分30秒750
 */
@Entity
@Table(name = "A_ERP_ORDER")
public class ErpOrder extends LongGridBean {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * id. */
    private Long id;

    /** * code. */
    private String code;

    /** * orderDate. */
    private Date orderDate;

    /** * customer. */
    private String customer;

    /** * linkMan. */
    private String linkMan;

    /** * tel. */
    private String tel;

    /** * amount. */
    private String amount;

    /** * status. */
    private String status;

    /** * handMan. */
    private String handMan;

    /** * payment. */
    private String payment;

    /** * delivery. */
    private String delivery;

    /** * descn. */
    private String descn;

    /** * inputMan. */
    private String inputMan;

    /** * inputTime. */
    private Date inputTime;

    /** * erpOrderInfos. */
    private Set<ErpOrderInfo> erpOrderInfos = new HashSet<ErpOrderInfo>(0);

    /** * 构造方法. */
    public ErpOrder() {
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

    /** * @return code. */
    @Column(name = "CODE", length = 50)
    public String getCode() {
        return code;
    }

    /** * @param code String. */
    public void setCode(String code) {
        this.code = code;
    }

    /** * @return orderDate. */
    @Column(name = "ORDER_DATE")
    public Date getOrderDate() {
        if (orderDate == null) {
            return null;
        } else {
            return (Date) orderDate.clone();
        }
    }

    /** * @param orderDate Date. */
    public void setOrderDate(Date orderDate) {
        if (orderDate == null) {
            this.orderDate = null;
        } else {
            this.orderDate = (Date) orderDate.clone();
        }
    }

    /** * @return customer. */
    @Column(name = "CUSTOMER", length = 50)
    public String getCustomer() {
        return customer;
    }

    /** * @param customer String. */
    public void setCustomer(String customer) {
        this.customer = customer;
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

    /** * @return tel. */
    @Column(name = "TEL", length = 50)
    public String getTel() {
        return tel;
    }

    /** * @param tel String. */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /** * @return amount. */
    @Column(name = "AMOUNT", length = 50)
    public String getAmount() {
        return amount;
    }

    /** * @param amount String. */
    public void setAmount(String amount) {
        this.amount = amount;
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

    /** * @return handMan. */
    @Column(name = "HAND_MAN", length = 50)
    public String getHandMan() {
        return handMan;
    }

    /** * @param handMan String. */
    public void setHandMan(String handMan) {
        this.handMan = handMan;
    }

    /** * @return payment. */
    @Column(name = "PAYMENT", length = 50)
    public String getPayment() {
        return payment;
    }

    /** * @param payment String. */
    public void setPayment(String payment) {
        this.payment = payment;
    }

    /** * @return delivery. */
    @Column(name = "DELIVERY", length = 50)
    public String getDelivery() {
        return delivery;
    }

    /** * @param delivery String. */
    public void setDelivery(String delivery) {
        this.delivery = delivery;
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

    /** * @return erpOrderInfos. */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "erpOrder")
    public Set<ErpOrderInfo> getErpOrderInfos() {
        return erpOrderInfos;
    }

    /** * @param erpOrderInfos Set. */
    public void setErpOrderInfos(Set<ErpOrderInfo> erpOrderInfos) {
        this.erpOrderInfos = erpOrderInfos;
    }
}
