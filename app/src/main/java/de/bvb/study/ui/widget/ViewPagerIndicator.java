package de.bvb.study.ui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.List;

import de.bvb.study.util.DimenUtil;
import de.bvb.study.util.LogUtil;
import de.bvb.study.util.ScreenUtil;

/**
 * 使用 ViewPager 和 RadioGroup 封装的一个导航控件<p/>
 * 有2种布局方式:<br/>
 * 1. ViewPager +　图标在上文本在下的tab 栏<br/>
 * 2. 仅有文本的tab + ViewPager<br/>
 * 只需要调用 viewPagerIndicator.bind(fragments, indicatorEntityList) 方法<br/>
 * 其中如果indicatorEntityList 中如果传入图标,就是布局方式1,否则就是布局方式2<br/>
 * <br/> Fragment需要用v4的 ,Activity需要继承自 FragmentActivity
 */
public class ViewPagerIndicator extends LinearLayout implements RadioGroup.OnCheckedChangeListener {

    /**
     * 需要的实体类<p/>
     * 如果只设置tab的名字, 则不会显示图标<br/>
     * 如果设置了图标,会显示对应的图标
     */
    public static class IndicatorEntity {
        /** tab的名字 */
        String indicator;
        /** tab的选中状态和非选中状态的图标 */
        int selectedRes, unSelectedRes;
        boolean isShowIconEnable;

        /**
         * @param indicator tab的名字
         */
        public IndicatorEntity(String indicator) {
            this.indicator = indicator;
            isShowIconEnable = false;
        }

        /**
         * @param indicator     tab的名字
         * @param selectedRes   tab的选中状态的图标
         * @param unSelectedRes tab的非选中状态的图标
         */
        public IndicatorEntity(String indicator, int selectedRes, int unSelectedRes) {
            this.indicator = indicator;
            this.selectedRes = selectedRes;
            this.unSelectedRes = unSelectedRes;
            isShowIconEnable = true;
        }
    }

    /** 选中和非选中tab的前景色, 也就是字体颜色 */
    public static final int selected_fg_Color = 0xff0000ff, unselected_fg_Color = 0xFF000000;
    /** 选中和非选中tab的背景色 */
    public static final int selected_bg_Color = 0xff00ff00, unselected_bg_Color = 0xcccccccc;
    /** 分隔线的颜色 */
    public static final int divide_line_color = 0xFF000000;
    /** 指示条的颜色 */
    public static final int indicator_color = 0xFF0000FF;


    // 是否允许ViewPager 滑动
    private boolean scrollable = true;
    // radioGroup 即tab 栏的高度,可以使用 DimenUtil 传入dp单位的高度
    private final int radioGroupShowIconHeight = DimenUtil.dip2px(54f);
    private final int radioGroupGoneIconHeight = DimenUtil.dip2px(36f);
    private int radioGroupHeight = radioGroupShowIconHeight;
    private Context context;
    // 屏幕宽度
    private int mScreenWidth;
    private RadioGroup mRadioGroup;
    private MyViewPager mViewPager;
    private List<IndicatorEntity> indicatorEntityList;
    // 是否显示图标
    private boolean isShowIconEnable = true;

    /** 上一次的指示条的位置 */
    private int lastSelectedIndex;
    /** 指示条控件 */
    private View indicatorView;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.setOrientation(VERTICAL);
        // 获取屏幕宽度
        mScreenWidth = ScreenUtil.getScreenWidthPixels(context);
        LogUtil.d("mScreenWidth " + mScreenWidth);
    }

    /** 设置是否允许ViewPager 滑动 */
    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    /** 设置 viewPager 的页面 */
    public void setViewPageCurrentItem(int index) {
        mViewPager.setCurrentItem(index);
    }

    /**
     * 绑定页面<br/>
     * fragments 和 indicatorEntityList 的数量必须一致
     * <p/>
     *
     * @param fragments           绑定的fragment
     * @param indicatorEntityList 绑定的tab的名字或者图标
     */
    public void bind(List<Fragment> fragments, List<IndicatorEntity> indicatorEntityList) {
        if (fragments == null || fragments.size() <= 0 || indicatorEntityList == null || indicatorEntityList.size() <= 0 || fragments.size() != indicatorEntityList.size()) {
            new InvalidParameterException("fragments 和 indicatorEntityList 的数量必须一致");
        }
        this.indicatorEntityList = indicatorEntityList;
        isShowIconEnable = indicatorEntityList.get(0).isShowIconEnable;
        initViews();
        mViewPager.setFragments(fragments);
        for (int i = 0; i < indicatorEntityList.size(); i++) {
            mRadioGroup.addView(generateRadioButton(i));
        }
        //设置第一个tab为选中状态
        ((RadioButton) mRadioGroup.getChildAt(0)).setChecked(true);
        mViewPager.setCurrentItem(0);
    }

    /** 分隔线 */
    private View generateDivideLine() {
        View view = new View(context);
        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        view.setBackgroundColor(divide_line_color);
        return view;
    }

    private void initViewPager() {
        mViewPager = new MyViewPager(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        layoutParams.weight = 1;
        mViewPager.setLayoutParams(layoutParams);
        mViewPager.setId((int) (System.currentTimeMillis() % 10000)); // Id 必须设置,不然会有 NotFoundException 异常
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    private void initRadioGroup() {
        radioGroupHeight = isShowIconEnable ? radioGroupShowIconHeight : radioGroupGoneIconHeight;
        mRadioGroup = new RadioGroup(context);
        mRadioGroup.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, radioGroupHeight));
        mRadioGroup.setOrientation(HORIZONTAL);
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    /** 初始化需要添加的添加控件以及事件 */
    private void initViews() {
        initViewPager();
        initRadioGroup();
        if (isShowIconEnable) {
            this.addView(mViewPager);
            this.addView(generateDivideLine());
            this.addView(mRadioGroup);
        } else {
            this.addView(mRadioGroup);
            initIndicatorView();
            this.addView(indicatorView);
            this.addView(generateDivideLine());
            this.addView(mViewPager);
        }
    }

    private void initIndicatorView() {
        indicatorView = new View(context);
        LayoutParams layoutParams = new LayoutParams((int) (mScreenWidth / indicatorEntityList.size() * 0.8f), radioGroupHeight / 10);
        layoutParams.setMargins((int) (mScreenWidth / indicatorEntityList.size() * 0.1f), -layoutParams.height, 0, 0);
        indicatorView.setLayoutParams(layoutParams);
        indicatorView.setBackgroundColor(indicator_color);
    }

    private RadioButton generateRadioButton(int index) {
        RadioButton rb = new RadioButton(context);
        // 均分宽度,TODO 不知道这里用 weight 为什么无法显示出来
        rb.setLayoutParams(new LayoutParams(mScreenWidth / indicatorEntityList.size(), ViewGroup.LayoutParams.MATCH_PARENT));
        rb.setId(index);
        rb.setTextSize(12);
        rb.setGravity(Gravity.CENTER);
        rb.setText(indicatorEntityList.get(index).indicator);
        rb.setPadding(0, radioGroupHeight / 10, 0, radioGroupHeight / 10); // 设置上下边距,不至于挨着边
//        rb.setCompoundDrawablePadding(2); // 文本和图片的间距
        rb.setButtonDrawable(android.R.color.transparent); // 去掉圆圈圈
        return rb;
    }

    /** 处理tab切换以后的ui状态 */
    private void setSelectedItem(int position) {
        for (int i = 0; i < indicatorEntityList.size(); i++) {
            ((RadioButton) mRadioGroup.getChildAt(i)).setTextColor(i == position ? selected_fg_Color : unselected_fg_Color); // 字体颜色
            if (isShowIconEnable) {
                Drawable drawable = ContextCompat.getDrawable(context, i == position ? indicatorEntityList.get(i).selectedRes : indicatorEntityList.get(i).unSelectedRes);
                drawable.setBounds(0, 0, radioGroupHeight / 2, radioGroupHeight / 2);
                ((RadioButton) mRadioGroup.getChildAt(i)).setCompoundDrawables(null, drawable, null, null);// 图标
            } else {
                mRadioGroup.getChildAt(i).setBackgroundColor(i == position ? selected_bg_Color : unselected_bg_Color); // 背景颜色
            }
        }
        // 设置 indicatorView 动画 TODO indicatorView 动画是在 ViewPager 滑动动画完成后执行的,不是和 ViewPager 同步的
        if (indicatorView != null && lastSelectedIndex != position) {
            ObjectAnimator.ofFloat(indicatorView, "TranslationX", lastSelectedIndex * mScreenWidth / indicatorEntityList.size(), position * mScreenWidth / indicatorEntityList.size())
                    .setDuration(300).start();
            lastSelectedIndex = position;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (mViewPager != null) mViewPager.setCurrentItem(checkedId);
        setSelectedItem(checkedId);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            setSelectedItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    /**
     * 自定义ViewPager<p/>
     * Override onTouchEvent() 和  onInterceptTouchEvent()  是为了屏蔽滑动事件<br/>
     * Override setCurrentItem()  是为了在点击非相邻的tab时,避免中间滚动太多<br/>
     */
    class MyViewPager extends ViewPager {

        private Context context;
        private List<Fragment> mFragments;
        private MyFragmentPagerAdapter mAdapter;

        public MyViewPager(Context context) {
            this(context, null);
        }

        public MyViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.context = context;
        }

        /**
         * 如果Activity不是继承 FragmentActivity,会crash掉
         *
         * @param fragments
         */
        public void setFragments(List<Fragment> fragments) {
            if (!(context instanceof FragmentActivity)) {
                throw new ClassCastException("activity need to extents FragmentActivity");
            }
            mAdapter = new MyFragmentPagerAdapter(((FragmentActivity) context).getSupportFragmentManager());
            this.setAdapter(mAdapter);
            mFragments = fragments;
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            return scrollable && super.onTouchEvent(ev);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return scrollable && super.onInterceptTouchEvent(ev);
        }

        @Override
        public void setCurrentItem(int item) {
            try {
                Field mFirstLayout = ViewPager.class.getDeclaredField("mFirstLayout");
                mFirstLayout.setAccessible(true);
                mFirstLayout.set(this, true);
                getAdapter().notifyDataSetChanged();
                super.setCurrentItem(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

            public MyFragmentPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments == null ? null : mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments == null ? 0 : mFragments.size();
            }
        }
    }
}
