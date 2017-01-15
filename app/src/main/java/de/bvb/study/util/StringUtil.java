package de.bvb.study.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/5/21.
 */
public class StringUtil {
    /** unicode 转码为 中文, 下载文件后改名用 */
    public static String unicode2Cn(String data) {
        try {
            return URLDecoder.decode(data, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /** 中文 转码为 unicode , 请求参数中有中文 */
    public static String cn2Unicode(String data) {
        try {
            return URLEncoder.encode(data, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
