package anni.core.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


/**
 * id是Long的Named类实现.
 *
 * @author Lingo
 * @since 2007-06-05
 */
@MappedSuperclass
public class LongNamedEntityBean extends LongEntityBean
    implements NamedEntityBean {
    /** * name. */
    protected String name = null;

    /**
     * @return String name.
     */
    @Column(unique = true, nullable = false)
    public String getName() {
        return name;
    }

    /**
     * @param nameIn name.
     */
    public void setName(String nameIn) {
        name = nameIn;
    }
}
