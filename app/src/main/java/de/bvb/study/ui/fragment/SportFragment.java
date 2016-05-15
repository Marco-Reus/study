package de.bvb.study.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import de.bvb.study.MyApplication;
import de.bvb.study.R;
import de.bvb.study.common.CommonAdapter;
import de.bvb.study.common.CommonViewHolder;
import de.bvb.study.entity.SportListViewAdapterEntity;
import de.bvb.study.ui.activity.SportDetailActivity;
import de.bvb.study.util.JsonUtil;
import de.bvb.study.util.LogUtil;

/**
 * Created by Administrator on 2016/5/8.
 */
public class SportFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    public static final String url = "http://route.showapi.com/196-1?showapi_appid=18729&showapi_sign=c16816c445284126a3b24f3162fb439e&num=3";

    private RequestQueue mQueue;

    private View view;
    private ListView listViewSport;
    private List<SportListViewAdapterEntity.ShowapiResBodyBean.NewslistBean> dataList;
    private CommonAdapter<SportListViewAdapterEntity.ShowapiResBodyBean.NewslistBean> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mQueue = Volley.newRequestQueue(MyApplication.getContext());
        mQueue.add(new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.e("json", s);
                SportListViewAdapterEntity entity = JsonUtil.fromJson(s, SportListViewAdapterEntity.class);
                if (entity != null && entity.showapi_res_code == 0
                        && entity.showapi_res_body != null && entity.showapi_res_body.code == 200
                        && entity.showapi_res_body.newslist != null && entity.showapi_res_body.newslist.size() > 0) {
                    dataList = entity.showapi_res_body.newslist;
                    for (final SportListViewAdapterEntity.ShowapiResBodyBean.NewslistBean bean : dataList) {

                        mQueue.add(new ImageRequest(bean.picUrl, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                bean.bitmap = bitmap;
                                adapter.setDataList(dataList);
                            }
                        }, 360, 240, Bitmap.Config.ARGB_8888, null));
                    }
                    adapter.setDataList(dataList);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("error", volleyError.getMessage());
            }
        }));


        view = View.inflate(getContext(), R.layout.fragment_sport, null);
        listViewSport = $(view, R.id.listViewSport);
        adapter = new CommonAdapter<SportListViewAdapterEntity.ShowapiResBodyBean.NewslistBean>(getContext(), dataList, R.layout.adapter_sport_list_view) {
            @Override
            public void convert(CommonViewHolder holder, SportListViewAdapterEntity.ShowapiResBodyBean.NewslistBean newslistBean) {
                holder.setText(R.id.tvTitle, newslistBean.title);
                holder.setText(R.id.tvTime, newslistBean.ctime);
                if (newslistBean.bitmap == null) {
                    holder.setImage(R.id.imageView, R.mipmap.ic_launcher);
                } else {
                    holder.setImage(R.id.imageView, newslistBean.bitmap);
                }
            }
        };
        listViewSport.setAdapter(adapter);
        listViewSport.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), SportDetailActivity.class);
        intent.putExtra(SportDetailActivity.EXTRA_KEY_URL, dataList.get(position).url);
        startActivity(intent);
    }
}
