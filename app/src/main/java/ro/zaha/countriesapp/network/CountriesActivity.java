package ro.zaha.countriesapp.network;

import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ro.zaha.countriesapp.R;
import ro.zaha.countriesapp.adapter.CountryAdapter;
import ro.zaha.countriesapp.model.CountryInfo;
import java.util.ArrayList;
import java.util.List;

public class CountriesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<CountryInfo> countryInfoList;
    CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        recyclerView = findViewById(R.id.recyclerView);
        countryInfoList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiCall();
    }

    private void apiCall() {

        String URL = "https://corona.lmao.ninja/v2/countries";
        StringRequest request = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String countryName = jsonObject.getString("country");

                                JSONObject jsonObject1 = jsonObject.getJSONObject("countryInfo");
                                String flag = jsonObject1.getString("flag");

                                CountryInfo countryInfo = new CountryInfo(countryName, flag);
                                countryInfoList.add(countryInfo);
                            }
                            countryAdapter = new CountryAdapter(CountriesActivity.this, countryInfoList);
                            recyclerView.setAdapter(countryAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CountriesActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}