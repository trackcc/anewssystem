package anni.core.search.compass;

import org.compass.core.Compass;
import org.compass.core.CompassHits;
import org.compass.core.CompassSession;
import org.compass.core.support.search.CompassSearchCommand;
import org.compass.core.support.search.CompassSearchHelper;


/**
 * @author <a href="mailto:rory.cn@gmail.com">somebody</a>
 * @since Aug 23, 2007 2:04:19 PM
 * @version $Id AdvanceCompassSearchHelper.java$
 */
public class AdvanceCompassSearchHelper extends CompassSearchHelper {
    private String[] highlightFields;

    /**
     * @param compass
     */
    public AdvanceCompassSearchHelper(Compass compass) {
        super(compass);
    }

    public String[] getHighlightFields() {
        return highlightFields;
    }

    public void setHighlightFields(String[] highlightFields) {
        this.highlightFields = highlightFields;
    }

    /* (non-Javadoc)
     * @see org.compass.core.support.search.CompassSearchHelper#doProcessBeforeDetach(org.compass.core.support.search.CompassSearchCommand, org.compass.core.CompassSession, org.compass.core.CompassHits, int, int)
     */
    @Override
    protected void doProcessBeforeDetach(
        CompassSearchCommand searchCommand, CompassSession session,
        CompassHits hits, int from, int size) {
        if (from < 0) {
            from = 0;
            size = hits.getLength();
        }

        if (highlightFields == null) {
            return;
        }

        // highlight fields
        for (int i = from; i < size; i++) {
            for (String highlightField : highlightFields) {
                hits.highlighter(i).fragment(highlightField);
            }
        }
    }
}
