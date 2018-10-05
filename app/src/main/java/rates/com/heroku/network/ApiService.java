package rates.com.heroku.network;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Timed;
import rates.com.heroku.network.model.RateResult;
import retrofit2.http.GET;

public interface ApiService {
    // Fetch all notes
    @GET("rates")
    Observable<RateResult>fetchAllRates();

}