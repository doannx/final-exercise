package vn.elca.training.util;

/**
 * String Utilities.
 * 
 * @author xdg, 20160721
 *
 */
public class StringUtil {
    /**
     * Build regular expression from input criterion.
     * 
     * @param criterion
     * @return the completed regular expression
     */
    public static String buildRegexFromcriterion(String criterion) {
        // build the regular expression pattern
        StringBuilder rexPattern = new StringBuilder("");
        // if *criterion* => .*criterion.*
        if (criterion.startsWith("*") && criterion.endsWith("*")) {
            rexPattern.append(".*").append(criterion.toLowerCase().substring(1).replace("*", ".*"));
        } else if (criterion.startsWith("*")) { // if *criterion => .*criterion$
            rexPattern.append(".*").append(criterion.toLowerCase().substring(1)).append("$");
        } else if (criterion.endsWith("*")) { // if criterion* => ^criterion.*
            rexPattern.append("^").append(criterion.toLowerCase().replace("*", ".*"));
        } else { // if criterion1*criterion2 => ^criterion1.*criterion2$
            rexPattern.append("^").append(criterion.toLowerCase().replace("*", ".*")).append("$");
        }
        return rexPattern.toString();
    }
}
