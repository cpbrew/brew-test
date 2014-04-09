package com.opentok.android;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A utility class for communicating with OTKit.
 * @author cbrew
 */
public final class OpenTokConfig {
    static {
        System.loadLibrary("opentok");
    }
    
    private OpenTokConfig() { }
    
    private static final String LOG_TAG = "opentok-config";
    
    /**
     * Set the SDK's api url, for switching environments.
     * @param apiRootURL
     * @throws MalformedURLException
     */
    public static void setAPIRootURL(String apiRootURL)
            throws MalformedURLException {
        setAPIRootURL(apiRootURL, true);
    }

    /**
     * Set the SDK's api url, for switching environments.
     * @param apiRootURL
     * @throws MalformedURLException
     */
    public static void setAPIRootURL(String apiRootURL, boolean rumorSSL)
            throws MalformedURLException {
        URL url = new URL(apiRootURL);
        boolean ssl = "https".equals(url.getProtocol());
        int port = url.getPort();
        if (port == -1) {
            port = ssl ? 443 : 80;
        }
        
        setAPIRootURLNative(url.getHost(), ssl, port, rumorSSL);
    }

    /**
     * Set whether or not to print OTKit logs.
     * @param otkitLogs
     */
    public static void setOTKitLogs(boolean otkitLogs) {
        setOTKitLogsNative(otkitLogs);
    }
    
    /**
     * Set whether or not to print JNI logs.
     * @param jniLogs
     */
    public static void setJNILogs(boolean jniLogs) {
        setJNILogsNative(jniLogs);
    }
    
    /**
     * Get publisher info stats.
     * @param publisher
     * @return
     */
    public static String getPublisherInfoStats(PublisherKit publisher) {
        String publisherStats = getPublisherInfoStatsNative(publisher);
        
        return publisherStats;
    }
    
    /**
     * Get subscriber info stats.
     * @param subscriber
     * @return
     */
    public static String getSubscriberInfoStats(SubscriberKit subscriber) {
        String subscriberStats = getSubscriberInfoStatsNative(subscriber);
        
        return subscriberStats;
    }
    
    
    private static native void setAPIRootURLNative(String host, boolean ssl,
            int port, boolean rumorSSL);
    private static native void setOTKitLogsNative(boolean otkitLogs);
    private static native void setJNILogsNative(boolean jniLogs);
    private static native String getPublisherInfoStatsNative(
            PublisherKit publisher);
    private static native String getSubscriberInfoStatsNative(
            SubscriberKit subscriber);
}