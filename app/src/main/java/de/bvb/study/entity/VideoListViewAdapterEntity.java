package de.bvb.study.entity;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/11.
 */
public class VideoListViewAdapterEntity implements Serializable {

    public String url;
    public String title;
    public Bitmap bitmap;

    public VideoListViewAdapterEntity(String url, String title, Bitmap bitmap) {
        this.url = url;
        this.title = title;
        this.bitmap = bitmap;
    }
}
