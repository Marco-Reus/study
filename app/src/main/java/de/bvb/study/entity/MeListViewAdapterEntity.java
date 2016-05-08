package de.bvb.study.entity;

/**
 * Created by Administrator on 2016/5/8.
 */
public class MeListViewAdapterEntity {
    public int titleIconResId;
    public String titleName;
    public Class interfaceClassName;

    public MeListViewAdapterEntity(int titleIconResId, String titleName, Class interfaceClassName) {
        this.titleIconResId = titleIconResId;
        this.titleName = titleName;
        this.interfaceClassName = interfaceClassName;
    }
}
