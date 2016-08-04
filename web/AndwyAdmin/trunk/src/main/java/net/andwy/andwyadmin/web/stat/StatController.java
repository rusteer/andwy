package net.andwy.andwyadmin.web.stat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletRequest;
import net.andwy.andwyadmin.bean.Bean;
import net.andwy.andwyadmin.bean.Stat;
import net.andwy.andwyadmin.entity.admin.Developer;
import net.andwy.andwyadmin.entity.admin.Market;
import net.andwy.andwyadmin.entity.admin.Product;
import net.andwy.andwyadmin.entity.client.App;
import net.andwy.andwyadmin.service.admin.DeveloperService;
import net.andwy.andwyadmin.service.admin.MarketAccountService;
import net.andwy.andwyadmin.service.admin.MarketService;
import net.andwy.andwyadmin.service.admin.PackageService;
import net.andwy.andwyadmin.service.admin.ProductService;
import net.andwy.andwyadmin.service.client.AppService;
import net.andwy.andwyadmin.service.stat.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "stat")
public class StatController {
    private static final int DAYS_COUNT = 30;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    MarketService marketService;
    @Autowired
    AppService appService;
    @Autowired
    ProductService productService;
    @Autowired
    PackageService packageService;
    @Autowired
    MarketAccountService marketAccountService;
    @Autowired
    DeveloperService developerService;
    @Autowired
    StatService statService;
    @RequestMapping(value = "")
    public String index(ServletRequest request) {
        return "stat/result";
    }
    @RequestMapping(value = "/market")
    public String marketStat(ServletRequest request) {
        request.setAttribute("title", "市场统计");
        List<Bean> beans = new ArrayList<Bean>();
        for (Market market : marketService.getAll()) {
            beans.add(new Bean(market.getId(), market.getName()));
        }
        List<Stat> statList = statService.getMarketData(getFromDate(), getToDate());
        return populateData(request, beans, statList);
    }
    private String populateData(ServletRequest request, List<Bean> beans, List<Stat> statList) {
        String rowAllKey = "汇总";
        Bean rowBean = new Bean(0L, rowAllKey);
        rowBean.setHasData(true);
        beans.add(0, rowBean);
        Map<String, List<Stat>> stats = new LinkedHashMap<String, List<Stat>>();
        Map<String, Map<Long, Stat>> map = new HashMap<String, Map<Long, Stat>>();
        for (Stat stat : statList) {
            String date = stat.getStatDate();
            Map<Long, Stat> dateMap = map.get(date);
            if (dateMap == null) {
                dateMap = new HashMap<Long, Stat>();
                map.put(date, dateMap);
            }
            dateMap.put(stat.getEntityId(), stat);
        }
        Map<Long, Stat> columnCountMap = new HashMap<Long, Stat>();
        List<Stat> columnStats = new ArrayList<Stat>();
        for (Bean bean : beans) {
            Stat stat = new Stat();
            stat.setEntityId(bean.getId());
            stat.setBean(bean);
            columnCountMap.put(stat.getEntityId(), stat);
            columnStats.add(stat);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int count = DAYS_COUNT;
        while (count-- > 0) {
            String date = dateFormat.format(calendar.getTime());
            Map<Long, Stat> dateMap = map.get(date);
            List<Stat> list = new ArrayList<Stat>();
            boolean hasDateStat = false;
            Stat rowStat = new Stat(rowAllKey, 0L);
            rowStat.setBean(rowBean);
            list.add(rowStat);
            for (int i = 1; i < beans.size(); i++) {
                Bean bean = beans.get(i);
                Long beanId = bean.getId();
                Stat stat = null;
                if (dateMap != null) stat = dateMap.get(beanId);
                if (stat != null) {
                    hasDateStat = true;
                    bean.setHasData(true);
                } else stat = new Stat(date, beanId);
                rowStat.setInstallCount(rowStat.getInstallCount() + stat.getInstallCount());
                rowStat.setInstallEarning(rowStat.getInstallEarning() + stat.getInstallEarning());
                rowStat.setPushCount(rowStat.getPushCount() + stat.getPushCount());
                stat.setBean(bean);
                list.add(stat);
                //columnStat
                Stat columnStat = columnCountMap.get(beanId);
                columnStat.setInstallCount(columnStat.getInstallCount() + stat.getInstallCount());
                columnStat.setInstallEarning(columnStat.getInstallEarning() + stat.getInstallEarning());
                columnStat.setPushCount(columnStat.getPushCount() + stat.getPushCount());
                columnStat = columnCountMap.get(0L);
                columnStat.setInstallCount(columnStat.getInstallCount() + stat.getInstallCount());
                columnStat.setInstallEarning(columnStat.getInstallEarning() + stat.getInstallEarning());
                columnStat.setPushCount(columnStat.getPushCount() + stat.getPushCount());
            }
            if (hasDateStat) stats.put(date, list);
            calendar.add(Calendar.DATE, -1);
        }
        if ("true".equals(request.getParameter("hideEmpty"))) {
            for (int i = beans.size() - 1; i >= 0; i--) {
                Bean bean = beans.get(i);
                if (!bean.isHasData()) beans.remove(i);
            }
        }
        String columnAllKey = stats.size() + "日数据汇总";
        stats.put(columnAllKey, columnStats);
        request.setAttribute("beans", beans);
        request.setAttribute("stats", stats);
        return "stat/result";
    }
    @RequestMapping(value = "/batch")
    public String batchStat(ServletRequest request) {
        request.setAttribute("title", "批次统计");
        List<Bean> beans = new ArrayList<Bean>();
        String property = "batchId";
        String params = getParamsString(request, property);
        for (Long batchId : new Long[] { 1L, 2L, 3L, 4L, 5L, 10L, 11L, 12L, 13L, 14L, 15L, 50L }) {
            StringBuilder link = new StringBuilder();
            link.append("<a href='/stat/product?batchId=").append(batchId).append(params).append("'>");
            link.append(getBatchName(batchId));
            link.append("</a>");
            beans.add(new Bean(Long.valueOf(batchId), link.toString()));
        }
        List<Stat> statList = statService.getBatchData(getFromDate(), getToDate());
        return populateData(request, beans, statList);
    }
    private String getParamsString(ServletRequest request, String property) {
        StringBuilder sb = new StringBuilder();
        @SuppressWarnings("unchecked")
        Map<String, Object> map = request.getParameterMap();
        for (String key : map.keySet()) {
            if (property != null && property.equals(key)) continue;
            sb.append("&").append(key).append('=').append(request.getParameter(key));
        }
        String params = sb.toString();
        return params;
    }
    private String getBatchName(Long batchId) {
        return batchId < 10 ? "0" + batchId : "" + batchId;
    }
    private boolean showPrivateAppStat(ServletRequest request) {
        return "y".equalsIgnoreCase(request.getParameter("showPrivateApp"));
    }
    @RequestMapping(value = "/app")
    public String appStat(ServletRequest request) {
        request.setAttribute("title", "广告统计");
        List<Bean> beans = new ArrayList<Bean>();
        for (App app : appService.getAll()) {
            beans.add(new Bean(app.getId(), app.getName()));
        }
        List<Stat> statList = statService.getAppData(getFromDate(), getToDate());
        if (!showPrivateAppStat(request)) {
            //不显示私有App
            Set<Long> ids = new HashSet<Long>();
            for (App app : this.appService.getAll()) {
                if (!app.isPrivateApp()) {
                    ids.add(app.getId());
                }
            }
            List<Stat> tempList = new ArrayList<Stat>();
            for (Stat stat : statList) {
                if (ids.contains(stat.getEntityId())) {
                    tempList.add(stat);
                }
            }
            statList = tempList;
        }
        return populateData(request, beans, statList);
    }
    @RequestMapping(value = "/product")
    public String productStat(ServletRequest request) {
        Long batchId = Long.valueOf(request.getParameter("batchId"));
        request.setAttribute("title", "批次" + getBatchName(batchId) + "产品统计");
        String params = getParamsString(request, "batchId");
        List<Bean> beans = new ArrayList<Bean>();
        for (Product entity : productService.getListByBatchId(batchId)) {
            StringBuilder link = new StringBuilder();
            link.append("<a href='/stat/package?productId=").append(entity.getId()).append(params).append("'>");
            link.append(entity.getProductName());
            link.append("</a>");
            beans.add(new Bean(entity.getId(), link.toString()));
        }
        List<Stat> statList = statService.getProductData(batchId, getFromDate(), getToDate());
        return populateData(request, beans, statList);
    }
    @RequestMapping(value = "/package")
    public String packageStat(ServletRequest request) {
        Long productId = Long.valueOf(request.getParameter("productId"));
        Product product = productService.get(productId);
        request.setAttribute("title", "《" + product.getProductName() + "》软件包统计");
        List<Bean> beans = new ArrayList<Bean>();
        for (net.andwy.andwyadmin.entity.admin.Package entity : packageService.getListByProductId(productId)) {
            beans.add(new Bean(entity.getId(), entity.getMarketAccount().getDeveloper().getName() + "/" + entity.getMarketAccount().getMarket().getName()));
        }
        List<Stat> statList = statService.getPackageData(productId, getFromDate(), getToDate());
        return populateData(request, beans, statList);
    }
    @RequestMapping(value = "/developer")
    public String developerStat(ServletRequest request) {
        request.setAttribute("title", "开发者统计");
        List<Bean> beans = new ArrayList<Bean>();
        for (Developer entity : developerService.getEnabledList()) {
            beans.add(new Bean(entity.getId(), entity.getName()));
        }
        return populateData(request, beans, statService.getDeveloperData(getFromDate(), getToDate()));
    }
    private String getFromDate() {
        return dateFormat.format(new Date(System.currentTimeMillis() - 24L * 3600 * 1000 * DAYS_COUNT));
    }
    private String getToDate() {
        return dateFormat.format(new Date());
    }
}
