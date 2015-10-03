package com.socket9.tsl.Fragments;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.MapView;
import com.socket9.tsl.R;
import com.socket9.tsl.Utils.MapHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmergencyFragment extends Fragment {


    @Bind(R.id.mapView)
    MapView mapView;
    @Bind(R.id.btnRequest)
    Button btnRequest;
    private MapHelper mapHelper;
    private MapHelper.MapListener mapListener;

    public EmergencyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_emergency, container, false);
        ButterKnife.bind(this, rootView);
        mapHelper = new MapHelper();
        mapHelper.initMap(getContext(), mapView, savedInstanceState);
        mapHelper.getMap().setMyLocationEnabled(true);
        mapListener = new MapHelper.MapListener() {
            @Override
            public void onMyLocationChanged(Location location) {
                Timber.i("%s",location.toString());
            }
        };
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapHelper.getmMapView().onResume();
        mapHelper.setOnMyLocationChangeListener(mapListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapHelper.getmMapView().onPause();
        mapHelper.removeOnMyLocationChangeListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapHelper.getmMapView().onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapHelper.getmMapView().onLowMemory();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
