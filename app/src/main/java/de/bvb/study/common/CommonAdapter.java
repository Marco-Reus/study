package de.bvb.study.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/5/8.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    private List<T> dataList;
    private Context context;
    private int layoutId;

    /**
     * 通用的 adapter 构造
     *
     * @param context
     * @param dataList 数据源
     * @param layoutId 布局文件
     */
    public CommonAdapter(Context context, List<T> dataList, int layoutId) {
        super();
        this.dataList = dataList;
        this.context = context;
        this.layoutId = layoutId;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {return dataList == null ? 0 : dataList.size(); }

    @Override
    public T getItem(int position) {return dataList.get(position); }

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder holder = CommonViewHolder.getInstance(context, parent, layoutId, position, convertView);
        convert(holder, dataList.get(position));
        return holder.getConvertView();
    }

    public abstract void convert(CommonViewHolder holder, T t);
}
