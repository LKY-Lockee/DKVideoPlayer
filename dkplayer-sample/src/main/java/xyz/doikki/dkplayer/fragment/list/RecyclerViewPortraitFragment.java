package xyz.doikki.dkplayer.fragment.list;

import xyz.doikki.dkplayer.util.Utils;
import xyz.doikki.dkplayer.widget.controller.PortraitWhenFullScreenController;
import xyz.doikki.videocontroller.component.CompleteView;
import xyz.doikki.videocontroller.component.ErrorView;
import xyz.doikki.videocontroller.component.GestureView;
import xyz.doikki.videocontroller.component.TitleView;
import xyz.doikki.videoplayer.player.VideoView;

/**
 * 全屏后手动横屏，并不完美，仅做参考
 * <p>
 * Modified by LKY-Lockee on 2026/6/22
 */
public class RecyclerViewPortraitFragment extends RecyclerViewAutoPlayFragment {

    @Override
    protected void initVideoView() {
        mVideoView = new VideoView(requireActivity());
        mVideoView.setOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                if (playState == VideoView.STATE_IDLE) {
                    Utils.removeViewFormParent(mVideoView);
                    mLastPos = mCurPos;
                    mCurPos = -1;
                }
            }
        });
        mController = new PortraitWhenFullScreenController(requireActivity());
        mErrorView = new ErrorView(requireActivity());
        mController.addControlComponent(mErrorView);
        mCompleteView = new CompleteView(requireActivity());
        mController.addControlComponent(mCompleteView);
        mTitleView = new TitleView(requireActivity());
        mController.addControlComponent(mTitleView);
        mController.addControlComponent(new GestureView(requireActivity()));
        mController.setEnableOrientation(true);
        mVideoView.setVideoController(mController);
    }

    @Override
    public void onItemChildClick(int position) {
        mVideoView.startFullScreen();
        super.onItemChildClick(position);
    }
}
