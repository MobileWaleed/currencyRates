package rates.com.heroku.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import rates.com.heroku.R;
import rates.com.heroku.adapters.RatesAdapter;
import rates.com.heroku.network.ApiClient;
import rates.com.heroku.network.ApiService;
import rates.com.heroku.network.model.Rate;
import rates.com.heroku.network.model.RateResult;
import rates.com.heroku.utils.MyDividerItemDecoration;


public class MainActivity extends AppCompatActivity implements MainViewInterface {
    @BindView(R.id.rates)
    RecyclerView rates;
    @BindView(R.id.txt_empty_rates_view)
    TextView noRatesView;
    RatesAdapter adapter;
    MainPresenter mainPresenter;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupRecyclerView();
        setupMVP();
        getRateList();

    }
    private void setupRecyclerView()
    {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rates.setLayoutManager(mLayoutManager);
        rates.setItemAnimator(new DefaultItemAnimator());
        rates.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
    }
    private void setupMVP() {
        mainPresenter = new MainPresenter(this);
    }
    private void getRateList() {

        mainPresenter.getRates();

    }
    @Override
    public void showToast(String str) {
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
    }


    @Override
    public void displayRates(RateResult ratesResponse) {
        if(ratesResponse!=null) {
            if(adapter==null) {
                adapter = new RatesAdapter(MainActivity.this, ratesResponse.getRates(), ratesResponse.getRates());
            }else
            {
                adapter = new RatesAdapter(MainActivity.this, ratesResponse.getRates(), adapter.getPrevList());
            }
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    rates.setAdapter(adapter);

                }
            });

        }else{
            Log.d(TAG,"Rates response null");
        }
    }

    @Override
    public void displayError(String s) {

    }

    @Override
    public void toggleEmptyNotes(List<Rate> rates) {
        if (rates.size() > 0) {
            noRatesView.setVisibility(View.GONE);
        } else {
            noRatesView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public ApiService getApiService() {
        return ApiClient.getClient(MainActivity.this)
                .create(ApiService.class);
    }
}
