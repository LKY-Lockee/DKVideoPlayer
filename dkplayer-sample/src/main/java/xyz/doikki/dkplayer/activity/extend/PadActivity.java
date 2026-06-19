package xyz.doikki.dkplayer.activity.extend;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.BaseActivity;
import xyz.doikki.dkplayer.util.DataUtil;
import xyz.doikki.videocontroller.StandardVideoController;

/**
 * Modified by LKY-Lockee on 2026/6/22
 */
@SuppressWarnings("rawtypes")
public class PadActivity extends BaseActivity {

    private StandardVideoController mController;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pad;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (mController.isLocked()) {
                    mController.show();
                    Toast.makeText(PadActivity.this, R.string.dkplayer_lock_tip, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mVideoView.isFullScreen()) {
                    mVideoView.stopFullScreen();
                    return;
                }
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        mVideoView = findViewById(R.id.video_view);

        mVideoView.setUrl(DataUtil.SAMPLE_URL);

        mController = new StandardVideoController(this);
        mController.addDefaultControlComponent("pad", false);

        mController.findViewById(R.id.fullscreen).setOnClickListener(v -> {
            if (mVideoView.isFullScreen()) {
                mVideoView.stopFullScreen();
            } else {
                mVideoView.startFullScreen();
            }
        });

        mController.findViewById(R.id.back).setOnClickListener(v -> mVideoView.stopFullScreen());

        mVideoView.setVideoController(mController);

        mVideoView.start();
    }
}
