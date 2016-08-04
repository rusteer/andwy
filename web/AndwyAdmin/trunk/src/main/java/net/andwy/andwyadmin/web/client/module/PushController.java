package net.andwy.andwyadmin.web.client.module;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import module.ads.bean.PushType;
import module.ads.bean.params.PushParams;
import net.andwy.andwyadmin.entity.KeyValue;
import net.andwy.andwyadmin.entity.client.App;
import net.andwy.andwyadmin.entity.client.Client;
import net.andwy.andwyadmin.entity.client.ClientPackage;
import net.andwy.andwyadmin.entity.client.ClientPushLog;
import net.andwy.andwyadmin.entity.client.Config;
import net.andwy.andwyadmin.web.client.RequestMethod;
import net.andwy.andwyadmin.web.client.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class PushController extends ModuleController {
    public KeyValue<String, JSONObject> execute(ClientPackage clientPackage, PushParams params, HttpServletRequest request) {
        String reason = null;
        JSONObject obj = null;
        if (reason == null) {
            Config config = configService.getConfig(clientPackage.getPkg());
            if (config != null) {
                boolean debug = isClinetDebug(request);
                if (!debug) {
                    if (reason == null && !"Y".equals(config.getEnablePush())) {
                        reason = "handlePushRequest:(config.getEnablePush()=" + config.getEnablePush() + ")";
                    }
                    if ("Xiaomi".equalsIgnoreCase(clientPackage.getClient().getManufacturer())) {
                        reason = "小米手机暂停推送";
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
                    KeyValue<String, App> selectOne = getApp(clientPackage, config, params, pushLog, debug);
                    App app = selectOne.value;
                    if (app != null) {
                        createPushLog(clientPackage, app, pushLog);
                        obj = toJson(app, RequestMethod.push, PushType.get(config.getPushType().intValue() - 1));
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
    private KeyValue<String, App> getApp(ClientPackage clientPackage, Config config, PushParams params, ClientPushLog pushLog, boolean debug) {
        String emptyReason = null;
        if (!debug) {
            if (isPublishing(clientPackage)) emptyReason = "The package of the versionCode " + clientPackage.getPkg().getPublishingVersionCode() + " is publishing,ingore ads";
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
            filterExceedLimitsApps(pushList, params);
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
                List<App> list = pickAppByHotOrder(pushList, pushLog, 1);
                if (list.size() == 1) app = list.get(0);
            }
        }
        return new KeyValue<String, App>(emptyReason, app);
    }
    private Random random = new Random();
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
    private Map<Long, Long> appPushTime = new HashMap<Long, Long>();
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
}
