package de.bvb.study.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import de.bvb.study.R;
import de.bvb.study.ui.widget.video.MediaHelp;
import de.bvb.study.ui.widget.video.VideoMediaController;
import de.bvb.study.ui.widget.video.VideoSuperPlayer;

/**
 * Created by Administrator on 2016/5/15.
 */
public class VideoDetailActivity extends BaseActivity {
    private VideoSuperPlayer mVideo;

    public static void playInFullScreen(Context context, String url, int position) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_detail);

        mVideo = (VideoSuperPlayer) findViewById(R.id.video);
        String url = getIntent().getExtras().getString("url");
        mVideo.loadAndPlay(MediaHelp.getInstance(), url, getIntent()
                .getExtras().getInt("position"), true);
        mVideo.setPageType(VideoMediaController.PageType.EXPAND);
        mVideo.setVideoPlayCallback(new VideoSuperPlayer.VideoPlayCallbackImpl() {

            @Override
            public void onSwitchPageType() {
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    finish();
                }
            }

            @Override
            public void onPlayFinish() {
                finish();
            }

            @Override
            public void onCloseVideo() {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("position", mVideo.getCurrentPosition());
        setResult(RESULT_OK, intent);
        super.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaHelp.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaHelp.resume();
    }
}
