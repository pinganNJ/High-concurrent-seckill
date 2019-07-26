package cn.nupt.utils;

import java.util.UUID;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-25 22:32
 */

public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");//去掉原生的"-"
    }

}
