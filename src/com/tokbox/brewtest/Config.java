package com.tokbox.brewtest;

/**
 * Provides various config parameters for the sample app.
 * 
 * @author cbrew
 */
public final class Config {

	public static final String LOG_TAG = "brewer";
	
    /**
     * Available environments.
     */
    private static enum Env {
        PROD,
        PROD_TEST,
        REL,
        DEV,
        SCRATCH
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
        
        switch (ENV) {
        case PROD:
            API_KEY = 100;
            API_SECRET = "19f149fdf697474f915f13de40e0ad53";
            BASE_URL = null;
            if (SESSION_TYPE == SessionType.P2P) {
                SESSION_ID = "1_MX4xMDB-MTI3LjAuMC4xflRodSBKdW4gMTIgMTE6NDU6MDYgUERUIDIwMTR-MC43OTIwMzk2flB-";
            } else { // SESSION_TYPE == SessionType.MANTIS
                SESSION_ID = "1_MX4xMDB-MTI3LjAuMC4xflRodSBKdW4gMTIgMTE6NTg6NTcgUERUIDIwMTR-MC44MDY4MTN-fg";
            }
            break;
            
        case PROD_TEST:
        	API_KEY = 44945022;
        	API_SECRET = "29b2d98601d9c0d4aa4df120c559dc36530f130f";
        	BASE_URL = null;
        	if (SESSION_TYPE == SessionType.P2P) {
        		SESSION_ID = "1_MX40NDk0NTAyMn4xMjcuMC4wLjF-VGh1IEF1ZyAyMSAxMTowMzoxNSBQRFQgMjAxNH4wLjcyNzc2NX5Qfg";
        	} else { // SESSION_TYPE == SessionType.MANTIS
        		SESSION_ID = "2_MX40NDk0NTAyMn4xMjcuMC4wLjF-VGh1IEF1ZyAyMSAxMTowMzo0NyBQRFQgMjAxNH4wLjA3MTIwNjgxfn4";
        	}
        	break;

        case REL:
            API_KEY = 100;
            API_SECRET = "19f149fdf697474f915f13de40e0ad53";
            BASE_URL = "https://anvil-rel.opentok.com";
            if (SESSION_TYPE == SessionType.P2P) {
                SESSION_ID = "2_MX4xMDB-MTI3LjAuMC4xfjE0MTQ3OTA4NzYwOTV-enNZR1VuSmk3VW94YUYrdjlvRUk1T3lYflB-";
            } else { // SESSION_TYPE == SessionType.MANTIS
                SESSION_ID = "1_MX4xMDB-MTI3LjAuMC4xfjE0MTQ3OTA5MDgyNTd-V2JJNWphRkZMRmdMa3NkRk1ZTGcwRW1Gfn4";
            }
            break;

        case DEV:
            API_KEY = 100;
            API_SECRET = "19f149fdf697474f915f13de40e0ad53";
            BASE_URL = "https://anvil-dev.opentok.com";
            if (SESSION_TYPE == SessionType.P2P) {
                SESSION_ID = "1_MX4xMDB-MTI3LjAuMC4xflR1ZSBNYXkgMjAgMTQ6MjA6MjQgUERUIDIwMTR-MC45MjMzMTEzflB-";
            } else { // SESSION_TYPE == SessionType.MANTIS
                SESSION_ID = "2_MX4xMDB-MTI3LjAuMC4xflR1ZSBKdWwgMDEgMTA6MjY6MjQgUERUIDIwMTR-MC4xNjc1Njk4OH5-";
            }
            break;
            
        case SCRATCH:
            API_KEY = 100;
            API_SECRET = "19f149fdf697474f915f13de40e0ad53";
            BASE_URL = "https://anvil-dev.opentok.com";
            if (SESSION_TYPE == SessionType.P2P) {
            	SESSION_ID = "2_MX4xMDB-MTI3LjAuMC4xfjE0MTkwMzE1ODExNDZ-QnQwM0pvZk1Sd081WGpva1dSb1RnOHExflB-";
            } else { // SESSION_TYPE == SessionType.MANTIS
            	SESSION_ID = "1_MX4xMDB-MTI3LjAuMC4xfjE0MTkwMzE2MTc4MjB-L3I4bSswVDR3VG5NczIxZnJiTTF2VkVNfn4";
            }
//            TOKEN = "T1==cGFydG5lcl9pZD0xMDAmc2RrX3ZlcnNpb249dGJwaHAtdjAuOTEuMjAxMS0wNy0wNSZzaWc9ZWY5Zjg5NjU1MjcyNWI0OGVkNDAwNWJkNWMxZDc2OTkwZmE3ZDRkNTpzZXNzaW9uX2lkPTFfTVg0eE1EQi1NVEkzTGpBdU1DNHhma1p5YVNCS2RXNGdNVE1nTVRVNk1UVTZNRFVnVUVSVUlESXdNVFItTUM0eU1UUTNOakUxZm40JmNyZWF0ZV90aW1lPTE0MDI2OTgyMzUmcm9sZT1tb2RlcmF0b3Imbm9uY2U9MTQwMjY5ODIzNS42MzA0MTE4MzA0NTAwOCZleHBpcmVfdGltZT0xNDA1MjkwMjM1";
            break;

        default:
            API_KEY = -1;
            API_SECRET = null;
            BASE_URL = null;
            SESSION_ID = null;
        }
    }
    
    private Config() { }
}