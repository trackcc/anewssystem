package anni.core.grid;

import java.io.Serializable;


/**
 * 只能定义一个id，这个超类有什么意义.
 *
 * @author Lingo
 * @since 2007-09-19
 */
public class LongGridBean implements Serializable {
    /** * serial. */
    static final long serialVersionUID = 0L;

    /** * id. */
    private Long id;

    /** * @return id. */
    public Long getId() {
        return id;
    }

    /** * @param id Long. */
    public void setId(Long id) {
        this.id = id;
    }
}
