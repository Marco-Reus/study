package de.bvb.study.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import de.bvb.study.R;
import de.bvb.study.entity.download.FileInfo;
import de.bvb.study.service.MyDownloadService;
import de.bvb.study.util.LogUtil;

/**
 * Created by Administrator on 2016/5/8.
 */
public class DownloadingFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

    private View view;
    private TextView tvName;
    private ProgressBar pb;
    private ToggleButton toggleButton;
    FileInfo fileInfo = new FileInfo(0, "WeChat.exe", url, 0, 0);
    public static final String url = "http://dlglobal.qq.com/weixin/Windows/WeChat_C1018.exe";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = view.inflate(getActivity(), R.layout.adapter_downloading_list_view, null);
        tvName = $(view, R.id.tvName);
        pb = $(view, R.id.pb);
        pb.setMax(100);
        toggleButton = $(view, R.id.toggleButtonDownload);
        toggleButton.setOnCheckedChangeListener(this);

//        fileInfo = new FileInfo(0, "WeChat.exe", url, 0, 0);
//        fileInfo = new FileInfo(0, "csdnedu.apk", "http://csdn-app.csdn.net/csdnedu.apk", 0, 0);
        fileInfo = new FileInfo(0, "csdn.apk", "http://csdn-app.csdn.net/csdn.apk", 0, 0);
        tvName.setText(fileInfo.fileName);

        // 注册广播
        IntentFilter filter = new IntentFilter(MyDownloadService.ACTION_UPDATE);
        getActivity().registerReceiver(downBroadcastReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(downBroadcastReceiver);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        toggleButton.setText(isChecked ? "暂停" : "继续");

        Intent intent = new Intent(getActivity(), MyDownloadService.class);
        intent.putExtra(MyDownloadService.EXTRA_KEY_FILE_INFO, fileInfo);
        intent.setAction(isChecked ? MyDownloadService.ACTION_START : MyDownloadService.ACTION_PAUSE);
        getActivity().startService(intent);
    }

    /**
     * 更新进度条ui的广播接收者
     */
    BroadcastReceiver downBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && MyDownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                double progress = intent.getDoubleExtra(MyDownloadService.EXTRA_KEY_DOWNLOADING_PROGRESS, 0d);
                LogUtil.d("download", " receiver " + progress);
                pb.setProgress((int) Math.ceil(progress));
                tvName.setText(fileInfo.fileName + "=====> " + progress + "%");
            }
        }
    };
}
