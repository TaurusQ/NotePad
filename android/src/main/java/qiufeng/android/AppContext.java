package qiufeng.android;

import android.app.Activity;
import android.app.Application;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import static qiufeng.android.utils.LogUtils.makeLogTag;

/**
 * Created by Administrator on 2016/2/28.
 */
public class AppContext extends com.activeandroid.app.Application {
    private static final String TAG = makeLogTag(AppContext.class);
    private static HashMap<String, WeakReference<Activity>> mContexts = new HashMap<String, WeakReference<Activity>>();
    private static AppContext mInstance;
    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static synchronized void setActiveContext(Activity context) {
        WeakReference<Activity> reference = new WeakReference<Activity>(context);
        mContexts.put(context.getClass().getSimpleName(), reference);
    }

    public static synchronized Activity getActiveContext(String className) {
        WeakReference<Activity> reference = mContexts.get(className);
        if (reference == null) {
            return null;
        }

        final Activity context = reference.get();

        if (context == null) {
            mContexts.remove(className);
        }
        return context;
    }

    public static HashMap<String, WeakReference<Activity>> getmContexts() {
        return mContexts;
    }

    public static AppContext getInstance(){
        return mInstance;
    }
}
