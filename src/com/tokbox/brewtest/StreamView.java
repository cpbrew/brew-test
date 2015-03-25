package com.tokbox.brewtest;

import static com.tokbox.brewtest.Config.LOG_TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.tokbox.brewtest.R;

/**
 * Contains a stream and possibly a subscriber.
 * 
 * @author cbrew
 */
public class StreamView extends LinearLayout {
    
    private MainActivity mMainActivity;
    private TextView mTitle;
    private Button mSubscribeButton;
    private Button mOptionsButton;
    private RelativeLayout mSubscriberView;
    
    private Stream mStream;
    private Subscriber mSubscriber;
    private SubscriberListener mSubscriberListener;
    private SubscriberOptions mSubscriberOptions;

    private StreamView(Context context) {
        super(context);
    }
    
    /**
     * Create a new StreamView corresponding to the given stream.
     * @param mainActivity
     * @param stream
     */
    public StreamView(MainActivity mainActivity, Stream stream) {
        super(mainActivity);
        
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName()
                + "(" + mainActivity + ", " + stream + ")");
        
        setOrientation(LinearLayout.VERTICAL);
        
        mMainActivity = mainActivity;
        mStream = stream;
        
        mTitle = new TextView(mMainActivity);
        boolean ownStream = stream.getConnection().equals(
                stream.getSession().getConnection());
        mTitle.setText((ownStream ? "SELF - " : "") + mStream.getStreamId());
        addView(mTitle, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        mSubscribeButton = new Button(mMainActivity);
        mSubscribeButton.setText(R.string.subscribe_button_text);
        mSubscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSubscribe();
            }
        });
        addView(mSubscribeButton,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        
        mOptionsButton = new Button(mainActivity);
        mOptionsButton.setText(R.string.options_button_text);
        mOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleOptions();
            }
        });
        addView(mOptionsButton,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        
        mSubscriberOptions = new SubscriberOptions(mainActivity);
    }

    private void toggleSubscribe() {
        Log.i(LOG_TAG,
                Thread.currentThread().getStackTrace()[2].getMethodName());
        
        mSubscribeButton.setEnabled(false);

        if (null == mSubscriber) {
            mMainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    float width = mMainActivity.getResources().getDimension(
                            R.dimen.video_view_width);
                    float height = mMainActivity.getResources().getDimension(
                            R.dimen.video_view_height);
                    LayoutParams params = new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT);
                    
                    mSubscriberView = new RelativeLayout(mMainActivity);
                    addView(mSubscriberView, new LayoutParams((int) width,
                            (int) height));
                    
                    mSubscriberListener = new SubscriberListener(
                            StreamView.this);
                    mSubscriber = new Subscriber(mMainActivity, mStream);
                    mSubscriber.setSubscriberListener(mSubscriberListener);
                    mSubscriber.setVideoListener(mSubscriberListener);
                    mSubscriber.setAudioLevelListener(mSubscriberListener);
                    mSubscriber.setAudioStatsListener(mSubscriberListener);
                    mSubscriber.setVideoStatsListener(mSubscriberListener);
                    mSubscriberOptions.setSubscriber(mSubscriber);
                    mSubscriberView.addView(mSubscriber.getView(), params);
                    
                    mStream.getSession().subscribe(mSubscriber);
                }
            });
        } else {
            mSubscriberOptions.setSubscriber(null);
            mStream.getSession().unsubscribe(mSubscriber);
        }
    }
    
    private void toggleOptions() {
        new AlertDialog.Builder(mMainActivity)
            .setInverseBackgroundForced(true)
            .setView(mSubscriberOptions.getMenu())
            .setPositiveButton("OK", null)
            .create()
            .show();
    }
    
    public Subscriber getSubscriber() {
        return mSubscriber;
    }
    
    public void setSubscriber(Subscriber subscriber) {
        mSubscriber = subscriber;
    }
    
    public void setSubscriberListener(SubscriberListener subscriberListener) {
        mSubscriberListener = subscriberListener;
    }
    
    public RelativeLayout getSubscriberView() {
        return mSubscriberView;
    }
    
    public void setSubscriberView(RelativeLayout subscriberView) {
        mSubscriberView = subscriberView;
    }
    
    public Button getSubscribeButton() {
        return mSubscribeButton;
    }

}
