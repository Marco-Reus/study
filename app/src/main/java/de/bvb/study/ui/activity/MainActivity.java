package de.bvb.study.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.bvb.study.R;
import de.bvb.study.ui.fragment.MatchFragment;
import de.bvb.study.ui.fragment.MeFragment;
import de.bvb.study.ui.fragment.NewsFragment;
import de.bvb.study.ui.fragment.VideoFragment;
import de.bvb.study.ui.widget.SlidingMenuHorizontalScrollView;
import de.bvb.study.ui.widget.TopBar;
import de.bvb.study.ui.widget.ViewPagerIndicator;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    public TopBar topBar;
    private ViewPagerIndicator viewPagerIndicator;
    private SlidingMenuHorizontalScrollView slidingMenuHorizontalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        slidingMenuHorizontalScrollView = $(this, R.id.slidingMenuHorizontalScrollView);
        viewPagerIndicator = $(this, R.id.viewPagerIndicatorMain);
        topBar = $(this, R.id.topBar);
        topBar.setTopBarLeftIcon(R.mipmap.ic_launcher);
        topBar.setTopBarClickListener(new TopBar.TopBarClickListener() {
            @Override
            public void onRightClick() {

            }

            @Override
            public void onLeftClick() {
                slidingMenuHorizontalScrollView.toggleLeftMenuDisplayState();
            }
        });

        $(this, R.id.ll0).setOnClickListener(this);
        $(this, R.id.ll1).setOnClickListener(this);
        $(this, R.id.ll2).setOnClickListener(this);
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

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (slidingMenuHorizontalScrollView.isLeftMenuVisibility) {
//            slidingMenuHorizontalScrollView.setLeftMenuGone();
//            return false;
//        } else {
//            return super.dispatchTouchEvent(event);
//        }
//    }


    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (slidingMenuHorizontalScrollView.isLeftMenuVisibility) {
                slidingMenuHorizontalScrollView.setLeftMenuGone();
            } else {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    Toast.makeText(this, "在按一次退出", Toast.LENGTH_SHORT).show();
                    mExitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll0:
                break;
            case R.id.ll1:
                break;
            case R.id.ll2:
                break;
        }
        Toast.makeText(this, v.getId() + "aa", Toast.LENGTH_SHORT).show();
    }
}
