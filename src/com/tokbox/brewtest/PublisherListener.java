package com.tokbox.brewtest;

import static com.tokbox.brewtest.Config.LOG_TAG;

import android.util.Log;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Stream;
import com.tokbox.brewtest.R;

/**
 * Handles all of the Publisher.Listener callbacks.
 * 
 * @author cbrew
 */
public class PublisherListener implements Publisher.PublisherListener,
                                          Publisher.AudioLevelListener,
                                          Publisher.CameraListener {
    
    private final MainActivity mMainActivity;
    
    /**
     * A constructor.
     * @param mainActivity
     */
    PublisherListener(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }
    
    // PublisherListener Methods
    
    @Override
    public void onStreamCreated(PublisherKit publisher, Stream stream) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + stream.getStreamId() + ")");
        
        mMainActivity.addStream(stream);

        mMainActivity.getPublishButton().setText(
                R.string.unpublish_button_text);
        mMainActivity.getPublishButton().setEnabled(true);
    }
    
    @Override
    public void onStreamDestroyed(PublisherKit publisher, Stream stream) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + stream.getStreamId() + ")");
        
        mMainActivity.dropStream(stream);

        if (null != mMainActivity.getPublisher()
                && null != mMainActivity.getPublisher().getView()) {
            mMainActivity.getPublisherView().removeView(
                    mMainActivity.getPublisher().getView());
        }
        mMainActivity.setPublisher(null);
        mMainActivity.setPublisherListener(null);
        
        mMainActivity.getPublishButton().setText(R.string.publish_button_text);
        mMainActivity.getPublishButton().setEnabled(
                null != mMainActivity.getSession()
                && null != mMainActivity.getSession().getConnection());
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

    // AudioLevelListener methods
    
    @Override
    public void onAudioLevelUpdated(PublisherKit publisher,
    		float audioLevel) {
//    	Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
//    			+ "(" + audioLevel + ")");
    };
    
    // CameraListener methods
    
    @Override
    public void onCameraChanged(Publisher publisher, int newCameraId) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + newCameraId + ")");
    }
    
    @Override
    public void onCameraError(Publisher publisher, OpentokError error) {
        Log.e(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + error.getErrorDomain() + ":" + error.getErrorCode()
                + " - " + error.getMessage() + ")");
    }

}

