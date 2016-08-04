package net.andwy.andwyadmin.repository.admin;
import java.util.List;
import net.andwy.andwyadmin.entity.admin.Package;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PackageDao extends PagingAndSortingRepository<Package, Long>, JpaSpecificationExecutor<Package> {
    @Query("from Package r where r.product.id=?1 order by r.product.priority")
    List<Package> getListByProductId(Long productId);
    @Query("from Package r where r.needBuild='Y' order by r.product.priority")
    List<Package> getBuilList();
    @Query("from Package r where r.product.batchId=?1 order by r.product.priority")
    List<Package> getAllByBatchId(Long batchId);
    Package findByUid(String uid);
    @Query("from Package r where r.marketAccount.id=?1 order by r.id desc")
    List<Package> getPackageListByMarketAccount(Long marketAccountId);
}
