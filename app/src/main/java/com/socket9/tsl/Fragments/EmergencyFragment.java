package com.socket9.tsl.Fragments;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.socket9.tsl.Events.Bus.ApiFire;
import com.socket9.tsl.Events.Bus.ApiReceive;
import com.socket9.tsl.MainActivity;
import com.socket9.tsl.R;
import com.socket9.tsl.Utils.BusProvider;
import com.socket9.tsl.Utils.MapHelper;
import com.socket9.tsl.Utils.Singleton;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmergencyFragment extends Fragment implements View.OnClickListener {


    private static final String MECHANIC = "MECHANIC";
    private static final String TOWCAR = "TOW CAR";
    @Bind(R.id.mapView)
    MapView mapView;
    @Bind(R.id.btnRequest)
    Button btnRequest;
    @Bind(R.id.ivMechanic)
    ImageView ivMechanic;
    @Bind(R.id.ivTowCar)
    ImageView ivTowCar;
    private MapHelper mapHelper;
    private MapHelper.MapListener mapListener;
    private boolean isZoom = false;
    private Marker myLocationMarker;
    private boolean isMechanic = true;
    @NonNull
    private String requestEmergency = "MECHANIC";

    public EmergencyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_emergency, container, false);
        ButterKnife.bind(this, rootView);
        initMap(savedInstanceState);
        setListener();
        return rootView;
    }

    private void setListener() {
        ivMechanic.setOnClickListener(this);
        ivTowCar.setOnClickListener(this);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LatLng myPosition = myLocationMarker.getPosition();
                    BusProvider.post(new ApiFire.EmergencyCall(myPosition.latitude + "", myPosition.longitude + "", requestEmergency));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Subscribe
    public void onReceiveEmergencyCall(ApiReceive.EmergencyCall emergencyCall) {
        try {
            Singleton.toast(getContext(), emergencyCall.getBaseModel().getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMap(Bundle savedInstanceState) {
        try {
            mapHelper = new MapHelper();
            mapHelper.initMap(getContext(), mapView, savedInstanceState);
            mapHelper.getMap().setMyLocationEnabled(true);
            mapListener = new MapHelper.MapListener() {
                @Override
                public void onMyLocationChanged(@NonNull Location location) {

                    Timber.i("%s", location.toString());
                    if (!isZoom) {
                        mapHelper.moveTo(location.getLatitude(), location.getLongitude());
                        myLocationMarker = mapHelper.addMarker(location.getLatitude(), location.getLongitude(), 0);
                        isZoom = true;
                    } else {
                        myLocationMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                    }

                }
            };
        } catch (Exception e) {
            Singleton.toast(getActivity(), getString(R.string.toast_update_google_play_services));
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapHelper.getmMapView().onResume();
        try {
            mapHelper.setOnMyLocationChangeListener(mapListener);
        } catch (Exception e) {
            Singleton.toast(getActivity(), getString(R.string.toast_update_google_play_services));
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapHelper.getmMapView().onPause();
        mapHelper.removeOnMyLocationChangeListener();
        isZoom = false;
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        BusProvider.getInstance().register(this);
        ((MainActivity) getActivity()).onFragmentAttached(MainActivity.FRAGMENT_DISPLAY_EMERGENCY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.ivTowCar:
                if (isMechanic) {
                    ivTowCar.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.towcar_active_en));
                    ivMechanic.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.mechanic_en));
                    requestEmergency = TOWCAR;
                    isMechanic = false;
                }
                break;
            case R.id.ivMechanic:
                if (!isMechanic) {
                    ivMechanic.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.mechanic_active_en));
                    ivTowCar.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.towcar_en));
                    requestEmergency = MECHANIC;
                    isMechanic = true;
                }
                break;
        }
    }
}
