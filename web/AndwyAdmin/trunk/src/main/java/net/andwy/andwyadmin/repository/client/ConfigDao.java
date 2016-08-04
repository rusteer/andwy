package net.andwy.andwyadmin.repository.client;
import java.util.List;
import net.andwy.andwyadmin.entity.client.Config;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConfigDao extends PagingAndSortingRepository<Config, Long>, JpaSpecificationExecutor<Config> {
    @Query("from net.andwy.andwyadmin.entity.client.Config a where a.level=?1 order by a.id desc")
    List<Config> getListByLevel(Long level);
}
