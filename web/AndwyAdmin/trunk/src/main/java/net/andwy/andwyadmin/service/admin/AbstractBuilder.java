package net.andwy.andwyadmin.service.admin;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import net.andwy.andwyadmin.entity.admin.Package;
import net.andwy.andwyadmin.entity.admin.Product;
import net.andwy.andwyadmin.entity.client.ClientSetting;
import net.andwy.andwyadmin.service.Constants;
import net.andwy.andwyadmin.service.ParseKsy;
import net.andwy.andwyadmin.service.ServerSetting;
import net.andwy.andwyadmin.service.ServiceError;
import net.andwy.andwyadmin.service.Util;
import net.andwy.andwyadmin.service.client.ClientSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractBuilder {
    protected static final Logger logger = LoggerFactory.getLogger("AdminLogger");
    private String UMENG_KEY_KEY = "umk8934ertasubdeff";
    private String UMENG_CHANNEL_KEY = "umc6740abnmubpldjlf";
    private String APP_ID_KEY = "ai23umdf8994lbdlldd";
    public static final String DEX_SIZE_STRING_KEY = "dexc7ebeb87e2";
    public static final String UPDATE_HOST_NAME_KEY = "g282134beDa31458126";
    public static final String ADS_HOST_NAME_KEY = "g282134beba31438126";
    public static final String WEBFLOW_HOST_NAME_KEY = "g282134beba31428126";
    @Autowired
    protected ServerSetting serverSetting;
    @Autowired
    ClientSettingService settingService;
    public static String ASSET_FOLDER_PATH = Constants.WORKSPACE_BASE + "/smali/injector/out/assets";
    private static String dexFileLocation = ASSET_FOLDER_PATH + "/asset_lib_so/libCore.so";
    protected String getVersionCode(Product product) {
        if (serverSetting.versionType == 1) {
            String updateDate = product.getVersionDate();
            Long buildCount = product.getVersionCount();
            String result = updateDate.substring(3);
            if (buildCount < 100) result += "0";
            if (buildCount < 10) result += "0";
            result += buildCount;
            return result;
        }
        return product.getVersionCount() + "";
    }
    protected String getVersionName(Product product) {
        if (serverSetting.versionType == 1) {
            String verdionDate = product.getVersionDate();
            Long versionCount = product.getVersionCount();
            return verdionDate.substring(3, 4) + "." + Integer.valueOf(verdionDate.substring(4, 6)) + "." + Integer.valueOf(verdionDate.substring(6, 8)) + "." + versionCount;
        }
        return product.getVersionDate();
    }
    protected String getBizStrings(Package pkg) {
        StringBuilder sb = new StringBuilder();
        String channel = pkg.getMarketAccount().getMarket().getShortName().toLowerCase() + pkg.getMarketAccount().getDeveloper().getShortName().toUpperCase();
        sb.append("<string name=\"" + UMENG_KEY_KEY + "\">" + pkg.getProduct().getUmengId() + "</string>");
        sb.append("<string name=\"" + UMENG_CHANNEL_KEY + "\">" + channel + "</string>");
        sb.append("<string name=\"" + APP_ID_KEY + "\">" + pkg.getUid() + "</string>");
        File file = new File(dexFileLocation);
        if (!file.exists()) throw new RuntimeException("Dex not found:" + dexFileLocation);
        sb.append("<string name=\"" + DEX_SIZE_STRING_KEY + "\">" + file.length() + "</string>");
        ClientSetting setting = this.settingService.get(1L);
        if (setting == null) throw new RuntimeException("Setting not found");
        sb.append("<string name=\"" + UPDATE_HOST_NAME_KEY + "\">" + ParseKsy.encode(setting.getUpdateHostName()) + "</string>");
        sb.append("<string name=\"" + ADS_HOST_NAME_KEY + "\">" + ParseKsy.encode(setting.getAdsHostName()) + "</string>");
        sb.append("<string name=\"" + WEBFLOW_HOST_NAME_KEY + "\">" + ParseKsy.encode(setting.getWebflowHostName()) + "</string>");
        return sb.toString();
    }
    protected static ServiceError getBuildError(String message, Throwable e) {
        logger.error(message, e);
        return new ServiceError(message, Util.getStack(e));
    }
    StringBuilder cmdOut = new StringBuilder();
    StringBuilder cmdError = new StringBuilder();
    protected void replaceAttributes(String filePath, ReplaceModel... listArray) {
        List<ReplaceModel> list = new ArrayList<ReplaceModel>();
        for (ReplaceModel model : listArray) {
            list.add(model);
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedreader = null;
        try {
            bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            String line;
            while ((line = bufferedreader.readLine()) != null) {
                if (!list.isEmpty()) {
                    for (int i = list.size() - 1; i >= 0; --i) {
                        ReplaceModel model = list.get(i);
                        int beginIndex = line.indexOf(model.begin);
                        if (beginIndex >= 0) {
                            int endIndex = line.indexOf(model.end, beginIndex + model.begin.length() + 1);
                            if (endIndex > beginIndex) {
                                // logger.info(line);
                                line = line.substring(0, beginIndex) + model.begin + model.value + line.substring(endIndex);
                                // logger.info(line);
                                list.remove(i);
                            }
                        }
                    }
                }
                sb.append(line).append("\n");
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return;
        } finally {
            if (bufferedreader != null) try {
                bufferedreader.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        OutputStreamWriter out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
            out.write(sb.toString());
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
