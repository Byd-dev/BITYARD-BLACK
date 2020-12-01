package com.ltqh.qh.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.ltqh.qh.BuildConfig;
import com.ltqh.qh.R;
import com.ltqh.qh.base.AppContext;
import com.ltqh.qh.base.BaseActivity;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.ImgEntity;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.entity.TipEntity;
import com.ltqh.qh.entity.TipPersonEntity;
import com.ltqh.qh.entity.UserInfoEntity;
import com.ltqh.qh.language.LanguageType;
import com.ltqh.qh.language.LanguageUtil;
import com.ltqh.qh.operation.base.OBaseActivity;
import com.ltqh.qh.utils.AppUtil;
import com.ltqh.qh.utils.FileUtil;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.view.CircleImageView;
import com.ltqh.qh.view.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;

public class PersonActivity extends OBaseActivity implements View.OnClickListener {

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    private static final String CROP_IMAGE_FILE_NAME = "bala_crop.jpg";
    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 1;
    private static final int CODE_CAMERA_REQUEST = 2;
    private static final int CODE_RESULT_REQUEST = 3;

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 480;
    private static int output_Y = 480;
    //改变头像的标记位
    private int new_icon = 0xa3;
    private ImageView headImage = null;
    private String mExtStorDir;
    private Uri mUriPath;

    private final int PERMISSION_READ_AND_CAMERA = 0;//读和相机权限
    private final int PERMISSION_READ = 1;//读取权限


    @BindView(R.id.btn_logout)
    Button btn_logout;
    @BindView(R.id.layout_delete)
    RelativeLayout layout_delete;

    @BindView(R.id.tv_version)
    TextView text_version;
    @BindView(R.id.text_username)
    TextView text_username;
    @BindView(R.id.text_nickname)
    TextView text_nickname;
    @BindView(R.id.layout_change)
    LinearLayout layout_change;
    @BindView(R.id.text_sign)
    TextView text_signature;
    @BindView(R.id.text_hc)
    TextView text_hc;

    @BindView(R.id.layout_sign)
    RelativeLayout layout_sign;

    @BindView(R.id.text_language)
    TextView text_language;

    @BindView(R.id.layout_nickname)
    RelativeLayout layout_nickname;

    private LoginEntity loginEntity;

    @BindView(R.id.layout_view)
    LinearLayout layout_view;

    @BindView(R.id.img_user)
    CircleImageView img_user;

    public static void enter(Context context) {
        Intent intent = new Intent(context, PersonActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_person;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View view) {
        EventBus.getDefault().register(this);

       // setStatusBar(getResources().getColor(R.color.maincolor));

        StatusBarUtil.setStatusBarDarkTheme(PersonActivity.this, false);



        mExtStorDir = Environment.getExternalStorageDirectory().toString();


        loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        Log.d("print", "initView:用户Token: " + loginEntity.getData().getToken());
        btn_logout.setOnClickListener(this);
        this.findViewById(R.id.img_back).setOnClickListener(this);
        text_version.setText("version" + BuildConfig.VERSION_NAME);


        layout_change.setOnClickListener(this);
        layout_nickname.setOnClickListener(this);
        layout_sign.setOnClickListener(this);
        layout_delete.setOnClickListener(this);
        text_hc.setText(AppUtil.getAppClearSize(this));

        this.findViewById(R.id.layout_language).setOnClickListener(this);

        this.findViewById(R.id.rl_head).setOnClickListener(this);

      /*  PersonalCenterFragment personalCenterFragment = new PersonalCenterFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_fragment_containter, personalCenterFragment).commit();*/
    }

    @Override
    protected void initData() {
        String language = SPUtils.getString(Constant.LANGUAGE);

        text_language.setText(language);


        getUserInfo();
    }

    //只有修改成功了 才更新数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventData(Integer data) {
        if (data == Constant.ONRESUME_PERSON) {
            getUserInfo();
        }
    }

    @Override
    protected void initEvent() {

    }

    private void clearCache() {
        AppUtil.cleanExternalCache(PersonActivity.this);
        AppUtil.cleanDatabases(PersonActivity.this);
        AppUtil.cleanInternalCache(PersonActivity.this);

        showToast("缓存已清理");
        text_hc.setText("0.0MB");

        text_hc.invalidate();
    }

    private void getUserInfo() {
        String token = loginEntity.getData().getToken();
        Log.d("print", "getUserInfo:194:用户:  "+token);
        OkGo.<String>get(Constant.USER_INFO_URL)
                .tag(this)
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                            Log.d("print", "onSuccess:152 " + codeMsgEntity);
                            if (codeMsgEntity.getCode() == 1) {
                                UserInfoEntity userInfoEntity = new Gson().fromJson(response.body(), UserInfoEntity.class);
                                Log.d("print", "onSuccess:用户信息 " + userInfoEntity);

                                String user_nickname = userInfoEntity.getData().getUser_nickname();
                                String mobile = userInfoEntity.getData().getMobile();
                                text_username.setText(mobile);

                                if (user_nickname.equals("")) {
                                    text_nickname.setText("用户");
                                } else {
                                    text_nickname.setText(user_nickname);
                                }
                                String signature = userInfoEntity.getData().getSignature();
                                if (signature.equals("")) {
                                    text_signature.setText("开开心心每一天~");
                                } else {
                                    text_signature.setText(signature);
                                }

                                SPUtils.putData(UserConfig.USER, userInfoEntity);
                                Glide.with(PersonActivity.this)
                                        .load(Constant.USER_AVATER_URL + userInfoEntity.getData().getAvatar())
                                        .asBitmap()
                                        .error(R.mipmap.user_icon)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .centerCrop()
                                        .into(img_user);

                            } else {
                                SPUtils.remove(UserConfig.LOGIN_USER);
                                SPUtils.remove(UserConfig.USER);
                                Toast.makeText(PersonActivity.this, "用户登录已失效", Toast.LENGTH_SHORT).show();
                                UserActivity.enter(PersonActivity.this, Constant.LOGIN);
                                finish();
                            }


                        }
                    }
                });
    }

    private void postLogout() {

        String token = loginEntity.getData().getToken();

        Log.d("print", "postLogout:Token:" + token);
        if (loginEntity == null) {
            return;
        }

        OkGo.<String>post(Constant.LOGIN_OUT_URL)
                .tag(this)
                .headers(Constant.PARAM_CONTENT_TYPE, Constant.PARAM_APPLICATION)
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();


                        if (!TextUtils.isEmpty(response.body()) && !response.body().equals("{")) {
                            Log.d("print", "onSuccess:88 " + response.body().toString());

                            TipEntity tipEntity = new Gson().fromJson(response.body(), TipEntity.class);
                            Toast.makeText(PersonActivity.this, tipEntity.getMsg(), Toast.LENGTH_SHORT).show();

                            if (tipEntity.getCode() == 1) {
                                SPUtils.remove(UserConfig.LOGIN_USER);
                                SPUtils.remove(UserConfig.USER);
                                PersonActivity.this.finish();
                            }

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                        Toast.makeText(PersonActivity.this, "当前网络不好，请检查网络", Toast.LENGTH_SHORT).show();


                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                postLogout();
                break;

            case R.id.img_back:
                finish();
                break;

            case R.id.layout_change:
                UserActivity.enter(PersonActivity.this, Constant.RESET);
                break;

            case R.id.layout_nickname:
                UserActivity.enter(PersonActivity.this, Constant.NICKNAME);
                break;

            case R.id.layout_sign:
                UserActivity.enter(PersonActivity.this, Constant.SIGNATURE);

                break;
            case R.id.layout_delete:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(PersonActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(PersonActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //进入到这里代表没有权限.请求权限
                        ActivityCompat.requestPermissions(PersonActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2000);
                    } else {
                        clearCache();
                    }
                } else {
                    clearCache();
                }
                break;

            case R.id.rl_head:


                showPopWindow();


                break;

            case R.id.layout_language:
                showItemPopWindow();
                break;
        }
    }
    private PopupWindow popupWindow;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showItemPopWindow() {


        View view = LayoutInflater.from(this).inflate(R.layout.o_item_language_pop, null);


        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);


        LinearLayout layout_chinese=view.findViewById(R.id.layout_chinese);

        layout_chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String   language = LanguageType.CHINESE.getLanguage();
                popupWindow.dismiss();
                backgroundAlpha(1f);
                changeLanguage(language);

            }
        });

        LinearLayout layout_english=view.findViewById(R.id.layout_english);
        layout_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String   language = LanguageType.ENGLISH.getLanguage();
                changeLanguage(language);
                popupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });
        WindowManager.LayoutParams params =getWindow().getAttributes();
        params.alpha = 0.6f;
        getWindow().setAttributes(params);

        TextView text_back = view.findViewById(R.id.text_back);
        text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });
        //关键
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });



        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(layout_view, Gravity.CENTER, 0, 0);

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void changeLanguage(String language) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtil.changeAppLanguage(AppContext.getAppContext(), language);
        }
        SPUtils.putString(Constant.LANGUAGE, language);
        MainActivity.reStart(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPopWindow() {


        View view = LayoutInflater.from(this).inflate(R.layout.item_headselect_layout, null);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        PopupWindow popupWindow = new PopupWindow(view, width, height);

        //PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.6f;
        getWindow().setAttributes(params);
        view.findViewById(R.id.text_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupWindow.dismiss();
                checkStoragePermission();
                backgroundAlpha(1f);


            }
        });


        view.findViewById(R.id.text_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupWindow.dismiss();
                checkReadPermission();
                backgroundAlpha(1f);

            }
        });

        view.findViewById(R.id.text_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });
        popupWindow.setAnimationStyle(R.style.pop_anim);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(layout_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    public void backgroundAlpha(float bgalpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgalpha;
        getWindow().setAttributes(lp);


    }

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        // 设置文件类型    （在华为手机中不能获取图片，要替换代码）
        /*Intent intentFromGallery = new Intent();
        intentFromGallery.setType("image*//*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);*/

        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CODE_GALLERY_REQUEST);

    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        String savePath = mExtStorDir;

        Intent intent = null;
        // 判断存储卡是否可以用，可用进行存储

        if (hasSdcard()) {
            //设定拍照存放到自己指定的目录,可以先建好
            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            Uri pictureUri;
            File pictureFile = new File(savePath, IMAGE_FILE_NAME);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //  pictureUri = FileProvider.getUriForFile(PersonActivity.this, getPackageName() + ".fileProvider", pictureFile);
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, pictureFile.getAbsolutePath());
                pictureUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            } else {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pictureUri = Uri.fromFile(pictureFile);
            }
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, pictureFile.getAbsolutePath());
                pictureUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            } else {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pictureUri = Uri.fromFile(pictureFile);
            }*/
            if (intent != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        pictureUri);
                startActivityForResult(intent, CODE_CAMERA_REQUEST);
            }
        }
    }

    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {

        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            // Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }

        Log.d("print", "onActivityResult: 498:" + requestCode);

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                Uri uri = intent.getData();
                // Log.d("print", "onActivityResult:504: " + uri);
                // addHeaderPhoto(uri);
                //裁剪图片
                cropRawPhoto(uri);
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
//                    cropRawPhoto(Uri.fromFile(tempFile));
                    cropRawPhoto(getImageContentUri(tempFile));
                } else {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;

            case CODE_RESULT_REQUEST:
               /* if (intent != null) {
                    setImageToHeadView(intent);    //此代码在小米有异常，换以下代码
                }*/
                // Log.d("print", "onActivityResult:508: "+intent.getData());
                Log.d("print", "onActivityResult:526: file:///storage/emulated/0/1553493679091bala_crop.jpg-----" + mUriPath);
                // addHeaderPhoto(mUriPath);
                Log.d("print", "onActivityResult:530: " + intent.getData());
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mUriPath));
                    Log.d("print", "onActivityResult:534: " + bitmap);
                    setImageToHeadView(intent, bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    //直接从相册获取 不裁剪的方法
    private void addHeaderPhoto(Uri uri) {
        Log.d("print", "addHeaderPhoto:549: " + uri);
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        //游标
        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String string = cursor.getString(columnIndex);
        //把获取到的文件转换类型
        File file = new File(string);

        String token = loginEntity.getData().getToken();

        Log.d("print", "addHeaderPhoto:560:  /storage/emulated/0/tencent/MicroMsg/WeiXin/20190325_132804.jpg" + file);

        if (loginEntity == null) {
            return;
        } else {

            OkGo.<String>post(Constant.USER_UPLOADIMG_URL)
                    .isMultipart(true)
                    .headers(Constant.PARAM_XX_TOKEN, token)
                    .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                    .params(Constant.PARAM_FILE, file)
                    .execute(new StringCallback() {

                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);
                            showProgressDialog();
                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            dismissProgressDialog();
                            Log.d("print", "onSuccess:612 " + response.body());
                            if (!TextUtils.isEmpty(response.body())) {
                                CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                                if (codeMsgEntity.getCode() == 1) {
                                    ImgEntity imgEntity = new Gson().fromJson(response.body(), ImgEntity.class);
                                    postChange(imgEntity.getData().getUrl());
                                    Toast.makeText(PersonActivity.this, codeMsgEntity.getMsg(), Toast.LENGTH_SHORT).show();


                                }
                            }

                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            dismissProgressDialog();

                        }
                    });
        }


    }

    private void postImgFile(File file) {


        String token = loginEntity.getData().getToken();

        if (loginEntity == null) {
            return;
        } else {

            OkGo.<String>post(Constant.USER_UPLOADIMG_URL)
                    .isMultipart(true)
                    .headers(Constant.PARAM_XX_TOKEN, token)
                    .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                    .params(Constant.PARAM_FILE, file)
                    .execute(new StringCallback() {

                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);
                            showProgressDialog();
                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            dismissProgressDialog();
                            Log.d("print", "onSuccess:612 " + response.body());
                            if (!TextUtils.isEmpty(response.body())) {
                                CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                                if (codeMsgEntity.getCode() == 1) {
                                    ImgEntity imgEntity = new Gson().fromJson(response.body(), ImgEntity.class);
                                    postChange(imgEntity.getData().getUrl());
                                    Toast.makeText(PersonActivity.this, codeMsgEntity.getMsg(), Toast.LENGTH_SHORT).show();


                                }
                            }

                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            dismissProgressDialog();

                        }
                    });
        }


    }

    private void postChange(String avatar) {
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        if (loginEntity == null) {
            return;
        }
        String token = loginEntity.getData().getToken();


        OkGo.<String>post(Constant.USER_INFO_URL)
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .params(Constant.PARAM_AVATAR, avatar)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        if (!TextUtils.isEmpty(response.body())) {
                            Log.d("print", "onSuccess:682:   " + response.body());
                            TipPersonEntity tipEntity = new Gson().fromJson(response.body(), TipPersonEntity.class);
                            Toast.makeText(PersonActivity.this, tipEntity.getMsg(), Toast.LENGTH_SHORT).show();
                            if (tipEntity.getCode() == 1) {
                                EventBus.getDefault().postSticky(Constant.ONRESUME_PERSON);

                                //  getUserInfo();
                            }

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                        Toast.makeText(PersonActivity.this, "当前网络不好，请检查网络", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            int result = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int resultCAMERA = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (result == PackageManager.PERMISSION_DENIED || resultCAMERA == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_READ_AND_CAMERA);
            } else {
                choseHeadImageFromCameraCapture();
            }
        }


    }


    private void checkReadPermission() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(PersonActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(PersonActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //进入到这里代表没有权限.请求权限
                ActivityCompat.requestPermissions(PersonActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2000);
            } else {
                choseHeadImageFromGallery();

            }
        }

     /*   int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission == PackageManager.PERMISSION_DENIED) {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_READ);
        } else {
            choseHeadImageFromGallery();
        }*/

    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {
        // Log.d("print", "cropRawPhoto: 683:" + uri);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        //startActivityForResult(intent, CODE_RESULT_REQUEST); //直接调用此代码在小米手机有异常，换以下代码
        String mLinshi = System.currentTimeMillis() + CROP_IMAGE_FILE_NAME;
        File mFile = new File(mExtStorDir, mLinshi);
//        mHeadCachePath = mHeadCacheFile.getAbsolutePath();
        // Log.d("print", "cropRawPhoto:702:  /storage/emulated/0/1553495151372bala_crop.jpg--- " + mFile);
        mUriPath = Uri.parse("file://" + mFile.getAbsolutePath());

        //将裁剪好的图输出到所建文件中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPath);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //注意：此处应设置return-data为false，如果设置为true，是直接返回bitmap格式的数据，耗费内存。设置为false，然后，设置裁剪完之后保存的路径，即：intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPath);
//        intent.putExtra("return-data", true);
        intent.putExtra("return-data", false);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }


    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent, Bitmap b) {
        /*Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            headImage.setImageBitmap(photo);
        }*/
        try {
            if (intent != null) {
                Bitmap bitmap = imageZoom(b);//看个人需求，可以不压缩
                // headImage.setImageBitmap(b);
                img_user.setImageBitmap(b);
                long millis = System.currentTimeMillis();
                //    Log.d("print", "setImageToHeadView:783: "+Environment.getExternalStorageState()+millis+CROP_IMAGE_FILE_NAME+bitmap);
                File file = FileUtil.saveFile(mExtStorDir, millis + CROP_IMAGE_FILE_NAME, bitmap);
                //  Log.d("print", "setImageToHeadView: 785:/storage/emulated/0/1555310163598bala_crop.jpg"+file);
                if (file != null) {
                    //传递新的头像信息给我的界面
                 /*   Intent ii = new Intent();
                    setResult(new_icon, ii);
                    Glide.with(this).load(file)
                            .into(img_user);*/
//                uploadImg(mExtStorDir,millis+CROP_IMAGE_FILE_NAME);
                    postImgFile(file);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    private Bitmap imageZoom(Bitmap bitMap) {
        //图片允许最大空间   单位：KB
        double maxSize = 1000.00;
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length / 1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),
                    bitMap.getHeight() / Math.sqrt(i));
        }
        return bitMap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

}
