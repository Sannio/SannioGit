package com.sannio.screen.utils;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.WindowManager;

/**
 * Created by wujingli on 2016/1/6.
 * brightness is in (0,255)
 */
public class ScreenBrightnessUtil {

    public static int getScreenBrightness(Context context){
        try {
            return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 如果是自动调整亮度则无法生效 需关闭自动调整
     * 若未保存则只对当前窗口有效
     * @param activity
     * @param brightness
     */
    public static void setBrightness(Activity activity, int brightness){
        boolean isAutoBrightness = isAutoBrightness(activity);
        if(isAutoBrightness){
            stopAutoBrightness(activity);
        }
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f/255f);
        activity.getWindow().setAttributes(lp);
        if(isAutoBrightness){
            startAutoBrightness(activity);
        }
    }
    public static boolean isAutoBrightness(Context context){
        try {
            return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void stopAutoBrightness(Context context){
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }
    public static void startAutoBrightness(Context context){
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

}
