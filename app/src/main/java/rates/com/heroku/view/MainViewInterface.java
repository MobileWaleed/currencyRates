package rates.com.heroku.view;

import java.util.List;

import rates.com.heroku.network.ApiService;
import rates.com.heroku.network.model.Rate;
import rates.com.heroku.network.model.RateResult;

public interface MainViewInterface {

    void showToast(String s);
    void displayRates(RateResult ratesResponse);
    void displayError(String s);
    void toggleEmptyNotes(List<Rate> rates);
    ApiService getApiService();
}