package com.gazilla.mihail.gazillaj.utils.dagger2.Modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context context(){
        return context.getApplicationContext();
    }
}
