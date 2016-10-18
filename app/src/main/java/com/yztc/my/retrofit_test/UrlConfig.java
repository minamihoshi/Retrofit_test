package com.yztc.my.retrofit_test;

/**
 * Created by zhonghang on 2016/10/17.
 */

public class UrlConfig {

    //http://qt.qq.com/php_cgi/news/php/varcache_getnews.php?id=12&page=0&plat=ios&version=33
    public static final String BASE_URL="http://qt.qq.com/";
    //生产环境
    //测试环境
    //线上环境
    public static class Path{
        public static final String GET_NEWS="php_cgi/news/php/varcache_getnews.php";
    }
    public  static class Param{
        public static final String ID="id";
        public static final String PAGE="page";
        public static final String PLAT="plat";
        public static final String VERSION="version";
    }
//    id=12&page=0&plat=ios&version=33
    public static class DefaultValue{
        public static final String PAGE="0";
        public static final String PLAT="ios";
        public static final String VERSION="33";
    }
}
