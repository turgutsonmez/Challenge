package turgutsonmez.com.challenge;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.tapadoo.alerter.Alert;
import com.tapadoo.alerter.Alerter;

public class MainActivity extends AppCompatActivity {

  EditText editText_City;
  EditText editText_Place;
  Button btn_Search;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    editText_City = findViewById(R.id.editText_City);
    editText_Place = findViewById(R.id.editText_Place);
    btn_Search = findViewById(R.id.btn_Search);

    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

    } else {

    }


    btn_Search.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final String edtPlace = editText_Place.getText().toString();
        final String edtCity = editText_City.getText().toString();

        if (edtPlace.length() <= 2) {
          Alerter.create(MainActivity.this)
            .setText("Mekan türü için en az 3 karakter girmelisiniz")
            .setBackgroundColor(R.color.alert_default_error_background)
            .setIcon(R.drawable.ic_alert)
            .show();
        }else{
          if (edtCity.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, PlacesActivity.class);
            intent.putExtra("place", edtPlace);
            intent.putExtra("city", edtCity);
            startActivity(intent);
          } else {
            Intent intent = new Intent(MainActivity.this, PlacesActivity.class);
            intent.putExtra("place", edtPlace);
            intent.putExtra("city", edtCity);
            startActivity(intent);
          }
        }
      }
    });

  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    //fusedLocationProviderClient.removeLocationUpdates(locationCallback);
  }
}
