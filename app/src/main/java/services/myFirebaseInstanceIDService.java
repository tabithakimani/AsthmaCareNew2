//package services;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//import android.util.Log;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
//
//public class myFirebaseInstanceIDService extends FirebaseInstanceIdService {
//    public myFirebaseInstanceIDService() {
//    }
//
//    private static final String TAG="myFirebaseIdService";
//    private static final String SUBSCRIBE_TO="user";
//
//    @Override
//    public void onTokenRefresh() {
//        super.onTokenRefresh();
//        String token = FirebaseInstanceId.getInstance().getToken();
//
//        // Once the token is generated, subscribe to topic with the userId
////        FirebaseMessaging.getInstance().subscribeToTopic(SUBSCRIBE_TO);
//        Log.i(TAG, "onTokenRefresh completed with token: " + token);
//    }
//}
