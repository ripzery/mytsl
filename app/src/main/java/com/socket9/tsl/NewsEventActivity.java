package com.socket9.tsl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.socket9.tsl.Events.Bus.ApiFire;
import com.socket9.tsl.Events.Bus.ApiReceive;
import com.socket9.tsl.ModelEntities.NewsEventEntity;
import com.socket9.tsl.Utils.BusProvider;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsEventActivity extends BaseActivity {

    @Bind(R.id.toolbarTitle)
    TextView toolbarTitle;
    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.layoutProgress)
    LinearLayout layoutProgress;
    private ShareActionProvider mShareActionProvider;
    private Intent sendIntent;
    private boolean isAlreadyGetNewsOrEvent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_event);
        ButterKnife.bind(this);
        initToolbar(myToolbar, "", true);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isAlreadyGetNewsOrEvent)
            return;
        boolean isNews = getIntent().getBooleanExtra("isNews", true);
        int id = getIntent().getIntExtra("id", 0);
        if (isNews)
            getNews(id);
        else
            getEvent(id);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void getNews(int id) {
        BusProvider.post(new ApiFire.GetNew(id));
    }

    private void getEvent(int id) {
        BusProvider.post(new ApiFire.GetEvent(id));
    }

    @Subscribe
    public void onReceiveNews(ApiReceive.New m) {
        setInfo(m.getNewsEvent().getData());
        isAlreadyGetNewsOrEvent = true;
    }

    @Subscribe
    public void onReceiveEvent(ApiReceive.Event event) {
        setInfo(event.getNewsEvent().getData());
        isAlreadyGetNewsOrEvent = true;
    }

    private void setInfo(@NonNull final NewsEventEntity entity) {
//        boolean isBlue = entity.getType().equalsIgnoreCase("service");
        webView.loadUrl(entity.getContentEn());
        layoutProgress.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                layoutProgress.setVisibility(View.GONE);
                //Init share intent
                sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, entity.getTitleEn());
                sendIntent.putExtra(Intent.EXTRA_TEXT, url);
                setShareIntent(sendIntent);

            }

        });

//        Glide.with(this).load(entity.getPic()).centerCrop().into(ivPhoto);
//        tvTag.setText("Auto " + entity.getType());
//        tvTag.setBackgroundColor(ContextCompat.getColor(this, isBlue ? R.color.colorPrimary : R.color.colorTextSecondary));
//        tvTitle.setText(entity.getTitleEn());
//        tvDate.setText(entity.getDate());
//        tvContent.setText(entity.getTitleEn());
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_event, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);
        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        return true;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else {
            startActivity(Intent.createChooser(sendIntent, getString(R.string.share)));
        }

        return super.onOptionsItemSelected(item);
    }
}
