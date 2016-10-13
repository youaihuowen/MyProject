package com.example.classtest_fragment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.classtest_fragment.R;
import com.example.classtest_fragment.adapter.FragBNameAdapter;
import com.example.classtest_fragment.adapter.FragBTypeAdapter;
import com.example.classtest_fragment.entity.NameEntity;
import com.example.classtest_fragment.entity.TypeEntity;

import java.util.ArrayList;

/**
 * Created by WUYIXIONG on 2016-10-10
 * fragmentB
 */
public class FragmentB extends Fragment {

    private ListView lv_type;
    private ListView lv_name;

    ArrayList<TypeEntity> typeList = new ArrayList<TypeEntity>();
    ArrayList<NameEntity> nameList = new ArrayList<NameEntity>();

    FragBTypeAdapter adapter_type;
    FragBNameAdapter adapter_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_b, container, false);

        lv_type=(ListView)view.findViewById(R.id.lv_type);
        lv_name=(ListView)view.findViewById(R.id.lv_name);

        for (int i=0;i<5;i++){
            TypeEntity type=new TypeEntity();
            type.setType((i*10)+"-"+(i*10+9));
            typeList.add(type);
        }

        adapter_type=new FragBTypeAdapter(typeList,view.getContext());
        lv_type.setAdapter(adapter_type);

        details(0);
        adapter_name=new FragBNameAdapter(nameList,view.getContext());
        lv_name.setAdapter(adapter_name);
        setListener();

        return view;

    }

    private void setListener(){
        lv_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nameList.removeAll(nameList);
                details(position);
                adapter_name.notifyDataSetChanged();
            }
        });
    }

    private void details(int select){
        for (int i=0;i<10;i++){
            NameEntity name=new NameEntity();
            name.setName(select*10+i+"");
            nameList.add(name);
        }
    }
}
