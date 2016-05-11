package de.bvb.study.entity;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Administrator on 2016/5/11.
 */
public class SportListViewAdapterEntity {

    public ShowapiResBodyBean showapi_res_body;
    public int showapi_res_code;
    public String showapi_res_error;

    public static class ShowapiResBodyBean {
        public int code;
        public String msg;
        public List<NewslistBean> newslist;

        public static class NewslistBean {
            public String ctime;
            public String description;
            public String picUrl;
            public String title;
            public String url;
            public Bitmap bitmap;
        }
    }
}
