package com.sty.object.box.app;

import android.app.Application;

import com.sty.object.box.BuildConfig;
import com.sty.object.box.model.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * Created by tian on 2018/7/2.
 */

public class MyApplication extends Application {
    private static MyApplication mApp;
    private static BoxStore mBoxStore;

    @Override
    public void onCreate() {
        super.onCreate();

        mApp = this;
        //Init ObjectBox
        mBoxStore = MyObjectBox.builder().androidContext(mApp).build();
        if(BuildConfig.DEBUG){
            new AndroidObjectBrowser(mBoxStore).start(mApp);
        }
    }

    public static MyApplication getmApp(){
        return mApp;
    }

    public static BoxStore getmBoxStore(){
        return mBoxStore;
    }
}
