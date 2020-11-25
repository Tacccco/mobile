package com.onon.android.mapappagain;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    Spinner spinner;

    ArrayList<Marker> markers;
    ArrayList<Location> mymarkers;
    Marker lasMarker;
    Marker selectedMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //spinner
        spinner = findViewById(R.id.spin);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID); break;
                    case 1: mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); break;
                    case 2: mMap.setMapType(GoogleMap.MAP_TYPE_NONE); break;
                    case 3: mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE); break;
                    case 4: mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN); break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //markers
        mymarkers = new ArrayList<>();
        markers  = new ArrayList<>();

        //location manager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 5, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {


                    Double latitude = location.getLatitude();
                    Double longitude = location.getLongitude();
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> locs = geocoder.getFromLocation(latitude,longitude,1);
                        String title = locs.get(0).getLocality() + " ,";
                        title += locs.get(0).getCountryName();
                        LatLng currentLoc = new LatLng(latitude, longitude);

                        mymarkers.add(location);

                        if(lasMarker != null) lasMarker.remove();
                        lasMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 15f));
                        if(mymarkers.size()>=2) {
                            double destLatitude = mymarkers.get(mymarkers.size() - 1).getLatitude();
                            double destLongitude = mymarkers.get(mymarkers.size() - 1).getLongitude();
                            double originLatitude = mymarkers.get(mymarkers.size() - 2).getLatitude();
                            double originLongitude = mymarkers.get(mymarkers.size() - 2).getLongitude();
                            mMap.addPolyline(new PolylineOptions().add(new LatLng(originLatitude, originLongitude), new LatLng(destLatitude, destLongitude)).width(5).color(Color.RED));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 5, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {


                    Double latitude = location.getLatitude();
                    Double longitude = location.getLongitude();
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> locs = geocoder.getFromLocation(latitude,longitude,1);
                        String title = locs.get(0).getLocality() + " ,";
                        title += locs.get(0).getCountryName();
                        LatLng currentLoc = new LatLng(latitude, longitude);

                        mymarkers.add(location);

                        if(lasMarker != null) lasMarker.remove();
                        lasMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 15f));
                        if(mymarkers.size()>=2) {
                            double destLatitude = mymarkers.get(mymarkers.size() - 1).getLatitude();
                            double destLongitude = mymarkers.get(mymarkers.size() - 1).getLongitude();
                            double originLatitude = mymarkers.get(mymarkers.size() - 2).getLatitude();
                            double originLongitude = mymarkers.get(mymarkers.size() - 2).getLongitude();
                            mMap.addPolyline(new PolylineOptions().add(new LatLng(originLatitude, originLongitude), new LatLng(destLatitude, destLongitude)).width(5).color(Color.RED));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /* Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng somewhere = new LatLng(7,29);

        mMap.addMarker(new MarkerOptions().position(somewhere).title("wonder where it is"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(somewhere));*/
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {


            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                //if(selectedMarker != null) selectedMarker.remove();
                markers.add(mMap.addMarker(new MarkerOptions().position(latLng).draggable(true)));
                if(markers.size() >= 2){
                    Marker m1 = markers.get(markers.size() - 1);
                    Marker m2 = markers.get(markers.size() - 2);
                    float[] result = new float[10];
                    Location.distanceBetween(m2.getPosition().latitude, m2.getPosition().longitude, m1.getPosition().latitude, m1.getPosition().longitude, result);
                    m1.setTitle("Distance:" + result[0]);
                    System.out.println("distance" + result[0]);
                }
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                selectedMarker = marker;

                return false;
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                markers.get(markers.indexOf(marker)).remove();
                markers.remove(marker);
                if(markers.size() >= 2){
                    Marker m1 = markers.get(markers.size() - 1);
                    Marker m2 = markers.get(markers.size() - 2);
                    float[] result = new float[10];
                    Location.distanceBetween(m2.getPosition().latitude, m2.getPosition().longitude, m1.getPosition().latitude, m1.getPosition().longitude, result);
                    m1.setTitle("Distance:" + result[0]);
                    System.out.println("distance" + result[0]);
                }

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });

    }
}
