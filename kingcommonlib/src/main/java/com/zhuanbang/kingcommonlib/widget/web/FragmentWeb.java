package com.zhuanbang.kingcommonlib.widget.web;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.zhuanbang.kingcommonlib.R;
import com.zhuanbang.kingcommonlib.base.BaseFragment;
import com.zhuanbang.kingcommonlib.config.Constant;
import com.zhuanbang.kingcommonlib.dagger.component.AppComponent;
import com.zhuanbang.kingcommonlib.utils.ArmsUtils;
import com.zhuanbang.kingcommonlib.utils.ToastUitl;

import org.devio.takephoto.model.TResult;

import java.io.File;

import butterknife.Unbinder;


public class FragmentWeb extends BaseFragment {

    private static final String TAG = "ActivityFragment";

    RelativeLayout mToolbarBack;
    TextView mToolbarTitle;
    TextView mToolbarRight;
    Toolbar mToolbar;
    WebView mWebview;
    TextView mBtnRetry;
    Unbinder unbinder;
    View mErrorView;

    String mUrl, mData, mBaseUrl;
    //点击查看
    String jsimg = "function()\n { var imgs = document.getElementsByTagName(\"img\");for(var i = 0; i < imgs.length; i++){  imgs[i].onclick = function()\n{mainActivity.startPhotoActivity(this.src);}}}";
    //替换img属性
    String varjs = "<script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
    boolean isActivity = true;
    String head = "<html lang=\"zh-CN\"> <head/><body>";
    String end = "</body></html>";

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final int RESULT_CODE_PICK_FROM_ALBUM_BELLOW_LOLLILOP = 1;
    private final int RESULT_CODE_PICK_FROM_ALBUM_ABOVE_LOLLILOP = 2;
    private int REQUEST_CODE = RESULT_CODE_PICK_FROM_ALBUM_ABOVE_LOLLILOP;

    public static FragmentWeb newInstance(String title, String url, boolean isActivity) {
        Bundle args = new Bundle();
        FragmentWeb fragment = new FragmentWeb();
        args.putString(Constant.EXTRA_WEB_URL, url);
        args.putString(Constant.EXTRA_WEB_TITLE, title);
        args.putBoolean(Constant.EXTRA_WEB_IS_ACTIVITY, isActivity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_web;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void initView() {
        mToolbarBack = mRootView.findViewById(R.id.toolbar_back);
        mToolbarTitle = mRootView.findViewById(R.id.toolbar_title);
        mToolbarRight = mRootView.findViewById(R.id.toolbar_right);
        mToolbar = mRootView.findViewById(R.id.top_tool_bar);
        mWebview = mRootView.findViewById(R.id.webview);
        mBtnRetry = mRootView.findViewById(R.id.btn_retry);

        Bundle bundle = getArguments();
        mUrl = bundle.getString(Constant.EXTRA_WEB_URL);
        if (TextUtils.isEmpty(mUrl) && !mUrl.startsWith("https://")) {
            mUrl = mUrl.replace("http", "https");
        }
        mBaseUrl = bundle.getString(Constant.EXTRA_WEB_BASE_URL);
        mData = bundle.getString(Constant.EXTRA_WEB_DATA);
        mToolbarTitle.setText(bundle.getString(Constant.EXTRA_WEB_TITLE));
        isActivity = bundle.getBoolean(Constant.EXTRA_WEB_IS_ACTIVITY);
        if (isActivity) {
            mToolbarBack.setOnClickListener(v -> {
                if (mWebview != null && mWebview.canGoBack()) {
                    mWebview.goBack();
                } else {
                    getActivity().finish();
                }
            });
        } else {
            mToolbarBack.setVisibility(View.GONE);
            mToolbarTitle.setTextColor(getResources().getColor(R.color.white));
            mToolbar.setBackgroundColor(getColorPrimaryDark());
        }
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setDatabaseEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        String ua = webSettings.getUserAgentString();
        webSettings.setUserAgentString(ua + "/webJSY");
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setSupportZoom(true);
        if (TextUtils.isEmpty(mData)) {
            loadUrl();
        } else {
            loadData();
        }

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebview.loadUrl("javascript:(" + jsimg + ")()");
            }

            @SuppressWarnings("deprecation")
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (view != null) {
                    view.stopLoading();
                    view.setVisibility(View.GONE);
                    view.clearView();
                }

//                LoadingDialog.cancelDialogForLoading();
                if (mErrorView != null) mErrorView.setVisibility(View.VISIBLE);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mUrl = url;
                if (mErrorView != null) mErrorView.setVisibility(View.GONE);
                if (view != null) view.setVisibility(View.VISIBLE);
                if (url.indexOf(".apk") != -1) {
                    ArmsUtils.sendUrl(mContext, url);
                } else if (url.indexOf("tel:") != -1) {
                    ArmsUtils.sendUrl(mContext, url);
                } else if (url.indexOf("tmast://") != -1) {
                    ArmsUtils.sendUrl(mContext, url);
                } else if (url.startsWith("taobao://")) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        Uri content_url = Uri.parse(url);
                        intent.setData(content_url);
                        startActivity(intent);
                    } catch (Exception o) {
                        ToastUitl.showLong("请下载淘宝");
                        return true;
                    }
                    return true;
                } else {
//                    view.loadUrl(url);
                    return false;
                }
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
            }

        });
        mWebview.setWebChromeClient(new MyWebChromeClient());
    }

    private void loadUrl() {
        mWebview.loadUrl(mUrl);
    }

    private void loadData() {
//        String data = Html.fromHtml(mData).toString();

        mWebview.loadDataWithBaseURL(mBaseUrl, head + varjs + mData + end, "text/html", "UTF-8", null);
//        mWebview.loadDataWithBaseURL(mBaseUrl, data, "text/html", "UTF-8", null);
    }

    /**
     * 获取主题颜色
     *
     * @return
     */
    public int getColorPrimaryDark() {
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

    }

    //    @OnClick(R2.id.btn_retry)
    void retry() {
//        LoadingDialog.showDialogForLoading(getActivity(), "加载中", true);
        mWebview.loadUrl(mUrl);
    }

    /**
     * 释放webview
     */
    public void destoryWebView() {
        if (mWebview != null) {
            mWebview.destroy();
            mWebview = null;
        }

    }

    /**
     * 返回上一页
     */
    public void backUp() {
        if (mWebview.canGoBack()) {
            mWebview.goBack();
        } else {
            if (getActivity() != null) getActivity().finish();
        }
    }

    /**
     * 打开图库,同时处理图片（项目业务需要统一命名）
     */
    private void selectImage(int resultCode) {
        REQUEST_CODE = resultCode;
//        TakePhotoConfig.Builder builder = new TakePhotoConfig.Builder();
//        builder.setMaxSize(1).
//                setCrop(false);
//        TakePhotoPopFragment.newInstance().setTakePhoto(getTakePhoto(), builder.create())
//                .setDismissListener(() -> clearPhotoClinet()).show
//                (getChildFragmentManager(), TakePhotoPopFragment.TAG);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (mUploadMessage == null && mUploadCallbackAboveL == null) {
            return;
        }
        Uri uri = null;
        if (REQUEST_CODE == RESULT_CODE_PICK_FROM_ALBUM_ABOVE_LOLLILOP) {
            try {
                uri = Uri.fromFile(new File(result.getImage().getOriginalPath()));
                if (uri == null) {
                    mUploadCallbackAboveL.onReceiveValue(new Uri[]{});
                    mUploadCallbackAboveL = null;
                    return;
                }
                if (mUploadCallbackAboveL != null && uri != null) {
                    mUploadCallbackAboveL.onReceiveValue(new Uri[]{uri});
                    mUploadCallbackAboveL = null;
                }
            } catch (Exception e) {
                mUploadCallbackAboveL = null;
                e.printStackTrace();
            }
        } else if (REQUEST_CODE == RESULT_CODE_PICK_FROM_ALBUM_BELLOW_LOLLILOP) {
            uri = Uri.fromFile(new File(result.getImage().getOriginalPath()));
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(uri);
                mUploadMessage = null;
            }
        }

    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        clearPhotoClinet();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        clearPhotoClinet();
    }

    private void clearPhotoClinet() {
        if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
        }
        if (mUploadCallbackAboveL != null) {
            mUploadCallbackAboveL.onReceiveValue(null);
            mUploadCallbackAboveL = null;
        }
    }

    /**
     * 内部类
     */
    class MyWebChromeClient extends WebChromeClient {
        //openFileChooser（隐藏方法）仅适用android5.0以下的环境，android5.0及以上使用onShowFileChooser

        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType) {
            if (mUploadMessage != null)
                return;
            mUploadMessage = uploadMsg;
            selectImage(RESULT_CODE_PICK_FROM_ALBUM_BELLOW_LOLLILOP);
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "");
        }

        // For Android > 4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType, String capture) {
            openFileChooser(uploadMsg, acceptType);
        }

        // For Android 5.0+
        public boolean onShowFileChooser(WebView webView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            selectImage(RESULT_CODE_PICK_FROM_ALBUM_ABOVE_LOLLILOP);
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
//                LoadingDialog.cancelDialogForLoading();
            }
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            return true;
        }
    }
}
