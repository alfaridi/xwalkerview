package info.alfaridi.webview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

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
        VTConfig.CLIENT_KEY = "VT-client-SimkwEjR3_fKj73D";
        VTConfig.VT_IsProduction = false;

        VTCardDetails cardDetails = new VTCardDetails();
        cardDetails.setCard_number("4811111111111114");
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
                    XWalkView webview = (XWalkView) findViewById(R.id.vtwebview);
                    webview.setResourceClient(new VtXWalkResource(webview));
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

                    webview.load(vtToken.getRedirect_url(), null);
                }

            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private class VtXWalkResource extends XWalkResourceClient {

        public VtXWalkResource(XWalkView view) {
            super(view);
        }

        @Override
        public void onLoadStarted(XWalkView view, String url) {
            super.onLoadStarted(view, url);
        }

        @Override
        public void onLoadFinished(XWalkView view, String url) {
            super.onLoadFinished(view, url);
            if(url.startsWith("https://api.sandbox.veritrans.co.id/v2/token/callback")) {
                Toast.makeText(MainActivity.getContext(), "Callback success, continue to charging.", Toast.LENGTH_LONG).show();
            }
        }

    }
}
