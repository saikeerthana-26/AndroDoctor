package com.example.androdoctor.upload;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Callback extends WebViewClient {

    public boolean shouldOverrideUrlLoading(
            WebView view, String url) {
        return(false);
    }
}
