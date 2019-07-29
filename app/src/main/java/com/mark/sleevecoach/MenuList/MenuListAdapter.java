package com.mark.sleevecoach.MenuList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mark.sleevecoach.R;

import java.util.List;


/**
 * Created by user1 on 2/7/2017.
 */
public class MenuListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<MenuData> arrMenuItems;
    public int nSelectedMenu = 0;

    public MenuListAdapter(Context context, List<MenuData> aMenuItems){
        this.mContext = context;
        this.arrMenuItems = aMenuItems;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return arrMenuItems.size();
    }

    @Override
    public Object getItem(int location) {
        return arrMenuItems.get(location);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = inflater.inflate(R.layout.menu_low,null);
        }

        final MenuData _data = arrMenuItems.get(position);


        ImageView _ivIcon = (ImageView)convertView.findViewById(R.id.menu_icon);
        TextView  _txtTitle = (TextView)convertView.findViewById(R.id.menu_title);
        _txtTitle.setText(_data.strTitle);
        _ivIcon.setImageResource(_data.idResIcon);
        if(position == nSelectedMenu)
            _txtTitle.setTextColor(mContext.getResources().getColor(R.color.orange));
        else
            _txtTitle.setTextColor(mContext.getResources().getColor(R.color.white));
        return convertView;
    }


}
