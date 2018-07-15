package com.myscrap.mirzaadiltask.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.myscrap.mirzaadiltask.R;
import com.myscrap.mirzaadiltask.contoller.interfaces.APIInterface;
import com.myscrap.mirzaadiltask.contoller.network.APIClient;
import com.myscrap.mirzaadiltask.contoller.utils.ApplicationConstant;
import com.myscrap.mirzaadiltask.model.LocationDataModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


/**
 * Created by Mirza Adil on 6/27/2018.
 * <p>
 * This is a  MainActivity class.
 */

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private APIInterface apiInterface;
    private List<LocationDataModel.LocationData> locationDataList;
    private LatLng latlng;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing the Support Map Fragment.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * In this method we are call DataLocation Api.
     */

    private void apiCall() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        final Observable<Response<LocationDataModel>> locationdataCall = apiInterface.fetchLocationData(ApplicationConstant.MS_DISCOVER_FUNCTION, ApplicationConstant.API_KEY, ApplicationConstant.USER_ID);
        locationdataCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<LocationDataModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<LocationDataModel> value) {
                        if (value.isSuccessful()) {

                            locationDataList = value.body().getLocationData();

                            /**
                             * In this For loop draw 30 Google Map marker.
                             */

                            for (int i = 0; i < 30; i++) {
                                latlng = new LatLng(Double.valueOf(locationDataList.get(i).getLatitude()), Double.valueOf(locationDataList.get(i).getLongitude()));
                                googleMap.addMarker(new MarkerOptions().position(latlng)
                                        .title(locationDataList.get(i).getName()));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16f));
                            }


                        }
                    }

                    @Override
                    public void onError(Throwable e) {


                        Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        /**
         * In this method we are call DataLocation Api.
         */

        apiCall();
    }

}
