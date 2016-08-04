public class SymbianHostGen {
    public static void main(String args[]) {
        String[] hosts = {
                //
                "meixihost.com:2282",//
                "xiaoluohost.com:2282",//
        };
        StringBuilder sb = new StringBuilder();
        sb.append("aaaaaastart");
        for (int i = 0; i < hosts.length; i++) {
            String host = hosts[i];
            sb.append(host.replaceAll("\\.", "dot"));
            if (i < hosts.length - 1) {
                sb.append("and");
            }
        }
        sb.append("endzzzzzz");
        System.out.println(sb);
    }
}
