package com.tokbox.brewtestv20b2;

import java.util.ArrayList;

import android.content.Context;

import com.opentok.android.Subscriber;
import com.tokbox.brewtestv20b2.OptionsMenu.MenuItem;

/**
 * Manages subscriber options such as setting subscribeToAudio and
 * subscribeToVideo.
 * 
 * @author cbrew
 */
public class SubscriberOptions implements OptionsMenu.Listener {
    
    private static final String SUBSCRIBE_TO_AUDIO_KEY = "subscribeToAudio";
    private static final String SUBSCRIBE_TO_VIDEO_KEY = "subscribeToVideo";
    
    private boolean mSubscribeToAudio = true;
    private boolean mSubscribeToVideo = true;
    
    private Context mContext;
    private Subscriber mSubscriber;
    
    /**
     * Create a new SubscriberOptions object.
     * @param context
     */
    public SubscriberOptions(Context context) {
        mContext = context;
    }
    
    /**
     * Set the subscriber owned by this set of options.
     * @param subscriber
     */
    public void setSubscriber(Subscriber subscriber) {
        mSubscriber = subscriber;
        
        if (null != mSubscriber) {
            mSubscriber.setSubscribeToAudio(mSubscribeToAudio);
            mSubscriber.setSubscribeToVideo(mSubscribeToVideo);
        }
    }
    
    public boolean getSubscribeToAudio() {
        return mSubscribeToAudio;
    }
    
    /**
     * Set whether or not the subscriber should subscribe to audio.
     * @param subscribeToAudio
     */
    public void setSubscribeToAudio(boolean subscribeToAudio) {
        mSubscribeToAudio = subscribeToAudio;
        if (null != mSubscriber) {
            mSubscriber.setSubscribeToAudio(mSubscribeToAudio);
        }
    }
    
    public boolean getSubscribeToVideo() {
        return mSubscribeToVideo;
    }
    
    /**
     * Set whether or not the subscriber should subscribe to video.
     * @param subscribeToVideo
     */
    public void setSubscribeToVideo(boolean subscribeToVideo) {
        mSubscribeToVideo = subscribeToVideo;
        if (null != mSubscriber) {
            mSubscriber.setSubscribeToVideo(mSubscribeToVideo);
        }
    }
    
    /**
     * Create and return a menu that can be used to manipulate the options owned
     * by this object.
     * @return
     */
    public OptionsMenu getMenu() {
        ArrayList<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem.BoolItem(mContext, SUBSCRIBE_TO_AUDIO_KEY,
                mSubscribeToAudio, this));
        options.add(new MenuItem.BoolItem(mContext, SUBSCRIBE_TO_VIDEO_KEY,
                mSubscribeToVideo, this));
        
        return new OptionsMenu(mContext, options);
    }
    
    /**
     * Called when one of the options for the subscriber owned by this object is
     * changed by the menu.
     */
    public void onOptionSwitched(String option, Object value) {
        if (SUBSCRIBE_TO_AUDIO_KEY.equals(option)) {
            mSubscribeToAudio = ((Boolean) value).booleanValue();
            if (null != mSubscriber) {
                mSubscriber.setSubscribeToAudio(mSubscribeToAudio);
            }
        } else if (SUBSCRIBE_TO_VIDEO_KEY.equals(option)) {
            mSubscribeToVideo = ((Boolean) value).booleanValue();
            if (null != mSubscriber) {
                mSubscriber.setSubscribeToVideo(mSubscribeToVideo);
            }
        }
    }
    
}