package myapp.scichartexample;

import android.app.Application;

import myapp.scichartexample.Dagger.Components.AppComponent;
import myapp.scichartexample.Dagger.Components.DaggerAppComponent;

/**
 * Created by Julia on 19.11.2017.
 */

public class App extends Application {
    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();
    }

    public static AppComponent getComponent() {
        return component;
    }
}
