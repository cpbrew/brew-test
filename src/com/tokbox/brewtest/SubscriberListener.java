package com.tokbox.brewtest;

import static com.tokbox.brewtest.Config.LOG_TAG;
import android.util.Log;

import com.opentok.android.OpentokError;
import com.opentok.android.Subscriber;
import com.opentok.android.SubscriberKit;
import com.opentok.android.SubscriberKit.SubscriberAudioStats;
import com.opentok.android.SubscriberKit.SubscriberVideoStats;

/**
 * Handles all of the SubscriberKit.Listener callbacks.
 * 
 * @author cbrew
 */
public class SubscriberListener implements Subscriber.SubscriberListener,
                                           Subscriber.VideoListener,
                                           Subscriber.AudioLevelListener,
                                           Subscriber.AudioStatsListener,
                                           Subscriber.VideoStatsListener {

    private final StreamView mStreamView;
    
    /**
     * A constructor.
     * @param streamView
     */
    SubscriberListener(StreamView streamView) {
        mStreamView = streamView;
    }
    
    // SubscriberListener methods
    
    @Override
    public void onConnected(SubscriberKit subscriber) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + subscriber + ")");

        mStreamView.getSubscribeButton().setText(
                R.string.unsubscribe_button_text);
        mStreamView.getSubscribeButton().setEnabled(true);
    }

    @Override
    public void onDisconnected(SubscriberKit subscriber) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + subscriber + ")");

        if (null != mStreamView.getSubscriber()
                && null != mStreamView.getSubscriber().getView()) {
            mStreamView.getSubscriberView().removeView(
                    mStreamView.getSubscriber().getView());
        }
        mStreamView.setSubscriber(null);
        mStreamView.setSubscriberListener(null);
        
        mStreamView.removeView(mStreamView.getSubscriberView());
        mStreamView.setSubscriberView(null);
        
        mStreamView.getSubscribeButton().setText(
                R.string.subscribe_button_text);
        mStreamView.getSubscribeButton().setEnabled(true);
    }

    @Override
    public void onError(SubscriberKit subscriber, OpentokError error) {
        Log.e(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + error.getErrorDomain() + ":" + error.getErrorCode()
                + " - " + error.getMessage() + ")");

        if (null != mStreamView.getSubscriber()
                && null != mStreamView.getSubscriber().getView()) {
            mStreamView.getSubscriberView().removeView(
                    mStreamView.getSubscriber().getView());
        }
        mStreamView.setSubscriber(null);
        
        mStreamView.removeView(mStreamView.getSubscriberView());
        mStreamView.setSubscriberView(null);
        
        mStreamView.getSubscribeButton().setText(
                R.string.subscribe_button_text);
        mStreamView.getSubscribeButton().setEnabled(true);
    }
    
    // VideoListener methods

    @Override
    public void onVideoDataReceived(SubscriberKit subscriber) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + subscriber + ")");
    }
    
    @Override
    public void onVideoDisableWarning(SubscriberKit subscriber) {
    	Log.w(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
    			+ "(" + subscriber + ")");
    }
    
    @Override
    public void onVideoDisableWarningLifted(SubscriberKit subscriber) {
    	Log.w(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
    			+ "(" + subscriber + ")");
    }
    
    @Override
    public void onVideoDisabled(SubscriberKit subscriber, String string) {
    	Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
    			+ "(" + subscriber + ", " + string + ")");
    }

    @Override
    public void onVideoEnabled(SubscriberKit subscriber, String string) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + subscriber + ", " + string + ")");
    }
    
    // AudioLevelListener methods
    
    @Override
    public void onAudioLevelUpdated(SubscriberKit subscriber, float audioLevel) {
    	Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
    			+ "(" + audioLevel + ")");
    }
    
    // AudioStatsListener methods
    
    @Override
    public void onAudioStats(SubscriberKit subscriber, SubscriberAudioStats stats) {
    	Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
    			+ "()");
    	
    	Log.i(LOG_TAG, "audio stats:"
    			+ "\ntimestamp = " + stats.timeStamp
    			+ "\naudioBytesReceived = " + stats.audioBytesReceived
    			+ "\naudioPacketsReceived = " + stats.audioPacketsReceived
    			+ "\naudioPacketsLost = " + stats.audioPacketsLost);
    }
    
    // VideoStatsListener methods
    
    @Override
    public void onVideoStats(SubscriberKit subscriber, SubscriberVideoStats stats) {
    	Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
    			+ "()");
    	
    	Log.i(LOG_TAG, "video stats:"
    			+ "\ntimestamp = " + stats.timeStamp
    			+ "\nvideoBytesReceived = " + stats.videoBytesReceived
    			+ "\nvideoPacketsReceived = " + stats.videoPacketsReceived
    			+ "\nvideoPacketsLost = " + stats.videoPacketsLost);
    }
}

