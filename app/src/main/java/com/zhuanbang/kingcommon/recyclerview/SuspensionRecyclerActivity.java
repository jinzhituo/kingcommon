package com.zhuanbang.kingcommon.recyclerview;

import android.os.Bundle;
import android.widget.Toast;

import com.zhuanbang.kingcommon.R;
import com.zhuanbang.kingcommonlib.widget.section.SectionDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SuspensionRecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspension_recycler);
        recyclerView = findViewById(R.id.recyclerview);
        List<SuspensionSectionBean> list = new ArrayList<>();
        list.add(new SuspensionSectionBean("1111", "第一类", new SuspensionSectionBean.SuspensionSectionTagBean("第一类")));
        list.add(new SuspensionSectionBean("2222", "第二类", new SuspensionSectionBean.SuspensionSectionTagBean("第二类")));
        list.add(new SuspensionSectionBean("2222", "第二类", new SuspensionSectionBean.SuspensionSectionTagBean("第二类")));
        list.add(new SuspensionSectionBean("2222", "第二类", new SuspensionSectionBean.SuspensionSectionTagBean("第二类")));
        list.add(new SuspensionSectionBean("2222", "第二类", new SuspensionSectionBean.SuspensionSectionTagBean("第二类")));
        list.add(new SuspensionSectionBean("2222", "第二类", new SuspensionSectionBean.SuspensionSectionTagBean("第二类")));
        list.add(new SuspensionSectionBean("3333", "第三类", new SuspensionSectionBean.SuspensionSectionTagBean("第三类")));
        list.add(new SuspensionSectionBean("3333", "第三类", new SuspensionSectionBean.SuspensionSectionTagBean("第三类")));
        list.add(new SuspensionSectionBean("3333", "第三类", new SuspensionSectionBean.SuspensionSectionTagBean("第三类")));
        list.add(new SuspensionSectionBean("3333", "第三类", new SuspensionSectionBean.SuspensionSectionTagBean("第三类")));
        list.add(new SuspensionSectionBean("3333", "第三类", new SuspensionSectionBean.SuspensionSectionTagBean("第三类")));
        list.add(new SuspensionSectionBean("3333", "第三类", new SuspensionSectionBean.SuspensionSectionTagBean("第三类")));
        list.add(new SuspensionSectionBean("3333", "第三类", new SuspensionSectionBean.SuspensionSectionTagBean("第三类")));
        list.add(new SuspensionSectionBean("3333", "第三类", new SuspensionSectionBean.SuspensionSectionTagBean("第三类")));
        list.add(new SuspensionSectionBean("3333", "第三类", new SuspensionSectionBean.SuspensionSectionTagBean("第三类")));
        list.add(new SuspensionSectionBean("3333", "第三类", new SuspensionSectionBean.SuspensionSectionTagBean("第三类")));
        list.add(new SuspensionSectionBean("3333", "第三类", new SuspensionSectionBean.SuspensionSectionTagBean("第三类")));
        list.add(new SuspensionSectionBean("4444", "第四类", new SuspensionSectionBean.SuspensionSectionTagBean("第四类")));
        list.add(new SuspensionSectionBean("4444", "第四类", new SuspensionSectionBean.SuspensionSectionTagBean("第四类")));
        list.add(new SuspensionSectionBean("4444", "第四类", new SuspensionSectionBean.SuspensionSectionTagBean("第四类")));
        list.add(new SuspensionSectionBean("4444", "第四类", new SuspensionSectionBean.SuspensionSectionTagBean("第四类")));
        list.add(new SuspensionSectionBean("4444", "第四类", new SuspensionSectionBean.SuspensionSectionTagBean("第四类")));
        SuspensionAdapter adapter = new SuspensionAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SectionDecoration<>(list).setSectionConvert(new SuspensionSection()).setSectionClickListener(position -> {
            Toast.makeText(SuspensionRecyclerActivity.this, list.get(position).subtitle, Toast.LENGTH_SHORT).show();
        }));
    }
}
