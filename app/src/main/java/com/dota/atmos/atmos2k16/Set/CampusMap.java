package com.dota.atmos.atmos2k16.Set;

/**
 * Created by lakshmi sravani on 26-09-2016.
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dota.atmos.atmos2k16.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;


public class CampusMap extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION = 1002;
    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_campus_map, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        IconGenerator factory = new IconGenerator(getActivity());
        factory.setStyle(factory.STYLE_BLUE);
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);

        } else {
            showLocationOnMap();
        }


        for (int i = 0; i < ControllerConstants.names.length; i++) {
            Bitmap icon = factory.makeIcon(ControllerConstants.names[i]);
            LatLng location = new LatLng(ControllerConstants.latitudes[i], ControllerConstants.longitudes[i]);
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(icon)).position(location));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(ControllerConstants.latitudes[0], ControllerConstants.longitudes[0]))
                .zoom(18)
                .bearing(180)
                .tilt(60)
                .build();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showLocationOnMap();
            }
        }
    }

    private void showLocationOnMap() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }
}


