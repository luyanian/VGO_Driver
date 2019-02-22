package com.lanhi.ryon.utils.constant;

public class SPConstants {
    public static final String DEFAULT_NAME = "delivery_consignor";

    public static class USER {
        public static final String NAME = "user_name";
        public final static String TOKENID="tokenid";
        public final static String USER_INFO="user_info";
    }

    public static class LANGUAGE {
        public static final String NAME = "language";
        public static final String LANGUAGETYPE = "languagetype";
    }
    public static class PAYPAL{
        public static final String NAME="paypal";
        public static final String SANDBOX_ACCESS_TOKEN="sandboxAccessToken";
        public static final String SANDBOX_ACCESS_REFRESH_URL="sandboxAccessRefreshUrl";
        public static final String SANDBOX_ACCESS_ENV="sandboxAccessEnv";
        public static final String LIVE_ACCESS_TOKEN="liveAccessToken";
        public static final String LIVE_ACCESS_REFRESH_URL="liveAccessRefreshUrl";
        public static final String LIVE_ACCESS_ENV="liveAccessEnv";

    }
    //location
    public static class LOCATION{
        public final static String NAME="current_device_location";
        public final static String LAT="lat";
        public final static String LNG="lng";
    }
}
