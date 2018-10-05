package rates.com.heroku.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RateResult {

@SerializedName("rates")
@Expose
private List<Rate> rates = null;

public List<Rate> getRates() {
return rates;
}

public void setRates(List<Rate> rates) {
this.rates = rates;
}

}