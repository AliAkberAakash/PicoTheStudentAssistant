package com.example.cedwa.studentassistant.Home;


import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cedwa.studentassistant.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Map extends Fragment implements OnMapReadyCallback {

    //widgets
    EditText mSearchEditText;
    ImageView mGps;


    //constants
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final String FINE_LOCATION = "Manifest.permission.ACCESS_FINE_LOCATION";
    private static final String COARSE_LOCATION = "Manifest.permission.ACCESS_COARSE_LOCATION";
    private static final String MY_LOCATION = "My Location";

    //vars
    private boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getActivity(), "Map is ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        if (mLocationPermissionGranted) {

            getDevicesCurrentLocation();

            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            init();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.addLogAdapter(new AndroidLogAdapter());

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //initialize widgets
        mSearchEditText= view.findViewById(R.id.search_edit_text);
        mGps = view.findViewById(R.id.gps_id);


        if(isServicesOk())
        {
            getLocationPermission();
        }

        return view;
    }

    private void init()
    {
        Logger.d("Initializing...");
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if(actionId== EditorInfo.IME_ACTION_SEARCH
                        || actionId== EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction()==KeyEvent.ACTION_DOWN
                        || keyEvent.getAction()==KeyEvent.KEYCODE_ENTER)
                {
                    //execute method for searching
                    geoLocate();
                }

                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.d("Clicked GPS icon");

                getDevicesCurrentLocation();
            }
        });

        //hideSoftKeyBoard();
    }

    private void geoLocate()
    {
        Logger.d("Geo Locating...");
        String searchString = mSearchEditText.getText().toString().trim();

        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> list = new ArrayList<>();
        try
        {
            list = geocoder.getFromLocationName(searchString,1);
        }
        catch (IOException e)
        {
            Logger.d("Catch the IOException: "+e.getMessage());
        }

        if(list.size()>0)
        {
            Address address = list.get(0);
            Logger.d("Found a location: "+address.toString());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()),DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }

    }

    public void initMap()
    {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().
                findFragmentById(R.id.map_fragment);
        supportMapFragment.getMapAsync(this);
    }

    private void getDevicesCurrentLocation()
    {
        Logger.d("Getting devices current Location");
        mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(getActivity());

        try
        {
            if(mLocationPermissionGranted)
            {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful())
                        {
                            Logger.d("Found Location");
                            Location currentLocation = (Location) task.getResult();
                            if(currentLocation==null)
                            {
                                Toast.makeText(getActivity(), "Please enable GPS", Toast.LENGTH_SHORT).show();
                            }
                            else
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM, MY_LOCATION);
                        }
                        else
                        {
                            Logger.d("current location is null");
                            Toast.makeText(getActivity(), "Could not access current Location!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        catch (SecurityException e)
        {
            Logger.d("Security exception: "+ e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title)
    {
        Logger.d("Moving camera to lat: "+latLng.latitude+" , lng: "+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

        //drop a pin
        if(!title.equals(MY_LOCATION))
        {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

        //hideSoftKeyBoard();

    }

    private void getLocationPermission()
    {
        String[] permissions={
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {

            if(ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                mLocationPermissionGranted = true;
                Logger.d("getLocationPermission: permission is granted");
                initMap();
            }
            else
            {
                ActivityCompat.requestPermissions(getActivity(), permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else
        {
            Logger.d("getLocationPermission: permission is not granted");
            ActivityCompat.requestPermissions(getActivity(), permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted=false;

        switch (requestCode)
        {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if(grantResults.length>0)
                {
                    for(int i=0; i<grantResults.length; i++)
                    {
                        if(grantResults[i]!=LOCATION_PERMISSION_REQUEST_CODE) {
                            mLocationPermissionGranted = false;
                            Logger.d("onRequestPermissionResult : Permission Failed");
                            return;
                        }
                    }
                    Logger.d("onRequestPermissionResult : Permission Granted");
                    mLocationPermissionGranted=true;
                    //init map here
                    initMap();
                }
                break;
        }
    }

    public boolean isServicesOk()
    {
        Logger.d("isServiceOk: Checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());

        if(available== ConnectionResult.SUCCESS)
        {
            Logger.d("isServiceOk: Google play services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            Logger.d("isServiceOk: An error occurred but it can be fixed");
            Dialog dialog = GoogleApiAvailability.getInstance()
                    .getErrorDialog(getActivity(),available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else
        {
            Toast.makeText(getActivity(), "You can not make map requests!", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    /*private void hideSoftKeyBoard()
    {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }*/

}
