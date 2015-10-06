package com.socket9.tsl.Fragments;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.MainActivity;
import com.socket9.tsl.Models.BaseModel;
import com.socket9.tsl.R;
import com.socket9.tsl.Utils.MapHelper;
import com.socket9.tsl.Utils.OnFragmentInteractionListener;
import com.socket9.tsl.Utils.Singleton;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmergencyFragment extends Fragment {


    @Bind(R.id.mapView)
    MapView mapView;
    @Bind(R.id.btnRequest)
    Button btnRequest;
    private static final String MECHANIC = "MECHANIC";
    private static final String TOWCAR = "TOW CAR";
    private MapHelper mapHelper;
    private MapHelper.MapListener mapListener;
    private boolean isZoom = false;
    private Marker myLocationMarker;
    private OnFragmentInteractionListener mListener;

    public EmergencyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_emergency, container, false);
        ButterKnife.bind(this, rootView);
        initMap(savedInstanceState);
        setListener();
        return rootView;
    }

    private void setListener() {

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LatLng myPosition = myLocationMarker.getPosition();
                    mListener.onProgressStart();
                    ApiService.getTSLApi().emergencyCall(Singleton.getInstance().getToken(),
                            myPosition.latitude + "",
                            myPosition.longitude + "",
                            MECHANIC,
                            new MyCallback<BaseModel>() {
                                @Override
                                public void good(BaseModel m, Response response) {
                                    try {
                                        Singleton.toast(getContext(), m.getMessage(), Toast.LENGTH_LONG);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    mListener.onProgressComplete();
                                }

                                @Override
                                public void bad(String error) {
                                    mListener.onProgressComplete();
                                    Singleton.toast(getActivity(), error, Toast.LENGTH_LONG);
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initMap(Bundle savedInstanceState) {
        mapHelper = new MapHelper();
        mapHelper.initMap(getContext(), mapView, savedInstanceState);
        mapHelper.getMap().setMyLocationEnabled(true);
        mapListener = new MapHelper.MapListener() {
            @Override
            public void onMyLocationChanged(Location location) {
                Timber.i("%s", location.toString());
                if (!isZoom) {
                    mapHelper.moveTo(location.getLatitude(), location.getLongitude(), 17);
                    myLocationMarker = mapHelper.addMarker(location.getLatitude(), location.getLongitude(), 0);
                    isZoom = true;
                } else {
                    myLocationMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                }
            }
        };
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
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).onFragmentAttached(MainActivity.FRAGMENT_DISPLAY_EMERGENCY);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHomeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }





}
