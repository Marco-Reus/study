package de.bvb.study.common;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/5/8.
 */
public class CommonViewHolder {

    private Context context;
    private int position;
    private int layoutId;

    private View convertView;
    private SparseArray<View> views;

    private CommonViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.context = context;
        this.layoutId = layoutId;
        this.position = position;
        views = new SparseArray<>();
        convertView = View.inflate(context, layoutId, null);
        convertView.setTag(this);
    }

    public static CommonViewHolder getInstance(Context context, ViewGroup parent, int resId, int position, View convertView) {
        if (convertView == null) {
            return new CommonViewHolder(context, parent, resId, position);
        } else {
            CommonViewHolder holder = (CommonViewHolder) convertView.getTag();
            holder.position = position;
            return holder;
        }
    }

    public int getPosition() { return position;   }

    public View getConvertView() { return convertView;  }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = this.convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public CommonViewHolder setText(int viewId, String text) {
        ((TextView) getView(viewId)).setText(text);
        return this;
    }

    public CommonViewHolder setImageResource(int viewId, int resId) {
        ((ImageView) getView(viewId)).setImageResource(resId);
        return this;
    }

    public CommonViewHolder setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        getView(viewId).setOnClickListener(onClickListener);
        return this;
    }
}
