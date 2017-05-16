package disertatie.com.disertatie.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;

import disertatie.com.disertatie.R;

public class SplashScreenActivity extends Activity {

    private Handler handler;
    private Runnable runnable;

    private static final String TAG="Logistica";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                //go to next screen

                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        };
        handler.postDelayed(runnable, 2000);

        GoogleApiAvailability g = GoogleApiAvailability.getInstance();
        if(g.isGooglePlayServicesAvailable(this)!=0)
        {
            g.makeGooglePlayServicesAvailable(this);
        }
        else
        {
            Log.i(TAG,"Google Play is available #1");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GoogleApiAvailability g = GoogleApiAvailability.getInstance();
        if(g.isGooglePlayServicesAvailable(this)!=0)
        {
            g.makeGooglePlayServicesAvailable(this);
        }
        else
        {
            Log.i(TAG,"Google Play is available #2");
        }
    }
}
