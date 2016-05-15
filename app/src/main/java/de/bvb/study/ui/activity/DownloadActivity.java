package de.bvb.study.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import de.bvb.study.R;
import de.bvb.study.ui.fragment.DownloadedFragment;
import de.bvb.study.ui.fragment.DownloadingFragment;
import de.bvb.study.ui.widget.TopBar;
import de.bvb.study.ui.widget.ViewPagerIndicator;

/**
 * 下载页面
 */
public class DownloadActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        TopBar topBar = $(this, R.id.topBar);
        topBar.setTopBarTitle("下载");
        ViewPagerIndicator viewPagerIndicator = $(this, R.id.viewPagerIndicatorDownload);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DownloadedFragment());
        fragments.add(new DownloadingFragment());
        List<ViewPagerIndicator.IndicatorEntity> indicatorEntityList = new ArrayList<>();
        indicatorEntityList.add(new ViewPagerIndicator.IndicatorEntity("已下载"));
        indicatorEntityList.add(new ViewPagerIndicator.IndicatorEntity("下载中"));
        viewPagerIndicator.bind(fragments, indicatorEntityList);
        viewPagerIndicator.setViewPageCurrentItem(1);
    }
}
