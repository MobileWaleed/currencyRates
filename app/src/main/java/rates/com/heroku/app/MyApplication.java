package rates.com.heroku.app;

import android.app.Application;
import android.content.Context;

import rates.com.heroku.di.ApiComponent;
import rates.com.heroku.di.*;

/**
 * Created by Waleed on 6/10/2018.
 */

public class MyApplication extends Application {

    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule(Const.BASE_URL))
                .build();
    }

    public ApiComponent getNetComponent() {
        return mApiComponent;
    }
}