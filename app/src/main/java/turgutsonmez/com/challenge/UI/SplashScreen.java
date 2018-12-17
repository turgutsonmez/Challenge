package turgutsonmez.com.challenge.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import turgutsonmez.com.challenge.MainActivity;

public class SplashScreen extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent ıntent=new Intent(this,MainActivity.class);
    startActivity(ıntent);
    finish();
  }
}
