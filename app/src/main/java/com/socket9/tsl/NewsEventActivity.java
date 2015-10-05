package com.socket9.tsl;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.socket9.tsl.API.ApiService;
import com.socket9.tsl.API.MyCallback;
import com.socket9.tsl.ModelEntities.NewsEventEntity;
import com.socket9.tsl.Models.NewsEvent;
import com.socket9.tsl.Utils.Singleton;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;
import timber.log.Timber;

public class NewsEventActivity extends BaseActivity {

    @Bind(R.id.toolbarTitle)
    TextView toolbarTitle;
    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.webView)
    WebView webView;
    boolean isNews;
    int id;
    @Bind(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_event);
        ButterKnife.bind(this);
        isNews = getIntent().getBooleanExtra("isNews", true);
        initToolbar(myToolbar, "", true);
        id = getIntent().getIntExtra("id", 0);
        progress.setVisibility(View.VISIBLE);
        if (isNews)
            getNews(id);
        else
            getEvent(id);
    }

    public void getNews(int id) {
        ApiService.getTSLApi().getNew(Singleton.getInstance().getToken(), id, new MyCallback<NewsEvent>() {
            @Override
            public void good(NewsEvent m, Response response) {
                setInfo(m.getData());
            }

            @Override
            public void bad(String error) {
                Timber.d(error);
            }
        });
    }

    public void getEvent(int id) {
        ApiService.getTSLApi().getEvent(Singleton.getInstance().getToken(), id, new MyCallback<NewsEvent>() {
            @Override
            public void good(NewsEvent m, Response response) {
                setInfo(m.getData());
            }

            @Override
            public void bad(String error) {
                Timber.d(error);
            }
        });
    }

    public void setInfo(NewsEventEntity entity) {
//        boolean isBlue = entity.getType().equalsIgnoreCase("service");
        webView.loadUrl(entity.getContentEn());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                progress.setVisibility(View.GONE);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
