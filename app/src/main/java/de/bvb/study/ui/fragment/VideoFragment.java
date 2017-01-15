package de.bvb.study.ui.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.bvb.study.R;
import de.bvb.study.common.MyBaseAdapter;
import de.bvb.study.entity.VideoListViewAdapterEntity;
import de.bvb.study.entity.download.FileInfo;
import de.bvb.study.service.MyDownloadService;
import de.bvb.study.ui.activity.VideoDetailActivity;
import de.bvb.study.ui.widget.video.MediaHelp;
import de.bvb.study.ui.widget.video.VideoSuperPlayer;
import de.bvb.study.util.BitmapUtil;
import de.bvb.study.util.ListUtil;
import de.bvb.study.util.LogUtil;
import de.bvb.study.util.NumberUtil;
import de.bvb.study.util.ViewUtil;

/**
 * Created by Administrator on 2016/5/8.
 */
public class VideoFragment extends BaseFragment {
    private View view;
    private ListView lv;
    private List<VideoListViewAdapterEntity> dataList;
    private VideoAdapter adapter;
    private String[] url = {"http://byebyebeautiful.com/mv/ByeByeBeautiful.mp4", "http://byebyebeautiful.com/mv/Nemo.mp4"};
    //  private String[] url = {"/sdcard/qqmusic/mv/mv.mp4", "http://data.vod.itc.cn/?new=/134/254/8qoaCXUIovFdt1gLOlJitB.mp4&vid=1001883384&plat=17&mkey=zODjT9WGnUfZSPKp9b0wPn3fyI5KBNUV&ch=tv&prod=app"};
    private Bitmap[] bitmaps = {BitmapUtil.getBitmap(R.mipmap.bg_main), BitmapUtil.getBitmap(R.mipmap.bg_main)};

    private boolean isPlaying;
    private int indexPosition = -1;

    class VideoAdapter extends MyBaseAdapter {

        class ViewHolder {
            TextView info_title;
            VideoSuperPlayer mVideoViewLayout;
            ImageView mPlayBtnView, icon;
            ImageView imageViewDiscuss, imageViewShare, imageViewDownload;
        }


        @Override
        public int getCount() {
            return ListUtil.getLength(dataList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.adapter_video_list_view, null);
                holder = new ViewHolder();

                holder.info_title = ViewUtil.$(convertView, R.id.info_title);
                holder.mVideoViewLayout = ViewUtil.$(convertView, R.id.video);
                holder.mPlayBtnView = ViewUtil.$(convertView, R.id.play_btn);
                holder.icon = ViewUtil.$(convertView, R.id.icon);

                holder.imageViewDiscuss = ViewUtil.$(convertView, R.id.imageViewDiscuss);
                holder.imageViewShare = ViewUtil.$(convertView, R.id.imageViewShare);
                holder.imageViewDownload = ViewUtil.$(convertView, R.id.imageViewDownload);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ActionOnClickListener actionOnClickListener = new ActionOnClickListener(position);
            holder.imageViewDiscuss.setOnClickListener(actionOnClickListener);
            holder.imageViewShare.setOnClickListener(actionOnClickListener);
            holder.imageViewDownload.setOnClickListener(actionOnClickListener);

            holder.info_title.setText(dataList.get(position).title);
            holder.icon.setImageBitmap(dataList.get(position).bitmap);
            if (indexPosition == position) {
                holder.mVideoViewLayout.setVisibility(View.VISIBLE);
            } else {
                holder.mVideoViewLayout.setVisibility(View.GONE);
                holder.mVideoViewLayout.close();
            }
            holder.mPlayBtnView.setOnClickListener(new MyOnclick(
                    holder.mPlayBtnView, holder.mVideoViewLayout, position));

            return convertView;
        }

        class MyOnclick implements View.OnClickListener {
            VideoSuperPlayer mSuperVideoPlayer;
            ImageView mPlayBtnView;
            int position;

            public MyOnclick(ImageView mPlayBtnView,
                             VideoSuperPlayer mSuperVideoPlayer, int position) {
                this.position = position;
                this.mSuperVideoPlayer = mSuperVideoPlayer;
                this.mPlayBtnView = mPlayBtnView;
            }

            @Override
            public void onClick(View v) {
                MediaHelp.release();
                indexPosition = position;
                isPlaying = true;
                mSuperVideoPlayer.setVisibility(View.VISIBLE);
                mSuperVideoPlayer.loadAndPlay(MediaHelp.getInstance(), dataList.get(position).url, 0, false);
                mSuperVideoPlayer.setVideoPlayCallback(new MyVideoPlayCallback(
                        mPlayBtnView, mSuperVideoPlayer, dataList.get(position)));
                notifyDataSetChanged();
            }
        }

        class MyVideoPlayCallback implements VideoSuperPlayer.VideoPlayCallbackImpl {
            ImageView mPlayBtnView;
            VideoSuperPlayer mSuperVideoPlayer;
            VideoListViewAdapterEntity info;

            public MyVideoPlayCallback(ImageView mPlayBtnView,
                                       VideoSuperPlayer mSuperVideoPlayer, VideoListViewAdapterEntity info) {
                this.mPlayBtnView = mPlayBtnView;
                this.info = info;
                this.mSuperVideoPlayer = mSuperVideoPlayer;
            }

            @Override
            public void onCloseVideo() {
                closeVideo();
            }

            @Override
            public void onSwitchPageType() {
                if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    Intent intent = new Intent(new Intent(getActivity(),
                            VideoDetailActivity.class));
                    intent.putExtra("video", info);
                    intent.putExtra("position",
                            mSuperVideoPlayer.getCurrentPosition());
                    getActivity().startActivityForResult(intent, 1);
                }
            }

            @Override
            public void onPlayFinish() {
                closeVideo();
            }

            private void closeVideo() {
                isPlaying = false;
                indexPosition = -1;
                mSuperVideoPlayer.close();
                MediaHelp.release();
                mPlayBtnView.setVisibility(View.VISIBLE);
                mSuperVideoPlayer.setVisibility(View.GONE);
            }

        }


        class ActionOnClickListener implements View.OnClickListener {
            int position;

            public ActionOnClickListener(int position) {
                this.position = position;
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.imageViewDiscuss: // 评论
                        break;
                    case R.id.imageViewShare: // 分享
                        break;
                    case R.id.imageViewDownload: // 下载
                        LogUtil.d("download", "开始下载 " + dataList.get(position).url);
                        FileInfo fileInfo = new FileInfo(dataList.get(position).url);
                        MyDownloadService.startDownload(getActivity(), fileInfo);
                        break;
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(getActivity(), R.layout.fragment_video, null);
        lv = $(view, R.id.listViewVideo);

        dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add(new VideoListViewAdapterEntity(url[i % 2], "title " + i, bitmaps[i % 2]));
        }
        adapter = new VideoAdapter();
        lv.setAdapter(adapter);

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isPlaying && !NumberUtil.isInRange(indexPosition, lv.getFirstVisiblePosition(), lv.getLastVisiblePosition())) {
                    indexPosition = -1;
                    isPlaying = false;
                    adapter.notifyDataSetChanged();
                    MediaHelp.release();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view;
    }
}
