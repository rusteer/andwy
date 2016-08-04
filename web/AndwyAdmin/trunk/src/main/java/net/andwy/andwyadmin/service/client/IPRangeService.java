package net.andwy.andwyadmin.service.client;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.andwy.andwyadmin.entity.client.IPRange;
import net.andwy.andwyadmin.repository.client.IPRangeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class IPRangeService {
    private IPRangeDao dao;
    @Autowired
    public void setDao(IPRangeDao dao) {
        this.dao = dao;
    }
    public boolean isUnicomIp(String ip) {
        String formatIp = formatIp(ip);
        if (formatIp == null) return false;
        List<IPRange> list = dao.get("UNICOM", formatIp);
        return list != null && list.size() > 0;
    }
    public static void main(String[] args) {
        //calMask("61.167.0.0/16");
        try {
            getnerateImportSQL("E:\\cncgroup.txt");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    private static String formatIp(String ip) {
        if (ip == null) return null;
        String[] fields = ip.trim().split("\\.");
        if (fields.length != 4) return null;
        StringBuilder sb = new StringBuilder();
        for (String field : fields) {
            if (field.length() == 1) sb.append("00");
            else if (field.length() == 2) sb.append("0");
            sb.append(field);
        }
        return sb.toString();
    }
    static void handleIPRange(String ipRange) {
        int index = ipRange.indexOf('-');
        String firstIp = formatIp(ipRange.substring(0, index - 1).trim());
        String lastIp = formatIp(ipRange.substring(index + 1).trim());
        String result = firstIp + "-" + lastIp;
        if (set.contains(result)) return;
        set.add(result);
        IPRange range = new IPRange();
        range.setFirst(firstIp);
        range.setLast(lastIp);
        list.add(range);
        // System.out.println(result);
    }
    private static Set<String> set = new HashSet<String>();
    private static List<IPRange> list = new ArrayList<IPRange>();
    static void getnerateImportSQL(String file) throws Exception {
        set.clear();
        list.clear();
        BufferedReader bufferedreader = null;
        bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        String flag = "inetnum:";
        String flag2 = "Reverse zone for ";
        while ((line = bufferedreader.readLine()) != null) {
            if (line.contains(flag)) {
                String ipRange = line.substring(flag.length() + 1).trim();
                handleIPRange(ipRange);
            } else if (line.indexOf(flag2) > 0) {
                String maskRange = line.substring(line.indexOf(flag2) + flag2.length() + 1).trim();
                calMask(maskRange);
            }
        }
        bufferedreader.close();
        Collections.sort(list, new Comparator<IPRange>() {
            @Override
            public int compare(IPRange o1, IPRange o2) {
                int result = o1.getFirst().compareTo(o2.getFirst());
                if (result == 0) result = o1.getLast().compareTo(o2.getLast());
                return result;
            }
        });
        FileWriter fw = null;
        fw = new FileWriter("E:\\unicom-ip.sql");
        for (IPRange range : list) {
            fw.write("insert into t_ip_range (owner,first,last) values ('UNICOM','" + range.getFirst() + "','" + range.getLast() + "');\n");
        }
        fw.flush();
        fw.close();
    }
    private static int getMask(int num) {
        if (num >= 8) return 255;
        int bitpat = 0xff00;
        while (num > 0) {
            bitpat = bitpat >> 1;
            num--;
        }
        return bitpat & 0xff;
    }
    private static void calMask(String ipString) throws Exception {
        int index = ipString.indexOf("/");
        int networkLength = Integer.valueOf(ipString.substring(index + 1));
        String ipPart = ipString.substring(0, index);
        int[] ip = new int[4];
        String[] fields = ipPart.split("\\.");
        for (int i = 0; i < ip.length && i < fields.length; i++) {
            ip[i] = Integer.valueOf(fields[i]);
        }
        int[] mask = new int[4];
        int[] network = new int[4];
        int[] firstIpArray = new int[4];
        int[] lastIpArray = new int[4];
        int[] broadcast = new int[4];
        if (networkLength < 8) mask[0] = getMask(networkLength);
        else {
            mask[0] = 255;
            networkLength -= 8;
            if (networkLength < 8) mask[1] = getMask(networkLength);
            else {
                mask[1] = 255;
                networkLength -= 8;
                if (networkLength < 8) mask[2] = getMask(networkLength);
                else {
                    mask[2] = 255;
                    networkLength -= 8;
                    mask[3] = getMask(networkLength);
                }
            }
        }
        //System.out.println("掩码:" + mask[0] + "." + mask[1] + "." + mask[2] + "." + mask[3]);
        for (int i = 0; i < 4; i++) {
            network[i] = ip[i] & mask[i];
            broadcast[i] = ip[i] | ~mask[i] & 0xff;
        }
        // System.out.println("广播:" + broadcast[0] + "." + broadcast[1] + "." + broadcast[2] + "." + broadcast[3]); //错误
        //System.out.println("网络:" + network[0] + "." + network[1] + "." + network[2] + "." + network[3]);
        firstIpArray[0] = network[0];
        firstIpArray[1] = network[1];
        firstIpArray[2] = network[2];
        firstIpArray[3] = network[3];
        String firstIp = firstIpArray[0] + "." + firstIpArray[1] + "." + firstIpArray[2] + "." + firstIpArray[3];
        //System.out.println("第一个可用:" + firstIp);
        lastIpArray[0] = broadcast[0];
        lastIpArray[1] = broadcast[1];
        lastIpArray[2] = broadcast[2];
        lastIpArray[3] = broadcast[3];
        String lastIp = lastIpArray[0] + "." + lastIpArray[1] + "." + lastIpArray[2] + "." + lastIpArray[3];
        //System.out.println("最后可用:" + lastIp);
        handleIPRange(firstIp + " - " + lastIp);
    }
}
