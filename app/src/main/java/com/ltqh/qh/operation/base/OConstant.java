package com.ltqh.qh.operation.base;

public interface OConstant {

    public static String PANDANEWS_HOST = "https://zy.tigerqh.com";

     //public static String PANDANEWS_HOST = "https://xm.pandaqh.com";

    // public static String PANDANEWS_HOST = "https://xm.agjnw.com";

    String BASE_URL = "https://data.fk7h.com";


    //用户
    String API = "/api";
    String VF = "/vf";
    String SS0 = "/sso";
    String MINE = "/mine";
    String ACTIVITY = "/activity";
    String NEWS = "/news";
    String TRADE = "/trade";
    String PAY = "/pay";
    String HOME = "/home";
    String LOTTERY = "/lottery";

    String DISCOVER = "/discover";
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
    String STOCK = "/stock";
    String BOND = "/Bond";
    String BUSSINESS = "/business";
    String FILE = "/File";
    String INDEX = "/index";
    String UPLOAD = "/upload";
    String DUBIWANG = "/Dubiwang";
    String SINANEWS = "/sina_news";
    String QUESTION_BANK = "/question_bank";

    //注册
    String URL_REGISTER = PANDANEWS_HOST + API + SS0 + "/register.htm";
    //登录
    String URL_LOGIN = PANDANEWS_HOST + API + SS0 + "/user_login_check";
    //找回密码
    String URL_FINDBACK = PANDANEWS_HOST + API + SS0 + "/findback.htm";
    //修改密码
    String URL_RESETPASS = PANDANEWS_HOST + API + MINE + "/loginPasswd.htm";
    //设置取款密码
    String URL_SET_ATM_PASS = PANDANEWS_HOST + API + MINE + "/atmPasswd.htm";
    //退出
    String URL_LOGOUT = PANDANEWS_HOST + API + SS0 + "/user_logout";
    //获取用信息
    String URL_USER_MINE = PANDANEWS_HOST + API + MINE + "/index.htm";
    //获取用信息
    String URL_USER_BASE_MINE = PANDANEWS_HOST + API + MINE + "/profile.htm";
    //图片验证码
    String URL_REGISTER_IMG_CODE = PANDANEWS_HOST + API + VF + "/verifyCode.jpg";
    //7*24小时
    String URL_NEWS_HOURS = PANDANEWS_HOST + API + NEWS + "/expressList.htm";
    //7*24小时
    String O_URL_MESSAGE = PANDANEWS_HOST + API + HOME + "/kefu.htm";
    //BANNER
    public static String URL_BANNER = PANDANEWS_HOST + API + "/index.htm";
    //快讯
    public static String URL_NEWS_HOT = PANDANEWS_HOST + API + NEWS + "/newsList.htm";

    //快讯详情
    public static String URL_NEWS_HOT_DETAIL = PANDANEWS_HOST + API + NEWS + "/newsDetail.htm";
    //财经日历
    public static String URL_FINANCE_CALENDAR = PANDANEWS_HOST + API + NEWS + "/calendar.htm";
    //公告
    public static String URL_NEWS_REPORT = PANDANEWS_HOST + API + DISCOVER + "/index.htm";
    //注册的验证码
    String REGISTER_GETCODE_URL = PANDANEWS_HOST + API + SS0 + "/register.htm";
    //持仓的数据
    public static String URL_POSITION = PANDANEWS_HOST + API + TRADE + "/scheme.htm";
    //平仓的数据
    public static String URL_CLOSE = PANDANEWS_HOST + API + TRADE + "/close.htm";
    //设置止盈止损
    public static String URL_SPSL = PANDANEWS_HOST + API + TRADE + "/spsl.htm";
    //下单
    public static String URL_OPEN = PANDANEWS_HOST + API + TRADE + "/open.htm";
    //添加模拟金
    public static String URL_ADDSCORE = PANDANEWS_HOST + API + TRADE + "/addScore.htm";
    //充值
    public static String URL_RECHARGE = PANDANEWS_HOST + API + PAY + "/recharge.htm";
    //实名认证
    public static String URL_PROFILE_AUTH = PANDANEWS_HOST + API + MINE + "/profileAuth.htm";
    //获取银行卡列表
    public static String URL_BANKLIST = PANDANEWS_HOST + API + MINE + "/bankCard.htm";
    //添加银行卡
    public static String URL_BANK_ADD = PANDANEWS_HOST + API + MINE + "/bankCardAdd.htm";
    //设置银行卡
    public static String URL_BANK_UPDATE = PANDANEWS_HOST + API + MINE + "/bankCardUpdate.htm";
    //修改用户名
    public static String URL_USERNAME = PANDANEWS_HOST + API + MINE + "/username.htm";
    //修改手机
    public static String URL_MOBILE = PANDANEWS_HOST + API + MINE + "/mobile.htm";
    //提款
    public static String URL_WITHDRAW = PANDANEWS_HOST + API + PAY + "/withdraw.htm";
    //提款记录
    public static String URL_WITHDRAW_HISTORY = PANDANEWS_HOST + API + PAY + "/withdrawHistory.htm";
    //充值记录
    public static String URL_RECHARGE_HISTORY = PANDANEWS_HOST + API + PAY + "/rechargeHistory.htm";
    //资金明细
    public static String URL_FUNDS = PANDANEWS_HOST + API + MINE + "/funds.htm";
    //客服
    public static String URL_SERVICE = "https://v66.livechatvalue.com/chat/chatClient/chatbox.jsp?companyID=80002746&configID=865&enterurl=zhongyuan&info=";
    //活动列表
    public static String URL_TASK = PANDANEWS_HOST + API + ACTIVITY + "/task.htm";
    //签到
    public static String URL_TASK_CHECK = PANDANEWS_HOST + API + USER + "/checkin/activity.htm";
    //签到历史
    public static String URL_TASK_CHECK_HISTORY = PANDANEWS_HOST + API + USER + "/checkin/history.htm";
    //签到列表历史
    public static String URL_TASK_CHECK_IN_HISTORY = PANDANEWS_HOST + API + USER + "/checkin/pound.htm";
    //签到列表历史
    public static String URL_UNION = PANDANEWS_HOST + API + MINE + "/union.htm";
    //我的用户
    public static String URL_UNION_USER = PANDANEWS_HOST + API + MINE + "/unionUser.htm";
    //我的用户
    public static String URL_PAY = PANDANEWS_HOST + API + PAY + "/rechargeXXPay.htm";
    //抽奖历史记录
    public static String URL_LOTTERY = PANDANEWS_HOST + API + LOTTERY ;
    //api
    String URL_API = PANDANEWS_HOST + API;
    //行情
    String URL_MARKET = "https://www.quote01.com/quote.jsp";
    //1分
    String URL_QUOTE = "https://qts.realtimeqt.top/quota.jsp";
    //历史
    String URL_QUOTE_HISTORY = "https://qts.realtimeqt.top/history";
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
    String USER_AVATER_URL = BASE_URL + UPLOAD + "/";
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
    public static String URL_AUDIOVIDEO = BASE_URL + YAPI + FILE + "/video_list";
    //财经事件
    public static String URL_FIANCE_EVENT = BASE_URL + YAPI + INDEX + "/cjevent";

    //股票资讯
    public static String URL_STOCKNEWS = BASE_URL + YAPI + SINANEWS + "/netease";
    //股票资讯详情
    public static String URL_STOCKNEWS_DETAIL = BASE_URL + YAPI + SINANEWS + "/view";
    //趣味道题
    public static String URL_QUESTION = BASE_URL + YAPI + QUESTION_BANK + "/getList";


    public static String PARAM_LASTTIME = "lastTime";
    public static String PARAM_PAGESIZE = "pageSize";

    public static String PARAM_CALLBACK = "callback";
    public static String PARAM_SYMBOL = "symbol";
    public static String PARAM_RESOLUTION = "resolution";
    public static String PARAM_FROM = "from";
    public static String PARAM_TO = "to";
    public static String PARAM_EQUAL = "_=";
    public static String PARAM_XIAHUAXIAN = "_";

    public static String PARAM_SCHEMESORT = "schemeSort";
    public static String PARAM_TRADETYPE = "tradeType";
    public static String PARAM_BEGINTIME = "beginTime";
    public static String PARAM_STOPPROFIT = "stopProfit";
    public static String PARAM_STOPLOSS = "stopLoss";
    public static String PARAM_IDENTITY = "identity";
    public static String PARAM_SERVICE_CHANGE = "serviceCharge";
    public static String PARAM_EAGLE_DEDUCTION = "eagleDeduction";
    public static String PARAM_MONEY_TYPE = "moneyType";
    public static String PARAM_PLATFORM = "platform";
    public static String STAY_ANDROID = "android";


    public static String PARAM_CODE = "code";
    public static String PARAM_SIMPLE = "simple";
    public static String PARAM_BETTINGID = "bettingId";
    public static String PARAM_BETTINGLIST = "bettingList";

    public static String PARAM_SOURCE = "source";
    public static String PARAM_COMMODITY = "commodity";
    public static String PARAM_CONTRACT = "contract";
    public static String PARAM_ISBUY = "isBuy";
    public static String PARAM_PRICE = "price";


    public static int MARKET_PERIOD = 3 * 1000;
    public static int ONE_PERIOD = 1 * 1000;

    public static int PERIOD = 5 * 1000;


    public static String PARAM_ACTION = "action";
    public static String PARAM_PROVICE = "province";
    public static String PARAM_CITY = "city";
    public static String PARAM_SUBBRANCH = "subbranch";
    public static String PARAM_ID = "id";
    public static String PARAM_MONEY = "money";
    public static String PARAM_BANK_CARD = "bankCard";
    public static String PARAM_USER_ACTIVITY_ID = "userActivityId";

    public static String STAY_MESSAGE = "message";

    public static String STAY_SENDCODE = "sendCode";
    public static String STAY_PASSWD = "passwd";
    public static String STAY_APPLY = "apply";
    public static String STAY_MORE = "more";

    public static String PARAM_SWITCH_TYPE = "switchType";
    public static String STAY_UPDATE = "update";
    public static String STAY_DEL = "del";
    public static String STAY_VERIFY = "verify";
    public static String STAY_SEND_VERIFY = "sendVerify";
    public static String STAY_SEND_VERIFY_NEW = "sendVerifyNew";


    public static String PARAM_VERIFYCODE = "verifyCode";
    public static String PARAM_OLDPASS = "oldPass";

    public static String PARAM_PASSWORD = "password";
    public static String PARAM_WITHDRAWPW = "withdrawPw";
    public static String PARAM_WITHDRAWPWCFM = "withdrawPwCfm";


    public static String PARAM_NEWPASS = "newPass";
    public static String PARAM_NEWPASSCFM = "newPassCfm";
    public static String PARAM_IDENTITYNUMBER = "identityNumber";
    public static String PARAM_CHANNEL = "channel";
    public static String PARAM_DEVICE = "device";
    public static String PARAM_MAX = "max";
    public static String PARAM_MIN = "min";

    public static String STAY_CHECKNAME = "checkName";
    public static String STAY_AUTH_IDENTITY = "authIdentity";
    public static String STAY_AUTH = "auth";

    public static String STAY_ACTION = "sendCode";
    public static String STAY_GET_PAY_LIST = "getPayList";

    public static String STAY_REGISTER = "register";
    public static String STAY_VERIFYCODE_ACTION = "verifyCode";

    public static String PARAM_NEWPASS_CFM = "newPassCfm";

    public static String PARAM_MOBILE = "mobile";
    public static String PARAM_IMGCODE = "imageCode";
    public static String PARAM_MAXID = "maxId";


    public static String PARAM_TYPE = "type";
    public static String PARAM_USERNAME = "username";
    public static String STAY_PASSWD_ACTION = "passwd";
    public static String PARAM_LOTTERY_ID = "lotteryId";

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
    public static String PARAM_DATE = "date";


    public static String PARAM_POST_TITLE = "post_title";
    public static String PARAM_POST_CONTENT = "post_content";
    public static String PARAM_MORE = "more";
    public static String PARAM_TABLE_NAME = "table_name";
    public static String STAY_SHARE_POST = "share_post";
    public static String PARAM_OBJECT_ID = "object_id";
    public static String PARAM_CONTENT = "content";
    public static String PARAM_PARENT_ID = "parent_id";
    public static String PARAM_SLIDE_ID = "slide_id";
    public static String PARAM_NAME = "name";
    public static String PARAM_YEAR = "year";
    public static String PARAM_MONTHDAY = "monthday";


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
    public static String FIRSTLOAD = "firstload";
    public static String REFRESHTYPE = "refresh";
    public static String NULLTYPE = "nulltype";

    public static int INFO_LEARN = 6;
    public static int INFO_STRATEGY = 7;
    public static int INFO_TRY = 8;

    public static final String KEY_ARTICLE = "article";
    public static final String CACHE_KEY = "article_cache";
    public static final String CACHE_KEY_STRATEGY = "article_cache_strategy";

    public static int LOGIN = 9;
    public static int OADDMARKET = 10;
    public static int OEDITMARKET = 11;
    public static int FORGET = 12;
    public static int RESET = 13;
    public static int OQUETO = 14;
    public static int RESETPHONE = 15;
    public static int ONRESUME_PERSON = 16;
    public static int OACCOUNTSETTING = 17;
    public static int OREALNAME = 18;
    public static int BANK = 19;
    public static int O_EDIT_BANK = 20;
    public static int RESETPHONENEXT = 21;
    public static int SETWITHDRAWPASS = 22;
    public static int RESETWITHDRAWPASS = 23;
    public static int WITHDRAWFORGETPASS = 24;
    public static int CHECKREALNAME_ID = 25;
    public static int WITHDRAW = 26;

    public static int FINDBACKWITHDRAW = 27;
    public static int LEARNCLASS = 28;
    public static int SKILLALL = 29;
    public static int O_WITHDRAW_HISTORY = 30;
    public static int O_RECHARGE = 31;
    public static int O_RECHARGE_HISTORY = 32;
    public static int O_MONEY_DETAIL = 33;
    public static int O_MESSAGE = 34;
    public static int VIDEO = 35;
    public static int O_FINANCE = 36;
    public static int O_GUIDEBOOK = 37;
    public static int TYPE = 38;
    public static int O_ABOUT = 40;
    public static int O_ACTIVITY = 41;
    public static int O_TERMS = 42;
    public static int O_RECOMMEND = 43;
    public static int O_MYFRIEND = 44;
    public static int O_HOT = 45;
    public static int O_REPORT = 46;
    public static int O_RECHARGE_STEP = 46;
    public static int O_SEARCH = 47;
    public static int O_RAIDERS = 48;
    public static int O_NOVICE = 49;
    public static int O_CONVERSION = 50;
    public static int O_LOTTERY = 51;


    public static final String KEY_SP_FILENAME = "ltqh";

}
