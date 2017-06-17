package disertatie.com.disertatie;


import android.app.ListActivity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;
import java.util.concurrent.RunnableFuture;

import disertatie.com.disertatie.Constants.Constants;
import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.activities.ListaCereriOfertaActivity;
import disertatie.com.disertatie.entities.CerereOferta;

/**
 * Created by roxana on 16.05.2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG="Logistica";

    public int codDocument;
    public double cantitate;
    public double pret;
    public String dataLivrare;
    public Context context = getApplication();
    CerereOferta.Status status;
    NotificationCompat.Builder mBuilder;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i(TAG,"Message received : "+remoteMessage.toString());

        if(mBuilder == null) {
            mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("My notification")
                    .setContentText("Hello World!");
        }
       // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map data = remoteMessage.getData();
            status = CerereOferta.Status.valueOf(data.get("status").toString());
            if(status.equals(CerereOferta.Status.MODIFICAT)) {
                codDocument = Integer.parseInt(data.get("codDocument").toString());
                cantitate = Double.parseDouble(data.get("cantitate").toString());
                pret = Double.parseDouble(data.get("pret").toString());
                dataLivrare = String.valueOf(data.get("dataLivrare"));
                Log.d(TAG,"Cerere Oferta -" +
                        " codDocument="+codDocument+", cantitate="+cantitate+", pret="+pret+", dataLivrare="+dataLivrare
                        +"status="+status);
                //sendMessageToActivity(this, codDocument, cantitate, pret, dataLivrare, status);
                DatabaseHelper databaseHelper = new DatabaseHelper(this);
                databaseHelper.updateCerereOferta(codDocument, cantitate, pret, dataLivrare, status);
                mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Cerere de oferta modificata")
                        .setContentText(String.format("codDocument=%d\ncantitate=%f\npret=%f\ndataLivrare=%s",
                                codDocument,cantitate,pret,dataLivrare));
                int mNotificationId = 001;
                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
                final ListaCereriOfertaActivity i = ListaCereriOfertaActivity.getInstance();
                if(i!=null)
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            i.updateAdapter();
                        }
                    });
                }
            }else if(status.equals(CerereOferta.Status.ACCEPTAT)) {
                codDocument = Integer.parseInt(data.get("codDocument").toString());
                Log.d(TAG,"Cerere Oferta -" +
                        " codDocument="+codDocument+","+"status="+status);
                //sendMessageToActivity(this, codDocument, -1, -1, "", status);
                DatabaseHelper databaseHelper = new DatabaseHelper(this);
                databaseHelper.updateCerereOferta(codDocument, status);
                mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Cerere de oferta acceptata")
                        .setContentText(String.format("codDocument=%d",
                                codDocument));
                int mNotificationId = 001;
                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
                final ListaCereriOfertaActivity i = ListaCereriOfertaActivity.getInstance();
                if(i!=null)
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            i.updateAdapter();
                        }
                    });
                }
            }else{
                Log.e(TAG, "Status invalid primit=->"+data);
            }

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
               // handleNow();
            }
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
  }

    private static void sendMessageToActivity(Context context, int codDocument, double cantitate,
                                              double pret, String dataLivrare, CerereOferta.Status status) {
        Intent intent = new Intent("updateCerereOferta");
        intent.putExtra("codDocument", codDocument);
        intent.putExtra("cantitate", cantitate);
        intent.putExtra("pret", pret);
        intent.putExtra("dataLivrare", dataLivrare);
        intent.putExtra("status",status);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        Log.d(TAG,"sendMessageToActivity-bam-worked");
    }
}
