package com.socket9.tsl.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.Adapters.ContactAdapter;
import com.socket9.tsl.BranchDetailActivity;
import com.socket9.tsl.MainActivity;
import com.socket9.tsl.ModelEntities.ContactEntity;
import com.socket9.tsl.Models.ListContacts;
import com.socket9.tsl.R;
import com.socket9.tsl.SignInActivity;
import com.socket9.tsl.Utils.OnFragmentInteractionListener;
import com.socket9.tsl.Utils.Singleton;

import java.util.List;

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
    private static final int BASE_ID = 1000;

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

    public void setListener() {
        listener = new ContactAdapter.OnContactClickListener() {
            @Override
            public void onClick(ContactEntity contactEntity) {
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

    public void getContacts() {
        mListener.onProgressStart();
        ApiService.getTSLApi().getListContacts(Singleton.getInstance().getToken(), new MyCallback<ListContacts>() {
            @Override
            public void good(ListContacts m, Response response) {
                // Add more contact
                ContactAdapter contactAdapter = new ContactAdapter(addContact(m.getData()));
                contactAdapter.setContactListener(listener);
                recyclerView.setAdapter(contactAdapter);
                mListener.onProgressComplete();
            }

            @Override
            public void bad(String error, boolean isTokenExpired) {
                Timber.i(error);
                mListener.onProgressComplete();
                if (isTokenExpired) {
                    Singleton.toast(getActivity(), "Someone has access your account, please login again.", Toast.LENGTH_LONG);
                    Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, "");
                    startActivity(new Intent(getActivity(), SignInActivity.class));
                    getActivity().finish();
                }
            }
        });
    }

    public List<ContactEntity> addContact(List<ContactEntity> listContact) {
        listContact.add(new ContactEntity(BASE_ID + 1, "email", "services@tsl.co.th", R.drawable.ic_email_grey_500_24dp));
        listContact.add(new ContactEntity(BASE_ID + 2, "call center", "1234", R.drawable.ic_call_grey_500_24dp));
        listContact.add(new ContactEntity(BASE_ID + 3, "website", "www.tsl.co.th", R.drawable.ic_web_grey_500_24dp));
        listContact.add(new ContactEntity(BASE_ID + 4, "facebook", "TSL Auto Corporation", R.drawable.com_facebook_button_icon));
        listContact.add(new ContactEntity(BASE_ID + 5, "instagram", "TSL_Auto", R.mipmap.ic_launcher));
        return listContact;
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
