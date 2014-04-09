package com.tokbox.brewtestv20b2;

import android.database.DataSetObserver;
import android.util.Log;

import com.opentok.android.Connection;
import com.opentok.android.OpentokError;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;

/**
 * Handles all of the Session.Listener callbacks.
 * 
 * @author cbrew
 */
public class SessionListener implements Session.Listener {
    
    private static final String LOG_TAG = "brewer";
    
    private final MainActivity mMainActivity;
    
    /**
     * A constructor.
     * @param mainActivity
     */
    SessionListener(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }
    
    @Override
    public void connected(Session session) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + session + ")");
        
        mMainActivity.getConnectButton().setText(
                R.string.disconnect_button_text);
        mMainActivity.getConnectButton().setEnabled(true);
        mMainActivity.getPublishButton().setEnabled(true);
    }
    
    @Override
    public void disconnected(Session session) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + session + ")");

        mMainActivity.setSession(null);
        mMainActivity.setSessionListener(null);
        
        mMainActivity.getStreamViewMap().clear();
        mMainActivity.getStreamViewList().clear();
        for (DataSetObserver dataSetObserver
                : mMainActivity.getDataSetObservers()) {
            dataSetObserver.onChanged();
        }
        
        mMainActivity.getConnectButton().setText(R.string.connect_button_text);
        mMainActivity.getConnectButton().setEnabled(true);
        mMainActivity.getPublishButton().setText(R.string.publish_button_text);
        mMainActivity.getPublishButton().setEnabled(false);
    }
    
    @Override
    public void connectionCreated(Session session, Connection connection) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + connection.getConnectionId() + ")");
    }
    
    @Override
    public void connectionDestroyed(Session session, Connection connection) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + connection.getConnectionId() + ")");
    }
    
    @Override
    public void addPublisher(Session session, PublisherKit publisher) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + publisher + ")");

        mMainActivity.getPublishButton().setText(
                R.string.unpublish_button_text);
        mMainActivity.getPublishButton().setEnabled(true);
    }
    
    @Override
    public void removePublisher(Session session, PublisherKit publisher) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + publisher + ")");

        if (null != mMainActivity.getPublisher()
                && null != mMainActivity.getPublisher().getView()) {
            mMainActivity.getPublisherView().removeView(
                    mMainActivity.getPublisher().getView());
        }
        mMainActivity.setPublisher(null);
        mMainActivity.setPublisherKitListener(null);
        
        mMainActivity.getPublishButton().setText(R.string.publish_button_text);
        mMainActivity.getPublishButton().setEnabled(
                null != mMainActivity.getSession()
                && null != mMainActivity.getSession().getConnection());
    }
    
    @Override
    public void receivedStream(Session session, Stream stream) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + stream.getStreamId() + ")");

        mMainActivity.addStream(stream);
    }
    
    @Override
    public void droppedStream(Session session, Stream stream) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + stream.getStreamId() + ")");

        mMainActivity.dropStream(stream);
    }
    
    @Override
    public void streamChangeHasAudio(Session session, Stream stream,
            boolean hasAudio) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + stream.getStreamId() + ", " + hasAudio + ")");
    }

    @Override
    public void streamChangeHasVideo(Session session, Stream stream,
            boolean hasVideo) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + stream.getStreamId() + ", " + hasVideo + ")");
    }
//    @Override
//    public void streamChangeHasAudio(Session session, Stream stream,
//            int hasAudio) {
//        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
//                + "(" + stream.getStreamId() + ", " + (hasAudio == 1) + ")");
//    }
//
//    @Override
//    public void streamChangeHasVideo(Session session, Stream stream,
//            int hasVideo) {
//        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
//                + "(" + stream.getStreamId() + ", " + (hasVideo == 1) + ")");
//    }
    
    @Override
    public void streamChangeVideoDimensions(Session session, Stream stream,
            int width, int height) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + stream.getStreamId() + ", " + width + "x" + height
                + ")");
    }
    
    @Override
    public void signalReceived(Session session, String type, String data,
            Connection connection) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + type + ", " + data + ", " + connection.getConnectionId()
                + ")");
    }

    @Override
    public void archiveStart(Session session) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + session.getSessionId() + ")");
    }

    @Override
    public void archiveStop(Session session) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + session.getSessionId() + ")");
    }
    
    @Override
    public void error(Session session, OpentokError error) {
        Log.e(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + error.getErrorDomain() + ":" + error.getErrorCode()
                + " - " + error.getMessage() + ")");

        mMainActivity.setSession(null);

        mMainActivity.getStreamViewMap().clear();
        mMainActivity.getStreamViewList().clear();
        for (DataSetObserver dataSetObserver
                : mMainActivity.getDataSetObservers()) {
            dataSetObserver.onChanged();
        }
        
        mMainActivity.getConnectButton().setText(R.string.connect_button_text);
        mMainActivity.getConnectButton().setEnabled(true);
        mMainActivity.getPublishButton().setText(R.string.publish_button_text);
        mMainActivity.getPublishButton().setEnabled(false);
    }
}