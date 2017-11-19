package myapp.scichartexample.Services.Implementations;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import myapp.scichartexample.Services.PointsGeneratorService;

/**
 * Created by Julia on 19.11.2017.
 */

public class WebPointsGeneratorService extends PointsGeneratorService {

    @Override
    public Intent initialize(Context context) {
        return new Intent(context, WebPointsGeneratorService.class);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
