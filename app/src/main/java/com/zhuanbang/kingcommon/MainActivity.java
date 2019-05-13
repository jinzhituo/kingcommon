package com.zhuanbang.kingcommon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zhuanbang.kingcommon.recyclerview.SuspensionRecyclerActivity;
import com.zhuanbang.kingcommon.xpopup.DemoActivity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static void startAction(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_xpopup).setOnClickListener(v -> {
            DemoActivity.startAction(MainActivity.this);
        });
        findViewById(R.id.suspension_recycler).setOnClickListener(v -> {
            SuspensionRecyclerActivity.startAction(MainActivity.this);
        });
    }

}
