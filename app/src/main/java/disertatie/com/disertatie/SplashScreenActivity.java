package disertatie.com.disertatie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {

    private Handler handler;
    private Runnable runnable;


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

    }
}
