package com.tokbox.brewtestv20b2;

import android.util.Log;

import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Stream;

/**
 * Handles all of the Publisher.Listener callbacks.
 * 
 * @author cbrew
 */
public class PublisherKitListener implements Publisher.Listener {
    
    private static final String LOG_TAG = "brewer";
    
    private final MainActivity mMainActivity;
    
    /**
     * A constructor.
     * @param mainActivity
     */
    PublisherKitListener(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }
    
    @Override
    public void onStreamCreated(PublisherKit publisher, Stream stream) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + stream.getStreamId() + ")");
        
        mMainActivity.addStream(stream);
    }
    
    @Override
    public void onStreamDestroyed(PublisherKit publisher, Stream stream) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + stream.getStreamId() + ")");
        
        mMainActivity.dropStream(stream);
    }

    @Override
    public void onCameraChanged(PublisherKit publisher, int newCameraId) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + newCameraId + ")");
    }

    @Override
    public void onError(PublisherKit publisher, OpentokError error) {
        Log.e(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + error.getErrorDomain() + ":" + error.getErrorCode()
                + " - " + error.getMessage() + ")");

        if (null != mMainActivity.getPublisher()
                && null != mMainActivity.getPublisher().getView()) {
            mMainActivity.getPublisherView().removeView(
                    mMainActivity.getPublisher().getView());
        }
        mMainActivity.setPublisher(null);
        
        mMainActivity.getPublishButton().setText(R.string.publish_button_text);
        mMainActivity.getPublishButton().setEnabled(
                mMainActivity.getSession() != null
                && mMainActivity.getSession().getConnection() != null);
    }
    
}