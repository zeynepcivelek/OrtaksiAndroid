package com.cagataygul.ortaksison;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;

import com.cagataygul.ortaksison.Fragments.UsersFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;


public class MapsActivityAfterLogin extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int radius=6371;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user ;
    String id;
    Double lat , lng,curlat,curlng;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbref = database.getReference("User");
    public DatabaseReference myRef;
    public DatabaseReference myRef2;
    public DatabaseReference myRef3;
    private DatabaseReference RootRef;
    String currentUser;
    LocationManager locationManager;
    LocationListener locationListener;
    String gender="";
    String lookstatus ="";
    String st;
    private Toolbar toolbar;
    Button btn ;
    TextView text ;
    TextView hellousername ;
    static String dest,time,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_after_login);
        firebaseAuth = FirebaseAuth.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        currentUser=user.getEmail().toString();
        RootRef = FirebaseDatabase.getInstance().getReference();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        toolbar= (Toolbar) findViewById(R.id.mapstoolbar);
        btn = (Button) findViewById(R.id.chatbutton);
        text=(TextView) findViewById(R.id.Userinf);
        hellousername=(TextView) findViewById(R.id.userssnamees);
        btn.setVisibility(View.INVISIBLE);
        text.setVisibility(View.INVISIBLE);





    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.Logoutmenu)
        {
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        HashMap<String,String> hmap = (HashMap<String, String>) ds.getValue();
                        HashMap<String,Double> hmap2 = (HashMap<String, Double>) ds.getValue();
                        if( hmap.get("email").equalsIgnoreCase(currentUser)) {
                            id = hmap.get("id");


                        }

                    }
                    if(id!=null){
                        myRef=  database.getReference("User/"+id+"/status");
                        myRef.setValue("offline");


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }

        return true;
    }
    private void sendToStart() {

        Intent intent =new Intent(MapsActivityAfterLogin.this,LogInActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mMap.clear();

                LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());


                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            HashMap<String,String> hmap = (HashMap<String, String>) ds.getValue();
                            HashMap<String,Double> hmap2 = (HashMap<String, Double>) ds.getValue();
                            if( hmap.get("email").equalsIgnoreCase(currentUser)) {
                                id = hmap.get("id");
                                gender = hmap.get("gender");
                                lookstatus = hmap.get("status");
                                hellousername.setText("Welcome "+hmap.get("name").toString());
                                curlat = hmap2.get("latitude");
                                curlng = hmap2.get("longitude");


                            }


                            lat =hmap2.get("latitude");
                            lng = hmap2.get("longitude");

                            if(lat!=null&&lng!=null&&lat!=0.0&&lng!=0.0&&curlat!=null&&curlng!=null) {

                               

                                    LatLng userLocation1 = new LatLng(lat, lng);
                                    mMap.addMarker(new MarkerOptions().position(userLocation1).title(hmap.get("name") + " " + hmap.get("destination") + "  " + hmap.get("time"))
                                            .snippet(hmap.get("id"))
                                            .icon(BitmapDescriptorFactory
                                                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


                                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(Marker marker) {

                                            text.setText(marker.getTitle());
                                            dest = marker.getSnippet();
                                            btn.setVisibility(View.VISIBLE);
                                            text.setVisibility(View.VISIBLE);
                                            btn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent ant = new Intent(MapsActivityAfterLogin.this, MessageActivity.class);
                                                    ant.putExtra("userid", dest);

                                                    startActivity(ant);
                                                }
                                            });


                                            return false;
                                        }
                                    });
                                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                        @Override
                                        public void onMapClick(LatLng latLng) {

                                            btn.setVisibility(View.INVISIBLE);
                                            text.setVisibility(View.INVISIBLE);
                                            btn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            });

                                        }
                                    });



                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                if(id!=null){
                    myRef=  database.getReference("User/"+id+"/latitude");
                    myRef.setValue(location.getLatitude());
                    myRef2=  database.getReference("User/"+id+"/longitude");
                    myRef2.setValue(location.getLongitude());

                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if(Build.VERSION.SDK_INT>=23){
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        }
        else {locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);}

    }
    public Double getDistance(Double curlat,Double curlong ,Double delat,Double dlong) {
        Location l1 = new Location("One");
        l1.setLatitude(curlat);
        l1.setLongitude(curlong);

        Location l2 = new Location("Two");
        l2.setLatitude(delat);
        l2.setLongitude(dlong);

        double distancedes = l1.distanceTo(l2);

        return distancedes;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(grantResults.length>0){
            if(requestCode==1){
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    HashMap<String,String> hmap = (HashMap<String, String>) ds.getValue();
                    HashMap<String,Double> hmap2 = (HashMap<String, Double>) ds.getValue();
                    if( hmap.get("email").equalsIgnoreCase(currentUser)) {
                        id = hmap.get("id");

                    }

                }
                if(id!=null){
                    myRef2=  database.getReference("User/"+id+"/status");
                    myRef2.setValue("online");


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
