package windylabs.com.whisk.views;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;
import windylabs.com.whisk.MainActivity;
import windylabs.com.whisk.R;
import windylabs.com.whisk.models.foursquare.Location;
import windylabs.com.whisk.utils.OnLocationClickListener;
import windylabs.com.whisk.views.adapters.LocationAdapter;
import windylabs.com.whisk.models.foursquare.ResponseHolder;
import windylabs.com.whisk.utils.FoursquareService;
import windylabs.com.whisk.utils.WhiskConstants;

public class LocationActivity extends AppCompatActivity {
    private final static String TAG = LocationActivity.class.getSimpleName();

    @InjectView(R.id.location_list) protected RecyclerView locationRecyclerView;
    @InjectView(R.id.search_for_places) protected EditText searchForPlacesView;

    LocationAdapter locationAdapter;

    OnLocationClickListener onLocationClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        setupActionBar();

        this.onLocationClickListener = new OnLocationClickListener(LocationActivity.this);

        this.locationRecyclerView = (RecyclerView) findViewById(R.id.location_list);
        this.searchForPlacesView = (EditText) findViewById(R.id.search_for_places);

        this.locationRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.locationAdapter = new LocationAdapter(getApplicationContext(), LocationActivity.this);
        this.locationRecyclerView.setAdapter(this.locationAdapter);

        this.searchForPlacesView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged -- START" + s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged -- START" + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged -- START" + s.toString());
                searchPlaces(s.toString());
            }
        });
    }

    //@param query - what the user wants to search for
    public void searchPlaces(String query){
        Log.d(TAG, "searchPlaces -- START");
        try {
            // Create an retrofit2 adapter
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(WhiskConstants.FOURSQURE_API_PREFIX)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            // Create an instance of the Foursquare Search API interface
            FoursquareService.FoursquareSearch foursquareService = retrofit.create(FoursquareService.FoursquareSearch.class);

            Observable<ResponseHolder> metaRxCall = foursquareService.responseRx(WhiskConstants.FOURSQUARE_CLIENT_ID, WhiskConstants.FOURSQUARE_CLIENT_SECRET, "37.4249154,-122.0722049", WhiskConstants.FOURSQUARE_VERSION, query);
            metaRxCall
                    .subscribeOn(Schedulers.newThread())
                    .flatMap(s -> Observable.from(s.getResponse().getVenues()))
                    .forEach(venue -> this.locationAdapter.addItem(venue));

            this.locationAdapter.notifyDataSetChanged();
        } catch(Exception e){
            Log.d(TAG, "IOException: " + e.toString());
        }
    }

    private void setupActionBar(){
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowCustomEnabled(true);

        setTitle("");

        ab.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.create_event_background)));
        ab.setDisplayHomeAsUpEnabled(Boolean.TRUE);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ab.setElevation(0);
        }

        View customActionBarView = getLayoutInflater().inflate(R.layout.custom_action_bar, null);
        ((TextView)customActionBarView.findViewById(R.id.custom_action_bar_text)).setText("Add a location");
        ab.setCustomView(customActionBarView);
    }
}
