package turgutsonmez.com.challenge;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.tapadoo.alerter.Alerter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import turgutsonmez.com.challenge.Model.Explore.Explore;
import turgutsonmez.com.challenge.Model.Explore.Group;
import turgutsonmez.com.challenge.Model.Explore.Item_;
import turgutsonmez.com.challenge.Model.Explore.Photo;
import turgutsonmez.com.challenge.Model.Explore.Photos;
import turgutsonmez.com.challenge.Model.Explore.Venue;
import turgutsonmez.com.challenge.Service.FourSquareService;
import turgutsonmez.com.challenge.UI.PopUpActivity;

import static android.text.TextUtils.substring;

public class PlacesActivity extends AppCompatActivity {


  String Client_ID = "VIEQ0QX5GAJ1XLDJABA5WBS54XCVTNWLNY2NLAZVNB2ZDUYM";
  String Client_Secret = "COARL4531NXUEZTWDE21201TRAZXPEFIQKXFY4AJKHWHDXOT";
  String apiVersion = "20161010";
  String geoLocation = "41.09,29";
  String query = "cafe" + "bar";
  ListView listView;
  ProgressDialog mProgressDialog;
  String loc;
  private static final int REQUEST_CODE = 1000;
  FusedLocationProviderClient fusedLocationProviderClient;
  LocationRequest locationRequest;
  LocationCallback locationCallback;


  List<Item_> item_list = new ArrayList<Item_>();


  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case REQUEST_CODE: {
        if (grantResults.length > 0) {
          if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

          } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

          }
        }
      }
    }
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_places);
    findViewByIds();
    showProgressDialog("Lütfen Bekleyin", "Arama sonuçlarınız yükleniyor");

    //Check permission runtime
    if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
      ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    } else {
      //If permission is granted
      buildLocationRequest();
      buildLocationCallBack();

      fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
      if (ActivityCompat.checkSelfPermission(PlacesActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PlacesActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        return;
      }
      fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());


    }


  }

  private void buildLocationCallBack() {
    locationCallback = new LocationCallback() {
      @Override
      public void onLocationResult(LocationResult locationResult) {
        for (Location location : locationResult.getLocations()) {
          loc = String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());

        }
        FourSquareService fourSquareService = FourSquareService.retrofit.create(FourSquareService.class);
        if (!getIntent().getStringExtra("place").isEmpty()) {
          if (!getIntent().getStringExtra("city").isEmpty()) {
            String newQuery = getIntent().getStringExtra("place");
            newQuery = newQuery + " cafe";
            String place = getIntent().getStringExtra("city");
            final Call<Explore> call = fourSquareService.requestExplore2(Client_ID, Client_Secret, apiVersion, loc, newQuery, place);
            call.enqueue(new Callback<Explore>() {
              @Override
              public void onResponse(Call<Explore> call, Response<Explore> response) {
                try {
                  item_list = response.body().getResponse().getGroups().get(0).getItems();
                }catch (NullPointerException ex){
                  ex.printStackTrace();
                }

                ExploreListAdapter exploreListAdapter = new ExploreListAdapter(getApplicationContext(), R.layout.item_list, item_list);
                listView.setAdapter(exploreListAdapter);
                if (listView.getCount() == 0) {
                  Alerter.create(PlacesActivity.this)
                    .setText("Arama kriterlerinize göre bir sonuç bulamadık :(")
                    .setBackgroundColor(R.color.alert_default_error_background)
                    .setIcon(R.drawable.ic_alert)
                    .show();
                  if (!response.isSuccessful()){
                    try {
                      Thread.sleep(1000);
                    } catch (InterruptedException e) {
                      e.printStackTrace();
                    }
                    hideProgressDialog();
                    returnMainActivity();
                  }
                }
                if (response.isSuccessful())
                  hideProgressDialog();
              }

              @Override
              public void onFailure(Call<Explore> call, Throwable t) {
                Toast.makeText(PlacesActivity.this, "Arama kriterlerinize uygun sonuç bulunamadı", Toast.LENGTH_LONG).show();
              }
            });
          } else {
            String newQuery = getIntent().getStringExtra("place");
            newQuery = newQuery + " cafe";
            //if (newQuery.contains("cafe") || newQuery.contains("bistro") || newQuery.contains("restaurant") || newQuery.contains("bar")){
            final Call<Explore> call = fourSquareService.requestExplore(Client_ID, Client_Secret, apiVersion, loc, newQuery, 1);
            call.enqueue(new Callback<Explore>() {
              @Override
              public void onResponse(Call<Explore> call, Response<Explore> response) {
                try {
                  item_list = response.body().getResponse().getGroups().get(0).getItems();
                }catch (NullPointerException ex){
                  ex.printStackTrace();
                }
                ExploreListAdapter exploreListAdapter = new ExploreListAdapter(getApplicationContext(), R.layout.item_list, item_list);
                listView.setAdapter(exploreListAdapter);

                Alerter.create(PlacesActivity.this)
                  .setText("Bölge belirtmediğiniz için yakınınızdaki sonuçları getirdik")
                  .setBackgroundColor(R.color.alert_default_error_background)
                  .setIcon(R.drawable.ic_alert)
                  .show();
                if (listView.getCount() == 0) {
                  Alerter.create(PlacesActivity.this)
                    .setText("Arama kriterlerinize göre bir sonuç bulamadık :(")
                    .setBackgroundColor(R.color.alert_default_error_background)
                    .setIcon(R.drawable.ic_alert)
                    .show();
                  if (!response.isSuccessful()){
                    hideProgressDialog();
                    returnMainActivity();
                  }

                }
                if (response.isSuccessful())
                  hideProgressDialog();
              }

              @Override
              public void onFailure(Call<Explore> call, Throwable t) {
                Toast.makeText(PlacesActivity.this, "Arama kriterlerinize uygun sonuç bulunamadı", Toast.LENGTH_LONG).show();
              }
            });

          }
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(PlacesActivity.this, PopUpActivity.class);
            int pos = position;

            String placeName = item_list.get(pos).getVenue().getName();
            String lat = String.valueOf(item_list.get(pos).getVenue().getLocation().getLat());
            String lng = String.valueOf(item_list.get(pos).getVenue().getLocation().getLng());

            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);

            intent.putExtra("placename", placeName);
            intent.putExtra("position", pos);
            startActivity(intent);
          }
        });
      }
    };


  }


  @SuppressLint("RestrictedApi")
  private void buildLocationRequest() {
    locationRequest = new LocationRequest();
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    locationRequest.setInterval(5000);
    locationRequest.setFastestInterval(3000);
    locationRequest.setSmallestDisplacement(10);
  }


  void findViewByIds() {
    listView = findViewById(R.id.listivew);
  }

  public void showProgressDialog(String title, String message) {
    if (mProgressDialog == null) {
      mProgressDialog = new ProgressDialog(this);
      mProgressDialog.setTitle(title);
      mProgressDialog.setMessage(message);
      mProgressDialog.setIndeterminate(true);
    }

    mProgressDialog.show();
  }

  public void hideProgressDialog() {
    if (mProgressDialog != null && mProgressDialog.isShowing()) {
      mProgressDialog.dismiss();
    }
  }

  public void returnMainActivity() {
    Intent intent = new Intent(PlacesActivity.this, MainActivity.class);
    startActivity(intent);
  }

}
