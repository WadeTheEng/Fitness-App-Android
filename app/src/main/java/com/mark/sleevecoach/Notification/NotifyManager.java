package com.mark.sleevecoach.Notification;

/**
 * Created by user1 on 4/8/2017.
 */
public class NotifyManager {

    public static NotifyManager shared = new NotifyManager();

    NotifyManager(){


    }

    public void onNotifybecomeActive(){
        onNotifyChgExercise();
        onNotifyChgProtein();
        onNotifyChgWater();
        onNotifyChgVitamins();
    }

    public void onNotifyChgExercise(){

    }

    public void onNotifyChgProtein(){

    }

    public void onNotifyChgWater(){

    }

    public void onNotifyChgVitamins(){

    }


}
