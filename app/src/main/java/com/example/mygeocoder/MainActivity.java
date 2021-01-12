package com.example.mygeocoder;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Geocoder geocoder;
    TextView tv;
    Button btn;
    EditText editText;
    GoogleMap map;
    SupportMapFragment supportMapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.textView);
        btn=findViewById(R.id.button);
        editText=findViewById(R.id.locationName);
        supportMapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.googlemap);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result=null;
                geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
                try {
                    final double lat,lng;
                    List<Address> addresses=geocoder.getFromLocationName(editText.getText().toString(),1);
                    result=addresses.get(0).getAddressLine(0);
                    lat=addresses.get(0).getLatitude();
                    lng=addresses.get(0).getLongitude();
                    tv.setText(result);
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng point=new LatLng(lat,lng);
                            map=googleMap;
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(point,15));
                            map.addMarker(new MarkerOptions().position(point).title("Your point"));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
