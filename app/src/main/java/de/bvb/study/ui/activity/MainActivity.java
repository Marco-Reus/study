package de.bvb.study.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.Toast;

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

        viewPagerIndicator.setViewPageCurrentItem(3);
    }

    private void initView() {
        viewPagerIndicator = $(this, R.id.viewPagerIndicatorMain);
        topBar = $(this, R.id.topBar);
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


    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 1000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
