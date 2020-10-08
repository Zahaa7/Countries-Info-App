package ro.zaha.countriesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import ro.zaha.countriesapp.R;
import ro.zaha.countriesapp.model.CountryInfo;
import ro.zaha.countriesapp.network.DetailActivity;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.Holder> {

    Context context;
    List<CountryInfo> countryInfoList;

    public CountryAdapter(Context context, List<CountryInfo> countryInfoList) {
        this.context = context;
        this.countryInfoList = countryInfoList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.countries_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.Holder holder, int position) {
        String flag = countryInfoList.get(position).getFlag();
        String name = countryInfoList.get(position).getName();
        holder.countryName.setText(name);
        Glide.with(context).load(flag).into(holder.countryFlag);
    }

    @Override
    public int getItemCount() {
        return countryInfoList.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView countryFlag;
        TextView countryName;

        public Holder(@NonNull View itemView) {
            super(itemView);
            countryFlag = itemView.findViewById(R.id.countryFlag);
            countryName = itemView.findViewById(R.id.countryName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("countryName", countryInfoList.get(getAdapterPosition()).getName());
            context.startActivity(intent);
        }
    }
}
