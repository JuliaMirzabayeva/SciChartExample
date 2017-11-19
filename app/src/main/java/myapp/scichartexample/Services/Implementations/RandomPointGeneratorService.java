package myapp.scichartexample.Services.Implementations;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import myapp.scichartexample.Services.PointsGeneratorService;

/**
 * Created by Julia on 18.11.2017.
 */

public class RandomPointGeneratorService extends PointsGeneratorService {

    private final double rangeMin = -1;
    private final double rangeMax = 1;
    private double randomValue = 0.0;
    private Timer timer = new Timer();
    private GeneratePointTask myTask;

    @Override
    public void onCreate() {
        super.onCreate();
        myTask = new GeneratePointTask();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer.scheduleAtFixedRate(myTask, 0, 1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        myTask.cancel();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class GeneratePointTask extends TimerTask {
        public void run() {
            Random random = new Random();
            randomValue += rangeMin + (rangeMax - rangeMin) * random.nextDouble();
            sendPointIntent(randomValue);
        }
    }

    @Override
    public Intent initialize(Context context) {
        return new Intent(context, RandomPointGeneratorService.class);
    }
}
