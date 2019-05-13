package com.zhuanbang.kingcommon.xpopup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.zhuanbang.kingcommon.R;
import com.zhuanbang.kingcommonlib.base.BaseApplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Description:
 * Create by lxj, at 2019/2/2
 */
public class DemoActivity extends AppCompatActivity {
    EditText editText;
    public static void startAction(Context context) {
        Intent intent = new Intent(context, DemoActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        editText = findViewById(R.id.et);
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMultiPopup();
            }
        });
        showMultiPopup();

        final BasePopupView popupView = new XPopup.Builder(this)
                .atView(editText)
                .isRequestFocus(false)
                .hasShadowBg(false)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .asAttachList(new String[]{"联想到的内容 - 1", "联想到的内容 - 2", "联想到的内容 - 333"}, null, new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        Toast.makeText(BaseApplication.getAppContext(), text, Toast.LENGTH_LONG).show();
                    }
                });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    popupView.dismiss();
                    return;
                }
                if (popupView.isDismiss()) {
                    popupView.show();
                }
            }
        });
    }

    public void showMultiPopup() {
        final BasePopupView loadingPopup = new XPopup.Builder(this).asLoading();
        loadingPopup.show();
        new XPopup.Builder(DemoActivity.this)
                .autoDismiss(false)
                .asBottomList("haha", new String[]{"点我显示弹窗", "点我显示弹窗", "点我显示弹窗", "点我显示弹窗"}, new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        new XPopup.Builder(DemoActivity.this).asConfirm("测试", "aaaa", new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                loadingPopup.dismiss();
                            }
                        }).show();

                    }
                }).show();


    }
}
