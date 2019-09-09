package com.ltqh.qh.base;

public interface Constant {

    public static String NEWS_HOIST = "https://minsuart.com.cn/api/news";

    //public static String PANDANEWS_HOST = "https://xm.pandaqh.com/api/news";
    public static String PANDANEWS_HOST = "https://zy.tigerqh.com";

    public static String JUHE_HOST="http://apis.juhe.cn/goodbook";

    String BASE_URL = "https://data.fk7h.com";

    //金投网的资讯列表
    public static String URL_JINTOUWANG = NEWS_HOIST + "/getJinTouEvents";
    //金投网详情列表
    public static String URL_JINTOUWANGLIST = NEWS_HOIST + "/getJinTouEventDetails";
    //财经日历
    public static String URL_CAIJINGRILI = NEWS_HOIST + "/getCrawlersByTime";
    //7*24小时
    public static String URL_HOUR = NEWS_HOIST + "/getJqkaNewsByTime";
    //快讯
    public static String URL_ALERTS = PANDANEWS_HOST + "/newsList.htm";
    //资讯详情
    public static String URL_ALERTSDETAILS = PANDANEWS_HOST + "/newsDetail.htm";
    //图书分类
    public static String URL_BOOKTYPE=JUHE_HOST+"/catalog";
    //图书分类内容
    public static String URL_BOOKTYPE_CONTENT=JUHE_HOST+"/query";

    public static String DTYPE="dtype";
    public static String CATALOG_ID="catalog_id";

    public static String KEY="key";
    public static  String PN="pn";
    public static  String RN="rn";

    public static String KEY_VALUE="86ffbeb799ae13786bdb50ab9672e16b";


    //用户
    String API = "/api";
    String YAPI = "/yapi";
    String USER = "/user";
    String PUBLIC = "/public";
    String PROFILE = "/profile";
    String SHARE_IT = "/Share_It";
    String ATTENTION = "/Attention";
    String ZANS = "/zans";
    String DISCUSS = "/Discuss";
    String SLIDE = "/slide";
    String FINANCE = "/Finance";
    String NEWS = "/news";
    String STOCK = "/stock";
    String BOND = "/Bond";
    String BUSSINESS = "/business";
    String FILE = "/File";
    String INDEX="/index";
    String UPLOAD="/upload";
    String DUBIWANG="/Dubiwang";
    String SINANEWS="/sina_news";
    String QUESTION_BANK="/question_bank";

    //注册
    String REGISTER_URL = BASE_URL + API + USER + PUBLIC + "/register";
    //注册的验证码
    String REGISTER_GETCODE_URL = BASE_URL + API + USER + "/verification_code/send";
    //其它验证码
    String REGISTER_GETA_ARB_CODE_URL = BASE_URL + API + USER + "/verification_code/send_code";

    //登录
    String LOGIN_URL = BASE_URL + API + USER + PUBLIC + "/login";
    //退出
    String LOGIN_OUT_URL = BASE_URL + API + USER + PUBLIC + "/logout";
    //忘记密码
    String LOGIN_FORGET_URL = BASE_URL + API + USER + PUBLIC + "/passwordReset";
    //修改密码
    String LOGIN_CHANGE_URL = BASE_URL + API + USER + PROFILE + "/changePassword";
    //用户信息
    String USER_INFO_URL = BASE_URL + API + USER + PROFILE + "/userInfo";
    //头像地址
    String USER_AVATER_URL = BASE_URL + UPLOAD+"/" ;
    //上传图片
    String USER_UPLOADIMG_URL = BASE_URL + API + USER + UPLOAD + "/one";
    //股吧列表
    public static String URL_GUBA_LOGIN_URL = BASE_URL + API + USER + SHARE_IT + "/index";
    //股吧列表（不用登录）
    public static String URL_GUBA_UNLOGIN_URL = BASE_URL + API + USER + SHARE_IT + "/index_list";
    //加入股吧
    public static String URL_ADDGUBA_URL = BASE_URL + API + USER + SHARE_IT + "/add_share";
    //退出股吧
    public static String URL_DELGUBA_URL = BASE_URL + API + USER + SHARE_IT + "/del_share";
    //股聊列表
    public static String URL_GULIAOLIST_URL = BASE_URL + API + USER + ATTENTION + "/index";
    //发表股聊
    public static String URL_PUBLISH_URL = BASE_URL + API + USER + ATTENTION + "/publish";
    //点赞
    public static String URL_POSTZAN_URL = BASE_URL + API + USER + ZANS + "/setZans";
    //取消点赞
    public static String URL_POSTDELZAN_URL = BASE_URL + API + USER + ZANS + "/unsetZans";
    //股聊详情
    public static String URL_DETAIL_URL = BASE_URL + API + USER + ATTENTION + "/view";
    //股聊详情
    public static String URL_COMMENT_LIST_URL = BASE_URL + API + USER + DISCUSS + "/getComments";
    //发表评论
    public static String URL_COMMENT_POST_URL = BASE_URL + API + USER + DISCUSS + "/setComments";
    //获取评论列表
    public static String URL_MESSAGE = BASE_URL + API + USER + DISCUSS + "/getMyComments";
    //BANNER
    public static String URL_BANNER = BASE_URL + YAPI + SLIDE + "/index";
    //首页黄金
    public static String URL_HOME_GOLD_URL = BASE_URL + YAPI + FINANCE + "/gold_list";
    //链得快讯
    public static String URL_LIANDE = BASE_URL + YAPI + NEWS + "/kx_chaindd_json";
    //读币快讯
    public static String URL_DUBI = BASE_URL + YAPI + DUBIWANG + "/news";
    //沪深股市
    public static String URL_STOCK = BASE_URL + YAPI + STOCK + "/getNewSinaCf";
    //创业板
    public static String URL_STOCK_CHUANGYEBAN = BASE_URL + YAPI + BOND + "/business";
    //美股行情
    public static String URL_STOCK_USA = BASE_URL + YAPI + FINANCE + "/get_usa";
    //黄金换算
    public static String URL_JINSHU = BASE_URL + YAPI + "/rate";
    //投资损益
    public static String URL_TOUZISUNYI = BASE_URL + YAPI + "/Stock_income";
    //举报帖子
    public static String URL_REPORT = BASE_URL + API + USER + ATTENTION + "/report";
    //音视频
    public static String URL_AUDIOVIDEO= BASE_URL + YAPI  + FILE + "/video_list";
    //财经事件
    public static String URL_FIANCE_EVENT= BASE_URL + YAPI  + INDEX + "/cjevent";
    //财经日历
    public static String URL_FINANCE_CALENDAR= BASE_URL + YAPI  + INDEX + "/cjrl";
    //股票资讯
    public static String URL_STOCKNEWS = BASE_URL + YAPI  + SINANEWS +  "/netease";
    //股票资讯详情
    public static String URL_STOCKNEWS_DETAIL = BASE_URL + YAPI  + SINANEWS +  "/view";
    //趣味道题
    public static String URL_QUESTION = BASE_URL + YAPI  + QUESTION_BANK +  "/getList";

    public static String PARAM_LASTTIME = "lastTime";
    public static String PARAM_PAGESIZE = "pageSize";
    public static String PARAM_EVENTID = "eventId";
    public static String PARAM_TYPE = "type";
    public static String PARAM_CATEGORIES = "categories";
    public static String PARAM_ID = "id";
    public static String PARAM_USERNAME = "username";
    public static String PARAM_PASSWORD = "password";
    public static String PARAM_OLD_PASSWORD = "old_password";
    public static String PARAM_CONFIRM_PASSWORD = "confirm_password";
    public static String PARAM_NICKNAME = "user_nickname";
    public static String PARAM_AVATAR = "avatar";
    public static String PARAM_SIGNATURE = "signature";
    public static String PARAM_USER_URL = "user_url";
    public static String PARAM_SEX = "sex";
    public static String PARAM_BIRTHDAY = "birthday";
    public static String PARAM_SHARE_ID = "share_id";
    public static String PARAM_SHARE_POST_ID = "share_post_id";

    public static String PARAM_POST_TITLE = "post_title";
    public static String PARAM_POST_CONTENT = "post_content";
    public static String PARAM_MORE = "more";
    public static String PARAM_TABLE_NAME = "table_name";
    public static String STAY_SHARE_POST = "share_post";
    public static String PARAM_OBJECT_ID = "object_id";
    public static String PARAM_CONTENT = "content";
    public static String PARAM_PARENT_ID = "parent_id";
    public static String PARAM_SLIDE_ID = "slide_id";
    public static String PARAM_NAME= "name";
    public static String PARAM_YEAR= "year";
    public static String PARAM_MONTHDAY= "monthday";



    public static String PARAM_DEVICE_TYPE = "device_type";
    public static String PARAM_DEVICE_NAME = "iphone";
    public static String PARAM_CONTENT_TYPE = "Content-Type";
    public static String PARAM_APPLICATION = "application/x-www-form-urlencoded";



    public static String PARAM_APPLICATION_JSON = "application/json";
    public static String PARAM_X_LC_ID = "X-LC-Id";


    public static String PARAM_X_LC_KEY = "X-LC-Key";

    public static String PARAM_X_BMOB_APPLICATION_ID = "X-Bmob-Application-Id";

    public static String PARAM_X_BMOB_REST_API_KEY = "X-Bmob-REST-API-Key";


    public static String PARAM_XX_TOKEN = "XX-Token";
    public static String PARAM_XX_DEVICE_TYPE = "XX-Device-Type";

    public static String PARAM_VERIFICATION_CODE = "verification_code";
    public static String PARAM_SIGNNAME = "signname";
    public static String STAY_SIGNNAME = "99999";
    public static String PARAM_FILE = "file";

    public static String PARAM_ACCESSKEYID = "accessKeyId";
    public static String PARAM_ACCESSKEYSECRET = "accessKeySecret";
    public static String PARAM_TEMPLATE_ID = "template_id";
    public static String PARAM_OUT_ID = "OutId";
    public static String PARAM_APP = "app";
    public static String PARAM_PAGE = "page";
    public static String PARAM_NUM = "num";
    public static String PARAM_BUYPRICE = "buyPrice";
    public static String PARAM_BUYAMT = "buyAmt";
    public static String PARAM_SELLPRICE = "sellPrice";
    public static String PARAM_SELLAMT = "sellAmt";
    public static String PARAM_BLOKERRATE = "brokerRate";
    public static String PARAM_STAMPTAX = "stampTax";
    public static String PARAM_TRANSFERFEE = "transferFee";

    public static String PARAM_NUMBER = "number";
    public static String PARAM_ASC = "asc";
    public static String PARAM_NODE = "node";
    public static String PARAM_NOTE = "note";
    public static String PARAM_CONTENT_URL = "content_url";

    public static String PARAM_SORT = "sort";
    public static String PARAM_MARKET = "market";

    public static String STAY_SORT = "trade";
    public static String STAY_SH_A = "sh_a";
    public static String PARAM_PAGE_SIZE = "page_size";
    public static String STAY_NICTATION = "nictation";
    public static String STAY_NOTICE = "notice";
    public static String STAY_VIEW = "view";
    public static String STAY_PRICECHANGE = "pricechange";//涨跌幅
    public static String STAY_CHANGEPERCENT = "changepercent";//涨跌额
    public static String STAY_VOLUME = "volume";//成交量
    public static String STAY_AMOUNT = "amount";//成交额
    public static String STAY_TURNOVERRATIO = "turnoverratio";//换手率


    public static int INFO_LEARN = 6;
    public static int INFO_STRATEGY = 7;
    public static int INFO_TRY = 8;

    public static final String KEY_ARTICLE = "article";
    public static final String CACHE_KEY = "article_cache";
    public static final String CACHE_KEY_STRATEGY = "article_cache_strategy";

    public static int LOGIN = 9;
    public static int REGISTER = 10;
    public static int PERSONUSER = 11;
    public static int FORGET = 12;
    public static int RESET = 13;
    public static int NICKNAME = 14;
    public static int SIGNATURE = 15;
    public static int ONRESUME_PERSON = 16;
    public static int FORUM_PUBLISH = 17;
    public static int PUBLISH_PERSON = 18;
    public static int ONRESUME_LOGIN = 19;
    public static int USER_MYMEAAAGE = 20;
    public static int STRATEGY = 21;
    public static int STOCKMARKET = 22;
    public static int TOOL = 23;
    public static int LEARN = 24;
    public static int INTRODUCE = 25;
    public static int SKILL = 26;
    public static int STOCKTOOL = 27;
    public static int LEARNCLASS = 28;
    public static int SKILLALL = 29;
    public static int FORUM = 30;
    public static int STOCKSLIDE = 31;
    public static int STOCKFOREIGNSLIDE = 32;
    public static int INFO = 33;
    public static int AUDIOVIDEO = 34;
    public static int VIDEO = 35;
    public static int BTC = 36;
    public static int BLOCK = 37;
    public static int TYPE = 38;
    public static int ONRESUME_TYPE = 40;
    public static int FEEDBACK = 41;
    public static int QUESTION=42;



    public static final String KEY_SP_FILENAME = "ltqh";


    //7*24小时
    String URL_NEWS_HOURS = PANDANEWS_HOST + API + NEWS + "/expressList.htm";
    public static String PARAM_MAXID = "maxId";
    //快讯
    public static String URL_NEWS_HOT = PANDANEWS_HOST + API + NEWS + "/newsList.htm";
}
