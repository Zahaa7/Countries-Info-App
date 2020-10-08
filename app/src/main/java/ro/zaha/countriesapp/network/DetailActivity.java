package ro.zaha.countriesapp.network;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ro.zaha.countriesapp.R;

public class DetailActivity extends AppCompatActivity {

    TextView capital, region, subregion, population, demonym, timezones, currencies, languages, translations,
            callingCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        capital = findViewById(R.id.tvCapitalCity);
        region = findViewById(R.id.tvRegion);
        subregion = findViewById(R.id.tvSubregion);
        population = findViewById(R.id.tvPopulation);
        demonym = findViewById(R.id.tvDemonym);
        timezones = findViewById(R.id.tvTimeZone);
        currencies = findViewById(R.id.tvCurrencies);
        languages = findViewById(R.id.tvLanguages);
        translations = findViewById(R.id.tvTranslations);
        callingCodes = findViewById(R.id.tvCountryCode);

        Intent intent = getIntent();
        String countryName = intent.getStringExtra("countryName");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(countryName);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ApiCall(countryName);
    }

    private void ApiCall(String countryName) {

        String URL = "https://restcountries.eu/rest/v2/name/" + countryName;
        StringRequest request = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                capital.setText(jsonObject.getString("capital"));
                                region.setText(jsonObject.getString("region"));
                                subregion.setText(jsonObject.getString("subregion"));
                                population.setText(jsonObject.getString("population"));
                                demonym.setText(jsonObject.getString("demonym"));
                                JSONArray timezonesArray = jsonObject.getJSONArray("timezones");
                                String timezonesText = "";
                                for (int j = 0; j < timezonesArray.length(); j++) {
                                    timezonesText += timezonesArray.get(j) + "\n";

                                }
                                timezones.setText(timezonesText);

                                JSONArray currencyArray = jsonObject.getJSONArray("currencies");
                                String currenciesText = "";
                                for (int j = 0; j < currencyArray.length(); j++) {
                                    currenciesText += ((JSONObject) currencyArray.get(i)).getString("name");
                                    if (j < currencyArray.length() - 1) {
                                        currenciesText += ", ";
                                    }
                                }
                                currencies.setText(currenciesText);

                                JSONArray languagesArray = jsonObject.getJSONArray("languages");
                                String languagesText = "";
                                for (int j = 0; j < languagesArray.length(); j++) {

                                    if (!languagesText.contains(((JSONObject) languagesArray.get(i)).getString("name"))) {
                                        languagesText += ((JSONObject) languagesArray.get(i)).getString("name");
                                    }
                                }
                                languages.setText(languagesText);


                                JSONObject translationObj = jsonObject.getJSONObject("translations");
                                translations.setText(translationObj.getString("fr").replaceFirst("", "fr: ") +
                                        "\n" + "de: " + translationObj.getString("de") +
                                        "\n" + "es: " + translationObj.getString("es") +
                                        "\n" + "it: " + translationObj.getString("it") +
                                        "\n" + "ja: " + translationObj.getString("ja") +
                                        "\n" + "fa: " + translationObj.getString("fa"));


                                JSONArray callingCodesArray = jsonObject.getJSONArray("callingCodes");
                                callingCodes.setText(jsonObject.getString("callingCodes"));
                                callingCodes.setText(callingCodesArray.getString(0));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Toast.makeText(DetailActivity.this, "" + error, Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}