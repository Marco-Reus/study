package de.bvb.study.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/5/8.
 */
public class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tv = new TextView(getActivity());
        tv.setText(getClass().getSimpleName() + hashCode());
        return tv;
    }

    public <T extends View> T $(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

    public <T extends View> T $(View view, int id) {
        return (T) view.findViewById(id);
    }
}
