package disertatie.com.disertatie.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.entities.CerereOferta;

public class SplashScreenActivity extends Activity {

    private Handler handler;
    private Runnable runnable;
    DatabaseHelper databaseHelper;

    private static final String TAG="Logistica";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        databaseHelper =  new DatabaseHelper(this);

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


        GoogleApiAvailability g = GoogleApiAvailability.getInstance();
        if(g.isGooglePlayServicesAvailable(this)!=0)
        {
            g.makeGooglePlayServicesAvailable(this);
        }
        else
        {
            Log.i(TAG,"Google Play is available #1");
        }
        /*
        if(getIntent().getExtras() != null){
            try {
                Intent intent = getIntent();
                int codDocument = intent.getIntExtra("codDocument", -1);
                double cantitate = intent.getDoubleExtra("cantitate", -1);
                double pret = intent.getDoubleExtra("pret", -1);
                String dataLivrare = intent.getStringExtra("dataLivrare");
                CerereOferta.Status status = CerereOferta.Status.valueOf(intent.getStringExtra("status"));
                if(status.equals(CerereOferta.Status.ACCEPTAT)) {
                    databaseHelper.updateCerereOferta(codDocument, status);
                   // updateAdapter();
                }else if(status.equals(CerereOferta.Status.MODIFICAT)){
                    databaseHelper.updateCerereOferta(codDocument, cantitate, pret, dataLivrare, status);
                   // updateAdapter();
                }else{
                    Log.e(TAG, "Message Receiver Error Invalid Status="+status);
                }

            }catch (NullPointerException ex){
                Log.e(TAG, "Message Receiver Error="+ex.getMessage());
            }
        }*/
        handler.postDelayed(runnable, 2000);
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
