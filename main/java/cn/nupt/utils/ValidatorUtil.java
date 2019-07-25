package cn.nupt.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-25 21:10
 */

public class ValidatorUtil {
    private static final Pattern mobile_pattern=Pattern.compile("1\\d{10}");//1开头，然后10个数字，那么正确的手机号
    //验证手机号格式
    public static boolean isMobile(String src) {
        if(StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher m=mobile_pattern.matcher(src);
        return m.matches();
    }
}
