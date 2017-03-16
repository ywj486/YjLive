package com.ywj.yjlive.http;

/**
 * Created by Administrator on 2017/2/17 0017.
 */
public class Contants {
    public static final String PUSH_STREAMER = "rtmp://cncpublish.bingdou.tv/live/";
    public static final String PULL_STREAMER = "rtmp://cncplay.bingdou.tv/live/";
    public final    static  String PIC="https://ss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=207b48fab54543a9ea1bfdcc2e168a7b/54fbb2fb43166d22dc28839a442309f79052d265.jpg";

    public static class API {
       public static final String BASE_URL = "http://121.42.26.175:14444/";

        public static final String Main = BASE_URL+"live/find.json";
        public static final String LOGIN = BASE_URL+"live/login.json";
        public static final String REGISTER = BASE_URL+"live/register.json";
        public static final String CREATE_LIVE= BASE_URL+"live/create.json";
        public static final String UPDATE_LIVE_STATUS= BASE_URL+"live/status/update.json";

    }
}
