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

import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.Adapters.NewsAdapter;
import com.socket9.tsl.MainActivity;
import com.socket9.tsl.ModelEntities.NewsEventEntity;
import com.socket9.tsl.Models.ListNewsEvent;
import com.socket9.tsl.NewsEventActivity;
import com.socket9.tsl.R;
import com.socket9.tsl.Utils.Singleton;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private NewsAdapter.OnCardClickListener listener;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, rootView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getNews();
        initOnCardClickListener();
        return rootView;
    }

    public void initOnCardClickListener(){
        listener = new NewsAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(NewsEventEntity viewHolder) {
//                Timber.d(viewHolder.getId()+"");
                Intent intent = new Intent(getActivity(), NewsEventActivity.class);
                intent.putExtra("id", viewHolder.getId());
                intent.putExtra("isNews", true);
                startActivity(intent);
            }
        };
    }

    public void getNews(){
        ApiService.getTSLApi().getListNews(Singleton.getInstance().getToken(), new MyCallback<ListNewsEvent>() {
            @Override
            public void good(ListNewsEvent m, Response response) {
                try{
                    NewsAdapter newsAdapter = new NewsAdapter(m.getData());
                    newsAdapter.setOnCardClickListener(listener);
                    recyclerView.setAdapter(newsAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void bad(String error) {
                Timber.i(error);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).onFragmentAttached(MainActivity.FRAGMENT_DISPLAY_NEWS);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
