package info.alfaridi.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.xwalk.core.XWalkView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XWalkView walkView = (XWalkView) findViewById(R.id.vtwebview);
        walkView.setPadding(0, 0, 0, 0);
        walkView.load("https://bbmpay.veritrans.co.id/merchant/#/login", null);
    }
}
