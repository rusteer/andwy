package net.andwy.andwyadmin.web.client;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.andwy.andwyadmin.entity.client.App;
import net.andwy.andwyadmin.entity.client.AppStat;
import net.andwy.andwyadmin.entity.client.Category;
import net.andwy.andwyadmin.service.Util;
import net.andwy.andwyadmin.service.client.AppService;
import net.andwy.andwyadmin.service.stat.AppStatService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping(value = "/client/app")
public class AppController {
    @Autowired
    AppService appService;
    @Autowired
    private AppStatService appStatService;
    @RequestMapping(value = "/removeField", method = RequestMethod.GET)
    public String removeField(HttpServletRequest request, HttpServletResponse response) {
        boolean result = true;
        Long id = Long.valueOf(request.getParameter("id"));
        String name = request.getParameter("field");
        if (id > 0) {
            App app = appService.get(id);
            if (app != null) {
                File appFolder = new File(appService.getAppFolder(app));
                File uploadFile = new File(appFolder.getAbsolutePath() + "/" + name);
                uploadFile.deleteOnExit();
                // setFieldStatus(app, name, null);
                appService.save(app);
            }
        }
        request.setAttribute("result", result);
        return "app/result";
    }
    @RequestMapping(value = "/dailyCount", method = RequestMethod.GET)
    public void dailyCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long paramAppId = 0;
        String appIdString = request.getParameter("appId");
        if (!StringUtils.isBlank(appIdString)) {
            paramAppId = Long.valueOf(appIdString.trim());
        }
        if (paramAppId > 0) {
            String paramFrom = request.getParameter("from");
            String paramTo = request.getParameter("to");
            App app = appService.get(paramAppId);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(paramTo));
            String date = dateFormat.format(calendar.getTime());
            int maxCount = 100;
            JSONArray array = new JSONArray();
            Map<String, JSONObject> map = new HashMap<String, JSONObject>();
            while (date.compareTo(paramFrom) >= 0) {
                JSONObject obj = new JSONObject();
                obj.put("statDate", date);
                obj.put("price", app.getPrice());
                obj.put("id", app.getId());
                map.put(date, obj);
                array.put(obj);
                calendar.add(Calendar.DATE, -1);
                date = dateFormat.format(calendar.getTime());
                if (maxCount-- <= 0) break;
            }
            List<AppStat> statList = appStatService.getStat(paramAppId, paramFrom, paramTo, true);
            for (AppStat appCount : statList) {
                JSONObject obj = map.get(appCount.getStatDate());
                if (obj != null) {
                    obj.put("pushCount", appCount.getPushCount());
                    obj.put("installCount", appCount.getInstallCount());
                    obj.put("installSyncCount", appCount.getSyncCount());
                }
            }
            WebUtil.write(request, response, array);
        }
    }
    @RequestMapping(value = "/syncCount")
    public void saveSyncCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long appId = Long.valueOf(request.getParameter("app"));
        String syncDate = request.getParameter("syncDate");
        int syncCount = Integer.valueOf(request.getParameter("syncCount"));
        AppStat stat = appStatService.getStat(appId, syncDate);
        if (stat == null) {
            stat = new AppStat(appId, syncDate);
        }
        stat.setSyncCount(syncCount);
        appStatService.save(stat);
    }
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public void getCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String paramFrom = request.getParameter("from");
        String paramTo = request.getParameter("to");
        List<AppStat> statList = appStatService.getStat(0L, paramFrom, paramTo, false);
        //List<AppCount> pushList = countService.getPushCount(0, paramFrom, paramTo, false);
        //List<AppCount> installList = countService.getInstallCount(0, paramFrom, paramTo, false);
        //List<AppCount> installSyncList = countService.getInstallSyncCount(0, paramFrom, paramTo, false);
        List<App> appList = appService.getAll();
        JSONObject result = new JSONObject();
        for (App app : appList) {
            result.put(String.valueOf(app.getId()), new JSONObject());
        }
        for (AppStat appCount : statList) {
            JSONObject obj = result.optJSONObject(String.valueOf(appCount.getAppId()));
            if (obj != null) {
                obj.put("pushCount", appCount.getPushCount());
                obj.put("installCount", appCount.getInstallCount());
                obj.put("installSyncCount", appCount.getSyncCount());
            }
        }
        WebUtil.write(request, response, result);
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response) {
        boolean result = true;
        Long id = Long.valueOf(request.getParameter("id"));
        App app = id == 0 ? new App() : appService.get(id);
        app.setName(request.getParameter("name"));
        app.setAdvertiser(request.getParameter("advertiser"));
        app.setComments(request.getParameter("comments"));
        app.setSharingType(request.getParameter("sharingType"));
        app.setPrice(Float.valueOf(request.getParameter("price")));
        app.setDescription(request.getParameter("description"));
        app.setHint(request.getParameter("hint"));
        app.setSize(request.getParameter("size"));
        app.setPkgName(request.getParameter("pkgName"));
        app.setPkgVersionName(request.getParameter("pkgVersionName"));
        app.setPkgVersionCode(request.getParameter("pkgVersionCode"));
        app.setType(Long.valueOf(request.getParameter("type")));
        Category category = new Category();
        category.setId(Long.valueOf(request.getParameter("category")));
        app.setCategory(category);
        app.setHot(Long.valueOf(request.getParameter("hot")));
        app.setPush("Y".equals(request.getParameter("push")) ? "Y" : "N");
        app.setDailyInstallLimit(Integer.valueOf(request.getParameter("dailyInstallLimit")));
        app.setPrivateApp("true".equalsIgnoreCase("privateApp"));
        Date date = new Date();
        app.setUpdateTime(date);
        if (app.getId() == null) {
            app.setUid(Util.generateUUID());
            app.setCreateTime(date);
        }
        app.setStatus("E".equals(request.getParameter("status")) ? "E" : "D");
        app = appService.save(app);
        File appFolder = new File(appService.getAppFolder(app));
        if (!appFolder.exists()) appFolder.mkdirs();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile mf = entity.getValue();
            if (mf.getSize() > 0) {
                String key = entity.getKey();
                String fileName = Util.generateUUID() + key.substring(key.lastIndexOf('.'));
                File uploadFile = new File(appFolder.getAbsolutePath() + "/" + fileName);
                try {
                    FileCopyUtils.copy(mf.getBytes(), uploadFile);
                    // setFieldStatus(app, key, fileName);
                } catch (IOException e) {
                    request.setAttribute("reason", Util.getStack(e));
                    result = false;
                    e.printStackTrace();
                }
            }
        }
        appService.save(app);
        request.setAttribute("result", result);
        return "app/result";
    }
}
