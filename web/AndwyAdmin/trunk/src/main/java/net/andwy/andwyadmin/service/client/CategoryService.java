package net.andwy.andwyadmin.service.client;
import java.util.List;
import net.andwy.andwyadmin.entity.client.Category;
import net.andwy.andwyadmin.repository.client.CategoryDao;
import net.andwy.andwyadmin.service.AbstractCacheService;
import net.andwy.andwyadmin.service.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class CategoryService extends AbstractCacheService<Category> {
    private static final Long CACHE_KEY_ENABLED_LIST = 1L;
    private CategoryDao dao;
    @Autowired
    public void setDao(CategoryDao dao) {
        this.dao = dao;
    }
    @Override
    public CategoryDao getDao() {
        return dao;
    }
    @Override
    protected List<Category> fetchListFromDB(Long key) {
        if (CACHE_KEY_ENABLED_LIST.equals(key)) return dao.getEanbledList();
        return null;
    }
    public List<Category> getEanbledList() {
        return getCacheList(CACHE_KEY_ENABLED_LIST);
    }
    public String getIconPath(Category category) {
        return Constants.BASE + "/category/" + category.getUid();
    }
}
