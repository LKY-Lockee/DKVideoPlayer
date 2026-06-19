package xyz.doikki.dkplayer.activity.extend;

import android.view.View;

import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.BaseActivity;
import xyz.doikki.dkplayer.widget.videoview.ExoVideoView;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videoplayer.exo.ExoMediaPlayer;
import xyz.doikki.videoplayer.exo.ExoMediaSourceHelper;
import xyz.doikki.videoplayer.player.AbstractPlayer;

/**
 * 自定义MediaPlayer，有多种情形：
 * 第一：继承某个现成的MediaPlayer，对其功能进行扩展，此demo就演示了通过继承{@link ExoMediaPlayer}
 * 对其功能进行扩展。
 * 第二：通过继承{@link AbstractPlayer}扩展一些其他的播放器。
 * <p>
 * Modified by LKY-Lockee on 2026/6/22
 */
@UnstableApi
public class CustomExoPlayerActivity extends BaseActivity<ExoVideoView> {

    private ExoMediaSourceHelper mHelper;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_custom_exo_player;
    }

    @Override
    protected void initView() {
        super.initView();
        mVideoView = findViewById(R.id.vv);
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent("custom exo", false);
        mVideoView.setVideoController(controller);
        mHelper = ExoMediaSourceHelper.getInstance(this);
    }

    public void onButtonClick(View view) {
        mVideoView.release();
        mVideoView.setCacheEnabled(false);
        int id = view.getId();
        if (id == R.id.btn_cache) {
            mVideoView.setCacheEnabled(true);
            mVideoView.setUrl("http://playertest.longtailvideo.com/adaptive/bipbop/gear4/prog_index.m3u8");
        } else if (id == R.id.btn_concat) {//将多个视频拼接在一起播放
            List<MediaItem> mediaItems = Arrays.asList(
                    MediaItem.fromUri("http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4"),
                    MediaItem.fromUri("http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4"),
                    MediaItem.fromUri("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319222227698228.mp4"));
            mVideoView.setMediaItems(mediaItems);
        } else if (id == R.id.btn_clip) {
            //裁剪10-15秒的内容进行播放
            MediaItem clippedItem = new MediaItem.Builder()
                    .setUri("http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4")
                    .setClippingConfiguration(
                            new MediaItem.ClippingConfiguration.Builder()
                                    .setStartPositionUs(10_000_000)
                                    .setEndPositionUs(15_000_000)
                                    .build())
                    .build();
            mVideoView.setMediaItems(Collections.singletonList(clippedItem));
        } else if (id == R.id.btn_dash) {
            mVideoView.setUrl("http://www.bok.net/dash/tears_of_steel/cleartext/stream.mpd");
        } else if (id == R.id.btn_rtsp) {
            mVideoView.setUrl("rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov");
        }

        mVideoView.start();
    }
}
