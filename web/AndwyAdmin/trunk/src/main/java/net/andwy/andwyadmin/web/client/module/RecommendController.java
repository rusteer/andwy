package net.andwy.andwyadmin.web.client.module;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import module.ads.bean.CApp;
import module.ads.bean.CAppList;
import module.ads.bean.PushType;
import module.ads.bean.params.RecommendParams;
import net.andwy.andwyadmin.entity.KeyValue;
import net.andwy.andwyadmin.entity.client.App;
import net.andwy.andwyadmin.entity.client.ClientPackage;
import net.andwy.andwyadmin.entity.client.Config;
import net.andwy.andwyadmin.web.client.RequestMethod;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class RecommendController extends ModuleController {
    public KeyValue<String, JSONObject> execute(ClientPackage clientPackage, RecommendParams params, HttpServletRequest request) {
        JSONObject jsonResult = null;
        String reason = null;
        try {
            Config config = configService.getConfig(clientPackage.getPkg());
            if (config != null) {
                boolean debug = isClinetDebug(request);
                if (!debug) {
                    if (blackDeviceService.isBlack(clientPackage.getClient())) {
                        reason = "黑名单用户";
                    }
                    if (reason == null && !"Y".equals(config.getEnableRecommend())) {
                        reason = "应用推荐功能没有打开";
                    }
                    Long adsVersion=clientPackage.getClient().getAdsVersion();
                    if (reason == null && (adsVersion==null || adsVersion < 4)) {
                        reason = "客户端广告版本小于4:"+adsVersion;
                    }
                }
                if (reason == null) {
                    List<App> list = null;
                    switch (params.appType) {
                        case 0:
                            list = appService.getAppRecommendList();
                            break;
                        case 1:
                            list = appService.getGameRecommendList();
                            break;
                    }
                    //filterInstalledApp(list, params);
                    if (list != null) {
                        CAppList result = new CAppList();
                        List<CApp> cAppList = new ArrayList<CApp>();
                        for (App app : list) {
                            cAppList.add(toCApp(app, RequestMethod.recommend, PushType.SHOW_DETAIL));
                        }
                        result.list = cAppList;
                        result.serverCount = list.size() + 2;
                        jsonResult = result.toJson();
                    } else {
                        reason = "列表为空";
                    }
                }
            } else {
                reason = "产品没有设置";
            }
        } catch (Throwable e) {
            errorLogger.error(e.getMessage(), e);
            reason = "异常错误:" + e.getMessage();
        }
        return new KeyValue<String, JSONObject>(reason, jsonResult);
    }
}
