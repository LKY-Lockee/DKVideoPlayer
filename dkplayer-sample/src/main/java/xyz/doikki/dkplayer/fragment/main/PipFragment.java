package xyz.doikki.dkplayer.fragment.main;

import android.content.Intent;
import android.view.View;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.pip.AndroidOPiPActivity;
import xyz.doikki.dkplayer.activity.pip.PIPActivity;
import xyz.doikki.dkplayer.activity.pip.PIPListActivity;
import xyz.doikki.dkplayer.activity.pip.TinyScreenActivity;
import xyz.doikki.dkplayer.fragment.BaseFragment;

/**
 * Modified by LKY-Lockee on 2026/6/22
 */
public class PipFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_pip;
    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.btn_pip).setOnClickListener(this);
        findViewById(R.id.btn_pip_in_list).setOnClickListener(this);
        findViewById(R.id.btn_pip_android_o).setOnClickListener(this);
        findViewById(R.id.btn_tiny_screen).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_pip) {
            startActivity(new Intent(getActivity(), PIPActivity.class));
        } else if (id == R.id.btn_pip_in_list) {
            startActivity(new Intent(getActivity(), PIPListActivity.class));
        } else if (id == R.id.btn_pip_android_o) {
            startActivity(new Intent(getActivity(), AndroidOPiPActivity.class));
        } else if (id == R.id.btn_tiny_screen) {
            startActivity(new Intent(getActivity(), TinyScreenActivity.class));
        }
    }
}
