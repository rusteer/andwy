package net.andwy.andwyadmin.repository.client;
import java.util.List;
import net.andwy.andwyadmin.entity.client.Category;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryDao extends PagingAndSortingRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    @Query("from net.andwy.andwyadmin.entity.client.Category a where a.status='E'")
    List<Category> getEanbledList();
}
