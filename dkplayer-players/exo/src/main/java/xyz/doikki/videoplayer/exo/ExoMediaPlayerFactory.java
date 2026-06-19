package xyz.doikki.videoplayer.exo;

import android.content.Context;

import androidx.media3.common.util.UnstableApi;

import xyz.doikki.videoplayer.player.PlayerFactory;

/**
 * Modified by LKY-Lockee on 2026/6/22
 */
public class ExoMediaPlayerFactory extends PlayerFactory<ExoMediaPlayer> {

    public static ExoMediaPlayerFactory create() {
        return new ExoMediaPlayerFactory();
    }

    @UnstableApi
    @Override
    public ExoMediaPlayer createPlayer(Context context) {
        return new ExoMediaPlayer(context);
    }
}
