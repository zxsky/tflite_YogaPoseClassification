package com.example.android.tflitecamerademo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoseExampleFragment extends Fragment {

    public static final String newFragmentMessage = "PoseExample";

    private WebView webView;

    private String poseName;

    public PoseExampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        poseName = getArguments().getString(newFragmentMessage);
        //Log.i(newFragmentMessage, poseName);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pose_example, container, false);
        webView = (WebView) v.findViewById(R.id.webview);
        String url = "https://www.google.com/search?tbm=isch&q=yoga+pose+" + poseName;
        if(webView != null){
            webView.loadUrl(url);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return true;
                }
            });
        }
        return v;
    }

}
