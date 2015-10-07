package com.socket9.tsl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    @Bind(R.id.layoutProgress)
    LinearLayout layoutProgress;
    private ShareActionProvider mShareActionProvider;
    private Intent sendIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_event);
        ButterKnife.bind(this);
        isNews = getIntent().getBooleanExtra("isNews", true);
        initToolbar(myToolbar, "", true);
        id = getIntent().getIntExtra("id", 0);
        layoutProgress.setVisibility(View.VISIBLE);
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
            public void bad(String error, boolean isTokenExpired) {
                Timber.d(error);
                if (isTokenExpired) {
                    Singleton.toast(NewsEventActivity.this, "Someone has access your account, please login again.", Toast.LENGTH_LONG);
                    Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, "");
                    startActivity(new Intent(NewsEventActivity.this, SignInActivity.class));
                    finish();
                }
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
            public void bad(String error, boolean isTokenExpired) {
                Timber.d(error);
                if (isTokenExpired) {
                    Singleton.toast(NewsEventActivity.this, "Someone has access your account, please login again.", Toast.LENGTH_LONG);
                    Singleton.getInstance().setSharedPrefString(Singleton.SHARE_PREF_KEY_TOKEN, "");
                    startActivity(new Intent(NewsEventActivity.this, SignInActivity.class));
                    finish();
                }
            }
        });
    }

    public void setInfo(final NewsEventEntity entity) {
//        boolean isBlue = entity.getType().equalsIgnoreCase("service");
        webView.loadUrl(entity.getContentEn());
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
    public boolean onCreateOptionsMenu(Menu menu) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else {
            startActivity(Intent.createChooser(sendIntent, "Share to..."));
        }

        return super.onOptionsItemSelected(item);
    }
}
