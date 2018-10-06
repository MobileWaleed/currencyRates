package rates.com.heroku.view;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import rates.com.heroku.network.ApiService;
import rates.com.heroku.network.model.RateResult;

public class MainPresenter implements MainPresenterInterface {

    MainViewInterface mvi;
    private String TAG = "MainPresenter";

    public MainPresenter(MainViewInterface mvi) {
        this.mvi = mvi;
    }

    @Override
    public void getRates(Boolean showRefreshing) {
        if(showRefreshing) {
            mvi.showWait();
        }
        getObservable().repeatWhen(completed -> completed.delay(10, TimeUnit.SECONDS)).subscribeWith(getObserver(showRefreshing));
    }

    public Observable<RateResult> getObservable(){
        ApiService apiService = mvi.getApiService();
        return apiService.fetchAllRates().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<RateResult> getObserver(Boolean showRefreshing){
        return new DisposableObserver<RateResult>() {

            @Override
            public void onNext(@NonNull RateResult ratesResponse) {
                    if(showRefreshing) {
                        mvi.removeWait();
                    }else
                    {
                        mvi.stopRefreshing();
                    }
                    mvi.toggleEmptyRates(ratesResponse.getRates());
                    mvi.displayRates(ratesResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mvi.removeWait();
                e.printStackTrace();
                mvi.displayError("Error fetching Rates Data");
            }

            @Override
            public void onComplete() {

                Log.d("rxjava","Completed");
            }
        };
    }
}