package anni.core.dao.audit;

import java.util.Date;

import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;


/**
 * 侦听领域对象的PostUpdate事件，如果此对象实现了HistorizableEntity接口，将记录其修改前后的数据.
 * (todo:此类未完成)
 *
 * @author calvin
 */
public class HistoryEventListener implements PostUpdateEventListener {
    public void onPostUpdate(PostUpdateEvent event) {
        if (event.getEntity() instanceof HistorizableEntity) {
            Object id = event.getId();
            String type = event.getEntity().getClass().getSimpleName();
            HistorizableEntity he = (HistorizableEntity) event.getEntity();
            IUser user = he.getModifyUser();
            String loginid = null;

            if (user != null) {
                loginid = user.getLoginid();
            }

            Date date = he.getModifyTime();

            Object[] oldValues = event.getOldState();
            Object[] newValues = event.getState();

            for (int i = 0; i < oldValues.length; i++) {
                /*   if ((oldValues[i] != null && !oldValues[i].equals(newValues[i])) || (oldValues[i] == null && newValues[i] != null)) {
                    //todo：参考Compass的Hibernate Event listener,get Property Name, fix the lazy load bug(如果lazy load证明此对象未改动) ,insert int database
                    System.out.println(id + type + user + date + oldValues[i] + newValues[i]);
                }*/
            }
        }
    }
}
