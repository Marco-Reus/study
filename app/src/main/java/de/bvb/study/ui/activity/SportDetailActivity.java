package de.bvb.study.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import de.bvb.study.R;
import de.bvb.study.ui.widget.TopBar;
import de.bvb.study.util.LogUtil;

/**
 * Created by Administrator on 2016/5/15.
 */
public class SportDetailActivity extends BaseActivity {

    public static final String EXTRA_KEY_URL = "EXTRA_KEY_URL";
    private WebView webView;
    private ProgressBar pbDetail;
    private TopBar topBar;
    private String mUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_detail);
        if (getIntent() == null) return;
        mUrl = getIntent().getStringExtra(EXTRA_KEY_URL);
        LogUtil.d("news", "detail url :" + mUrl);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initView() {
        topBar = $(this, R.id.topBar);
        pbDetail = $(this, R.id.pbDetail);
        webView = $(this, R.id.webViewDetail);
        topBar.setTopBarTitle("体育新闻");
    }

    private void initData() {
        WebSettings webSettings = webView.getSettings();
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBuiltInZoomControls(true);//support zoom
        //webSettings.setPluginsEnabled(true);//support flash
        webSettings.setUseWideViewPort(true);// 这个很关键
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //优先使用缓存

        webView.loadUrl(mUrl);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbDetail.setVisibility(View.GONE);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int loadingProgress) {
                if (loadingProgress == 100) {
                    // 网页加载完成

                } else {
                    // 加载中
                    pbDetail.setProgress(loadingProgress);
                    if (pbDetail.getVisibility() != View.VISIBLE)
                        pbDetail.setVisibility(View.VISIBLE);
                }

            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();//返回上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
