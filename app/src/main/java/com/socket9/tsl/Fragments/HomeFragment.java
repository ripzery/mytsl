package com.socket9.tsl.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.socket9.tsl.Events.Bus.ApiFire;
import com.socket9.tsl.Events.Bus.ApiReceive;
import com.socket9.tsl.MainActivity;
import com.socket9.tsl.MyProfileActivity;
import com.socket9.tsl.R;
import com.socket9.tsl.Utils.BusProvider;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @Bind(R.id.ivUser)
    CircleImageView ivUser;
    @Bind(R.id.tvName)
    TextView tvName;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MyProfileActivity.class));

            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getProfile();
    }

    private void getProfile() {
        BusProvider.post(new ApiFire.GetProfile());
    }


    @Subscribe
    public void onReceiveProfile(ApiReceive.Profile profile) {
        try {
            GlideDrawableImageViewTarget glideImageViewTarget = new GlideDrawableImageViewTarget(ivUser) {
                @Override
                public void onResourceReady(@NonNull GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                    ivUser.setVisibility(View.VISIBLE);
                    super.onResourceReady(resource, animation);
                }
            };

            if (profile.getProfile().getData().getPic() != null) {
                Glide.with(getActivity()).load(profile.getProfile().getData().getPic()).centerCrop().into(glideImageViewTarget);
                ivUser.setVisibility(View.VISIBLE);
            }
            else if (profile.getProfile().getData().getFacebookPic() != null)
                Glide.with(getActivity()).load(profile.getProfile().getData().getFacebookPic()).centerCrop().into(glideImageViewTarget);
            else {
                ivUser.setVisibility(View.VISIBLE);
            }
            tvName.setText(profile.getProfile().getData().getNameEn());
            tvName.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        BusProvider.getInstance().register(this);
        ((MainActivity) getActivity()).onFragmentAttached(MainActivity.FRAGMENT_DISPLAY_HOME);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        BusProvider.getInstance().unregister(this);
    }
}
