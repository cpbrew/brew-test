package com.tokbox.brewtest;

/**
 * Provides various config parameters for the sample app.
 * 
 * @author cbrew
 */
public final class Config {

    /**
     * Available environments.
     */
    private static enum Env {
        PROD,
        REL,
        DEV
    }
    
    /**
     * Available session types.
     */
    private static enum SessionType {
        P2P,
        MANTIS
    }
    
    private static final Env ENV = Env.PROD;
    private static final SessionType SESSION_TYPE = SessionType.P2P;
    
    public static final String BASE_URL;
    public static final int API_KEY;
    public static final String API_SECRET;
    public static final String SESSION_ID;
    
    static {
        API_KEY = 100;
        API_SECRET = "19f149fdf697474f915f13de40e0ad53";
        
        switch (ENV) {
        case PROD:
            BASE_URL = null;
            if (SESSION_TYPE == SessionType.P2P) {
                SESSION_ID = "2_MX4xMDB-MTI3LjAuMC4xfldlZCBGZWIgMDUgMTU6MTE6NDQgUFNUIDIwMTR-MC40OTMyMjE4Mn4";
            } else { // SESSION_TYPE == SessionType.MANTIS
                SESSION_ID = "2_MX4xMDB-MTI3LjAuMC4xfldlZCBGZWIgMDUgMTU6MTI6MTEgUFNUIDIwMTR-MC45MjU4NzgzNX4";
            }
            break;

        case REL:
            BASE_URL = "https://anvil-rel.opentok.com";
            if (SESSION_TYPE == SessionType.P2P) {
                SESSION_ID = "1_MX4xMDB-MTI3LjAuMC4xfldlZCBGZWIgMDUgMTU6MTM6MjIgUFNUIDIwMTR-MC45OTUwNDYxNH4";
            } else { // SESSION_TYPE == SessionType.MANTIS
                SESSION_ID = "2_MX4xMDB-MTI3LjAuMC4xfldlZCBGZWIgMDUgMTU6MTM6MzkgUFNUIDIwMTR-MC45MTY2NzYyfg";
            }
            break;

        case DEV:
            BASE_URL = "https://anvil-dev.opentok.com";
            if (SESSION_TYPE == SessionType.P2P) {
                SESSION_ID = "1_MX4xMDB-MTI3LjAuMC4xfldlZCBGZWIgMDUgMTU6MTI6MzIgUFNUIDIwMTR-MC40NzI2ODg4NX4";
            } else { // SESSION_TYPE == SessionType.MANTIS
                SESSION_ID = "2_MX4xMDB-MTI3LjAuMC4xfldlZCBGZWIgMDUgMTU6MTM6MDAgUFNUIDIwMTR-MC4yNjM3Nzgyfg";
            }
            break;

        default:
            BASE_URL = null;
            SESSION_ID = null;
        }
    }
    
    private Config() { }
}