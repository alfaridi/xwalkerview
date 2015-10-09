package info.alfaridi.webview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by alfaridi on 10/9/15.
 */
public class CallbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Go to charge");

        final Intent intent = getIntent();
        final String action = intent.getAction();

        if (Intent.ACTION_VIEW.equals(action)) {
            final List<String> segments = intent.getData().getPathSegments();
            String token = segments.get(3);
            Toast.makeText(MainActivity.getContext(), "Charge process here with token: " + token, Toast.LENGTH_LONG).show();
        }
    }

}
