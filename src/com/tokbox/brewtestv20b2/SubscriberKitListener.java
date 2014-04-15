package com.tokbox.brewtestv20b2;

import android.util.Log;

import com.opentok.android.OpentokError;
import com.opentok.android.Subscriber;
import com.opentok.android.SubscriberKit;

/**
 * Handles all of the SubscriberKit.Listener callbacks.
 * 
 * @author cbrew
 */
public class SubscriberKitListener implements Subscriber.Listener {

    private static final String LOG_TAG = "brewer";
    
    private final StreamView mStreamView;
    
    /**
     * A constructor.
     * @param streamView
     */
    SubscriberKitListener(StreamView streamView) {
        mStreamView = streamView;
    }
    
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
        mStreamView.setSubscriberKitListener(null);
        
        mStreamView.removeView(mStreamView.getSubscriberView());
        mStreamView.setSubscriberView(null);
        
        mStreamView.getSubscribeButton().setText(
                R.string.subscribe_button_text);
        mStreamView.getSubscribeButton().setEnabled(true);
    }

    @Override
    public void onVideoDataReceived(SubscriberKit subscriber) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + subscriber + ")");
    }

    @Override
    public void onVideoDisabled(SubscriberKit subscriber) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + subscriber + ")");
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
}