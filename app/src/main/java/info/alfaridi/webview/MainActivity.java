package info.alfaridi.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.xwalk.core.XWalkView;

import id.co.veritrans.android.api.VTDirect;
import id.co.veritrans.android.api.VTInterface.ITokenCallback;
import id.co.veritrans.android.api.VTModel.VTCardDetails;
import id.co.veritrans.android.api.VTModel.VTToken;
import id.co.veritrans.android.api.VTUtil.VTConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VTDirect vtDirect = new VTDirect();
        VTConfig.CLIENT_KEY = "";
        VTConfig.VT_IsProduction = true;

        VTCardDetails cardDetails = new VTCardDetails();
        cardDetails.setCard_number("");
        cardDetails.setCard_cvv("");
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
}
