package net.andwy.andwyadmin.web.client;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import module.ads.bean.params.RequestParams;
import net.andwy.andwyadmin.entity.KeyValue;
import net.andwy.andwyadmin.entity.admin.Package;
import net.andwy.andwyadmin.entity.client.App;
import net.andwy.andwyadmin.entity.client.AppStat;
import net.andwy.andwyadmin.entity.client.Client;
import net.andwy.andwyadmin.entity.client.ClientPackage;
import net.andwy.andwyadmin.entity.client.ClientPushLog;
import net.andwy.andwyadmin.entity.client.Config;
import net.andwy.andwyadmin.service.Base64Util;
import net.andwy.andwyadmin.service.ServerSetting;
import net.andwy.andwyadmin.service.Util;
import net.andwy.andwyadmin.service.admin.PackageService;
import net.andwy.andwyadmin.service.client.AppService;
import net.andwy.andwyadmin.service.client.BlackClientService;
import net.andwy.andwyadmin.service.client.CategoryService;
import net.andwy.andwyadmin.service.client.ClientPackageService;
import net.andwy.andwyadmin.service.client.ClientPushLogService;
import net.andwy.andwyadmin.service.client.ClientService;
import net.andwy.andwyadmin.service.client.ConfigService;
import net.andwy.andwyadmin.service.client.IPRangeService;
import net.andwy.andwyadmin.service.stat.AppStatService;
import net.andwy.andwyadmin.service.stat.ClientStatService;
import net.andwy.andwyadmin.service.stat.PackageStatService;
import net.andwy.andwyadmin.web.client.module.ModuleController;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@Scope("request")
@RequestMapping(value = "/client")
public class ClientController {
    protected static final Logger testLogger = LoggerFactory.getLogger("TestLogger");
    private static final Logger clientLogger = LoggerFactory.getLogger("ClientLogger");
    private static final Logger fakeClientLogger = LoggerFactory.getLogger("FakeClientLogger");
    private static final Logger errorLogger = LoggerFactory.getLogger("ErrorLogger");
    protected static final Logger pushLogger = LoggerFactory.getLogger("PushLogger");
    protected static final Logger configLogger = LoggerFactory.getLogger("ConfigLogger");
    protected static final Logger recommendLogger = LoggerFactory.getLogger("RecommendLogger");
    protected static final Logger installReportLogger = LoggerFactory.getLogger("InstallReportLogger");
    protected static final Logger preDownloadLogger = LoggerFactory.getLogger("PreDownloadLogger");
    private Logger getLogger(RequestMethod method) {
        if (method != null) {
            switch (method) {
                case config:
                    return configLogger;
                case push:
                    return pushLogger;
                case recommend:
                    return recommendLogger;
                case installReport:
                    return installReportLogger;
                case preDownload:
                    return preDownloadLogger;
                default:
            }
        }
        return clientLogger;
    }
    private static final String SERVER_CONTEXT = "/6de7ba5c10d542faa771beba31428125";
    @Autowired
    private AppService appService;
    @Autowired
    private AppStatService appStatService;
    @Autowired
    private PackageStatService packageStatService;
    @Autowired
    private ClientStatService clientStatService;
    @Autowired
    private BlackClientService blackDeviceService;
    @Autowired
    private ClientPushLogService clientPushLogService;
    @Autowired
    private PackageService packageService;
    @Autowired
    private ConfigService configService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ClientService clientService;
    @Autowired
    ClientPackageService packageClientService;
    @Autowired
    IPRangeService ipRangeService;
    @Autowired
    ServerSetting ServerSetting;
    private final List<Object> lockerPool;
    private static final int LOCKER_POOL_SIZE = 10000;
    public ClientController() {
        lockerPool = new ArrayList<Object>();
        for (int i = 0; i < LOCKER_POOL_SIZE; i++) {
            lockerPool.add(new Object());
        }
    }
    private Random random = new Random();
    /*private String getTargetHost(ClientPackage clientPackage, HttpServletRequest request) {
        String host = ServerSetting.defaultHost;
        if (clientPackage != null) {
            Long batchId = clientPackage.getPkg().getProduct().getBatchId();
            if (StringUtils.isNotBlank(ServerSetting.defaultHost2) && batchId >= 10) {
                host = ServerSetting.defaultHost2;
            }
        }
        return ipRangeService.isUnicomIp(request.getRemoteAddr()) ? ServerSetting.unicomHost : host;
    }*/
    /* private String getTargetHost(HttpServletRequest request) {
         return ipRangeService.isUnicomIp(request.getRemoteAddr()) ? ServerSetting.unicomHost : ServerSetting.defaultHost;
     }*/
    public void logAccessReqeust(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder("client access:");
        sb.append(request.getRequestURL().toString()).append("?");
        for (Object key : request.getParameterMap().keySet()) {
            sb.append(key).append("=").append(request.getParameter((String) key)).append("&");
        }
        Logger logger = testLogger;
        logger.info(sb.toString());
    }
    @Autowired
    ClientControllerV2 v2;
    @RequestMapping(value = "/")
    public void handleClientRequest(HttpServletRequest request, HttpServletResponse response) {
        if (WebUtil.isRestTime()) {
            WebUtil.write(request, response, " ");
            return;
        }
        boolean isDebug = ModuleController.isClinetDebug(request);
        if (isDebug) logAccessReqeust(request);
        try {
            KeyValue<String, JSONObject> kv = null;
            ClientPackage clientPackage = null;
            RequestMethod method = null;
            JSONObject params = getParams(request);
            if (isDebug) testLogger.info("params:" + params.toString());
            if (params != null) {
                clientPackage = loadClientPackage(params, request);
                if (clientPackage == null) {
                    v2.handleClientRequest(request, response);
                    return;
                    //logAccessReqeust(request);
                    //JSONObject obj = new JSONObject();
                    //obj.put("params", params);
                    //obj.put("clientVersion", "v1");
                    //testLogger.warn("clientPackage=null,data:" + obj);
                }
                if (clientPackage != null) {
                    method = getRequestMethod(params);
                    if (isDebug) testLogger.info("method:" + method.name());
                    if (method == RequestMethod.config) {
                        kv = handleConfigRequest(clientPackage, request, response);
                    } else if (method == RequestMethod.push) {
                        kv = handlePushRequest(clientPackage, params, request);
                    } else if (method == RequestMethod.recommend) {
                        kv = handleRecommendRequest(clientPackage, params, request);
                    } else if (method == RequestMethod.app) {
                        kv = handleAppRequest(params, request);
                    } else if (method == RequestMethod.installReport) {
                        kv = handleReportInstall(clientPackage, params);
                    }
                }
            }
            if (kv != null) {
                if (kv.value == null) kv.value = new JSONObject();
                log(kv, clientPackage, params, method, request);
                WebUtil.write(request, response, kv.value);
            }
        } catch (Throwable e) {
            errorLogger.error(e.getMessage(), e);
        }
    }
    private String getRemoteAddr(HttpServletRequest request) {
        return request.getHeader("X-Real-IP");
    }
    private void log(KeyValue<String, JSONObject> kv, ClientPackage clientPackage, JSONObject params, RequestMethod method, HttpServletRequest request) {
        try {
            boolean fake = clientPackage == null;
            Logger log = fake ? fakeClientLogger : getLogger(method);
            JSONObject obj = new JSONObject();
            StringBuffer requestUrl = request.getRequestURL();
            String queryString = request.getQueryString();
            if (queryString != null) requestUrl.append("?").append(queryString);
            obj.put("url", requestUrl.toString());
            obj.put("requestData", params.optString("decodedData"));
            StringBuilder logBody = new StringBuilder();
            logBody.append(getRemoteAddr(request)).append(",http://").append(request.getHeader("HOST")).append(request.getHeader("REQUEST_PATH") + ":");
            logBody.append(method.name()).append("-");
            logBody.append(request.getRemoteAddr()).append("-");
            if (fake) {
                obj.put("rawData", params.optString("encodedData"));
            } else {
                if (kv.value != null && kv.value.length() > 0) obj.put("result", kv.value);
                logBody.append("[");
                logBody.append(clientPackage.getPkg().getProduct().getBatchId()).append(",");
                logBody.append(clientPackage.getPkg().getProduct().getProductName()).append(",");
                logBody.append(clientPackage.getPkg().getId()).append(",");
                logBody.append(clientPackage.getClient().getDeviceId());
                logBody.append("]");
            }
            if (kv.key != null) obj.put("reason", kv.key);
            logBody.append(":").append(obj.toString()).append("\n");
            log.info(logBody.toString());
        } catch (Throwable e) {
            errorLogger.error(e.getMessage(), e);
        }
    }
    private KeyValue<String, JSONObject> handleReportInstall(ClientPackage clientPackage, JSONObject params) {
        String uuid = params.optString("id");
        //long channel = params.optLong("channel");
        App app = appService.findByUid(uuid);
        if (app != null) {
            appStatService.increaseInstallStat(app.getId());
            packageStatService.increaseInstallStat(clientPackage.getPkg().getId(), app);
            clientStatService.increaseInstallStat(clientPackage.getClient().getId(), app);
        }
        return new KeyValue<String, JSONObject>(null, new JSONObject());
    }
    private KeyValue<String, JSONObject> handleAppRequest(JSONObject params, HttpServletRequest request) {
        JSONObject obj = null;
        long appId = params.optLong("aid");
        if (appId > 0) {
            App app = appService.getCached(appId);
            if (app != null) {
                obj = toJson(app, RequestMethod.app.ordinal(), true);
            }
        }
        return new KeyValue<String, JSONObject>(null, obj);
    }
    //filterExceedLimitsApps
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    protected List<App> filterExceedLimitsApps(List<App> list) {
        if (list != null && list.size() > 0) {
            Map<Long, Integer> installCountMap = new HashMap<Long, Integer>();
            for (AppStat stat : this.appStatService.getStats(dateFormat.format(new Date()))) {
                installCountMap.put(stat.getAppId(), stat.getInstallCount());
            }
            for (int i = list.size() - 1; i >= 0; i--) {
                App app = list.get(i);
                if (installCountMap.containsKey(app.getId()) && installCountMap.get(app.getId()) > app.getDailyInstallLimit()) {
                    list.remove(i);
                }
            }
        }
        return list;
    }
    private KeyValue<String, App> getPushApp(ClientPackage clientPackage, Config config, JSONObject params, ClientPushLog pushLog, boolean debug) {
        String emptyReason = null;
        if (!debug) {
            String status = clientPackage.getPkg().getPublishStatus();
            boolean publishing = "5".equals(status) || "2".equals(status);
            if (publishing) {
                Integer publishingVersionCode = clientPackage.getPkg().getPublishingVersionCode();
                if (publishingVersionCode != null && publishingVersionCode > 0) {
                    String clientVersionCode = clientPackage.getClient().getVersionCode();
                    if (StringUtils.isNotBlank(clientVersionCode) && clientVersionCode.equals(String.valueOf(publishingVersionCode))) {
                        emptyReason = "The package of the versionCode " + clientVersionCode + " is publishing,ingore ads";
                    }
                }
            }
            if (emptyReason == null) {
                long clientPushInterval = config.getDevicePushInterval();
                if (clientPushInterval > 0 && pushLog != null) {
                    //long passTime = (System.currentTimeMillis() - deviceLog.getUpdateTime().getTime()) / 1000;
                    long passTime = WebUtil.getBusinessSecondsBetween(pushLog.getUpdateTime(), new Date(), config.getPushStartHour(), config.getPushEndHour());
                    if (passTime < clientPushInterval) {
                        emptyReason = "Not device push time,passTime=" + passTime + ";devicePushInterval=" + clientPushInterval;
                    }
                }
            }
        }
        List<App> pushList = null;
        if (emptyReason == null) {
            pushList = appService.getPushList();
            if (pushList == null || pushList.size() == 0) {
                emptyReason = "appService.getPushList() is empty";
            }
        }
        if (emptyReason == null) {
            filterInstalledApp(pushList, params);
            if (CollectionUtils.isEmpty(pushList)) {
                emptyReason = "list is emtpy after invoke filterInstalledApp(pushList, params)";
            }
        }
        if (emptyReason == null) {
            pushList = filterExceedLimitsApps(pushList);
            if (CollectionUtils.isEmpty(pushList)) {
                emptyReason = "list is emtpy after invoke filterExceedLimitsApps(pushList, params)";
            }
        }
        if (emptyReason == null) {
            if (!debug) {
                long appPushInterval = config.getAppPushInterval();
                Map<Long, Date> map = getPushTimeMap(pushLog);
                StringBuilder sb = new StringBuilder();
                for (int i = pushList.size() - 1; i >= 0; i--) {
                    App app = pushList.get(i);
                    boolean remove = !isAppPushTime(app, sb);
                    if (!remove) {
                        if (map.containsKey(app.getId())) {
                            Date lastTime = map.get(app.getId());
                            long passTime = WebUtil.getBusinessSecondsBetween(lastTime, new Date(), config.getPushStartHour(), config.getPushEndHour());
                            if (passTime < appPushInterval) {
                                sb.append("ClientAppPushLog(").append(app.getName()).append(",passTime:").append(passTime).append(",configAppPushInterval:")
                                        .append(appPushInterval).append(")\n");
                                remove = true;
                            }
                        }
                    }
                    if (remove) pushList.remove(i);
                }
                if (pushList.isEmpty()) emptyReason = sb.toString();
            }
        }
        App app = null;
        if (!CollectionUtils.isEmpty(pushList)) {
            if (config.getPushStrategy() == 1) {
                app = pickAppByProbability(pushList);
            } else {
                app = pickAppByHotOrder(pushList, pushLog);
            }
        }
        return new KeyValue<String, App>(emptyReason, app);
    }
    private Map<Long, Date> getPushTimeMap(ClientPushLog pushLog) {
        Map<Long, Date> map = new HashMap<Long, Date>();
        if (pushLog != null) {
            String timesDetail = pushLog.getTimeDetail();
            if (StringUtils.isNotBlank(timesDetail)) {
                for (String appTime : timesDetail.split("\\|")) {
                    if (appTime.contains(",")) {
                        String[] fields = appTime.split(",");
                        if (fields.length == 2) map.put(Long.valueOf(fields[0].trim()), new Date(Long.valueOf(fields[1].trim())));
                    }
                }
            }
        }
        return map;
    }
    private App pickAppByHotOrder(List<App> pushList, ClientPushLog pushLog) {
        if (pushLog != null) {
            String detail = pushLog.getDetail();
            if (!StringUtils.isEmpty(detail)) {
                final Map<Long, Integer> pushCountMap = new HashMap<Long, Integer>();
                for (App app : pushList) {
                    pushCountMap.put(app.getId(), 0);
                }
                populateExistCount(pushCountMap, detail);
                Collections.sort(pushList, new Comparator<App>() {
                    @Override
                    public int compare(App app1, App app2) {
                        int result = pushCountMap.get(app1.getId()).compareTo(pushCountMap.get(app2.getId()));
                        if (result == 0) {
                            result = app2.getHot().compareTo(app1.getHot());
                        }
                        return result;
                    }
                });
            }
        }
        return pushList.get(0);
    }
    private void populateExistCount(final Map<Long, Integer> pushCountMap, String detail) {
        if (StringUtils.isNotEmpty(detail)) {
            for (String s : detail.split("\\|")) {
                if (s.contains(",")) {
                    String[] parts = s.split(",");
                    if (parts.length == 2) {
                        pushCountMap.put(Long.valueOf(parts[0]), Integer.valueOf(parts[1]));
                    }
                }
            }
        }
    }
    /**
     * 根据App的热度概率取App
     * @param pushList
     * @return
     */
    private App pickAppByProbability(List<App> pushList) {
        if (CollectionUtils.isEmpty(pushList)) return null;
        if (pushList.size() == 1) return pushList.get(0);
        List<Integer> indexList = new ArrayList<Integer>();
        for (int index = pushList.size() - 1; index >= 0; index--) {
            App app = pushList.get(index);
            long hot = app.getHot();
            while (hot-- >= 0) {
                indexList.add(index);
            }
        }
        return pushList.get(indexList.get(random.nextInt(indexList.size())));
    }
    private boolean isAppPushTime(App app, StringBuilder sb) {
        boolean result = false;
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (app.getPushStartHour() <= currentHour && app.getPushEndHour() >= currentHour) {
            if (appPushTime.containsKey(app.getId())) {
                long lastTime = appPushTime.get(app.getId());
                double passTime = (System.currentTimeMillis() - lastTime) * 1.00001 / 1000;
                //fakeClientLogger.info("lastTime:"+lastTime+";passTime:"+passTime+";appPushInterval:"+app.getPushInterval());
                int pushInterval = getInterval(app.getPushStartHour().intValue(), app.getPushEndHour().intValue(), app.getPushInterval());
                if (passTime > pushInterval) {
                    result = true;
                } else {
                    sb.append("isAppPushTime(id:").append(app.getId()).append(",passTime:").append(passTime).append(",appPushInterval:").append(pushInterval).append(")\n");
                }
            } else {
                result = true;
            }
        } else {
            sb.append("isAppPushTime(id:").append(app.getId()).append(",appPushStartHour:").append(app.getPushStartHour()).append(",appPushEndHour:").append(app.getPushEndHour())
                    .append(")\n");
        }
        return result;
    }
    private Map<Long, Long> appPushTime = new HashMap<Long, Long>();
    private void filterInstalledApp(List<App> list, JSONObject params) {
        if (CollectionUtils.isEmpty(list)) return;
        Set<Long> installedApp = getInstalledPackage(params);
        for (int i = list.size() - 1; i >= 0; i--) {
            App app = list.get(i);
            if (installedApp.contains(app.getId())) {
                list.remove(i);
            }
        }
    }
    private Set<Long> getInstalledPackage(JSONObject params) {
        Set<Long> set = new HashSet<Long>();
        JSONArray array = params.optJSONArray("list");
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                long id = array.optLong(i);
                if (id > 0) {
                    set.add(id);
                }
            }
        }
        return set;
    }
    public static final int PUSH_TYPE_SHOW_DETAIL = 1;
    public static final int PUSH_TYPE_PRE_DOWNLOAD_SHOW_DETAIL = 2;
    public static final int PUSH_TYPE_NOTIFY_INSTALL = 3;
    public static final int PUSH_TYPE_DIRECT_INSTALL = 4;
    private KeyValue<String, JSONObject> handlePushRequest(ClientPackage clientPackage, JSONObject params, HttpServletRequest request) {
        String reason = null;
        JSONObject obj = null;
        if (reason == null) {
            Config config = configService.getConfig(clientPackage.getPkg());
            if (config != null) {
                boolean debug = request.getParameter("debug") != null;
                if (!debug) {
                    if (reason == null && !"Y".equals(config.getEnablePush())) {
                        reason = "handlePushRequest:(config.getEnablePush()=" + config.getEnablePush() + ")";
                    }
                    if (reason == null) {
                        long start = config.getPushStartHour();
                        long end = config.getPushEndHour();
                        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                        boolean innerTime = currentHour >= start && currentHour <= end || currentHour < start && end >= 24 && end % 24 >= currentHour;
                        if (!innerTime) {
                            reason = "handlePushRequest(Outbound hour,currentHour=" + currentHour + ";start=" + start + ";end=" + end + ")";
                        }
                    }
                    if (reason == null) {
                        long passTime = (System.currentTimeMillis() - clientPackage.getCreateTime().getTime()) / 1000;
                        if (passTime < config.getPushRequestInterval()) {
                            reason = "handlePushRequest:(CreateTime[" + passTime + "]<pushRequestInterval[" + config.getPushRequestInterval() + "])";
                        }
                    }
                    if (reason == null && blackDeviceService.isBlack(clientPackage.getClient())) {
                        reason = "handlePushRequest:(Black User)";
                    }
                }
                if (reason == null) {
                    ClientPushLog pushLog = clientPushLogService.findByClientId(clientPackage.getClient());
                    KeyValue<String, App> selectOne = getPushApp(clientPackage, config, params, pushLog, debug);
                    App app = selectOne.value;
                    if (app != null) {
                        createPushLog(clientPackage, app, pushLog);
                        int pushType = params.optInt("pushType");
                        //boolean showDetailInfo = pushType == 0 || pushType == PUSH_TYPE_SHOW_DETAIL || pushType == PUSH_TYPE_PRE_DOWNLOAD_SHOW_DETAIL;
                        boolean showDetailInfo = pushType == PUSH_TYPE_SHOW_DETAIL || pushType == PUSH_TYPE_PRE_DOWNLOAD_SHOW_DETAIL;
                        obj = toJson(app, RequestMethod.push.ordinal(), showDetailInfo);
                        appPushTime.put(app.getId(), System.currentTimeMillis());
                    } else {
                        reason = "handlePushRequest:(" + selectOne.key + ")";
                    }
                }
            } else {
                reason = "handlePushRequest:(config==null)";
            }
        }
        return new KeyValue<String, JSONObject>(reason, obj);
    }
    private JSONObject toJson(App app, int channel, boolean showDetailInfo) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", app.getName());
            obj.put("id", app.getUid());
            obj.put("iconUrl", app.getIconUrl());
            obj.put("pkgName", app.getPkgName());
            obj.put("apkUrl", app.getApkUrl());
            obj.put("hint", app.getHint());
            obj.put("channel", channel);
            if (showDetailInfo) {
                obj.put("pkgVersionCode", app.getPkgVersionCode());
                obj.put("pkgVersionName", app.getPkgVersionName());
                obj.put("description", app.getDescription());
                String size = app.getSize().trim();
                int bytesSize = 0;
                if (size.endsWith("M")) bytesSize = (int) (Float.valueOf(size.replaceAll("M", "").trim()) * 1024 * 1024);
                else bytesSize = Integer.valueOf(size.replaceAll("[\\D]", ""));
                String readableSize = getReadableSize(bytesSize);
                obj.put("size", readableSize);
                obj.put("bytesSize", bytesSize);
                obj.put("type", app.getType());
                obj.put("provider", app.getAdvertiser());
                StringBuilder sb = new StringBuilder();
                append(sb, app.getScreen1Url());
                append(sb, app.getScreen2Url());
                obj.put("screenUrl", sb.toString());
            }
        } catch (Throwable e) {
            errorLogger.error(e.getMessage(), e);
        }
        return obj;
    }
    private static String getReadableSize(int bytesSize) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        if (bytesSize > 1024 * 1024 * 1024) return df.format(bytesSize * 1.0 / 1024 / 1024 / 1024) + "G";
        else if (bytesSize > 1024 * 1024) return df.format(bytesSize * 1.0 / 1024 / 1024) + "M";
        else if (bytesSize > 1024) return df.format(bytesSize * 1.0 / 1024) + "K";
        return bytesSize + "B";
    }
    private void append(StringBuilder sb, String url) {
        if (url != null) {
            url = url.trim();
            if (url.length() > 5) {
                sb.append(url).append(";");
            }
        }
    }
    private KeyValue<String, JSONObject> handleConfigRequest(ClientPackage clientPackage, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String reason = null;
        JSONObject result = null;
        if (clientPackage != null) {
            Config config = configService.getConfig(clientPackage.getPkg());
            if (config != null) {
                result = new JSONObject();
                //Push related fields
                result.put("pint", config.getPushRequestInterval());
                result.put("pstart", config.getPushStartHour());
                result.put("pend", config.getPushEndHour());
                result.put("ptype", config.getPushType());
                //TODO --------start, RECHECK BELOW LINE----------
                boolean debug = request.getParameter("debug") != null;
                if (debug) result.put("showSpirit", true);
                //public boolean showSpirit;
                //public int spiritType;
                //TODO ----------end-----------, RECHECK BELOW LINE
                //Recommend fields
                result.put("rint", config.getRecommendRequestInterval());
                result.put("rtype", config.getRecommendType());
                result.put("rexpire", config.getRecommendExpireTime());
                result.put("re", "Y".equals(config.getEnableRecommend()));
                //if (!ServerSetting.debug) {
                //Check the host name
                //String host = getTargetHost(clientPackage, request);
                //String requestHost = new URL(request.getRequestURL().toString()).getHost();
                // if (!host.equalsIgnoreCase(requestHost)) result.put("surl", "http://" + host + SERVER_CONTEXT);
                //}
                result.put("cid", clientPackage.getId());
                JSONArray array = new JSONArray();
                //
                List<App> list = appService.getPushList();
                for (App app : list) {
                    JSONObject o = new JSONObject();
                    o.put("id", app.getId());
                    o.put("name", app.getPkgName());
                    array.put(o);
                }
                result.put("list", array);
            } else {
                reason = "handleConfigRequest: config is null";
            }
        } else {
            reason = "handleConfigRequest: clientPackage is null";
        }
        return new KeyValue<String, JSONObject>(reason, result);
    }
    private KeyValue<String, JSONObject> handleRecommendRequest(ClientPackage clientPackage, JSONObject params, HttpServletRequest request) {
        JSONObject result = null;
        String reason = null;
        try {
            Config config = configService.getConfig(clientPackage.getPkg());
            if (config != null) {
                boolean debug = request.getParameter("debug") != null;
                if (!debug) {
                    if (blackDeviceService.isBlack(clientPackage.getClient())) {
                        reason = "黑名单";
                    }
                    if (reason == null && !"Y".equals(config.getEnableRecommend())) {
                        reason = "推荐列表功能没打开";
                    }
                    Long adsVersion = clientPackage.getClient().getAdsVersion();
                    if (reason == null && (adsVersion == null || adsVersion < 4)) {
                        reason = "客户端广告版本小于4:" + adsVersion;
                    }
                }
                int appType = params.optInt("appType");
                if (appType != 0 && appType != 1) {
                    reason = "没有合适的appType:" + appType;
                }
                if (reason == null) {
                    List<App> list = null;
                    switch (appType) {
                        case 0:
                            list = appService.getAppRecommendList();
                            break;
                        case 1:
                            list = appService.getGameRecommendList();
                            break;
                    }
                    filterInstalledApp(list, params);
                    if (list != null) {
                        result = new JSONObject();
                        JSONArray appsArray = new JSONArray();
                        for (App app : list) {
                            appsArray.put(toJson(app, RequestMethod.recommend.ordinal(), true));
                        }
                        result.put("app", appsArray);
                        JSONObject stat = new JSONObject();
                        stat.put("count", list.size() + 2);
                        stat.put("type", "game");
                        result.put("stat", stat);
                    } else {
                        reason = "列表为空";
                    }
                }
            } else {
                reason = "设置为空";
            }
        } catch (Throwable e) {
            errorLogger.error(e.getMessage(), e);
            reason = "未知错误:" + e.getMessage();
        }
        return new KeyValue<String, JSONObject>(reason, result);
    }
    private void createPushLog(ClientPackage clientPackage, App app, ClientPushLog pushLog) {
        Client client = clientPackage.getClient();
        {
            Date date = new Date();
            if (pushLog == null) {
                pushLog = new ClientPushLog();
                pushLog.setCreateTime(date);
                pushLog.setClient(client);
                pushLog.setStatus("E");
            }
            pushLog.setUpdateTime(date);
            {
                String detail = pushLog.getDetail();
                Map<Long, Integer> pushCount = new HashMap<Long, Integer>();
                populateExistCount(pushCount, detail);
                Long appId = app.getId();
                pushCount.put(appId, pushCount.containsKey(appId) ? pushCount.get(appId) + 1 : 1);
                StringBuilder sb = new StringBuilder();
                for (Long id : pushCount.keySet()) {
                    sb.append(id).append(",").append(pushCount.get(id)).append("|");
                }
                pushLog.setDetail(sb.toString());
            }
            {
                Map<Long, Date> map = getPushTimeMap(pushLog);
                map.put(app.getId(), new Date());
                StringBuilder timeDetail = new StringBuilder();
                for (Long id : map.keySet()) {
                    timeDetail.append(id).append(",").append(map.get(id).getTime()).append("|");
                }
                pushLog.setTimeDetail(timeDetail.toString());
            }
            clientPushLogService.save(pushLog);
        }
        appStatService.increasePushStat(app.getId());
        packageStatService.increasePushStat(clientPackage.getPkg().getId(), app);
    }
    private Object getLocker(String s) {
        return lockerPool.get(Math.abs(s.hashCode()) % LOCKER_POOL_SIZE);
    }
    private JSONObject getParams(HttpServletRequest request) {
        JSONObject obj = null;
        String encodedData = request.getParameter("p");
        String decodedData = null;
        if (!Util.isEmpty(encodedData)) {
            try {
                decodedData = Base64Util.decodeString(encodedData);
                obj = new JSONObject(decodedData);
            } catch (Throwable e) {
                errorLogger.error(e.getMessage(), e);
            }
        }
        if (obj == null) obj = new JSONObject();
        try {
            obj.put("encodedData", encodedData);
            obj.put("decodedData", decodedData);
        } catch (Throwable e) {
            errorLogger.error(e.getMessage(), e);
        }
        return obj;
    }
    private RequestMethod getRequestMethod(JSONObject params) {
        String method = params.optString("method");
        if ("p".equals(method)) return RequestMethod.push;
        if ("r".equals(method)) return RequestMethod.recommend;
        if ("c".equals(method)) return RequestMethod.config;
        if ("a".equals(method)) return RequestMethod.app;
        if ("i".equals(method)) return RequestMethod.installReport;
        return null;
    }
    private void updateClientFields(JSONObject params, Client client, HttpServletRequest request) {
        client.setScreenSize(params.optString("screenSize"));
        client.setNetworkOperator(params.optString("networkOperator"));
        client.setNetworkSubType(params.optString("networkSubType"));
        client.setPhoneModel(params.optString("phoneModel"));
        client.setVersion(params.optString("version"));
        client.setAndroidId(params.optString("android_id"));
        client.setWifi(params.optString("wifi"));
        client.setManufacturer(params.optString("manufacturer"));
        client.setIsTablet(params.optString("isTablet"));
        client.setUnknownSource(params.optString("unknownsource"));
        client.setCarrier(params.optString("carrier"));
        client.setLanguage(params.optString("language"));
        client.setIsConnectionFast(params.optString("isConnectionFast"));
        client.setUserAgent(request.getHeader("User-Agent"));
        client.setPhoneNumber(params.optString("pn"));
        client.setAvailableInternalMemorySize(params.optLong("availableInternalMemorySize"));
        client.setTotalInternalMemorySize(params.optLong("totalInternalMemorySize"));
        client.setAdsVersion(params.optLong("publiciteVersion"));
        client.setPackageName(params.optString("packageName"));
        client.setVersionCode(params.optString("versionCode"));
        client.setVersionName(params.optString("versionName"));
    }
    protected ClientPackage loadClientPackage(JSONObject params, HttpServletRequest request) {
        ClientPackage clientPackage = null;
        try {
            Date current = new Date();
            String deviceId = params.optString("did");
            String packageUid = params.optString("pid");
            RequestMethod method = getRequestMethod(params);
            if (method != null && deviceId != null && deviceId.length() > 6 && packageUid != null && packageUid.length() == 32) {
                Package pkg = packageService.findByUid(packageUid);
                if (pkg != null) {
                    Client client;
                    synchronized (getLocker(deviceId)) {
                        client = clientService.findByDeviceId(deviceId);
                        if (client == null) {
                            client = new Client(deviceId);
                            client.setCreateTime(current);
                            client.setStatus("E");
                        }
                        if (method == RequestMethod.config) updateClientFields(params, client, request);
                        client.setUpdateTime(current);
                        client = clientService.save(client);
                    }
                    String lockerKey = "locker-" + pkg.getId() + "-uniq-" + client.getId();
                    synchronized (getLocker(lockerKey)) {
                        clientPackage = packageClientService.findByUniqKey(pkg.getId(), client.getId());
                        if (clientPackage == null) {
                            clientPackage = new ClientPackage();
                            clientPackage.setClient(client);
                            clientPackage.setPkg(pkg);
                            clientPackage.setCreateTime(current);
                            clientPackage.setStatus("E");
                        }
                        clientPackage.setUpdateTime(current);
                        clientPackage = packageClientService.save(clientPackage);
                    }
                }
            }
        } catch (Throwable e) {
            errorLogger.error(e.getMessage(), e);
        }
        return clientPackage;
    }
    /**
     * 
     * @param startHour 9
     * @param endHour 23
     * @param intervalDefine, e.g. "21,22,23,24";
     * @return
     */
    private static int getInterval(int startHour, int endHour, String intervalDefine) {
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (currentHour < startHour || currentHour > endHour) return Integer.MAX_VALUE / 2;
        int interval = 20;
        List<Integer> intervalList = new ArrayList<Integer>();
        if (!StringUtils.isBlank(intervalDefine)) {
            intervalDefine = intervalDefine.trim();
            int index = intervalDefine.indexOf(",");
            if (index < 0) {
                try {
                    intervalList.add(Integer.valueOf(intervalDefine));
                } catch (NumberFormatException e) {
                    errorLogger.error(e.getMessage(), e);
                }
            } else {
                String[] fields = intervalDefine.split(",");
                for (String field : fields) {
                    if (!StringUtils.isBlank(field)) {
                        try {
                            intervalList.add(Integer.valueOf(field.trim()));
                        } catch (Throwable e) {
                            errorLogger.error(e.getMessage(), e);
                        }
                    }
                }
            }
        }
        int size = intervalList.size();
        if (size == 1) {
            interval = intervalList.get(0);
        } else if (size > 1) {
            int intervalLength = endHour - startHour + 1;
            if (size > intervalLength) size = intervalLength;
            int index = (currentHour - startHour - 1) / (intervalLength / size);
            if (index > size - 1) index = size - 1;
            interval = intervalList.get(index);
        }
        return interval;
    }
    /*private boolean isGzipSupported(HttpServletRequest request) {
    String encoding = (String) request.getHeader("Accept-Encoding");
    return ((encoding != null) && (encoding.indexOf("gzip") != -1));
    }*/
}
