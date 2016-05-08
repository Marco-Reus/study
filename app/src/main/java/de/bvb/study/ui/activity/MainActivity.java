package de.bvb.study.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import de.bvb.study.R;
import de.bvb.study.ui.fragment.MatchFragment;
import de.bvb.study.ui.fragment.MeFragment;
import de.bvb.study.ui.fragment.NewsFragment;
import de.bvb.study.ui.fragment.VideoFragment;
import de.bvb.study.ui.widget.TopBar;
import de.bvb.study.ui.widget.ViewPagerIndicator;

public class MainActivity extends BaseActivity {

    public TopBar topBar;
    private ViewPagerIndicator viewPagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        viewPagerIndicator = $(this, R.id.viewPagerIndicatorMain);
        topBar = $(this, R.id.topBar);
        topBar.setTopBarLeftGone();
    }

    private void initData() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new NewsFragment());
        fragments.add(new MatchFragment());
        fragments.add(new VideoFragment());
        fragments.add(new MeFragment());
        List<ViewPagerIndicator.IndicatorEntity> indicatorList = new ArrayList<>();
        indicatorList.add(new ViewPagerIndicator.IndicatorEntity("新闻", R.mipmap.news_selected, R.mipmap.news_unselected));
        indicatorList.add(new ViewPagerIndicator.IndicatorEntity("比赛", R.mipmap.news_selected, R.mipmap.news_unselected));
        indicatorList.add(new ViewPagerIndicator.IndicatorEntity("视屏", R.mipmap.news_selected, R.mipmap.news_unselected));
        indicatorList.add(new ViewPagerIndicator.IndicatorEntity("我", R.mipmap.news_selected, R.mipmap.news_unselected));
        viewPagerIndicator.bind(fragments, indicatorList);
        viewPagerIndicator.setScrollable(false);
    }

}
