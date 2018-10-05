package rates.com.heroku.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rates.com.heroku.R;
import rates.com.heroku.network.model.Rate;
import rates.com.heroku.utils.AppUtil;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.MyViewHolder> {
 
    private Context context;
    private List<Rate> prevRateList;
    private List<Rate> rateList;
 
    public class MyViewHolder extends RecyclerView.ViewHolder {
 
        @BindView(R.id.symbol)
        TextView symbol;

        @BindView(R.id.price)
        TextView price;
 
        @BindView(R.id.arrow)
        ImageView arrow;

 
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
 
 
    public RatesAdapter(Context context, List<Rate> rateList,List<Rate> prevRateList) {
        this.context = context;
        this.rateList = rateList;
        this.prevRateList = prevRateList;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rate_item, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Rate rateItem = rateList.get(position);
        Rate prevItem = prevRateList.get(position);
        holder.symbol.setText(rateItem.getSymbol());



        holder.price.setText(AppUtil.formatDoubleToGivenDecimals(rateItem.getPrice()));
        if(rateItem.getPrice()>=prevItem.getPrice())
        {
            holder.arrow.setColorFilter(ContextCompat.getColor(context, R.color.rate_increase), PorterDuff.Mode.SRC_IN);
            holder.arrow.setImageResource(R.drawable.ic_trending_up_black_24dp);
            holder.price.setTextColor(context.getResources().getColor(R.color.rate_increase));

        }else
        {
            holder.arrow.setColorFilter(ContextCompat.getColor(context, R.color.rate_decrease), PorterDuff.Mode.SRC_IN);
            holder.arrow.setImageResource(R.drawable.ic_trending_down_black_24dp);
            holder.price.setTextColor(context.getResources().getColor(R.color.rate_decrease));
        }

    }
 
    @Override
    public int getItemCount() {
        return rateList.size();
    }
 
    public List<Rate> getPrevList()
    {
       return rateList;
    }
}