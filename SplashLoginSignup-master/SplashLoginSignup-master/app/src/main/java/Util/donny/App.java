package Util.donny;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by donny on 5/24/18.
 */

public class App extends Application {

    private static final String sStorageDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FitnessApp/";

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = App.this;
    }

    public static Context getContext() {
        return context;
    }

    public static String getStorageDir() {
        File storageDir = new File(sStorageDir);
        if (!storageDir.exists()) storageDir.mkdirs();
        return sStorageDir;
    }

}
