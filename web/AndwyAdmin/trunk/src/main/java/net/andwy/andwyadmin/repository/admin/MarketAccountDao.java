package net.andwy.andwyadmin.repository.admin;
import net.andwy.andwyadmin.entity.admin.MarketAccount;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MarketAccountDao extends PagingAndSortingRepository<MarketAccount, Long>, JpaSpecificationExecutor<MarketAccount> {}
