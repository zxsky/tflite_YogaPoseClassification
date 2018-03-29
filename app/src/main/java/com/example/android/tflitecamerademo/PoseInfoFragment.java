package com.example.android.tflitecamerademo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoseInfoFragment extends Fragment {

    public final static String newFragmentMessage = "PoseInfo";

    private WebView webView;

    private String poseName;

    public PoseInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        poseName = getArguments().getString(newFragmentMessage);


        if(poseName.equals("warrior1")){
            poseName = "warrior-i";
        }else if(poseName.equals("warroir2")){
            poseName = "warrior-ii";
        }

        //Log.i(newFragmentMessage, poseName);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pose_info, container, false);
        webView = (WebView) v.findViewById(R.id.webview);
        String url = "https://www.yogajournal.com/poses/" + poseName + "-pose";

        if(webView != null){
            webView.loadUrl(url);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return true;
                }
            });
        }
        webView.setInitialScale(150);
        return v;
    }

}
