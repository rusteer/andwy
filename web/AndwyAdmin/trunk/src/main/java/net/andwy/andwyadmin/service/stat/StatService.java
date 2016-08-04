package net.andwy.andwyadmin.service.stat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.andwy.andwyadmin.bean.Stat;
import net.andwy.andwyadmin.repository.stat.StatDaoMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class StatService {
    @Autowired
    StatDaoMybatis dao;
    public List<Stat> getMarketData(String fromDate, String toDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("toDate", toDate);
        map.put("fromDate", fromDate);
        return dao.getMarketData(map);
    }
    public List<Stat> getBatchData(String fromDate, String toDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("toDate", toDate);
        map.put("fromDate", fromDate);
        return dao.getBatchData(map);
    }
    public List<Stat> getAppData(String fromDate, String toDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("toDate", toDate);
        map.put("fromDate", fromDate);
        return dao.getAppData(map);
    }
    public List<Stat> getDeveloperData(String fromDate, String toDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("toDate", toDate);
        map.put("fromDate", fromDate);
        return dao.getDeveloperData(map);
    }
    public List<Stat> getProductData(Long batchId, String fromDate, String toDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("toDate", toDate);
        map.put("fromDate", fromDate);
        map.put("batchId", batchId);
        return dao.getProductData(map);
    }
    public List<Stat> getPackageData(Long productId, String fromDate, String toDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("toDate", toDate);
        map.put("fromDate", fromDate);
        map.put("productId", productId);
        return dao.getPackageData(map);
    }
}
