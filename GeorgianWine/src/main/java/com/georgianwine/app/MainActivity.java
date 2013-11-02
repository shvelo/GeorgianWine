package com.georgianwine.app;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener  {

    public LocationClient locationClient;
    public Location currentLocation;
    public GoogleMap map;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map));
        if(mapFragment == null) finish();
        map = mapFragment.getMap();

        locationClient = new LocationClient(this, this, this);

        locationClient.connect();

        map.setMyLocationEnabled(true);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(41.5, 44.5), 6));

        map.addMarker(new MarkerOptions().position(new LatLng(41.693007,44.802878))
                .title("Vinotheca")
                .snippet("Tap for info"))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.qvevri));
        map.addMarker(new MarkerOptions().position(new LatLng(41.708396,44.801399))
                .title("Wine Gallery").snippet("Tap for info")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.qvevri))
                .snippet("Tap for info"));
        map.addMarker(new MarkerOptions().position(new LatLng(41.696557,44.805717))
                .title("Georgian Wine Center").snippet("Tap for info"))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.qvevri));
        map.addMarker(new MarkerOptions().position(new LatLng(41.709981,44.792998))
                .title("Wine House").snippet("Tap for info"))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.qvevri));

        map.addMarker(new MarkerOptions().position(new LatLng(41.706136,44.778525))
                .title("Derp\'s Wine").snippet("Tap for info"))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.qvevri));
        map.addMarker(new MarkerOptions().position(new LatLng(41.703509,44.785627))
                .title("City Wine").snippet("Tap for info"))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.qvevri));
        map.addMarker(new MarkerOptions().position(new LatLng(41.707065,44.774898))
                .title("Drunk guy's wine shop").snippet("Tap for info")
        ).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.qvevri));

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra("title", marker.getTitle());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(map == null) {
            map = mapFragment.getMap();
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(map != null) {
            map = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        currentLocation = locationClient.getLastLocation();

        LatLng me = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(me, 15));
    }

    @Override
    public void onDisconnected() {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
}
