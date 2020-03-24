package lzw.musicol.core;

import java.util.prefs.Preferences;

public class Setting {
    public static String getApi(){
        Preferences preferences = Preferences.userRoot();
        if (preferences.get("api", "").equals("")) preferences.put("api", "http://www.musictool.top/");
        return preferences.get("api", "http://www.musictool.top/");
    }
    public static void setApi(String api){
        Preferences preferences = Preferences.userRoot();
        preferences.put("api", api);
    }
}
