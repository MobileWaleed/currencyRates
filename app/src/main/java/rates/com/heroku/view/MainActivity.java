package rates.com.heroku.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rates.com.heroku.R;
import rates.com.heroku.adapters.RatesAdapter;
import rates.com.heroku.app.MyApplication;
import rates.com.heroku.network.ApiService;
import rates.com.heroku.network.model.Rate;
import rates.com.heroku.network.model.RateResult;
import rates.com.heroku.utils.MyDividerItemDecoration;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements MainViewInterface,SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rates)
    RecyclerView rates;
    @BindView(R.id.txt_empty_rates_view)
    TextView noRatesView;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    RatesAdapter adapter;
    MainPresenter mainPresenter;
    private String TAG = "MainActivity";
    @Inject
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MyApplication) getApplication()).getNetComponent().inject(this);
        ButterKnife.bind(this);
        setupRecyclerView();
        setupMVP();
        setupSwipeRefreshLayout();


    }
    private void setupSwipeRefreshLayout()
    {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                getRateList(true);
            }
        });
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
    private void getRateList(Boolean showRefreshing) {

        mainPresenter.getRates(showRefreshing);

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
    public void toggleEmptyRates(List<Rate> rates) {
        if (rates.size() > 0) {
            noRatesView.setVisibility(View.GONE);
        } else {
            noRatesView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        getRateList(false);
    }
}
