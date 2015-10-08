package com.socket9.tsl.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.MainActivity;
import com.socket9.tsl.Models.Profile;
import com.socket9.tsl.R;
import com.socket9.tsl.SignInActivity;
import com.socket9.tsl.Utils.OnFragmentInteractionListener;
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
    private OnFragmentInteractionListener mListener;

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
        mListener.onProgressStart();
        ApiService.getTSLApi().getProfile(Singleton.getInstance().getToken(), new MyCallback<Profile>() {
            @Override
            public void good(Profile m, Response response) {
                try {
                    if (m.getData().getPic() != null)
                        Glide.with(getActivity()).load(m.getData().getPic()).centerCrop().into(new GlideDrawableImageViewTarget(ivUser){
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                ivUser.setVisibility(View.VISIBLE);
                                super.onResourceReady(resource, animation);
                            }
                        });
                    else if(m.getData().getFacebookPic() != null)
                        Glide.with(getActivity()).load(m.getData().getFacebookPic()).centerCrop().into(new GlideDrawableImageViewTarget(ivUser){
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                ivUser.setVisibility(View.VISIBLE);
                                super.onResourceReady(resource, animation);
                            }
                        });
                    tvName.setText(m.getData().getNameEn());
                    tvName.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mListener.onProgressComplete();
            }

            @Override
            public void bad(String error,boolean isTokenExpired) {
                Singleton.toast(getContext(), error, Toast.LENGTH_LONG);
                mListener.onProgressComplete();
                if(isTokenExpired){
                    Singleton.toast(getActivity(), getString(R.string.toast_token_invalid), Toast.LENGTH_LONG);
                    Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, "");
                    startActivity(new Intent(getActivity(), SignInActivity.class));
                    getActivity().finish();
                }
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
}
