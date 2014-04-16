package com.tokbox.brewtest;

import java.util.ArrayList;

import android.content.Context;

import com.opentok.android.Publisher;
import com.tokbox.brewtest.OptionsMenu.MenuItem;

/**
 * Manages publisher options such as setting publishAudio, publishVideo, and
 * swapping cameras.
 * 
 * @author cbrew
 */
public class PublisherOptions implements OptionsMenu.Listener {
    
    private static final String PUBLISH_AUDIO_KEY = "publishAudio";
    private static final String PUBLISH_VIDEO_KEY = "publishVideo";
    private static final String SWAP_CAMERA_KEY = "swapCamera";
    
    private boolean mPublishAudio = true;
    private boolean mPublishVideo = true;
    
    private Context mContext;
    private Publisher mPublisher;
    
    /**
     * Create a new PublisherOptions object.
     * @param context
     */
    public PublisherOptions(Context context) {
        mContext = context;
    }
    
    /**
     * Set the publisher owned by this set of options.
     * @param publisher
     */
    public void setPublisher(Publisher publisher) {
        mPublisher = publisher;
        
        if (null != mPublisher) {
            mPublisher.setPublishAudio(mPublishAudio);
            mPublisher.setPublishVideo(mPublishVideo);
        }
    }
    
    public boolean getPublishAudio() {
        return mPublishAudio;
    }
    
    /**
     * Set whether or not the publisher should publish audio.
     * @param publishAudio
     */
    public void setPublishAudio(boolean publishAudio) {
        mPublishAudio = publishAudio;
        if (null != mPublisher) {
            mPublisher.setPublishAudio(mPublishAudio);
        }
    }
    
    public boolean getPublishVideo() {
        return mPublishVideo;
    }
    
    /**
     * Set whether or not the publisher should publish video.
     * @param publishVideo
     */
    public void setPublishVideo(boolean publishVideo) {
        mPublishVideo = publishVideo;
        if (null != mPublisher) {
            mPublisher.setPublishVideo(mPublishVideo);
        }
    }
    
    public int getCameraId() {
        return mPublisher.getCameraId();
    }

    /**
     * Create and return a menu that can be used to manipulate the options owned
     * by this object.
     * @return
     */
    public OptionsMenu getMenu() {
        ArrayList<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem.BoolItem(mContext, PUBLISH_AUDIO_KEY,
                mPublishAudio, this));
        options.add(new MenuItem.BoolItem(mContext, PUBLISH_VIDEO_KEY,
                mPublishVideo, this));
        options.add(new MenuItem.ActiveItem(mContext, SWAP_CAMERA_KEY, this));
        
        return new OptionsMenu(mContext, options);
    }
    
    /**
     * Called when one of the options for the publisher owned by this object is
     * changed by the menu.
     */
    public void onOptionSwitched(String option, Object value) {
        if (PUBLISH_AUDIO_KEY.equals(option)) {
            mPublishAudio = ((Boolean) value).booleanValue();
            if (null != mPublisher) {
                mPublisher.setPublishAudio(mPublishAudio);
            }
        } else if (PUBLISH_VIDEO_KEY.equals(option)) {
            mPublishVideo = ((Boolean) value).booleanValue();
            if (null != mPublisher) {
                mPublisher.setPublishVideo(mPublishVideo);
            }
        } else if (SWAP_CAMERA_KEY.equals(option)) {
            if (null != mPublisher) {
                mPublisher.swapCamera();
            }
        }
    }
    
}