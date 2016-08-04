package net.andwy.andwyadmin.repository.admin;
import java.util.List;
import net.andwy.andwyadmin.entity.admin.Developer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeveloperDao extends PagingAndSortingRepository<Developer, Long>, JpaSpecificationExecutor<Developer> {
    @Query("from Developer t where t.status='E' and t.name<>'test' order by t.name,t.id")
    List<Developer> getEnabledList();
}
