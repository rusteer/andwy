package net.andwy.andwyadmin.web.client.module;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import module.ads.bean.CApp;
import module.ads.bean.PushType;
import module.ads.bean.params.RequestParams;
import net.andwy.andwyadmin.entity.client.App;
import net.andwy.andwyadmin.entity.client.AppStat;
import net.andwy.andwyadmin.entity.client.ClientPackage;
import net.andwy.andwyadmin.entity.client.ClientPushLog;
import net.andwy.andwyadmin.service.ServerSetting;
import net.andwy.andwyadmin.service.client.AppService;
import net.andwy.andwyadmin.service.client.BlackClientService;
import net.andwy.andwyadmin.service.client.ClientPushLogService;
import net.andwy.andwyadmin.service.client.ConfigService;
import net.andwy.andwyadmin.service.stat.AppStatService;
import net.andwy.andwyadmin.service.stat.PackageStatService;
import net.andwy.andwyadmin.web.client.RequestMethod;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModuleController {
    protected static final Logger errorLogger = LoggerFactory.getLogger("ErrorLogger");
    @Autowired
    ServerSetting ServerSetting;
    @Autowired
    protected AppService appService;
    @Autowired
    protected ConfigService configService;
    @Autowired
    protected AppStatService appStatService;
    @Autowired
    protected PackageStatService packageStatService;
    @Autowired
    protected BlackClientService blackDeviceService;
    @Autowired
    protected ClientPushLogService clientPushLogService;
    public static boolean isClinetDebug(HttpServletRequest request) {
        return request.getParameter("d") != null;
        //return true;
    }
    protected CApp toCApp(App app, RequestMethod requestMethod, PushType pushType) {
        int channel = requestMethod.ordinal();
        CApp capp = new CApp();
        capp.name = app.getName();
        capp.uuid = app.getUid();
        capp.iconUrl = app.getIconUrl();
        capp.pkgName = app.getPkgName();
        capp.apkUrl = app.getApkUrl();
        capp.hint = app.getHint();
        //String title = TextUtils.isEmpty(app.pushTitle) ? "为您推荐:" + app.name : app.pushTitle;
        //public String pushTitle;
        //  String text = TextUtils.isEmpty(app.pushShortDescription) ? app.hint : app.pushShortDescription;
        //public String pushShortDescription;
        capp.pushTitle = capp.name;
        capp.pushShortDescription = capp.hint;
        boolean isPush = requestMethod == RequestMethod.push;
        if (isPush) capp.pushType = pushType.ordinal();
        capp.channel = channel;
        boolean showDetailInfo = !isPush || pushType == PushType.SHOW_DETAIL || pushType == PushType.PRE_DOWNLOAD_SHOW_DETAIL;
        if (showDetailInfo) {
            capp.pkgVersionCode = Integer.valueOf(app.getPkgVersionCode());
            capp.pkgVersionName = app.getPkgVersionName();
            capp.description = app.getDescription();
            String size = app.getSize().trim();
            int bytesSize = 0;
            if (size.endsWith("M")) bytesSize = (int) (Float.valueOf(size.replaceAll("M", "").trim()) * 1024 * 1024);
            else bytesSize = Integer.valueOf(size.replaceAll("[\\D]", ""));
            capp.size = bytesSize;
            capp.provider = app.getAdvertiser();
            StringBuilder sb = new StringBuilder();
            append(sb, app.getScreen1Url());
            append(sb, app.getScreen2Url());
            capp.screenUrl = sb.toString();
        }
        return capp;
    }
    private void append(StringBuilder sb, String url) {
        if (url != null) {
            url = url.trim();
            if (url.length() > 5) {
                sb.append(url).append(";");
            }
        }
    }
    protected JSONObject toJson(App app, RequestMethod requestMethod, PushType pushType) {
        CApp capp = toCApp(app, requestMethod, pushType);
        try {
            return capp.toJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected boolean isPublishing(ClientPackage clientPackage) {
        String status = clientPackage.getPkg().getPublishStatus();
        boolean publishing = "5".equals(status) || "2".equals(status);
        if (publishing) {
            Integer publishingVersionCode = clientPackage.getPkg().getPublishingVersionCode();
            if (publishingVersionCode != null && publishingVersionCode > 0) {
                String clientVersionCode = clientPackage.getClient().getVersionCode();
                if (StringUtils.isNotBlank(clientVersionCode) && clientVersionCode.equals(String.valueOf(publishingVersionCode))) { return true; }
            }
        }
        return false;
    }
    //filterExceedLimitsApps
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    protected List<App> filterExceedLimitsApps(List<App> list, RequestParams params) {
        if (list != null) {
            if (CollectionUtils.isNotEmpty(list)) {
                Map<Long, Integer> installCountSet = new HashMap<Long, Integer>();
                for (AppStat stat : this.appStatService.getStats(dateFormat.format(new Date()))) {
                    installCountSet.put(stat.getAppId(), stat.getInstallCount());
                }
                for (int i = list.size() - 1; i >= 0; i--) {
                    App app = list.get(i);
                    if (installCountSet.containsKey(app.getId()) && installCountSet.get(app.getId()) > app.getDailyInstallLimit()) {
                        list.remove(i);
                    }
                }
            }
        }
        return list;
    }
    protected List<App> filterInstalledApp(List<App> list, RequestParams params) {
        if (list != null) {
            if (CollectionUtils.isNotEmpty(list)) {
                Set<Long> installedApp = getInstalledPackage(params);
                for (int i = list.size() - 1; i >= 0; i--) {
                    App app = list.get(i);
                    if (installedApp.contains(app.getId())) {
                        list.remove(i);
                    }
                }
            }
        }
        return list;
    }
    private Set<Long> getInstalledPackage(RequestParams params) {
        Set<Long> set = new HashSet<Long>();
        if (params.installedList != null) {
            for (Long id : params.installedList)
                set.add(id);
        }
        return set;
    }
    protected List<App> pickAppByHotOrder(List<App> pushList, ClientPushLog pushLog, int count) {
        List<App> result = new ArrayList<App>();
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
        for (int i = 0; i < pushList.size() && i < count; i++) {
            result.add(pushList.get(i));
        }
        return result;
    }
    protected void populateExistCount(final Map<Long, Integer> pushCountMap, String detail) {
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
}
