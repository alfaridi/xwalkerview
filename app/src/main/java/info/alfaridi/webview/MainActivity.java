package info.alfaridi.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import id.co.veritrans.android.api.VTDirect;
import id.co.veritrans.android.api.VTInterface.ITokenCallback;
import id.co.veritrans.android.api.VTModel.VTCardDetails;
import id.co.veritrans.android.api.VTModel.VTToken;
import id.co.veritrans.android.api.VTUtil.VTConfig;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);

        VTDirect vtDirect = new VTDirect();
        VTConfig.CLIENT_KEY = "d4b273bc-201c-42ae-8a35-c9bf48c1152b";
        VTConfig.VT_IsProduction = true;

        VTCardDetails cardDetails = new VTCardDetails();
        cardDetails.setCard_number("");
        cardDetails.setCard_cvv("123");
        cardDetails.setCard_exp_month(3);
        cardDetails.setCard_exp_year(2019);
        cardDetails.setSecure(true);
        cardDetails.setGross_amount(50000 + "");

        vtDirect.setCard_details(cardDetails);

        vtDirect.getToken(new ITokenCallback() {
            @Override
            public void onSuccess(VTToken vtToken) {
                if(vtToken.getRedirect_url() != null) {
                    WebView webview = (WebView) findViewById(R.id.webView);
                    webview.setWebViewClient(new VtResourceClient());
                    webview.setPadding(0, 0, 0, 0);
                    webview.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent motionEvent) {
                            switch (motionEvent.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                case MotionEvent.ACTION_UP:
                                    if (!v.hasFocus()) {
                                        v.requestFocus();
                                    }
                                    break;
                            }

                            return false;
                        }
                    });
                    webview.setWebChromeClient(new WebChromeClient());
                    webview.loadUrl(vtToken.getRedirect_url());
                }

            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private class VtResourceClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            Log.i("VT", "Start load " + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            Log.i("VT", "Finish load " + url);

            if(url.startsWith("https://api.veritrans.co.id/v2/token/callback")) {
                Toast.makeText(MainActivity.getContext(), "Callback success, continue to charging.", Toast.LENGTH_LONG).show();
            }
        }
    }

}
