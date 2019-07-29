package com.mark.sleevecoach.EatFeel;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mark.sleevecoach.Global.GloblConst;
import com.mark.sleevecoach.R;
import com.mark.sleevecoach.model.DataManager;
import com.mark.sleevecoach.model.EatFeel;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.ArrayList;

/**
 * Created by user1 on 4/7/2017.
 */
public class AllEntriesActivity extends AppCompatActivity implements
        SlideAndDragListView.OnDragListener, SlideAndDragListView.OnSlideListener,
        SlideAndDragListView.OnMenuItemClickListener,
        SlideAndDragListView.OnItemDeleteListener {

    private Menu mMenu;
    ArrayList<EatFeel> arrEatFeel;
    SlideAndDragListView<EatFeel> mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allentries);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initData();
        initMenu();
        initUiAndListener();
    }


    public void initData() {
        arrEatFeel = (ArrayList)DataManager.shared.getEatFeelFrom(GloblConst.get60daysAgo());
    }

    public void initMenu() {
        mMenu = new Menu(true, true);
        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width) )
                .setBackground(new ColorDrawable(getResources().getColor(R.color.red)))
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setText("Delete")
                .setTextColor(Color.GRAY)
                .setTextSize(14)
                .build());
        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width))
                .setBackground(new ColorDrawable(Color.GREEN))
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setText("Edit")
                .setTextColor(Color.BLACK)
                .setTextSize((14))
                .build());
    }

    public void initUiAndListener() {
        mListView = (SlideAndDragListView) findViewById(R.id.allentires_slv);
        mListView.setMenu(mMenu);
        mListView.setAdapter(mAdapter);
        mListView.setOnDragListener(this, arrEatFeel);
        mListView.setOnSlideListener(this);
        mListView.setOnMenuItemClickListener(this);
        mListView.setOnItemDeleteListener(this);

    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return arrEatFeel.size();
        }

        @Override
        public Object getItem(int position) {
            return arrEatFeel.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CustomViewHolder cvh;
            if (convertView == null) {
                cvh = new CustomViewHolder();
                convertView = LayoutInflater.from(AllEntriesActivity.this).inflate(R.layout.allentries_entry_row, null);
                cvh.txtName = (TextView) convertView.findViewById(R.id.entry_row_content);
                convertView.setTag(cvh);
            } else {
                cvh = (CustomViewHolder) convertView.getTag();
            }

            EatFeel _eatFeel = arrEatFeel.get(position);
            String _strCont = String.format("%s / %s - %s",_eatFeel.eating,_eatFeel.feeling,GloblConst.getDateHourString(_eatFeel.date));
            cvh.txtName.setText(_strCont);
            return convertView;
        }

        class CustomViewHolder {
            public TextView txtName;

        }
    };

    @Override
    public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
        //Log.i(TAG, "onMenuItemClick   " + itemPosition + "   " + buttonPosition + "   " + direction);

        switch (buttonPosition) {
            case 1:
                return Menu.ITEM_SCROLL_BACK;
            case 0:
                return Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
        }
        return Menu.ITEM_NOTHING;
    }

    @Override
    public void onItemDelete(View view, int position) {
        EatFeel _objEatFeel = arrEatFeel.get(position);
        _objEatFeel.delete();
        arrEatFeel.remove(position - mListView.getHeaderViewsCount());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDragViewStart(int position) {

    }

    @Override
    public void onDragViewMoving(int position) {

    }

    @Override
    public void onDragViewDown(int position) {

    }

    @Override
    public void onSlideOpen(View view, View parentView, int position, int direction) {

    }

    @Override
    public void onSlideClose(View view, View parentView, int position, int direction) {

    }
}
