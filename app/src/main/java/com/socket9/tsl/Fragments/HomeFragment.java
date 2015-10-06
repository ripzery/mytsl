package com.socket9.tsl.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.MainActivity;
import com.socket9.tsl.Models.Profile;
import com.socket9.tsl.R;
import com.socket9.tsl.Utils.Singleton;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @Bind(R.id.ivUser)
    CircleImageView ivUser;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.layoutProgress)
    LinearLayout layoutProgress;
    private OnHomeListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onImageClick();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getProfile();
    }

    public void getProfile() {
        mListener.onGetProfileBegin();
        ApiService.getTSLApi().getProfile(Singleton.getInstance().getToken(), new MyCallback<Profile>() {
            @Override
            public void good(Profile m, Response response) {
                try {
                    if (m.getData().getPic() != null)
                        Glide.with(getActivity()).load(m.getData().getPic()).centerCrop().into(ivUser);
                    tvName.setText(m.getData().getNameEn());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mListener.onGetProfileComplete();
            }

            @Override
            public void bad(String error) {
                Singleton.toast(getContext(), error, Toast.LENGTH_LONG);
                mListener.onGetProfileComplete();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).onFragmentAttached(MainActivity.FRAGMENT_DISPLAY_HOME);
        try {
            mListener = (OnHomeListener) context;
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


    public interface OnHomeListener {
        // TODO: Update argument type and name
        void onImageClick();
        void onGetProfileComplete();
        void onGetProfileBegin();
    }
}
