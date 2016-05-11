package de.bvb.study.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by Administrator on 2016/5/11.
 */
public class JsonUtil {
    public static <T> T fromJson(String json, Class<T> t) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, t);
        } catch (JsonSyntaxException e) {
            LogUtil.e("json", json + "\n 不是合法的json格式的数据");
            return null;
        }
    }
}
