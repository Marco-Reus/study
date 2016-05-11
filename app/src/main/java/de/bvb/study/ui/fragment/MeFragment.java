package de.bvb.study.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.bvb.study.R;
import de.bvb.study.common.CommonAdapter;
import de.bvb.study.common.CommonViewHolder;
import de.bvb.study.entity.MeListViewAdapterEntity;
import de.bvb.study.ui.activity.FeedbackActivity;
import de.bvb.study.ui.activity.LoginActivity;
import de.bvb.study.util.ActivityUtil;

/**
 * Created by Administrator on 2016/5/8.
 */
public class MeFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private View view;
    private ListView listView;
    private CommonAdapter<MeListViewAdapterEntity> adapter;
    private List<MeListViewAdapterEntity> dataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(getActivity(), R.layout.fragment_me, null);
        $(view, R.id.llLogin).setOnClickListener(this);
        listView = $(view, R.id.listViewMe);

        dataList = new ArrayList<>();
        dataList.add(new MeListViewAdapterEntity(R.mipmap.ic_launcher, "收藏", FeedbackActivity.class));
        dataList.add(new MeListViewAdapterEntity(R.mipmap.ic_launcher, "下载", FeedbackActivity.class));
        dataList.add(new MeListViewAdapterEntity(R.mipmap.ic_launcher, "设置", FeedbackActivity.class));
        dataList.add(new MeListViewAdapterEntity(R.mipmap.ic_launcher, "反馈", FeedbackActivity.class));
        adapter = new CommonAdapter<MeListViewAdapterEntity>(getContext(), dataList, R.layout.adapter_me_list_view) {
            @Override
            public void convert(CommonViewHolder holder, MeListViewAdapterEntity meListViewAdapterEntity) {
                holder.setImage(R.id.ivIcon, meListViewAdapterEntity.titleIconResId);
                holder.setText(R.id.tvTitle, meListViewAdapterEntity.titleName);
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ActivityUtil.startActivity(getContext(), dataList.get(position).interfaceClassName);
    }

    @Override
    public void onClick(View v) {
        ActivityUtil.startActivity(getContext(), LoginActivity.class);
    }
}
