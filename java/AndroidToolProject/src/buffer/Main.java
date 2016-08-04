package buffer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String args[]) throws Exception {
        Map<Long, AdsBean> map = new HashMap<Long, AdsBean>();
        for (int i = 0; i < 100; i++) {
            AdsBean bean = new AdsBean();
            bean.id = i;
            bean.name = "i" + i;
            bean.popConfig = new PopConfig();
            bean.popConfig.endHour = 23;
            map.put(bean.id, bean);
        }
        
        File file=new File("d:/map.txt");
        
        FileOutputStream outStream=new FileOutputStream(file);
        
        ObjectOutputStream oout = new ObjectOutputStream(outStream);
        oout.writeObject(map);
        oout.flush();
        Map<Long, AdsBean> m1 = (Map<Long, AdsBean>) (new ObjectInputStream(new FileInputStream(file)).readObject());
        System.out.println(m1);
        System.out.println(m1.get(2L).popConfig.endHour);
    }
}
