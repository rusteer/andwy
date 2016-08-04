package net.andwy.andwyadmin.repository.stat;
import java.util.List;
import java.util.Map;
import net.andwy.andwyadmin.bean.Stat;
import net.andwy.andwyadmin.repository.MyBatisRepository;

@MyBatisRepository
public interface StatDaoMybatis {
    List<Stat> getMarketData(Map<String, Object> parameters);
    List<Stat> getBatchData(Map<String, Object> map);
    List<Stat> getAppData(Map<String, Object> map);
    List<Stat> getDeveloperData(Map<String, Object> map);
    List<Stat> getProductData(Map<String, Object> map);
    List<Stat> getPackageData(Map<String, Object> map);
}
