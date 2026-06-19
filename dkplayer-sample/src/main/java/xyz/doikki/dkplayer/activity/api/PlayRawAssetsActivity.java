package xyz.doikki.dkplayer.activity.api;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.net.Uri;
import android.view.View;

import androidx.media3.common.util.UnstableApi;

import java.io.IOException;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.BaseActivity;
import xyz.doikki.dkplayer.util.Utils;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videoplayer.exo.ExoMediaPlayerFactory;
import xyz.doikki.videoplayer.player.VideoView;

/**
 * 播放raw/assets视频
 * <p>
 * Modified by LKY-Lockee on 2026/6/22
 */

public class PlayRawAssetsActivity extends BaseActivity<VideoView> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_play_raw_assets;
    }

    @Override
    protected int getTitleResId() {
        return R.string.str_raw_or_assets;
    }

    @Override
    protected void initView() {
        super.initView();
        mVideoView = findViewById(R.id.player);
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent(getString(R.string.str_raw_or_assets), false);
        mVideoView.setVideoController(controller);
    }

    @UnstableApi
    public void onButtonClick(View view) {
        mVideoView.release();
        Object playerFactory = Utils.getCurrentPlayerFactoryInVideoView(mVideoView);

        int id = view.getId();
        if (id == R.id.btn_raw) {
            Uri rawUri = new Uri.Builder()
                    .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                    .path(Integer.toString(R.raw.movie))
                    .build();
            mVideoView.setUrl(rawUri.toString());
        } else if (id == R.id.btn_assets) {
            if (playerFactory instanceof ExoMediaPlayerFactory) { //ExoPlayer
                mVideoView.setUrl("file:///android_asset/" + "test.mp4");
            } else { //MediaPlayer,IjkPlayer
                AssetManager am = getResources().getAssets();
                AssetFileDescriptor afd = null;
                try {
                    afd = am.openFd("test.mp4");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mVideoView.setAssetFileDescriptor(afd);
            }
        }

        mVideoView.start();
    }
}
