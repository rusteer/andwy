package net.andwy.andwyadmin.repository.client;
import net.andwy.andwyadmin.entity.client.BlackClient;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BlackClientDao extends PagingAndSortingRepository<BlackClient, Long>, JpaSpecificationExecutor<BlackClient> {
    @Query("from BlackClient a where a.client.id=?1")
    BlackClient getByClientId(Long clientId);
}
