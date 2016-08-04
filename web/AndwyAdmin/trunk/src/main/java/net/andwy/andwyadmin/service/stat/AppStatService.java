package net.andwy.andwyadmin.service.stat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.andwy.andwyadmin.entity.client.AppStat;
import net.andwy.andwyadmin.repository.stat.AppStatDao;
import net.andwy.andwyadmin.repository.stat.AppStatDaoMybatis;
import net.andwy.andwyadmin.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class AppStatService extends AbstractService<AppStat> {
    //private static final Logger logger = LoggerFactory.getLogger("ClientLogger");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    AppStatDao dao;
    @Autowired
    AppStatDaoMybatis mybatisDao;
    @Override
    protected CrudRepository<AppStat, Long> getDao() {
        return dao;
    }
    public AppStat getStat(Long appId, String statDate) {
        return dao.getStat(appId, statDate);
    }
    public List<AppStat> getStats(String statDate) {
        return dao.getStats(statDate);
    }
    public List<AppStat> getStat(Long appId, String fromDate, String endDate, boolean groupByDate) {
        if (groupByDate) return dao.getStat(appId, fromDate, endDate);
        Map<String, Object> parameters = new HashMap<String, Object>();
        if (appId != null && appId > 0) parameters.put("appId", appId);
        parameters.put("fromDate", fromDate);
        parameters.put("toDate", endDate);
        return mybatisDao.getStat(parameters);
    }
    @Transactional(readOnly = false)
    public void increasePushStat(Long appId) {
        String today = dateFormat.format(new Date());
        AppStat stat = dao.getStat(appId, today);
        if (stat == null) {
            stat = new AppStat(appId, today);
        }
        stat.setPushCount(stat.getPushCount() + 1);
        this.dao.save(stat);
        //AppStat stat = new AppStat(appId,today );
        //mybatisDao.increasePushStat(stat);
    }
    @Transactional(readOnly = false)
    public void increaseInstallStat(Long appId) {
        String today = dateFormat.format(new Date());
        AppStat stat = dao.getStat(appId, today);
        if (stat == null) {
            stat = new AppStat(appId, today);
        }
        stat.setInstallCount(stat.getInstallCount() + 1);
        this.dao.save(stat);
        //AppStat stat = new AppStat(appId, dateFormat.format(new Date()));
        //mybatisDao.increaseInstallStat(stat);
    }
}
