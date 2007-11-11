package anni.aerp2.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import anni.core.grid.LongGridBean;

import org.hibernate.annotations.GenericGenerator;


/**
 * Erp2Product generated by Lingo.
 *
 * @author Lingo
 * @since 2007年11月03日 下午 12时54分39秒31
 */
@Entity
@Table(name = "A_ERP2_PRODUCT")
public class Erp2Product extends LongGridBean {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * id. */
    private Long id;

    /** * erp2Supplier. */
    private Erp2Supplier erp2Supplier;

    /** * name. */
    private String name;

    /** * type. */
    private Integer type;

    /** * material. */
    private String material;

    /** * factory. */
    private String factory;

    /** * price. */
    private Double price;

    /** * unit. */
    private Integer unit;

    /** * descn. */
    private String descn;

    /** * num. */
    private Integer num;

    /** * total. */
    private Double total;

    /** * erp2Bids. */
    private Set<Erp2Bid> erp2Bids = new HashSet<Erp2Bid>(0);

    /** * erp2BuyOrderInfos. */
    private Set<Erp2BuyOrderInfo> erp2BuyOrderInfos = new HashSet<Erp2BuyOrderInfo>(0);

    /** * 构造方法. */
    public Erp2Product() {
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

    /** * @return erp2Supplier. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPLIER_ID")
    public Erp2Supplier getErp2Supplier() {
        return erp2Supplier;
    }

    /** * @param erp2Supplier Erp2Supplier. */
    public void setErp2Supplier(Erp2Supplier erp2Supplier) {
        this.erp2Supplier = erp2Supplier;
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

    /** * @return type. */
    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    /** * @param type Integer. */
    public void setType(Integer type) {
        this.type = type;
    }

    /** * @return material. */
    @Column(name = "MATERIAL", length = 50)
    public String getMaterial() {
        return material;
    }

    /** * @param material String. */
    public void setMaterial(String material) {
        this.material = material;
    }

    /** * @return factory. */
    @Column(name = "FACTORY", length = 50)
    public String getFactory() {
        return factory;
    }

    /** * @param factory String. */
    public void setFactory(String factory) {
        this.factory = factory;
    }

    /** * @return price. */
    @Column(name = "PRICE")
    public Double getPrice() {
        return price;
    }

    /** * @param price Double. */
    public void setPrice(Double price) {
        this.price = price;
    }

    /** * @return unit. */
    @Column(name = "UNIT")
    public Integer getUnit() {
        return unit;
    }

    /** * @param unit Integer. */
    public void setUnit(Integer unit) {
        this.unit = unit;
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

    /** * @return num. */
    @Column(name = "NUM")
    public Integer getNum() {
        return num;
    }

    /** * @param num Integer. */
    public void setNum(Integer num) {
        this.num = num;
    }

    /** * @return total. */
    @Column(name = "TOTAL")
    public Double getTotal() {
        return total;
    }

    /** * @param total Double. */
    public void setTotal(Double total) {
        this.total = total;
    }

    /** * @return erp2Bids. */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "erp2Product")
    public Set<Erp2Bid> getErp2Bids() {
        return erp2Bids;
    }

    /** * @param erp2Bids Set. */
    public void setErp2Bids(Set<Erp2Bid> erp2Bids) {
        this.erp2Bids = erp2Bids;
    }

    /** * @return erp2BuyOrderInfos. */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "erp2Product")
    public Set<Erp2BuyOrderInfo> getErp2BuyOrderInfos() {
        return erp2BuyOrderInfos;
    }

    /** * @param erp2BuyOrderInfos Set. */
    public void setErp2BuyOrderInfos(
        Set<Erp2BuyOrderInfo> erp2BuyOrderInfos) {
        this.erp2BuyOrderInfos = erp2BuyOrderInfos;
    }
}
