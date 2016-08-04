import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class UnicodeConverter {
    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("js");// ScriptEngine用来处理脚本解释和求值
    private static Bindings bindings = engine.createBindings();
    public static void main(String args[]) {
        //System.out.println(decode("\u5929\u4e00"));
        //System.out.println(encode("卸载"));
        //\u6b63\u5728\u5378\u8f7d
        
        print("保护上限");
        print("僚机个数");
        print("僚机火力");
        print("初始火力");
        print("初始生命");
        print("必杀时间");
        print("暴击时间");
        print("玩家攻击");
    }
    
    
    public static void print(String s){
        System.out.println(s+"\t"+"_fld"+encode(s).toUpperCase().replaceAll("\\\\U", ""));
    }
    
    public static String intToHex(int num) {
        String sResult = "";
        while (num > 0) {
            int m = num % 16;
            if (m < 10) sResult = (char) ('0' + m) + sResult;
            else sResult = (char) ('A' + m - 10) + sResult;
            num = num / 16;
        }
        return sResult.toLowerCase();
    }
    public static String encode(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            sb.append("\\u").append(intToHex(c));
        }
        return sb.toString();
    }
    public static String decode(String line) {
        bindings.put("result", "");
        try {
            engine.eval("result='" + line + "'", bindings);
            String result = (String) bindings.get("result");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(line);
            return null;
        }
    }
}
