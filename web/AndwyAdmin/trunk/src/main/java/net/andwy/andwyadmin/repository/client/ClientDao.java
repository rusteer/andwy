package net.andwy.andwyadmin.repository.client;
import net.andwy.andwyadmin.entity.client.Client;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientDao extends PagingAndSortingRepository<Client, Long>, JpaSpecificationExecutor<Client> {
    public Client findByDeviceId(String deviceId);
}
