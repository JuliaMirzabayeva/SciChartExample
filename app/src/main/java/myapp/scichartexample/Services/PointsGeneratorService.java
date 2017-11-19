package myapp.scichartexample.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import myapp.scichartexample.Activities.MainActivity;

/**
 * Created by Julia on 19.11.2017.
 */

public abstract class PointsGeneratorService extends Service {

    public void sendPointIntent(Double rate){
        Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
        intent.putExtra(MainActivity.RANDOM_POINT_RATE, rate);
        intent.putExtra(MainActivity.RANDOM_POINT_DATE, System.currentTimeMillis());
        sendBroadcast(intent);
    }
    public abstract Intent initialize(Context context);
}
