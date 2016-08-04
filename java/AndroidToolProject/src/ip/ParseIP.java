package ip;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class ParseIP {
    static String folder = "E:/workspace/android/code/java/AndroidToolProject/resources/source/ip-resources/";
    static class Bean {
        int provinceId;
        String provinceName;
        int areaId;
        String areaName;
        String areaCode;
    }
    public static void main(String args[]) throws Exception {
        //Map<String, Map<String, Bean>> map = getBeanMap();
        //long a=1223246164000L;
        parse(getBeanMap());
        String s = "潍坊市";
        if (s.endsWith("市")) {
            s = s.substring(0, s.length() - 1);
        }
        System.out.println(s);
        //System.out.println(formatIp("192.168.0.0"));
        // System.out.println(formatIp("1.168.255.255"));
    }
    public static Map<String, Map<String, Bean>> getBeanMap() throws Exception {
        List<String> lines = FileUtils.readLines(new File(folder + "areas.csv"), "UTF-8");
        Map<String, Map<String, Bean>> map = new HashMap<String, Map<String, Bean>>();
        for (String line : lines) {
            String parts[] = line.replace("\"", "").split(",");
            if (parts.length < 4) continue;
            Bean bean = new Bean();
            bean.provinceId = Integer.valueOf(parts[0].trim());
            bean.provinceName = parts[1].trim();
            if (bean.provinceName.endsWith("省")) {
                bean.provinceName = bean.provinceName.substring(0, bean.provinceName.length() - 1);
            }
            bean.areaId = Integer.valueOf(parts[2].trim());
            bean.areaName = parts[3].trim();
            if (bean.areaName.endsWith("市")) {
                bean.areaName = bean.areaName.substring(0, bean.areaName.length() - 1);
            }
            bean.areaCode = parts[4].trim();
            Map<String, Bean> pMap = map.get(bean.provinceName);
            if (pMap == null) {
                pMap = new HashMap<String, Bean>();
                map.put(bean.provinceName, pMap);
            }
            pMap.put(bean.areaName, bean);
        }
        return map;
    }
    public static void parse(Map<String, Map<String, Bean>> map) throws Exception {
        List<String> lines = FileUtils.readLines(new File(folder + "ip.txt"), "UTF-8");
        List<String> values = new ArrayList<String>();
        for (String line : lines) {
            for (String provinceName : map.keySet()) {
                if (line.contains(provinceName)) {
                    String parts[] = line.split("[\\s]+");
                    long fromIp = formatIp(parts[0]);
                    // String fromIp =  parts[0] ;
                    long toIp = formatIp(parts[1]);
                    //String toIp =  parts[1] ;
                    String location = parts[2];
                    Map<String, Bean> m = map.get(provinceName);
                    boolean match = false;
                    for (String areaName : m.keySet()) {
                        String[] fields = areaName.split("\\|");
                        for (String field : fields) {
                            if (location.contains(field)) {
                                Bean bean = m.get(areaName);
                                values.add("(" + fromIp + "," + toIp + "," + bean.provinceId + "," + bean.areaId + ")");
                                match = true;
                                break;
                            }
                        }
                        if (match) break;
                    }
                    if (!match) {
                        values.add("(" + fromIp + "," + toIp + "," + m.values().iterator().next().provinceId + "," + 0 + ")");
                    }
                    break;
                }
            }
        }
        StringBuilder sb = new StringBuilder("insert into t_ip_info (ip_from,ip_to,province_id,city_id) values \n");
        File file = new File(folder + "ip-result.sql");
        FileUtils.writeStringToFile(file, "", false);
        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i));
            if (i % 10000 == 0 || i == values.size() - 1) {
                sb.append(";\n");
                FileUtils.writeStringToFile(file, sb.toString(), true);
                sb = new StringBuilder("insert into t_ip_info (ip_from,ip_to,province_id,city_id) values \n");
            } else {
                sb.append(",\n");
            }
        }
    }
    public static long formatIp(String ip) {
        StringBuilder sb = new StringBuilder("1");
        String[] parts = ip.split("\\.");
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.length() == 2) sb.append("0");
            else if (part.length() == 1) sb.append("00");
            sb.append(part);
            if (i < parts.length - 1) {
                //sb.append(".");
            }
        }
        return Long.valueOf(sb.toString());
    }
}
