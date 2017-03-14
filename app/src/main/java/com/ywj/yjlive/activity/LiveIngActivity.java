package com.ywj.yjlive.activity;

import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ywj.yjlive.R;
import com.ywj.yjlive.activity.base.FrameActivity;
import com.ywj.yjlive.fragment.LiveViewFragment;
import com.ywj.yjlive.fragment.MainDialogFragment;

import java.io.IOException;


/**
 * Created by Administrator on 2017/3/13 0013.
 */

public class LiveIngActivity extends FrameActivity {

    private KSYMediaPlayer ksyMediaPlayer;
    private Surface mSurface = null;
    private SurfaceView mVideoSurfaceView = null;
    private SurfaceHolder mSurfaceHolder = null;
    private final SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            ksyMediaPlayer.setDisplay(holder);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livning_ing);
        initPlayer();
        LiveViewFragment liveViewFragment = new LiveViewFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.flmain, liveViewFragment).commit();
        new MainDialogFragment().show(getSupportFragmentManager(),"MainDialogFragment");
    }
    private void initPlayer() {
        mVideoSurfaceView= (SurfaceView) findViewById(R.id.paly_view);
        mSurfaceHolder = mVideoSurfaceView.getHolder();
        mSurfaceHolder.addCallback(mSurfaceCallback);

        ksyMediaPlayer = new KSYMediaPlayer.Builder(this.getApplicationContext()).build();
        ksyMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                ksyMediaPlayer.start();
            }
        });
        ksyMediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                if (ksyMediaPlayer != null) {
                    ksyMediaPlayer.stop();
                    ksyMediaPlayer.release();
                }
            }
        });
        try {
            ksyMediaPlayer.setDataSource("rtmp://live.hkstv.hk.lxdns.com/live/hks");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ksyMediaPlayer.prepareAsync();
    }

}
