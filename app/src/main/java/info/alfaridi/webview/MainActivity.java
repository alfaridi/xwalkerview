package info.alfaridi.webview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

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

        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Generate token");

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
                textView.setText("Token: " + vtToken.getToken_id());

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(vtToken.getRedirect_url()));
                Log.i("VT", vtToken.getRedirect_url());
                startActivity(browserIntent);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

}
