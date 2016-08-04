package net.andwy.andwyadmin.repository.admin;
import java.util.List;
import net.andwy.andwyadmin.entity.admin.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductDao extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("from Product r where r.batchId=?1 order by r.priority")
    List<Product> getListByBatchId(Long batchId);
}
