package disertatie.com.disertatie;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by george on 16.05.2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG="Logistica";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i(TAG,"Message received : "+remoteMessage.toString());
    }

}
