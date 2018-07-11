package com.md.View.fragments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.md.splashloginsignup.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FreeActionListViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> datas = new ArrayList<String>();

    public FreeActionListViewAdapter(Context context) {
        this.context = context;
    }

    /** 添加item数据 */
    public void addData(String text) {
        if (datas != null)
            datas.add(text);// 添加数据
    }

    public void addData(Map items) {
        for(Object item : items.keySet()){
            String key =(String) item;
            String data = (String) items.get(key);
            if (datas != null)
                datas.add(key+"        "+data);// 添加数据
        }

    }

    /** 移除item数据 */
    public void delData() {
        if (datas != null && datas.size() > 0)
            datas.remove(datas.size() - 1);// 移除最后一条数据
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (datas == null)
            return 0;
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /**
     * listview要判断item的位置，第一条，最后一条和中间的item是不一样的。
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        // 判断datas数据条数是否>1
        if (datas.size() > 1) {
            // 判断位置 顶部、中间、底部
            if (position == 0) {
                // 第一条数据 位置在顶部
                convertView = View.inflate(context, R.layout.list_item_top,null);
            } else if (position == datas.size() - 1) {
                // 底部位置的数据
                convertView = View.inflate(context, R.layout.list_item_bottom,null);
            } else {
                // 中间位置的数据
                convertView = View.inflate(context, R.layout.list_item_middle,null);
            }
        } else {
            // 只有一条数据
            convertView = View.inflate(context, R.layout.list_item_single, null);
        }
        String text = datas.get(position);
        // 设置文本
        ((TextView) convertView.findViewById(R.id.title)).setText(text);
        return convertView;
    }

}
