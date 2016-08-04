import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FileUtils;

public class ParseMobilePrefix {
    static String folder = "E:\\workspace\\android\\code\\java\\AndroidToolProject\\resources\\";
    public static void main(String args[]) throws Exception {
        parse(getBeanMap());
    }
    public static Map<String, Map<String, String>> getBeanMap() throws Exception {
        List<String> lines = FileUtils.readLines(new File(folder + "source/t_province.csv"), "UTF-8");
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
        for (String line : lines) {
            String parts[] = line.split(",");
            if (parts.length < 3) continue;
            String pName = parts[0].trim();
            String aName = parts[1].trim();
            String aCode = parts[2].trim();
            Map<String, String> pMap = map.get(pName);
            if (pMap == null) {
                pMap = new HashMap<String, String>();
                map.put(pName, pMap);
            }
            pMap.put(aName, aCode);
        }
        return map;
    }
    private static boolean match(Set<String> areaSet, String name) {
        for (String a : areaSet) {
            if (name.contains(a) || a.contains(name)) return true;
        }
        return false;
    }
    public static void parse(Map<String, Map<String, String>> map) throws Exception {
        List<String> lines = FileUtils.readLines(new File(folder + "source/yd.csv"), "gb2312");
        Map<String, Set<String>> failedSet = new HashMap<String, Set<String>>();
        List<String> values = new ArrayList<String>();
        for (String line : lines) {
            String parts[] = line.split(",");
            String prefix = parts[0].trim();
            String pName = parts[1].trim();
            String aName = parts[2].trim();
            Set<String> areaSet = new HashSet<String>();
            if (aName.contains("|")) {
                for (String part : aName.split("\\|")) {
                    if (part.trim().length() > 0) areaSet.add(part.trim());
                }
            } else {
                areaSet.add(aName);
            }
            boolean match = false;
            for (String mapProvinceName : map.keySet()) {
                if (mapProvinceName.contains(pName) || pName.contains(mapProvinceName)) {
                    Map<String, String> m = map.get(mapProvinceName);
                    for (String mapAreaName : m.keySet()) {
                        if (match(areaSet, mapAreaName)) {
                            String aCode = m.get(mapAreaName);
                            String value = "('" + prefix + "','" + aCode + "')";
                            values.add(value);
                            match = true;
                            break;
                        }
                    }
                    break;
                }
            }
            if (!match) {
                Set<String> set = failedSet.get(pName);
                if (set == null) {
                    set = new HashSet<String>();
                    failedSet.put(pName, set);
                }
                set.add(aName);
            }
        }
        StringBuilder failedLines = new StringBuilder();
        for (String p : failedSet.keySet()) {
            for (String a : failedSet.get(p)) {
                failedLines.append(p).append(",").append(a).append("\n");
            }
        }
        FileUtils.writeStringToFile(new File(folder + "fail.csv"), failedLines.toString(), false);
        StringBuilder sb = new StringBuilder("insert into t_mobile_info_tmp (mobile_number,area_code ) values \n");
        File file = new File(folder + "prefix-result.sql");
        FileUtils.writeStringToFile(file, "", false);
        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i));
            if (i % 5000 == 0 || i == values.size() - 1) {
                sb.append(";\n");
                FileUtils.writeStringToFile(file, sb.toString(), true);
                sb = new StringBuilder("insert into t_mobile_info_tmp (mobile_number,area_code ) values \n");
            } else {
                sb.append(",\n");
            }
        }
    }
}
