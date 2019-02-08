package com.gazilla.mihail.gazillaj.utils.dagger2.Modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.gazilla.mihail.gazillaj.model.data.db.AppDatabase;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class AppDatabaseModule {

    @Provides
    public AppDatabase appDatabase(Context context){
        return Room.databaseBuilder(context, AppDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();
    }
}
