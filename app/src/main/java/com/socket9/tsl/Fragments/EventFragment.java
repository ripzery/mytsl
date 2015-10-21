package com.socket9.tsl.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socket9.tsl.Adapters.EventAdapter;
import com.socket9.tsl.Events.Bus.ApiFire;
import com.socket9.tsl.Events.Bus.ApiReceive;
import com.socket9.tsl.MainActivity;
import com.socket9.tsl.ModelEntities.NewsEventEntity;
import com.socket9.tsl.NewsEventActivity;
import com.socket9.tsl.R;
import com.socket9.tsl.Utils.BusProvider;
import com.socket9.tsl.Utils.OnFragmentInteractionListener;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EventFragment extends Fragment {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private EventAdapter.OnCardClickListener listener;
    @Nullable
    private OnFragmentInteractionListener mListener;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, rootView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getEvents();
        initOnCardClickListener();

        return rootView;
    }

    private void initOnCardClickListener() {
        listener = new EventAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(@NonNull NewsEventEntity viewHolder) {
//                Timber.d(viewHolder.getId()+"");
                Intent intent = new Intent(getActivity(), NewsEventActivity.class);
                intent.putExtra("id", viewHolder.getId());
                intent.putExtra("isNews", false);
                startActivity(intent);
            }
        };
    }

    private void getEvents() {
        BusProvider.post(new ApiFire.GetListEvents());
    }

    @Subscribe
    public void onReceiveListEvents(ApiReceive.ListEvents listEvents) {
        try {
            EventAdapter eventAdapter = new EventAdapter(listEvents.getListNewsEvent().getData());
            eventAdapter.setOnCardClickListener(listener);
            recyclerView.setAdapter(eventAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onDetach() {
        BusProvider.getInstance().unregister(this);
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
