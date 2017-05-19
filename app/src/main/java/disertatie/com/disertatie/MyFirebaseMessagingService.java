package disertatie.com.disertatie;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import disertatie.com.disertatie.activities.CerereOfertaActivity;

import static android.R.attr.id;

/**
 * Created by roxana on 16.05.2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG="Logistica";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i(TAG,"Message received : "+remoteMessage.toString());

        String cantitate = "";
        Object obj = remoteMessage.getData().get("cantitate");
        if (obj != null) {
            try {
                cantitate = obj.toString();
            } catch (Exception e) {
                cantitate = "";
                e.printStackTrace();
            }
        }

        String pret = "";
        obj = remoteMessage.getData().get("pret");
        if (obj != null) {
            try {
                pret = (String) obj;
            } catch (Exception e) {
                pret = "";
                e.printStackTrace();
            }
        }
        String data_livrare = "";
        obj = remoteMessage.getData().get("dataLivrare");
        if (obj != null) {
            try {
                data_livrare = (String) obj;
            } catch (Exception e) {
                data_livrare = "";
                e.printStackTrace();
            }
        }

        Intent intent = new Intent();
        PendingIntent pendingIntent;
        if (pret.equals("")) { // Simply run your activity
            intent = new Intent(this, CerereOfertaActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else { // open a link
            String url = "";
            if (!pret.equals("")) {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pret));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        }
        pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder notificationBuilder = null;

        try {
            notificationBuilder =  new NotificationCompat.Builder(this)
                   // .setSmallIcon(R.drawable.activities)          // don't need to pass icon with your message if it's already in your app !
                    .setContentTitle(URLDecoder.decode(getString(R.string.app_name), "UTF-8"))
                    .setContentText(URLDecoder.decode(cantitate, "UTF-8"))
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        if (notificationBuilder != null) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(id, notificationBuilder.build());
        } else {
            Log.d(TAG, "error NotificationManager");
        }
    }
  }
}
