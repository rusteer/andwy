package net.andwy.andwyadmin.repository.client;
import net.andwy.andwyadmin.entity.client.ClientSetting;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientSettingDao extends PagingAndSortingRepository<ClientSetting, Long>, JpaSpecificationExecutor<ClientSetting> {
    
}
