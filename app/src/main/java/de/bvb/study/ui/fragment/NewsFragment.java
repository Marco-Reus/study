package de.bvb.study.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.bvb.study.R;
import de.bvb.study.ui.widget.ViewPagerIndicator;

/**
 * Created by Administrator on 2016/5/8.
 */
public class NewsFragment extends BaseFragment {

    //    private boolean hasFragmentLayoutChanged = false;
    private View view;
    private ViewPagerIndicator viewPagerIndicator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(getActivity(), R.layout.fragment_news, null);
        viewPagerIndicator = $(view, R.id.viewPagerIndicatorNews);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new BaseFragment());
        fragments.add(new BaseFragment());
        fragments.add(new BaseFragment());
        fragments.add(new BaseFragment());
        List<ViewPagerIndicator.IndicatorEntity> indicatorList = new ArrayList<>();
        indicatorList.add(new ViewPagerIndicator.IndicatorEntity("体育"));
        indicatorList.add(new ViewPagerIndicator.IndicatorEntity("财经"));
        indicatorList.add(new ViewPagerIndicator.IndicatorEntity("科技"));
        indicatorList.add(new ViewPagerIndicator.IndicatorEntity("深圳"));
        viewPagerIndicator.bind(fragments, indicatorList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return viewPagerIndicator;
    }
}
