package com.example.sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    double latitude = 0;
    double longitude = 0;
    private LocationManager manager;
    private GPSReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myButtonListenerMethod();
        receiver = new GPSReceiver();
        manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1.0F, receiver);


    }

    public void myButtonListenerMethod(){
        Button button = (Button)findViewById(R.id.sendSOS);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager sms = SmsManager.getDefault();
                String PhoneNumber = "081388332451";
                String MessageBody = "[Julio Yosafat ]Free Starbucks at this Locations Hiya Hiya :"+Double.toString(longitude)+" "+Double.toString(latitude);
                try {
                    sms.sendTextMessage(PhoneNumber,null,MessageBody,null,null);
                    Toast.makeText(getApplicationContext(),"SOS has been sent",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"You're in danger",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public class GPSReceiver implements LocationListener{
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if (location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(getApplicationContext(),"Ready",Toast.LENGTH_LONG).show();
            }
            else{Toast.makeText(getApplicationContext(),"Not ready",Toast.LENGTH_LONG).show();}
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            Toast.makeText(getApplicationContext(),"GPS Enabled",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            Toast.makeText(getApplicationContext(),"Please enable GPS",Toast.LENGTH_LONG).show();
        }
    }
}