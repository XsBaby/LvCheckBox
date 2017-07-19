package com.xushuai.lc;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.xushuai.lc.ListAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {

    // 添加
    private TextView tv_add;
    // 删除和全选
    private Button btn_detele, btn_select_all;
    // 列表
    private ListView listview;
    // 适配器
    private ListAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActionBar().hide();
        setContentView(R.layout.activity_main);

        // 初始化控件
        initView();
        // 初始化数据
        initData();
    }

    private void initData() {
        // 默认显示的数据
        for (int i = 1; i < 101; i++) {
            list.add("第" + i + "条数据");
        }

        adapter = new ListAdapter(this,list);
        listview.setAdapter(adapter);
    }

    private void initView() {
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_add.setOnClickListener(this);
        btn_detele = (Button) findViewById(R.id.btn_detele);
        btn_detele.setOnClickListener(this);
        btn_select_all = (Button) findViewById(R.id.btn_select_all);
        btn_select_all.setOnClickListener(this);
        listview = (ListView) findViewById(R.id.listview);
    }

    // 点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 添加数据
            case R.id.tv_add:
//                adapter.addData(new Bean("刘桂林"));
                for (int i = 1; i < 11; i++) {
                    list.add("第" + i + "条数据");
                }
                // 通知刷新适配器
                adapter.notifyDataSetChanged();
                break;
            // 全选数据
            case R.id.btn_select_all:
                // 全选——全不选
                Map<Integer, Boolean> isCheck = adapter.getMap();
                if (btn_select_all.getText().equals("全选")) {
                    adapter.initCheck(true);
                    // 通知刷新适配器
                    adapter.notifyDataSetChanged();
                    btn_select_all.setText("全不选");
                    btn_select_all.setTextColor(Color.YELLOW);
                } else if (btn_select_all.getText().equals("全不选")) {
                    adapter.initCheck(false);
                    // 通知刷新适配器
                    adapter.notifyDataSetChanged();
                    btn_select_all.setText("全选");
                    btn_select_all.setTextColor(Color.YELLOW);
                }
                break;
            // 删除数据
            case R.id.btn_detele:
                // 拿到所有数据
                Map<Integer, Boolean> isCheck_delete = adapter.getMap();
                // 获取到条目数量，map.size = list.size,所以
                int count = adapter.getCount();
                // 遍历
                for (int i = 0; i < count; i++) {
                    // 删除有两个map和list都要删除 ,计算方式
                    int position = i - (count - adapter.getCount());
                    // 判断状态 true为删除
                    if (isCheck_delete.get(i) != null && isCheck_delete.get(i)) {
                        // listview删除数据
                        isCheck_delete.remove(i);
                        adapter.removeData(position);
                    }
                }
                btn_select_all.setText("全选");
                btn_select_all.setTextColor(Color.WHITE);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    // listview的点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // 判断view是否相同
        if (view.getTag() instanceof ViewHolder) {
            // 如果是的话，重用
            ViewHolder holder = (ViewHolder) view.getTag();
            // 自动触发
            holder.cbCheckBox.toggle();
        }
    }
}
