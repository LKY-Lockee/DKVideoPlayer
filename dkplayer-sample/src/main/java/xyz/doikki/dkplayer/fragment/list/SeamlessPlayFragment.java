package xyz.doikki.dkplayer.fragment.list;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;

import androidx.core.app.ActivityOptionsCompat;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.list.DetailActivity;
import xyz.doikki.dkplayer.adapter.VideoRecyclerViewAdapter;
import xyz.doikki.dkplayer.bean.VideoBean;
import xyz.doikki.dkplayer.util.IntentKeys;
import xyz.doikki.dkplayer.util.Tag;
import xyz.doikki.dkplayer.util.Utils;
import xyz.doikki.videoplayer.player.VideoView;

/**
 * 无缝播放
 * <p>
 * Modified by LKY-Lockee on 2026/6/22
 */
public class SeamlessPlayFragment extends RecyclerViewAutoPlayFragment {

    private boolean mSkipToDetail;

    @Override
    protected void initView() {
        super.initView();

        //提前添加到VideoViewManager，供详情使用
        getVideoViewManager().add(mVideoView, Tag.SEAMLESS);

        mAdapter.setOnItemClickListener(position -> {
            mSkipToDetail = true;
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            Bundle bundle = new Bundle();
            VideoBean videoBean = mVideos.get(position);
            if (mCurPos == position) {
                //需要无缝播放
                bundle.putBoolean(IntentKeys.SEAMLESS_PLAY, true);
                bundle.putString(IntentKeys.TITLE, videoBean.getTitle());
            } else {
                //无需无缝播放，把相应数据传到详情页
                mVideoView.release();
                //需要把控制器还原
                mController.setPlayState(VideoView.STATE_IDLE);
                bundle.putBoolean(IntentKeys.SEAMLESS_PLAY, false);
                bundle.putString(IntentKeys.URL, videoBean.getUrl());
                bundle.putString(IntentKeys.TITLE, videoBean.getTitle());
                mCurPos = position;
            }
            intent.putExtras(bundle);
            View itemView = mLinearLayoutManager.findViewByPosition(position);
            if (itemView != null) {
                View sharedView = itemView.findViewById(R.id.player_container);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), sharedView, DetailActivity.VIEW_NAME_PLAYER_CONTAINER);
                requireActivity().startActivity(intent, options.toBundle());
            } else {
                requireActivity().startActivity(intent);
            }
        });
    }

    @Override
    protected void startPlay(int position) {
        mVideoView.setVideoController(mController);
        super.startPlay(position);
    }

    @Override
    protected void pause() {
        if (!mSkipToDetail) {
            super.pause();
        }
    }

    @Override
    protected void resume() {
        if (mSkipToDetail) {
            mSkipToDetail = false;
        } else {
            super.resume();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!addTransitionListener()) {
            restoreVideoView();
        }
    }

    private boolean addTransitionListener() {
        final Transition transition = requireActivity().getWindow().getSharedElementExitTransition();
        if (transition != null) {
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    restoreVideoView();
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                }

                @Override
                public void onTransitionResume(Transition transition) {
                }
            });
            return true;
        }
        return false;
    }

    private void restoreVideoView() {
        //还原播放器
        View itemView = mLinearLayoutManager.findViewByPosition(mCurPos);
        if (itemView == null) return;
        VideoRecyclerViewAdapter.VideoHolder viewHolder = (VideoRecyclerViewAdapter.VideoHolder) itemView.getTag();
        mVideoView = getVideoViewManager().get(Tag.SEAMLESS);
        Utils.removeViewFormParent(mVideoView);
        viewHolder.mPlayerContainer.addView(mVideoView, 0);
        // 请点进去看isDissociate的解释
        mController.addControlComponent(viewHolder.mPrepareView, true);
        mController.setPlayState(mVideoView.getCurrentPlayState());
        mController.setPlayerState(mVideoView.getCurrentPlayerState());

        mRecyclerView.postDelayed(() -> mVideoView.setVideoController(mController), 100);
    }
}
