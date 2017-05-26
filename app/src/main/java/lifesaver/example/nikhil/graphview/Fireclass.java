package lifesaver.example.nikhil.graphview;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by nikhil on 23-05-2017.
 */

public class Fireclass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
