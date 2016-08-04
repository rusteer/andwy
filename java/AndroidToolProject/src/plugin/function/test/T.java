package plugin.function.test;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class T {
    public static String SPLITTER = "`";
    
    /**
     * ["text","enclosed", "regex","left","right"]
     */
    private static String[] supportedMethod = { //
        "text","enclosed", "regex","left","right"
    };
    
    /**
     * @param target
     * @param searcher, 1:text(),2:enclosed(),3:regex(),4:left(),5:right()
     * @return
     */
    public static String parseVariable(String target, String searcher) {
        try {
            if (target != null && !TextUtils.isEmpty(searcher) && searcher.endsWith(")")) {
                for (int i = 0; i < supportedMethod.length; i++) {
                    String method = supportedMethod[i];
                    if (searcher.startsWith(method + "(")) {
                        String variable = searcher.substring(method.length() + 1, searcher.length() - 1);
                        switch (i) {
                            case 0:
                                return variable;
                            case 1:
                                return searchEnclosed(target, variable);
                            case 2:
                                return searchRegex(target, variable);
                            case 3:
                                return searchLeft(target, variable);
                            case 4:
                                return searchRight(target, variable);
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //left`right`matcherIndex
    public static String searchEnclosed(String target, String searcher) {
        if (target != null && searcher != null) {
            String left = null;
            String right = null;
            int matcherIndex = -1;
            String[] fields = searcher.split(SPLITTER);
            if (fields.length >= 2) {
                left = fields[0];
                right = fields[1];
                if (fields.length >= 3) {
                    matcherIndex = Integer.valueOf(fields[2].trim());
                }
            }
            return searchEnclosed(target, left, right, matcherIndex);
        }
        return null;
    }
    public static String searchEnclosed(String target, String left, String right) {
        return searchEnclosed(target, left, right, 0);
    }
    public static String searchEnclosed(String target, String left, String right, int matcherIndex) {
        if (!TextUtils.isEmpty(target) && !TextUtils.isEmpty(left) && !TextUtils.isEmpty(right)) {
            int index = 0;
            int leftPlace = 0;
            int rightPlace = 0;
            int fromPlace = 0;
            while (leftPlace >= 0 && rightPlace >= 0) {
                leftPlace = target.indexOf(left, fromPlace);
                if (leftPlace >= 0) {
                    fromPlace = leftPlace + left.length() + 1;
                    rightPlace = target.indexOf(right, fromPlace);
                    if (rightPlace > 0 && (matcherIndex == -1 || matcherIndex == index++)) {//
                        return target.substring(leftPlace + left.length(), rightPlace);
                    }
                    fromPlace = rightPlace + right.length() + 1;
                }
            }
        }
        return null;
    }
    public static String searchLeft(String target, String searcher) {
        if (target != null && searcher != null) {
            int place = target.indexOf(searcher);
            if (place >= 0) { return target.substring(0, place); }
        }
        return null;
    }
    //regex`groupIndex`matcherIndex
    public static String searchRegex(String target, String searcher) {
        String result = null;
        String regex = null;
        int matcherIndex = -1;
        int groupIndex = -1;
        if (searcher != null) {
            String[] fields = searcher.split(SPLITTER);
            if (fields.length >= 2) {
                regex = fields[0];
                groupIndex = Integer.valueOf(fields[1].trim());
                if (fields.length >= 3) {
                    matcherIndex = Integer.valueOf(fields[2].trim());
                }
            }
        }
        if (regex != null && groupIndex >= 0) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(target);
            int index = 0;
            while (matcher.find()) {
                if (matcherIndex == -1 || index++ == matcherIndex) {
                    if (groupIndex <= matcher.groupCount()) {
                        result = matcher.group(groupIndex);
                    }
                    break;
                }
            }
        }
        return result;
    }
    public static String searchRight(String target, String searcher) {
        if (target != null && searcher != null) {
            int place = target.indexOf(searcher);
            if (place >= 0) { //
                return target.substring(place + searcher.length());
            }
        }
        return null;
    }
}
