package net.andwy.andwyadmin.web.client;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import module.ads.bean.CClient;
import module.ads.bean.params.CommonParams;
import module.ads.bean.params.ConfigParams;
import module.ads.bean.params.InstallParams;
import module.ads.bean.params.PreDownloadParams;
import module.ads.bean.params.PushParams;
import module.ads.bean.params.RecommendParams;
import net.andwy.andwyadmin.entity.KeyValue;
import net.andwy.andwyadmin.entity.admin.Package;
import net.andwy.andwyadmin.entity.client.Client;
import net.andwy.andwyadmin.entity.client.ClientPackage;
import net.andwy.andwyadmin.service.Base64Util;
import net.andwy.andwyadmin.service.Util;
import net.andwy.andwyadmin.service.admin.PackageService;
import net.andwy.andwyadmin.service.client.ClientPackageService;
import net.andwy.andwyadmin.service.client.ClientService;
import net.andwy.andwyadmin.web.client.module.ConfigController;
import net.andwy.andwyadmin.web.client.module.InstallReportController;
import net.andwy.andwyadmin.web.client.module.ModuleController;
import net.andwy.andwyadmin.web.client.module.PreDownloadController;
import net.andwy.andwyadmin.web.client.module.PushController;
import net.andwy.andwyadmin.web.client.module.RecommendController;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import common.bean.Json;

@Controller
//@Scope("request")
@RequestMapping(value = "/clientV2")
public class ClientControllerV2 {
    protected static final Logger testLogger = LoggerFactory.getLogger("TestLogger");
    protected static final Logger clientLogger = LoggerFactory.getLogger("ClientLogger");
    protected static final Logger fakeClientLogger = LoggerFactory.getLogger("FakeClientLogger");
    protected static final Logger errorLogger = LoggerFactory.getLogger("ErrorLogger");
    //
    protected static final Logger pushLogger = LoggerFactory.getLogger("PushLogger");
    protected static final Logger configLogger = LoggerFactory.getLogger("ConfigLogger");
    protected static final Logger recommendLogger = LoggerFactory.getLogger("RecommendLogger");
    protected static final Logger installReportLogger = LoggerFactory.getLogger("InstallReportLogger");
    protected static final Logger preDownloadLogger = LoggerFactory.getLogger("PreDownloadLogger");
    @Autowired
    private PackageService packageService;
    @Autowired
    ClientService clientService;
    @Autowired
    ClientPackageService packageClientService;
    @Autowired
    private ConfigController configController;
    @Autowired
    private PushController pushController;
    @Autowired
    private RecommendController recommendController;
    @Autowired
    InstallReportController installReportController;
    @Autowired
    PreDownloadController preDownloadController;
    private final List<Object> lockerPool;
    private static final int LOCKER_POOL_SIZE = 10000;
    public ClientControllerV2() {
        lockerPool = new ArrayList<Object>();
        for (int i = 0; i < LOCKER_POOL_SIZE; i++) {
            lockerPool.add(new Object());
        }
    }
    private Logger getLogger(HttpServletRequest request, RequestMethod method) {
        if (ModuleController.isClinetDebug(request)) return testLogger;
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
            if (isDebug) testLogger.info(params.toString());
            if (params != null) {
                CommonParams commonParams = Json.optObj(CommonParams.class, params);
                clientPackage = loadClientPackage(commonParams, params, request);
                if (clientPackage == null) {
                    logAccessReqeust(request);
                    JSONObject obj = new JSONObject();
                    obj.put("params", params);
                    obj.put("clientVersion", "v2");
                    testLogger.warn("clientPackage=null,data:" + obj);
                }
                if (clientPackage != null) {
                    method = getRequestMethod(commonParams.method);
                    switch (method) {
                        case config:
                            kv = configController.execute(clientPackage, request, response);
                            break;
                        case push:
                            kv = pushController.execute(clientPackage, Json.optObj(PushParams.class, params), request);
                            break;
                        case recommend:
                            kv = recommendController.execute(clientPackage, Json.optObj(RecommendParams.class, params), request);
                            break;
                        case installReport:
                            kv = installReportController.execute(clientPackage, Json.optObj(InstallParams.class, params));
                            break;
                        case preDownload:
                            kv = preDownloadController.execute(clientPackage, Json.optObj(PreDownloadParams.class, params));
                        default:
                            break;
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
    public void logAccessReqeust(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder("client access:");
        sb.append(request.getRequestURL().toString()).append("?");
        for (Object key : request.getParameterMap().keySet()) {
            sb.append(key).append("=").append(request.getParameter((String) key)).append("&");
        }
        Logger logger = testLogger;
        logger.info(sb.toString());
    }
    private String getRemoteAddr(HttpServletRequest request) {
        return request.getHeader("X-Real-IP");
    }
    private void log(KeyValue<String, JSONObject> kv, ClientPackage clientPackage, JSONObject params, RequestMethod method, HttpServletRequest request) {
        try {
            boolean fake = clientPackage == null;
            Logger log = fake ? fakeClientLogger : getLogger(request, method);
            JSONObject obj = new JSONObject();
            StringBuffer requestUrl = request.getRequestURL();
            String queryString = request.getQueryString();
            if (queryString != null) requestUrl.append("?").append(queryString);
            obj.put("url", requestUrl.toString());
            obj.put("requestData", params.optString("decodedData"));
            StringBuilder logBody = new StringBuilder();
            logBody.append(getRemoteAddr(request)).append(",http://").append(request.getHeader("HOST")).append(request.getHeader("REQUEST_PATH") + ":");
            logBody.append(method.name()).append("-");
            //logBody.append(request.getRemoteAddr()).append("-");
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
    private RequestMethod getRequestMethod(String method) {
        if ("p".equals(method)) return RequestMethod.push;
        if ("r".equals(method)) return RequestMethod.recommend;
        if ("c".equals(method)) return RequestMethod.config;
        if ("a".equals(method)) return RequestMethod.app;
        if ("i".equals(method)) return RequestMethod.installReport;
        if ("prd".equals(method)) return RequestMethod.preDownload;
        return null;
    }
    protected ClientPackage loadClientPackage(CommonParams params, JSONObject rawParams, HttpServletRequest request) {
        ClientPackage clientPackage = null;
        try {
            Date current = new Date();
            String deviceId = params.deviceId;
            String packageUid = params.packageId;
            RequestMethod method = getRequestMethod(params.method);
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
                        if (method == RequestMethod.config) updateClientFields(rawParams, client, request);
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
    private void updateClientFields(JSONObject params, Client client, HttpServletRequest request) {
        ConfigParams configParams = Json.optObj(ConfigParams.class, params);
        CClient c = configParams.client;
        client.setVersion(String.valueOf(c.sdkVersion));
        client.setCarrier(c.carrier);
        client.setNetworkOperator(c.networkOperator);
        client.setPhoneModel(c.phoneModel);
        client.setManufacturer(c.manufacturer);
        client.setAndroidId(c.androidId);
        client.setScreenSize(c.screenSize);
        client.setNetworkSubType(c.networkSubType);
        client.setIsTablet(String.valueOf(c.isTablet));
        //c.screenDp
        client.setUnknownSource(c.installUnknownsource);
        client.setAvailableInternalMemorySize(c.availableInternalMemorySize);
        client.setTotalInternalMemorySize(c.totalInternalMemorySize);
        client.setPackageName(c.packageName);
        client.setVersionName(c.versionName);
        client.setVersionCode(String.valueOf(c.versionCode));
        client.setAdsVersion(c.adsVersion);
        client.setUserAgent(request.getHeader("User-Agent"));
    }
}
