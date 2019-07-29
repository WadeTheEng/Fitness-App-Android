package com.mark.sleevecoach;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.bigkoo.pickerview.OptionsPickerView;
import com.mark.sleevecoach.EatFeel.EatFeelFragment;
import com.mark.sleevecoach.Global.MenuCase;
import com.mark.sleevecoach.Goal.GoalFragment;
import com.mark.sleevecoach.Helps.FiveDayResetFragment;
import com.mark.sleevecoach.Helps.TutorialFragment;
import com.mark.sleevecoach.History.HistoryFragment;
import com.mark.sleevecoach.Home.HomeFragment;
import com.mark.sleevecoach.Intakes.MainIntakesFragment;
import com.mark.sleevecoach.Notification.NotifyManager;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private MenuCase mCurMenu = MenuCase.VT_HOME;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    public ActionListner _actionListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //JodaTimeAndroid.init(this);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new HomeFragment())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotifyManager.shared.onNotifybecomeActive();
    }

    public void selectMenuChanged(int position){
        mNavigationDrawerFragment.selectMenuChanged(position);
    }

    public void gotoView(MenuCase position){
        selectMenuChanged(position.getValue());
        Fragment _fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        mTitle = getString(R.string.app_name);
        mCurMenu = position;
        switch (position){
            case VT_HOME:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new HomeFragment())
                        .commit();
                break;
            case VT_GOAL:
                mTitle = getString(R.string.mt_goalsetting);
                _fragment = new GoalFragment();
                _actionListner = (GoalFragment)_fragment;
                fragmentManager.beginTransaction()
                        .replace(R.id.container, _fragment)
                        .commit();
                break;
            case VT_PROTEIN:
            case VT_WATER:
            case VT_EXERCISE:
            case VT_VITAMINS:
            case VT_WEIGHT:
                _fragment = new MainIntakesFragment();
                Bundle args = new Bundle();
                args.putInt("SubMenu", position.getValue());
                _fragment.setArguments(args);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, _fragment)
                        .commit();
                break;
            case VT_TUTOR:
                _fragment = new TutorialFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container,_fragment)
                        .commit();
                break;
            case VT_5DAYRST:
                _fragment = new FiveDayResetFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, _fragment)
                        .commit();
                break;
            case VT_EATFELL:
                mTitle = getString(R.string.mt_eatfeeling);
                _fragment = new EatFeelFragment();
                _actionListner = (EatFeelFragment)_fragment;
                fragmentManager.beginTransaction()
                        .replace(R.id.container, _fragment)
                        .commit();
                break;
            case VT_HISTORY:
                mTitle = getString(R.string.mt_history);
                _fragment = new HistoryFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, _fragment)
                        .commit();
                break;
            default:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position.getValue() + 1))
                        .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mTitle);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        gotoView(MenuCase.getMenuCase(position));

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mCurMenu == MenuCase.VT_GOAL){
            if (!mNavigationDrawerFragment.isDrawerOpen()) {
                // Only show items in the action bar relevant to this screen
                // if the drawer is not showing. Otherwise, let the drawer
                // decide what to show in the action bar.
                getMenuInflater().inflate(R.menu.goal_menu, menu);
                restoreActionBar();
                return true;
            }
        }
        if(mCurMenu == MenuCase.VT_EATFELL){
            if (!mNavigationDrawerFragment.isDrawerOpen()) {
                // Only show items in the action bar relevant to this screen
                // if the drawer is not showing. Otherwise, let the drawer
                // decide what to show in the action bar.
                getMenuInflater().inflate(R.menu.eatfeel_menu, menu);
                restoreActionBar();
                return true;
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in Android0Manifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.goal_act_save) {
            _actionListner.onMenuAction();
            return true;
        }
        if (id == R.id.eatfeel_act_all) {
            _actionListner.onMenuAction();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        OptionsPickerView pvOptions;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);

            return rootView;
        }



        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainMenuActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
