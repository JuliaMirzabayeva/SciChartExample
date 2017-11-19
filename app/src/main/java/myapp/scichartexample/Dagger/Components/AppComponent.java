package myapp.scichartexample.Dagger.Components;

import dagger.Component;
import myapp.scichartexample.Dagger.Modules.PointsModule;
import myapp.scichartexample.Activities.MainActivity;

/**
 * Created by Julia on 19.11.2017.
 */
@Component(modules = {PointsModule.class})
public interface AppComponent {
    void injectsMainActivity(MainActivity mainActivity);
}
