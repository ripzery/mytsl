package com.socket9.tsl.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.Adapters.ContactAdapter;
import com.socket9.tsl.MainActivity;
import com.socket9.tsl.ModelEntities.ContactEntity;
import com.socket9.tsl.Models.ListContacts;
import com.socket9.tsl.R;
import com.socket9.tsl.Utils.OnFragmentInteractionListener;
import com.socket9.tsl.Utils.Singleton;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private ContactAdapter.OnContactClickListener listener;
    private OnFragmentInteractionListener mListener;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        getContacts();
        setListener();
        return rootView;
    }

    public void setListener(){
        listener = new ContactAdapter.OnContactClickListener() {
            @Override
            public void onClick(ContactEntity contactEntity) {
                Timber.i(contactEntity.getId()+"");
            }
        };
    }

    public void getContacts(){
        mListener.onProgressStart();
        ApiService.getTSLApi().getListContacts(Singleton.getInstance().getToken(), new MyCallback<ListContacts>() {
            @Override
            public void good(ListContacts m, Response response) {
                ContactAdapter contactAdapter = new ContactAdapter(m.getData());
                contactAdapter.setContactListener(listener);
                recyclerView.setAdapter(contactAdapter);
                mListener.onProgressComplete();
            }

            @Override
            public void bad(String error) {
                Timber.i(error);
                mListener.onProgressComplete();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).onFragmentAttached(MainActivity.FRAGMENT_DISPLAY_CONTACT);
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
