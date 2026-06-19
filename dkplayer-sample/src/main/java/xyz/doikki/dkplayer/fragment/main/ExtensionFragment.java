package xyz.doikki.dkplayer.fragment.main;

import android.content.Intent;
import android.view.View;

import androidx.media3.common.util.UnstableApi;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.api.PlayerActivity;
import xyz.doikki.dkplayer.activity.extend.ADActivity;
import xyz.doikki.dkplayer.activity.extend.CacheActivity;
import xyz.doikki.dkplayer.activity.extend.CustomExoPlayerActivity;
import xyz.doikki.dkplayer.activity.extend.CustomIjkPlayerActivity;
import xyz.doikki.dkplayer.activity.extend.DanmakuActivity;
import xyz.doikki.dkplayer.activity.extend.DefinitionPlayerActivity;
import xyz.doikki.dkplayer.activity.extend.FullScreenActivity;
import xyz.doikki.dkplayer.activity.extend.PadActivity;
import xyz.doikki.dkplayer.activity.extend.PlayListActivity;
import xyz.doikki.dkplayer.fragment.BaseFragment;
import xyz.doikki.dkplayer.util.DataUtil;

/**
 * Modified by LKY-Lockee on 2026/6/22
 */
public class ExtensionFragment extends BaseFragment implements View.OnClickListener {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_extension;
    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.btn_fullscreen).setOnClickListener(this);
        findViewById(R.id.btn_danmu).setOnClickListener(this);
        findViewById(R.id.btn_ad).setOnClickListener(this);
        findViewById(R.id.btn_proxy_cache).setOnClickListener(this);
        findViewById(R.id.btn_play_list).setOnClickListener(this);
        findViewById(R.id.btn_pad).setOnClickListener(this);
        findViewById(R.id.btn_custom_exo_player).setOnClickListener(this);
        findViewById(R.id.btn_custom_ijk_player).setOnClickListener(this);
        findViewById(R.id.btn_definition).setOnClickListener(this);
        findViewById(R.id.btn_custom_render_view).setOnClickListener(this);
    }

    @UnstableApi
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_fullscreen) {
            startActivity(new Intent(getActivity(), FullScreenActivity.class));
        } else if (id == R.id.btn_danmu) {
            startActivity(new Intent(getActivity(), DanmakuActivity.class));
        } else if (id == R.id.btn_ad) {
            startActivity(new Intent(getActivity(), ADActivity.class));
        } else if (id == R.id.btn_proxy_cache) {
            startActivity(new Intent(getActivity(), CacheActivity.class));
        } else if (id == R.id.btn_play_list) {
            startActivity(new Intent(getActivity(), PlayListActivity.class));
        } else if (id == R.id.btn_pad) {
            startActivity(new Intent(getActivity(), PadActivity.class));
        } else if (id == R.id.btn_custom_exo_player) {
            startActivity(new Intent(getActivity(), CustomExoPlayerActivity.class));
        } else if (id == R.id.btn_custom_ijk_player) {
            startActivity(new Intent(getActivity(), CustomIjkPlayerActivity.class));
        } else if (id == R.id.btn_definition) {
            startActivity(new Intent(getActivity(), DefinitionPlayerActivity.class));
        } else if (id == R.id.btn_custom_render_view) {
            PlayerActivity.start(requireActivity(), DataUtil.SAMPLE_URL, getString(R.string.str_custom_render_view), false, true);
        }
    }
}
