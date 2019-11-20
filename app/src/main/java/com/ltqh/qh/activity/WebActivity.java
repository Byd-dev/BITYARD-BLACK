package com.ltqh.qh.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.ltqh.qh.BuildConfig;
import com.ltqh.qh.R;
import com.ltqh.qh.base.BaseActivity;
import com.ltqh.qh.config.HttpKeys;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.operation.base.OBaseActivity;
import com.ltqh.qh.utils.AppJs;
import com.ltqh.qh.utils.PhoneInfoUtil;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.utils.WebFileUploader;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class WebActivity extends OBaseActivity {


    private RelativeLayout layout_bar;


    private View stay_line;

    private ImageView img_back;

    public static class UrlBuilder {
        private String url;
        private Map<String, Object> params;

        public UrlBuilder url(String url) {
            this.url = url;
            return this;
        }

        public UrlBuilder put(String key, Object param) {
            if (params == null) {
                this.params = new HashMap<>();
            }
            this.params.put(key, param);
            return this;
        }

        public String toUrl() {
            StringBuilder builder = new StringBuilder();
            if (!TextUtils.isEmpty(url)) {
                builder.append(url);
            }

            if (params != null && !params.isEmpty()) {
                builder.append("?");
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (entry.getValue() != null) {
                        builder.append(entry.getKey());
                        builder.append('=');
                        builder.append(entry.getValue().toString());
                        builder.append('&');
                    }
                }
                if (builder.toString().endsWith("&")) {
                    builder.deleteCharAt(builder.length() - 1);
                }
            }
            return builder.toString();
        }
    }

    private static final String TASK_CENTER_VERSION = "8";

    private static final String TAG = "WebView";

    private static final String KEY_TITLE = "title";
    private static final String KEY_URL = "url";
    private static final String KEY_HAS_SERVICE = "has_service";
    private static final String KEY_BACKGROUND_COLOR = "background_color";
    private static final String KEY_HAS_CLOSE_BUTTON = "has_close_button";
    private static final String KEY_HTML = "html";
    private static final String KEY_HAS_SHARE_ARTICLE = "has_share_article";
    private static final String KEY_ARTICLE = "article";
    private static final String KEY_FROM = "from";
    private static final String KEY_HAS_TITLE = "hasTitle";

    private static void openWeb(Context context, Intent intent) {
        context.startActivity(intent);
    }


    public static void openAbsPage(Context context, String url, String title) {
//        String token = "";
//        if (UserInfoManager.getInstance().isLogin()) {
//            token = UserInfoManager.getInstance().getToken();
//        }
//        url = new UrlBuilder()
//                .url(url)
//                .put(HttpKeys.VERSION, TASK_CENTER_VERSION)
//                .put(HttpKeys.TOKEN, token)
//                .put(HttpKeys.TYPE, "original")
//                .toUrl();
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_URL, url);
        intent.putExtra(KEY_HAS_TITLE, true);
        intent.putExtra(KEY_FROM, "adv");
        //openWebForResult(context, intent, ActivityConfig.RequestCode.TASK_CENTER);
        openWeb(context, intent);
    }


    public static void openCaijin(Context context, String H5url) {
        if (context != null) {
            String url = new UrlBuilder()
                    .url(H5url)
                    .toUrl();
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra(KEY_URL, url);
            intent.putExtra(KEY_HAS_TITLE, true);
            openWeb(context, intent);
        }
    }

    /**
     * 关于我们
     *
     * @param context
     */
    public static void openAboutUs(Context context) {
        if (context != null) {
            String title = "关于我们";

            String url = new UrlBuilder()
                    .url("https://aassdd.kk05.cn/r/about?type=original")
                    .put("version", BuildConfig.VERSION_NAME)
                    .put(HttpKeys.TYPE, "original")
                    .toUrl();

            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra(KEY_TITLE, title);
            intent.putExtra(KEY_URL, url);
            intent.putExtra(KEY_HAS_TITLE, false);
            openWeb(context, intent);
        }
    }

    /*打开智齿客服的*/
    public static void openZhiChiService(Context context) {

        if (context != null) {

            String title = "客服";
            LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);

            //  String format = String.format(ApiConfig.Web.CUSTOMER_SERVICE_ZHICHI, AppConfig.APPKEY, UserInfoManager.getInstance().getUserId(), UserInfoManager.getInstance().getNickName(), "");
            String url = new UrlBuilder()
                    .url("https://www.sobot.com/chat/h5/index.html")
                    .put("sysNum", "9ec98afea51647059292fa126a0e4c01")
                    .put("source", "2")
                    .put("tel", loginEntity.getUser().getData().getUser().getMobile())
                    .put("uname", loginEntity.getData().getUser().getUser_nickname())
                    .put("face", "")
                    .put("remark", "Android-" + "期货" + "-" + "20010")
                    .toUrl();
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra(KEY_URL, url);
            intent.putExtra(KEY_TITLE, title);
            intent.putExtra(KEY_HAS_TITLE, true);
            openWeb(context, intent);

        }
    }

    public static void openEvent(Context context, String H5url) {
        if (context != null) {
            String url = new UrlBuilder()
                    .url(H5url)
                    .toUrl();
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra(KEY_URL, url);
            intent.putExtra(KEY_HAS_TITLE, true);
            openWeb(context, intent);
        }
    }

    private static boolean isProgress = true;

    public static void openZhiyuan(Context context, String H5url) {
        isProgress = false;
        if (context != null) {
            String url = new UrlBuilder()
                    .url(H5url)
                    .put(HttpKeys.TYPE, "original")
                    .toUrl();
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra(KEY_URL, url);
            intent.putExtra(KEY_HAS_TITLE, false);
            openWeb(context, intent);
        }
    }


    private ProgressBar mProgressBar;
    protected WebView mWebView;

    private String mUrl;
    private String mTitle;

    private WebFileUploader mWebFileUploader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initViews();
        initData();
        processIntent(getIntent());

        //EventBus.getDefault().register(this);
        //load(null);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.o_activity_main_web;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View view) {
        String uniqueId = PhoneInfoUtil.getUniqueID(this);
    }

    protected void load(String url) {
        if (url != null) {
            mWebView.loadUrl(url);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.removeJavascriptInterface("AppJs");
            mWebView.destroy();
        }
        //EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void goBack() {
        mWebView.goBack();
    }

    public void goForward() {
        mWebView.goForward();
    }

    public void goBottom() {
        while (mWebView.canGoBack()) {
            mWebView.goBack();
        }
    }

    private void processIntent(Intent intent) {


        if (intent != null) {
            mTitle = intent.getStringExtra(KEY_TITLE);
            mUrl = intent.getStringExtra(KEY_URL);


            Boolean hasService = intent.getBooleanExtra(KEY_HAS_SERVICE, false);
            int bgColorRes = intent.getIntExtra(KEY_BACKGROUND_COLOR, R.color.white);
            //boolean hasCloseButton = intent.getBooleanExtra(KEY_HAS_CLOSE_BUTTON, false);
            String html = intent.getStringExtra(KEY_HTML);
            boolean hasShareArticle = intent.getBooleanExtra(KEY_HAS_SHARE_ARTICLE, false);

            boolean hasTitle = intent.getBooleanExtra(KEY_HAS_TITLE, true);

            if (hasTitle) {
                layout_bar.setVisibility(View.VISIBLE);
                stay_line.setVisibility(View.VISIBLE);
                img_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }

//            } else {
//                mTitleBar.setBackButtonDrawable(R.drawable.ic_white_back);
//            }

            if (!TextUtils.isEmpty(html)) {
                mWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
                return;
            }
            mWebView.loadUrl(mUrl);
        }
    }


    private void initViews() {

        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        layout_bar = findViewById(R.id.title_bar);
        img_back = findViewById(R.id.img_back);
        stay_line = findViewById(R.id.stay_line);

        mWebView = new WebView(getApplicationContext());
        FrameLayout container = (FrameLayout) findViewById(R.id.container);
        container.addView(mWebView);
        initWebViewSetting();
        mWebView.setBackgroundColor(0);
        mWebView.addJavascriptInterface(new AppJs(this), "AppJs");

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {//  Logger.i("print", "web url override = 653:" + url);
                Log.d("print", "shouldOverrideUrlLoading:671: " + url);
                if (url.contains("mqqwpa")) { //企业QQ
                    openApp(url, "请先安装qq");
                } else if (url.startsWith("http://wpd.b.qq.com/")) { //防止跳回腾讯页面
                    // mWebView.loadUrl(ApiConfig.getFullUrl(ApiConfig.Web.CUSTOMER_SERVICE));
                } else if (url.startsWith("intent://")) {
                    openApp(url, "未安装应用");
                } else if (url.startsWith("alipays://") || url.startsWith("mqqapi://")) {
                    startAlipayActivity(url);
                    //pay.palmpay
                } else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.M) && (url.contains("alipays://") || url.contains("mqqapi://"))) {
                    Log.d("print", "shouldOverrideUrlLoading:683:: " + url);

                    startAlipayActivity(url);
                } else if (url.startsWith("weixin://")) {
                    Log.d("print", "shouldOverrideUrlLoading:686:" + url);
                    //如果return false  就会先提示找不到页面，然后跳转微信
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } catch (Exception e) {
                        showToast("请下载安装最新版微信");
                    }
                    return true;
                } else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.M) && (url.startsWith("weixin://"))) {
                    Log.d("print", "shouldOverrideUrlLoading:699: " + url);

                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } catch (Exception e) {
                        showToast("请下载安装最新版微信");
                    }
                    return true;
                } else {
                    //H5微信支付要用，不然说"商家参数格式有误"
                    Map<String, String> extraHeaders = new HashMap<String, String>();
                    extraHeaders.put("Referer", "http://www.smartgouwu.com");
                    view.loadUrl(url, extraHeaders);
                    Log.d("print", "shouldOverrideUrlLoading:729: " + url);
                    // mWebView.loadUrl(url);
                }


                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                String titleText = view.getTitle();
                Log.d(TAG, "onPageFinished:658: " + titleText);
                if (!TextUtils.isEmpty(titleText) && !url.contains(titleText)) {
                    // mTitle = titleText;
                    //mTitleBar.setTitle(mTitle);
                }
                if ("adv".equals(getIntent() != null ? getIntent().getStringExtra(KEY_FROM) : null)) {
                    mWebView.loadUrl("javascript: "
                            + "Array.prototype.slice.call(document.getElementsByTagName('img')).forEach(function(item) { item.style.width = \"100%\"})");
                }
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (isProgress == true) {
                    if (newProgress == 100) {
                        mProgressBar.setVisibility(View.GONE);
                    } else {
                        if (mProgressBar.getVisibility() == View.GONE) {
                            mProgressBar.setVisibility(View.VISIBLE);
                        }
                        mProgressBar.setProgress(newProgress);
                    }
                } else {
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {

                return super.onConsoleMessage(consoleMessage);
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                return mWebFileUploader.onLOLLIPOP(filePathCallback, fileChooserParams);
            }

            //// Andorid 4.1+
            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mWebFileUploader.onJellyBean(uploadMsg, acceptType, capture);
            }

            // Andorid 3.0+
            protected void openFileChooser(ValueCallback valueCallback, String acceptType) {
                mWebFileUploader.onHoneyComB(valueCallback, acceptType);
            }

        });
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimeType, long contentLength) {
                openApp(url);
            }
        });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    // 调起支付宝并跳转到指定页面
    private void startAlipayActivity(String url) {
        Log.d("print", "startAlipayActivity:支付宝页面626: " + url);
        Intent intent;
        try {
            intent = Intent.parseUri(url,
                    Intent.URI_INTENT_SCHEME);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setComponent(null);
            startActivity(intent);
            //  finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initWebViewSetting() {
        WebSettings settings = mWebView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setAppCachePath(getCacheDir().getPath());
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);

        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setGeolocationEnabled(true);

        //设置自适应
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //settings.setUseWideViewPort(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
    }

    @Override
    public void initData() {
        mWebFileUploader = new WebFileUploader(this);
    }

    @Override
    protected void initEvent() {

    }


    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebFileUploader.onResult(requestCode, resultCode, intent);
    }


    //打开本地应用
    private void openApp(String... url) {
        try {
            Intent intent = Intent.parseUri(url[0], Intent.URI_INTENT_SCHEME);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else if (url.length > 1 && url[1] != null) {
                showToast(url[1]);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
