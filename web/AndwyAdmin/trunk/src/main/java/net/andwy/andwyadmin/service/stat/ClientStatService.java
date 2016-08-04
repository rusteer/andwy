package net.andwy.andwyadmin.service.stat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.andwy.andwyadmin.entity.client.App;
import net.andwy.andwyadmin.entity.client.ClientStat;
import net.andwy.andwyadmin.repository.stat.ClientStatDao;
import net.andwy.andwyadmin.repository.stat.ClientStatDaoMybatis;
import net.andwy.andwyadmin.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ClientStatService extends AbstractService<ClientStat> {
    //private static final Logger logger = LoggerFactory.getLogger("ClientLogger");
    private Map<Long, ClientStat> statMap = new HashMap<Long, ClientStat>();
    private static final Object locker = new Object();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    ClientStatDao dao;
    @Autowired
    ClientStatDaoMybatis countDao;
    private ClientStat getStat(Long clientId) {
        String statDate = dateFormat.format(new Date());
        ClientStat stat = dao.getStat(clientId, statDate);
        if (stat == null) {
            stat = dao.save(new ClientStat(clientId, statDate));
        }
        return stat;
    }
    public void clearCache() {
        synchronized (locker) {
            statMap.clear();
        }
    }
    @Transactional(readOnly = false)
    public void increaseInstallStat(Long clientId, App app) {
        float price = app.getPrice();
        ClientStat stat = getStat(clientId);
        if (!app.isPrivateApp()) {
            stat.setInstallCount(stat.getInstallCount() + 1);
            stat.setInstallEarning(stat.getInstallEarning() + price);
        }
        stat.setAllInstallCount(stat.getAllInstallCount() + 1);
        stat.setAllInstallEarning(stat.getAllInstallEarning() + price);
        dao.save(stat);
    }
    @Transactional(readOnly = false)
    public void batchSaveStat() {
        //logger.info("ClientStatService.batchSaveStat() start");
        for (ClientStat stat : statMap.values()) {
            dao.save(stat);
        }
        //logger.info("ClientStatService.batchSaveStat() end");
    }
    @Override
    protected CrudRepository<ClientStat, Long> getDao() {
        return dao;
    }
    public List<ClientStat> getStat(Long clientId, String fromDate, String endDate, boolean groupByDate) {
        if (groupByDate) return dao.getStat(clientId, fromDate, endDate);
        Map<String, Object> parameters = new HashMap<String, Object>();
        if (clientId != null && clientId > 0) parameters.put("clientId", clientId);
        parameters.put("fromDate", fromDate);
        parameters.put("toDate", endDate);
        return countDao.getStat(parameters);
    }
}
