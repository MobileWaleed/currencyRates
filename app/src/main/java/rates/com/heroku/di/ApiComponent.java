package rates.com.heroku.di;

import javax.inject.Singleton;

import dagger.Component;
import rates.com.heroku.view.MainActivity;
import rates.com.heroku.view.MainPresenter;

/**
 * Created by Waleed on 6/10/2018.
 */

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface ApiComponent {
    void inject(MainActivity activity);
}
