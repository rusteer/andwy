package net.andwy.andwyadmin.repository.client;
import net.andwy.andwyadmin.entity.client.ClientPackage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientPackageDao extends PagingAndSortingRepository<ClientPackage, Long>, JpaSpecificationExecutor<ClientPackage> {
    @Query("from ClientPackage r where r.pkg.id= ?1 and r.client.id = ?2")
    public ClientPackage findByUniqKey(Long packageId, Long clientId);
}
