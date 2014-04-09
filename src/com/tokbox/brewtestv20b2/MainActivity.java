package com.tokbox.brewtestv20b2;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

import com.opentok.android.OpenTokConfig;
import com.opentok.android.Publisher;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.api.OpenTokSDK;
import com.opentok.exception.OpenTokException;

/**
 * An app for manual testing.
 * @author cbrew
 */
public class MainActivity extends Activity implements ListAdapter {

    private static final String LOG_TAG = "brewer";
    
    private static String token;
    
    private Button mConnectButton;
    private Button mPublishButton;
    private RelativeLayout mPublisherView;
    
    private Session mSession;
    private Publisher mPublisher;
    private PublisherOptions mPublisherOptions;
    
    private SessionListener mSessionListener;
    private PublisherKitListener mPublisherKitListener;
    
    private Map<String, StreamView> mStreamViewMap;
    private List<StreamView> mStreamViewList;
    private Set<DataSetObserver> mDataSetObservers;
    
    static {
        OpenTokSDK sdk = new OpenTokSDK(Config.API_KEY, Config.API_SECRET);
        try {
            token = sdk.generate_token(Config.SESSION_ID);
        } catch (OpenTokException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        if (null != Config.BASE_URL) {
            try {
                OpenTokConfig.setAPIRootURL(Config.BASE_URL, false);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
    
    // Activity lifecycle methods
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Log.i(LOG_TAG,
                Thread.currentThread().getStackTrace()[2].getMethodName());
        
        mConnectButton = (Button) findViewById(R.id.connect_button);
        mPublishButton = (Button) findViewById(R.id.publish_button);
        mPublisherView = (RelativeLayout) findViewById(R.id.publisher_view);
        
        mConnectButton.setText(R.string.connect_button_text);
        mPublishButton.setText(R.string.publish_button_text);
        mPublishButton.setEnabled(false);

        mStreamViewMap = new HashMap<String, StreamView>();
        mStreamViewList = new ArrayList<StreamView>();
        mDataSetObservers = new HashSet<DataSetObserver>();
        ((GridView) findViewById(R.id.grid)).setAdapter(this);
        
        mPublisherOptions = new PublisherOptions(this);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        AudioManager audio =
                (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audio.setSpeakerphoneOn(true);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        
        if (null != mSession) {
            mSession.onPause();
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        if (null != mSession) {
            mSession.onResume();
        }
    }
    
    // Button onClick listener methods
    
    /**
     * Connect to or disconnect from a session.
     * @param view
     */
    public void toggleConnect(View view) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + view + ")");
        
        view.setEnabled(false);
        
        if (null == mSession) {
            mSessionListener = new SessionListener(this);
            mSession = new Session(this, Integer.toString(Config.API_KEY),
                    Config.SESSION_ID, mSessionListener);
            mSession.connect(token);
//            mSession = new Session(this, Config.SESSION_ID, mSessionListener);
//            mSession.connect(Integer.toString(Config.API_KEY), token);
        } else {
            mSession.disconnect();
        }
    }
    
    /**
     * Publish to or unpublish from a session.
     * @param view
     */
    public void togglePublish(View view) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + view + ")");
        
        view.setEnabled(false);
        
        if (null == mPublisher) {
            mPublisherKitListener = new PublisherKitListener(this);
            mPublisher = new Publisher(this, mPublisherKitListener,
                    "Hello from Android!");
            
            mPublisherOptions.setPublisher(mPublisher);
            
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            mPublisherView.addView(mPublisher.getView(), params);
            
            mSession.publish(mPublisher);
        } else {
            mPublisherOptions.setPublisher(null);
            mSession.unpublish(mPublisher);
        }
    }
    
    /**
     * Show the options menu for the publisher.
     * @param view
     */
    public void toggleOptions(View view) {
        new AlertDialog.Builder(this)
            .setInverseBackgroundForced(true)
            .setView(mPublisherOptions.getMenu())
            .setPositiveButton("OK", null)
            .create()
            .show();
    }
    
    /**
     * Add a video stream to the app display.
     * @param stream
     */
    public void addStream(Stream stream) {
        StreamView streamView = new StreamView(this, stream);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.MATCH_PARENT);
        streamView.setLayoutParams(params);
        
        mStreamViewMap.put(stream.getStreamId(), streamView);
        mStreamViewList.add(streamView);
        
        for (DataSetObserver dataSetObserver : mDataSetObservers) {
            dataSetObserver.onChanged();
        }
    }
    
    /**
     * Remove a stream from the app display.
     * @param stream
     */
    public void dropStream(Stream stream) {
        StreamView streamView = mStreamViewMap.get(stream.getStreamId());
        mStreamViewMap.remove(stream.getStreamId());
        mStreamViewList.remove(streamView);
        
        for (DataSetObserver dataSetObserver : mDataSetObservers) {
            dataSetObserver.onChanged();
        }
    }
    
    // ListAdapter methods
    
    @Override
    public int getCount() {
        return mStreamViewList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return IGNORE_ITEM_VIEW_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StreamView streamView;
        
        if (null == convertView) {
            streamView = mStreamViewList.get(position);
        } else {
            streamView = (StreamView) convertView;
        }
        
        return streamView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservers.add(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservers.remove(observer);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    
    // Misc methods

    public Session getSession() {
        return mSession;
    }

    public void setSession(Session session) {
        mSession = session;
    }

    public void setSessionListener(SessionListener sessionListener) {
        mSessionListener = sessionListener;
    }

    public Publisher getPublisher() {
        return mPublisher;
    }

    public void setPublisher(Publisher publisher) {
        mPublisher = publisher;
    }

    public void setPublisherKitListener(
            PublisherKitListener publisherKitListener) {
        mPublisherKitListener = publisherKitListener;
    }

    public RelativeLayout getPublisherView() {
        return mPublisherView;
    }

    public Button getConnectButton() {
        return mConnectButton;
    }
    
    public Button getPublishButton() {
        return mPublishButton;
    }

    public Map<String, StreamView> getStreamViewMap() {
        return mStreamViewMap;
    }

    public List<StreamView> getStreamViewList() {
        return mStreamViewList;
    }

    public Set<DataSetObserver> getDataSetObservers() {
        return mDataSetObservers;
    }

}
