package de.bvb.study.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;

import de.bvb.study.R;
import de.bvb.study.util.ScreenUtil;

/**
 * 仿 qq 侧滑菜单
 */
public class SlidingMenuHorizontalScrollView extends HorizontalScrollView {

    private int screenWidthPixels;
    public boolean isLeftMenuVisibility;

    public SlidingMenuHorizontalScrollView(Context context) {
        this(context, null);
    }

    public SlidingMenuHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenuHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        screenWidthPixels = ScreenUtil.getScreenWidthPixels(context);
        //自定义属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenuHorizontalScrollView, defStyleAttr, 0);
        contentWidthWhenLeftMenuVisibility = a.getDimensionPixelSize(R.styleable.SlidingMenuHorizontalScrollView_contentWidthWhenLeftMenuVisibility, screenWidthPixels / 4);// 默认值为屏幕宽度/4
        leftMenuBgColor = a.getColor(R.styleable.SlidingMenuHorizontalScrollView_leftMenuBgColor, Color.TRANSPARENT);
        a.recycle();
    }

    private LinearLayout wrapper;
    private ViewGroup menu;
    private ViewGroup content;
    private int contentWidthWhenLeftMenuVisibility;
    private int menuWidth;

    private boolean hasNotMeasured = true;
    private int leftMenuBgColor;

    //  设置子view的宽高,以及自己的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (hasNotMeasured) {
            wrapper = (LinearLayout) getChildAt(0);
            menu = (ViewGroup) wrapper.getChildAt(0);
            content = (ViewGroup) wrapper.getChildAt(1);
            menuWidth = menu.getLayoutParams().width = screenWidthPixels - contentWidthWhenLeftMenuVisibility;
            content.getLayoutParams().width = screenWidthPixels;
            hasNotMeasured = false;
            menu.setBackgroundColor(leftMenuBgColor);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    // 通过设置偏移量,将menu隐藏
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(menuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                if (getScrollX() >= menuWidth / 2) { // 隐藏菜单
                    this.smoothScrollTo(menuWidth, 0);
                    isLeftMenuVisibility = false;
                } else { //显示菜单
                    this.smoothScrollTo(0, 0);
                    isLeftMenuVisibility = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        // 获取缩放的比例
        float scale = l * 1f / menuWidth;// [1,0]
        float contentScale = 0.7f + 0.3f * scale;
        float leftMenuScale = 1f - 0.3f * scale;
        float leftMenuAlpha = 1f - 0.7f * scale;

        ViewHelper.setTranslationX(menu, menuWidth * scale * 0.7f);
        ViewHelper.setScaleX(menu, leftMenuScale);
        ViewHelper.setScaleY(menu, leftMenuScale);
        ViewHelper.setAlpha(menu, leftMenuAlpha);

        ViewHelper.setPivotX(content, 0);
        ViewHelper.setPivotY(content, content.getHeight() / 2);
        ViewHelper.setScaleX(content, contentScale);
        ViewHelper.setScaleY(content, contentScale);
    }

    public void setLeftMenuVisibility() {
        if (isLeftMenuVisibility) return;
        this.smoothScrollTo(0, 0);
        isLeftMenuVisibility = true;
    }

    public void setLeftMenuGone() {
        if (!isLeftMenuVisibility) return;
        this.smoothScrollTo(menuWidth, 0);
        isLeftMenuVisibility = false;
    }

    public void toggleLeftMenuDisplayState() {
        if (isLeftMenuVisibility) {
            setLeftMenuGone();
        } else {
            setLeftMenuVisibility();
        }
    }
}
