package net.andwy.andwyadmin.repository.stat;
import java.util.List;
import java.util.Map;
import net.andwy.andwyadmin.entity.client.ClientStat;
import net.andwy.andwyadmin.repository.MyBatisRepository;

@MyBatisRepository
public interface ClientStatDaoMybatis {
    /**
     * for mybatisc
     * @param parameters
     * @return
     */
    List<ClientStat> getStat(Map<String, Object> parameters);
}
