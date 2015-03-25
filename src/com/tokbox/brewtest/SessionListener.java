package com.tokbox.brewtest;

import static com.tokbox.brewtest.Config.LOG_TAG;

import android.database.DataSetObserver;
import android.util.Log;

import com.opentok.android.Connection;
import com.opentok.android.OpentokError;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Stream.StreamVideoType;

/**
 * Handles all of the Session.Listener callbacks.
 * 
 * @author cbrew
 */
public class SessionListener implements Session.SessionListener,
										Session.ConnectionListener,
										Session.StreamPropertiesListener,
										Session.SignalListener,
										Session.ArchiveListener {
    
    private final MainActivity mMainActivity;
    
    /**
     * A constructor.
     * @param mainActivity
     */
    SessionListener(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }
    
    // SessionListener methods
    
    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + session + ")");
        
        mMainActivity.getConnectButton().setText(
                R.string.disconnect_button_text);
        mMainActivity.getConnectButton().setEnabled(true);
        mMainActivity.getPublishButton().setEnabled(true);
        
//        String type = "foo";
//        String data = "bar";
//        session.sendSignal(type, data);
    }
    
    @Override
    public void onDisconnected(Session session) {
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
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + stream.getStreamId() + ")");

        mMainActivity.addStream(stream);
    }
    
    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + stream.getStreamId() + ")");

        mMainActivity.dropStream(stream);
    }

    @Override
    public void onError(Session session, OpentokError error) {
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
    
    // ConnectionListener methods
    
    @Override
    public void onConnectionCreated(Session session, Connection connection) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + connection.getConnectionId() + ")");
    }
    
    @Override
    public void onConnectionDestroyed(Session session, Connection connection) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + connection.getConnectionId() + ")");
    }
    
    // StreamPropertiesListener methods
    
    @Override
    public void onStreamHasAudioChanged(Session session, Stream stream,
            boolean hasAudio) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + stream.getStreamId() + ", " + hasAudio + ")");
    }

    @Override
    public void onStreamHasVideoChanged(Session session, Stream stream,
            boolean hasVideo) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + stream.getStreamId() + ", " + hasVideo + ")");
    }
    
    @Override
    public void onStreamVideoDimensionsChanged(Session session, Stream stream,
            int width, int height) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + stream.getStreamId() + ", " + width + "x" + height
                + ")");
    }
    
    @Override
    public void onStreamVideoTypeChanged(Session session, Stream stream,
    		StreamVideoType streamVideoType) {
    	Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
    			+ "(" + stream.getStreamId() + ", " + streamVideoType + ")");
    };
    
    // SignalListener methods
    
    @Override
    public void onSignalReceived(Session session, String type, String data,
            Connection connection) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + type + ", " + data + ", " + connection.getConnectionId()
                + ")");
    }
    
    // ArchiveListener methods

    @Override
    public void onArchiveStarted(Session session, String archiveId, String name) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + session.getSessionId() + ")");
    }

    @Override
    public void onArchiveStopped(Session session, String archiveId) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + session.getSessionId() + ")");
    }
}

