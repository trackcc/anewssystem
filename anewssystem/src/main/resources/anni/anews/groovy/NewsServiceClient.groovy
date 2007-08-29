package anni.anews.groovy;

import java.util.List;
import anni.anews.manager.NewsManager;


class NewsServiceClient implements NewsService {

    def newsManager

    List getAll() {
      newsManager.getAll()
    }
}
