package com.mark.sleevecoach.Home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mark.sleevecoach.Global.GloblConst;
import com.mark.sleevecoach.Global.MenuCase;
import com.mark.sleevecoach.MainMenuActivity;
import com.mark.sleevecoach.R;
import com.mark.sleevecoach.model.DataManager;
import com.mark.sleevecoach.model.InTake;

import java.util.ArrayList;

/**
 * Created by user1 on 3/27/2017.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        View _viewProtein = rootView.findViewById(R.id.home_linear_content_protein);
        _viewProtein.setOnClickListener(this);
        View _viewWater = rootView.findViewById(R.id.home_linear_content_water);
        _viewWater.setOnClickListener(this);
        View _viewExcercise = rootView.findViewById(R.id.home_linear_content_excercise);
        _viewExcercise.setOnClickListener(this);
        View _viewVitamins = rootView.findViewById(R.id.home_linear_content_vitamins);
        _viewVitamins.setOnClickListener(this);

        refreshUI(rootView);
        //DataManager.shared.getEatFeel();
        return rootView;
    }

    public void refreshUI(View rootView){

        ArrayList<ArrayList<Integer>> _arrSummaries = new ArrayList<>();
        ArrayList<Integer> _arrToday = new ArrayList<>();
        _arrToday.add(R.id.home_today_iv_1);
        _arrToday.add(R.id.home_today_iv_2);
        _arrToday.add(R.id.home_today_iv_3);
        _arrToday.add(R.id.home_today_iv_4);
        _arrToday.add(R.id.home_today_iv_5);
        _arrToday.add(R.id.home_today_iv_6);
        _arrToday.add(R.id.home_today_iv_7);
        _arrToday.add(R.id.home_today_iv_8);
        _arrToday.add(R.id.home_today_iv_9);
        _arrToday.add(R.id.home_today_iv_10);

        ArrayList<Integer> _arrYest = new ArrayList<>();
        _arrYest.add(R.id.home_yest_iv_1);
        _arrYest.add(R.id.home_yest_iv_2);
        _arrYest.add(R.id.home_yest_iv_3);
        _arrYest.add(R.id.home_yest_iv_4);
        _arrYest.add(R.id.home_yest_iv_5);
        _arrYest.add(R.id.home_yest_iv_6);
        _arrYest.add(R.id.home_yest_iv_7);
        _arrYest.add(R.id.home_yest_iv_8);
        _arrYest.add(R.id.home_yest_iv_9);
        _arrYest.add(R.id.home_yest_iv_10);
        _arrSummaries.add(_arrToday);
        _arrSummaries.add(_arrYest);

        InTake objITToday = DataManager.shared.getTodayIntake();
        InTake objITYesterday = DataManager.shared.getYesterdayIntake();
        ArrayList<InTake> _arrIntakes = new ArrayList<>();
        _arrIntakes.add(objITToday);
        _arrIntakes.add(objITYesterday);
        for(int i = 0; i < _arrIntakes.size(); i++){
            InTake _objIntake = _arrIntakes.get(i);
            int _nStars = _objIntake.getProteinEarnStarCount() + _objIntake.getWaterEarnStarCount()
            + _objIntake.getExerciseEarnStarCount() + _objIntake.getVitaminEarnStarCount();
            ArrayList<Integer> _arrImages = _arrSummaries.get(i);
            for(int j = 0; j < _arrImages.size(); j++ ){
                ImageView _imgView = (ImageView)rootView.findViewById(_arrImages.get(j));
                if( j < _nStars){
                    _imgView.setImageResource(R.drawable.icon_onestar_h);
                }
                else{
                    _imgView.setImageResource(R.drawable.icon_onestar_d);
                }
            }
        }

        int _todayStars[][] = {{R.id.home_prot_today_iv_onestar,R.id.home_prot_today_iv_twostar,R.id.home_prot_today_iv_tripstar},
                {R.id.home_water_today_iv_onestar,R.id.home_water_today_iv_twostar,R.id.home_water_today_iv_tripstar},
                {R.id.home_excercise_today_iv_onestar,R.id.home_excercise_today_iv_twostar,R.id.home_excercise_today_iv_tripstar}};

        int _yesterdayStars[][] ={{R.id.home_prot_yest_iv_onestar,R.id.home_prot_yest_iv_twostar,R.id.home_prot_yest_iv_tripstar},
                {R.id.home_water_yest_iv_onestar,R.id.home_water_yest_iv_twostar,R.id.home_water_yest_iv_tripstar},
                {R.id.home_excercise_yest_iv_onestar,R.id.home_excercise_yest_iv_twostar,R.id.home_excercise_yest_iv_tripstar}};

        int _titles[][] ={{R.id.home_txt_prottoday,R.id.home_txt_protyest},{R.id.home_txt_watertoday,R.id.home_txt_wateryest},
                {R.id.home_txt_excercisetoday,R.id.home_txt_excerciseyest}};

        int _progress[][] = {{R.id.home_prot_today_progress,R.id.home_prot_yest_progress},
                {R.id.home_water_today_progress,R.id.home_water_yest_progress},
                {R.id.home_excercise_today_progress,R.id.home_excercise_yest_progress}};

        TextView _txtToday,_txtYesterday;
        TextView _txtTodayProgress,_txtYestProgress;
        ImageView _ivTod60,_ivTod85,_ivTod100,_ivYest60,_ivYest85,_ivyest100;

        for(int i = 0; i < 3; i++){
            String _strUnit ="";

            int _nutITTodayValue = 0, _nutITYestValue = 0, _nutGoalValue = 0;
            double _dTodayProgress = 0,_dYestProgress = 0;

            switch (i) {
                case 0:
                {
                    _strUnit = GloblConst.getUnitString(MenuCase.VT_PROTEIN);
                    _nutITTodayValue    = objITToday.nITProtein;
                    _nutITYestValue    = objITYesterday.nITProtein;
                    _nutGoalValue     = objITToday.nGoalProtein;
                    _dTodayProgress     = objITToday.getProteinProgress();
                    _dYestProgress      = objITYesterday.getProteinProgress();
                }
                break;
                case 1:{
                    _strUnit = GloblConst.getUnitString(MenuCase.VT_WATER);
                    _nutITTodayValue    = objITToday.nITWater;
                    _nutITYestValue    = objITYesterday.nITWater;
                    _nutGoalValue     = objITToday.nGoalWater;
                    _dTodayProgress     = objITToday.getWaterProgress();
                    _dYestProgress      = objITYesterday.getWaterProgress();

                }
                break;
                case 2: //excercisse
                {
                    _strUnit = GloblConst.getUnitString(MenuCase.VT_EXERCISE);
                    _nutITTodayValue    = objITToday.nITExercise;
                    _nutITYestValue    = objITYesterday.nITExercise;
                    _nutGoalValue     =  objITToday.getExerciseGoal();
                    _dTodayProgress     = objITToday.getExerciseProgress();
                    _dYestProgress      = objITYesterday.getExerciseProgress();
                }
                break;
                default:
                    break;
            }
            _txtToday = (TextView)rootView.findViewById(_titles[i][0]);
            _txtToday.setText(String.format("Today : %d%s", _nutITTodayValue, _strUnit));

            _txtTodayProgress = (TextView)rootView.findViewById(_progress[i][0]);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                    _txtTodayProgress.getLayoutParams();
            params.weight = (float)_dTodayProgress;
            _txtTodayProgress.setLayoutParams(params);


            String _strSuf1 = "h",_strSuf2 = "h",_strSuf3 = "h";
            if (_dTodayProgress >=1.0) {
            }
            else if (_dTodayProgress >=.8) {
                _strSuf3 = "d";
            }
            else if (_dTodayProgress >=.65) {
                _strSuf2 = "d";
                _strSuf3 = "d";
            }
            else
            {
                _strSuf1 = "d";
                _strSuf2 = "d";
                _strSuf3 = "d";
            }
            _ivTod60 = (ImageView)rootView.findViewById(_todayStars[i][0]);
            _ivTod85 = (ImageView)rootView.findViewById(_todayStars[i][1]);
            _ivTod100 = (ImageView)rootView.findViewById(_todayStars[i][2]);

            _ivTod60.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_onestar_" + _strSuf1));
            _ivTod85.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_twostar_" + _strSuf2));
            _ivTod100.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_tripstar_" + _strSuf3));

            _txtYesterday = (TextView)rootView.findViewById(_titles[i][1]);
            _txtYesterday.setText(String.format("Yesterday : %d%s", _nutITYestValue, _strUnit));

            _txtYestProgress = (TextView)rootView.findViewById(_progress[i][1]);
            params = (LinearLayout.LayoutParams)
                    _txtYestProgress.getLayoutParams();
            params.weight = (float)_dYestProgress;
            _txtYestProgress.setLayoutParams(params);

            _strSuf1 = "h";
            _strSuf2 = "h";
            _strSuf3 = "h";

            if (_dYestProgress >=1) {

            }
            else if (_dYestProgress >=.8) {
                _strSuf3 = "d";
            }
            else if (_dYestProgress >=.65) {
                _strSuf2 = "d";
                _strSuf3 = "d";
            }
            else
            {
                _strSuf1 = "d";
                _strSuf2 = "d";
                _strSuf3 = "d";
            }

            _ivYest60 = (ImageView)rootView.findViewById(_yesterdayStars[i][0]);
            _ivYest85 = (ImageView)rootView.findViewById(_yesterdayStars[i][1]);
            _ivyest100 = (ImageView)rootView.findViewById(_yesterdayStars[i][2]);

            _ivYest60.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_onestar_" + _strSuf1));
            _ivYest85.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_twostar_" + _strSuf2));
            _ivyest100.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_tripstar_" + _strSuf3));

        }

        //vitamins
        ImageView _ivTodayVitamin,_ivYestVitamin;
        _ivTodayVitamin = (ImageView) rootView.findViewById(R.id.home_vitamins_today_iv_1);
        _ivYestVitamin = (ImageView) rootView.findViewById(R.id.home_vitamins_yest_iv_1);

        int _nTodayStars = objITToday.getVitaminEarnStarCount();
        if (_nTodayStars == 1)
            _ivTodayVitamin.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_onestar_h"));
        else
            _ivTodayVitamin.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_onestar_d"));
        int _nYestStars = objITYesterday.getVitaminEarnStarCount();
        if (_nYestStars == 1)
            _ivYestVitamin.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_onestar_h"));
        else
            _ivYestVitamin.setImageResource(GloblConst.getImageId(this.getActivity(), "icon_onestar_h"));

    }

    @Override
    public void onClick(View view) {
        MainMenuActivity _activity = (MainMenuActivity)getActivity();
        if(view.getId() == R.id.home_linear_content_protein){
            _activity.gotoView(MenuCase.VT_PROTEIN);
        }
        else if(view.getId() == R.id.home_linear_content_water){
            _activity.gotoView(MenuCase.VT_WATER);
        }
        else if(view.getId() == R.id.home_linear_content_excercise){
            _activity.gotoView(MenuCase.VT_EXERCISE);
        }
        else if(view.getId() == R.id.home_linear_content_vitamins){
            _activity.gotoView(MenuCase.VT_VITAMINS);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
}
