package myapp.scichartexample.Dagger.Modules;

/**
 * Created by Julia on 19.11.2017.
 */

import dagger.Module;
import dagger.Provides;
import myapp.scichartexample.Services.PointsGeneratorService;
import myapp.scichartexample.Services.Implementations.RandomPointGeneratorService;

/**
 * Created by Julia on 19.11.2017.
 */
@Module
public class PointsModule {
    @Provides
    PointsGeneratorService providePointGenerator(){
        return new RandomPointGeneratorService();
        // return new WebPointsGeneratorService();
    }
}
