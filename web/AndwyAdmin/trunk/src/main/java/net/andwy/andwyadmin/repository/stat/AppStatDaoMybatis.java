package net.andwy.andwyadmin.repository.stat;
import java.util.List;
import java.util.Map;
import net.andwy.andwyadmin.entity.client.AppStat;
import net.andwy.andwyadmin.repository.MyBatisRepository;

@MyBatisRepository
public interface AppStatDaoMybatis {
    /**
     * for mybatisc
     * @param parameters
     * @return
     */
    List<AppStat> getStat(Map<String, Object> parameters);
}
