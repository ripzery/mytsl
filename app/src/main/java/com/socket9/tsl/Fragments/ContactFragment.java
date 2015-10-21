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

import com.socket9.tsl.Adapters.ContactAdapter;
import com.socket9.tsl.BranchDetailActivity;
import com.socket9.tsl.Events.Bus.ApiFire;
import com.socket9.tsl.Events.Bus.ApiReceive;
import com.socket9.tsl.MainActivity;
import com.socket9.tsl.ModelEntities.ContactEntity;
import com.socket9.tsl.R;
import com.socket9.tsl.Utils.BusProvider;
import com.socket9.tsl.Utils.OnFragmentInteractionListener;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


    private static final int BASE_ID = 1000;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private ContactAdapter.OnContactClickListener listener;
    @Nullable
    private OnFragmentInteractionListener mListener;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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

    private void setListener() {
        listener = new ContactAdapter.OnContactClickListener() {
            @Override
            public void onClick(@NonNull ContactEntity contactEntity) {
                Timber.i(contactEntity.getId() + "");
                if (contactEntity.getId() < BASE_ID) { // contact from server
                    startActivity(new Intent(getActivity(), BranchDetailActivity.class)
                            .putExtra("contactId", contactEntity.getId())
                            .putExtra("contactName", contactEntity.getTitleEn()));

                } else { // manually added contact

                }
            }
        };
    }

    private void getContacts() {
        BusProvider.post(new ApiFire.GetListContacts());
    }

    @Subscribe
    public void onReceiveContacts(ApiReceive.ListContacts listContacts){
        ContactAdapter contactAdapter = new ContactAdapter(addContact(listContacts.getListContacts().getData()));
        contactAdapter.setContactListener(listener);
        recyclerView.setAdapter(contactAdapter);
    }

    @NonNull
    private List<ContactEntity> addContact(@NonNull List<ContactEntity> listContact) {
        listContact.add(new ContactEntity(BASE_ID + 1, getString(R.string.contact_us_email), "services@tsl.co.th", R.drawable.ic_email_grey_500_24dp));
        listContact.add(new ContactEntity(BASE_ID + 2, getString(R.string.contact_us_call_center), "1234", R.drawable.call_grey));
        listContact.add(new ContactEntity(BASE_ID + 3, getString(R.string.contact_us_website), "www.tsl.co.th", R.drawable.www_grey));
        listContact.add(new ContactEntity(BASE_ID + 4, getString(R.string.contact_us_facebook), "TSL Auto Corporation", R.drawable.fb_grey));
        listContact.add(new ContactEntity(BASE_ID + 5, getString(R.string.contact_us_instagram), "TSL_Auto", R.drawable.ig_grey));
        return listContact;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        BusProvider.getInstance().register(this);
        ((MainActivity) getActivity()).onFragmentAttached(MainActivity.FRAGMENT_DISPLAY_CONTACT);

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
}
