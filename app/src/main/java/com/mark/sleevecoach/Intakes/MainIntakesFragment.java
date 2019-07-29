package com.mark.sleevecoach.Intakes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mark.sleevecoach.Global.MenuCase;
import com.mark.sleevecoach.Home.HomeFragment;
import com.mark.sleevecoach.MainMenuActivity;
import com.mark.sleevecoach.R;

/**
 * Created by user1 on 3/30/2017.
 */
public class MainIntakesFragment  extends Fragment implements RadioGroup.OnCheckedChangeListener {

    MenuCase mcCurItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_intakes, container, false);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        RadioGroup _segment = (RadioGroup)getActivity().findViewById(R.id.mainintakes_segment);
        _segment.setOnCheckedChangeListener(this);
        int _nPos = getArguments().getInt("SubMenu");
        mcCurItem = MenuCase.getMenuCase(_nPos);

        RadioButton _btnItem;
        switch (mcCurItem) {
            case VT_PROTEIN:
                _btnItem = (RadioButton)getActivity().findViewById(R.id.mainintakes_radio_protein);
                _btnItem.setChecked(true);
                break;
            case VT_WATER:
                _btnItem = (RadioButton)getActivity().findViewById(R.id.mainintakes_radio_water);
                _btnItem.setChecked(true);
                break;
            case VT_EXERCISE:
                _btnItem = (RadioButton)getActivity().findViewById(R.id.mainintakes_radio_excercise);
                _btnItem.setChecked(true);
                break;
            case VT_VITAMINS:
                _btnItem = (RadioButton)getActivity().findViewById(R.id.mainintakes_radio_vitamins);
                _btnItem.setChecked(true);
                break;
            case VT_WEIGHT:
                _btnItem = (RadioButton)getActivity().findViewById(R.id.mainintakes_radio_weight);
                _btnItem.setChecked(true);
                break;
        }

        refreshContent();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.mainintakes_radio_protein:
                mcCurItem = MenuCase.VT_PROTEIN;
                break;
            case R.id.mainintakes_radio_water:
                mcCurItem = MenuCase.VT_WATER;
                break;
            case R.id.mainintakes_radio_excercise:
                mcCurItem = MenuCase.VT_EXERCISE;
                break;
            case R.id.mainintakes_radio_vitamins:
                mcCurItem = MenuCase.VT_VITAMINS;
                break;
            case R.id.mainintakes_radio_weight:
                mcCurItem = MenuCase.VT_WEIGHT;
                break;
        }
        MainMenuActivity _main = (MainMenuActivity)getActivity();
        _main.selectMenuChanged(mcCurItem.getValue());
        refreshContent();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    public void refreshContent(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment _fragment;
        switch (mcCurItem){
            case VT_PROTEIN:
            case VT_WATER:
            case VT_EXERCISE:
                _fragment = new CommonIntakeFragment();
                Bundle args = new Bundle();
                args.putInt("SubMenu", mcCurItem.getValue());
                _fragment.setArguments(args);
                fragmentManager.beginTransaction()
                        .replace(R.id.mainintakes_container, _fragment)
                        .commit();
                break;
            case VT_VITAMINS:
                _fragment = new VitaminsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainintakes_container, _fragment)
                        .commit();
                break;
            case VT_WEIGHT:
                _fragment = new WeightFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainintakes_container, _fragment)
                        .commit();
                break;
        }

    }
}