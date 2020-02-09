package com.app.kenny.testcomponents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    String url = "https://www.youtube.com/embed/DPH3F0v1jB0";
    private WebView mWebView;
    private boolean mIsPaused = false;
    private ImageView img_conf;
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        img_conf =  findViewById(R.id.img_conf);
        Drawable img = img_conf.getDrawable();
        if (img instanceof AnimatedVectorDrawableCompat) {
            AnimatedVectorDrawableCompat icon = (AnimatedVectorDrawableCompat) img;
            icon.start();
        } else if (img instanceof AnimatedVectorDrawable) {
            AnimatedVectorDrawable icon = (AnimatedVectorDrawable) img;
            icon.start();
        }
        getSupportActionBar().hide();
        String media_url = url;

        img_conf.setOnClickListener(v -> {
            drawer = findViewById(R.id.drawer_layout);
            if(!drawer.isDrawerOpen(GravityCompat.START)) drawer.openDrawer(GravityCompat.START);
            else drawer.closeDrawer(GravityCompat.END);
        });
        mWebView = (WebView) findViewById(R.id.webview_video);
        mWebView.setWebChromeClient(new WebChromeClient());

        WebSettings ws = mWebView.getSettings();
        ws.setBuiltInZoomControls(true);
        ws.setJavaScriptEnabled(true);

        mIsPaused = true;
        resumeBrowser();
        mWebView.loadUrl(media_url);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
    }

    @Override
    protected void onPause()
    {
        pauseBrowser();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        resumeBrowser();
        super.onResume();
    }

    private void pauseBrowser()
    {
        if (!mIsPaused)
        {
            // pause flash and javascript etc
            callHiddenWebViewMethod(mWebView, "onPause");
            mWebView.pauseTimers();
            mIsPaused = true;
        }
    }

    private void resumeBrowser()
    {
        if (mIsPaused)
        {
            // resume flash and javascript etc
            callHiddenWebViewMethod(mWebView, "onResume");
            mWebView.resumeTimers();
            mIsPaused = false;
        }
    }

    private void callHiddenWebViewMethod(final WebView wv, final String name)
    {
        try
        {
            final Method method = WebView.class.getMethod(name);
            method.invoke(mWebView);
        } catch (final Exception e)
        {}
    }

}
