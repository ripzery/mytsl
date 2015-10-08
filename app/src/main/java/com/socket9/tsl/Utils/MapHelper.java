package com.socket9.tsl.Utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.socket9.tsl.R;

/**
 * Created by visit on 9/16/15 AD.
 */
public class MapHelper {
    //    This latlng is the center location of Samui island
    public static double latitude = 9.498625;
    public static double longitude = 99.994406;
    private GoogleMap mMap;
    private MapView mMapView;
    private MapListener mapListener = null;

    public void initMap(Context context, View rootView, Bundle savedInstanceState) {
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMap = mMapView.getMap();
    }

    public void initMap(Context context, MapView mMapView, Bundle savedInstanceState) {
        this.mMapView = mMapView;
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMap = mMapView.getMap();
    }

    public GoogleMap getMap() {
        return mMap;
    }

    public MapView getmMapView() {
        return mMapView;
    }

    public Marker addMarker(double latitude, double longitude, int markerIcon) {
        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Hello Maps");


        if (markerIcon != 0) {
            marker.icon(BitmapDescriptorFactory.fromResource(markerIcon));
        }

        try {
            return mMap.addMarker(marker);
        } catch (NullPointerException e) {
            throw new NullPointerException("Please call initMap method first!");
        }
    }

    public Marker addMarkerThenZoom(double latitude, double longitude, int zoomLevel, int markerIcon) {
        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Hello Maps");

        if (markerIcon == 0) {
            // Changing marker icon
//            marker.icon(BitmapDescriptorFactory
//                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        } else {
            marker.icon(BitmapDescriptorFactory.fromResource(markerIcon));
        }

        // adding marker
        Marker mMarker = mMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(zoomLevel).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        try {
            return mMarker;
        } catch (NullPointerException e) {
            throw new NullPointerException("Please call initMap method first!");
        }
    }

    public void moveTo(double latitude, double longitude, int zoomLevel) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(zoomLevel).build();
        mMap.moveCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    public void zoomTo(double latitude, double longitude, int zoomLevel) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(zoomLevel).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    public void zoomTo(Marker marker, int zoomLevel) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude)).zoom(zoomLevel).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    public void setOnMyLocationChangeListener(MapListener mapListener) {
        try {
            this.mapListener = mapListener;
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    MapHelper.this.mapListener.onMyLocationChanged(location);
                }
            });
        } catch (NullPointerException e) {
            throw new NullPointerException("You must not be set the parameter to null!");
        }
    }

    public void removeOnMyLocationChangeListener() {
        mapListener = null;
        try{
            mMap.setOnMyLocationChangeListener(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Marker addMarkerThenZoom(LatLng latlng, int zoomLevel) {
        Marker mMarker = null;
        // create marker
        try{
            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(latlng.latitude, latlng.longitude)).title("Hello Maps");

            // Changing marker icon
//        marker.icon(BitmapDescriptorFactory
//                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            // adding marker
            mMarker = mMap.addMarker(marker);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latlng.latitude, latlng.longitude)).zoom(zoomLevel).build();
            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }catch(Exception e){
            e.printStackTrace();
        }


        try {
            return mMarker;
        } catch (NullPointerException e) {
            throw new NullPointerException("Please call initMap method first!");
        }

    }

    public interface MapListener {
        void onMyLocationChanged(Location location);
    }
}
