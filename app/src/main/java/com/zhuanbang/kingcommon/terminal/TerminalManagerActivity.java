package com.zhuanbang.kingcommon.terminal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhuanbang.kingcommon.R;
import com.zhuanbang.kingcommon.terminal.contract.TerminalContract;
import com.zhuanbang.kingcommon.terminal.presenter.TerminalPresenter;
import com.zhuanbang.kingcommonlib.base.BaseActivity;
import com.zhuanbang.kingcommonlib.dagger.component.AppComponent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class TerminalManagerActivity extends BaseActivity<TerminalPresenter> implements TerminalContract.View,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


//    @BindView(R.id.title_bar)
//    NormalTitleBar mTitleBar;
//    @BindView(R.id.recycler_view)
//    RecyclerView mRecyclerView;
//    @BindView(R.id.refresh_layout)
//    SwipeRefreshLayout mRefreshLayout;

//    private List<PosTerminal> mPosTerminalList = new ArrayList<>();
    private int pageNo = 1;
//    private TerminalAdapter mTerminalAdapter;
    View mEmptyView;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, TerminalManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onReturnTerminalList(List<PosTerminal> posTerminalList) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        mPresenter.getTerminalList(pageNo);
    }

    @Override
    public void onLoadMoreRequested() {
        pageNo++;
        mPresenter.getTerminalList(pageNo);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTerminalComponent.builder().appComponent(appComponent).terminalModule(new TerminalModule(this)).build()
                .inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_terminal_manager;
    }


    @Override
    public void initView() {
//        mTitleBar.setTitleText("设备管理");
//        mRefreshLayout.setOnRefreshListener(this);
//        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(),
//                false);
//        mTerminalAdapter = new TerminalAdapter(mPosTerminalList);
//        mTerminalAdapter.setPreLoadNumber(9);
//        mTerminalAdapter.setEnableLoadMore(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayout.HORIZONTAL, 1, mContext
//                .getResources().getColor(R.color.color_line)));
//        mRecyclerView.setAdapter(mTerminalAdapter);
//        mTerminalAdapter.setEmptyView(mEmptyView);
//
//        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                TerminalDetailActivity.startAction(TerminalManagerActivity.this, mTerminalAdapter.getItem(position)
//                        .getPosTerminalId());
//            }
//
//        });

        mPresenter.getTerminalList(pageNo);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void showErrorTip(int code, String message) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void stopRefresh() {

    }

    @Override
    public Context getCurrentContext() {
        return this;
    }
}
