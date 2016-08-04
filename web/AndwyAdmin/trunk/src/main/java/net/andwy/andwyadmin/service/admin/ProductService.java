package net.andwy.andwyadmin.service.admin;
import java.util.List;
import net.andwy.andwyadmin.entity.admin.Product;
import net.andwy.andwyadmin.repository.admin.ProductDao;
import net.andwy.andwyadmin.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ProductService extends AbstractService<Product> {
    public static final String PROJECT_TYPE_SOURCE = "source";
    public static final String PROJECT_TYPE_BINARY = "binary";
    private ProductDao dao;
    @Autowired
    public void setDao(ProductDao dao) {
        this.dao = dao;
    }
    @Override
    public ProductDao getDao() {
        return dao;
    }
    public List<Product> getListByBatchId(Long batchId) {
        return dao.getListByBatchId(batchId);
    }
}
