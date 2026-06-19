package xyz.doikki.dkplayer.activity.pip;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.bumptech.glide.Glide;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.BaseActivity;
import xyz.doikki.dkplayer.util.DataUtil;
import xyz.doikki.dkplayer.util.PIPManager;
import xyz.doikki.dkplayer.util.Tag;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videoplayer.player.VideoView;

/**
 * Modified by LKY-Lockee on 2026/6/22
 */
@SuppressWarnings("rawtypes")
public class PIPActivity extends BaseActivity {

    private PIPManager mPIPManager;

    private final OnBackPressedCallback mBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if (mPIPManager.onBackPress()) return;
            setEnabled(false);
            getOnBackPressedDispatcher().onBackPressed();
            setEnabled(true);
        }
    };

    private final ActivityResultLauncher<Intent> mOverlayPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (Settings.canDrawOverlays(PIPActivity.this)) {
                    mPIPManager.startFloatWindow();
                    mPIPManager.resume();
                    finish();
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pip);
        getOnBackPressedDispatcher().addCallback(this, mBackPressedCallback);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.str_pip_demo);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        FrameLayout playerContainer = findViewById(R.id.player_container);
        mPIPManager = PIPManager.getInstance();
        VideoView videoView = getVideoViewManager().get(Tag.PIP);
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent(getString(R.string.str_pip), false);
        videoView.setVideoController(controller);
        if (mPIPManager.isStartFloatWindow()) {
            mPIPManager.stopFloatWindow();
            controller.setPlayerState(videoView.getCurrentPlayerState());
            controller.setPlayState(videoView.getCurrentPlayState());
        } else {
            mPIPManager.setActClass(PIPActivity.class);
            ImageView thumb = controller.findViewById(R.id.thumb);
            Glide.with(this)
                    .load("http://sh.people.com.cn/NMediaFile/2016/0112/LOCAL201601121344000138197365721.jpg")
                    .placeholder(android.R.color.darker_gray)
                    .into(thumb);
            videoView.setUrl(DataUtil.SAMPLE_URL);
        }
        playerContainer.addView(videoView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPIPManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPIPManager.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPIPManager.reset();
    }


    public void startFloatWindow(View view) {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            mOverlayPermissionLauncher.launch(intent);
        } else {
            mPIPManager.startFloatWindow();
            mPIPManager.resume();
            finish();
        }
    }
}
