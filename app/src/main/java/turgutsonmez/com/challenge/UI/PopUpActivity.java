package turgutsonmez.com.challenge.UI;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import turgutsonmez.com.challenge.Model.Explore.Item_;
import turgutsonmez.com.challenge.R;

public class PopUpActivity extends Activity implements OnMapReadyCallback {

  TextView txt_popup_CafeName;
  TextView txt_Commit;

  PopupWindow popUp;
  LayoutParams params;
  FrameLayout layout;

  GoogleMap mMap;
  MapView place_map;
  LocationManager locationManager;
  LocationListener locationListener;


  String Client_ID = "VIEQ0QX5GAJ1XLDJABA5WBS54XCVTNWLNY2NLAZVNB2ZDUYM";
  String Client_Secret = "COARL4531NXUEZTWDE21201TRAZXPEFIQKXFY4AJKHWHDXOT";
  String apiVersion = "20161010";
  List<Item_> item_list = new ArrayList<Item_>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pop_up);
    txt_popup_CafeName = findViewById(R.id.txt_popup_CafeName);
    txt_Commit = findViewById(R.id.txt_Commit);



    place_map = findViewById(R.id.place_map);
    place_map.onCreate(savedInstanceState);



    Marker marker;


    popUp = new PopupWindow(this);

    // layout = new LinearLayout(this);
    layout = new FrameLayout(this);


    // popUp.setContentView(layout);

    params = new LayoutParams(LayoutParams.WRAP_CONTENT,
      LayoutParams.WRAP_CONTENT);

    DisplayMetrics dm = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(dm);

    int width = dm.widthPixels;
    int height = dm.heightPixels;
    getWindow().setLayout((int) (width * .8), (int) (height * .6));

    popUp.setBackgroundDrawable(new ColorDrawable(
      android.graphics.Color.TRANSPARENT));
    // layout.setBackgroundColor(Color.TRANSPARENT);
    popUp.setContentView(layout);


    /*final int pos = getIntent().getIntExtra("position", 0);
    String placeId = item_list.get(pos).getVenue().getId();


    FourSquareService fourSquareService = FourSquareService.retrofit.create(FourSquareService.class);
    final Call<Explore> call = fourSquareService.requestpopUpCafeName(placeId,Client_ID, Client_Secret, apiVersion);
    call.enqueue(new Callback<Explore>() {
      @Override
      public void onResponse(Call<Explore> call, Response<Explore> response) {
        item_list = response.body().getResponse().getGroups().get(0).getItems();

        txt_popup_CafeName.setText( item_list.get(pos).getVenue().getName());


      }

      @Override
      public void onFailure(Call<Explore> call, Throwable t) {
        Toast.makeText(PopUpActivity.this, "Arama kriterlerinize uygun sonuç bulunamadı", Toast.LENGTH_LONG).show();
      }
    });*/


    txt_popup_CafeName.setText(getIntent().getStringExtra("placename"));
    //txt_Commit.setText(getIntent().getStringExtra("tips"));
  }


  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap=googleMap;
    String lat = getIntent().getStringExtra("lat");
    String lng = getIntent().getStringExtra("lng");
    LatLng locations = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
    mMap.addMarker(new MarkerOptions().position(locations).title("aradığınız yer burada"));
    mMap.moveCamera(CameraUpdateFactory.newLatLng(locations));



  }
}
