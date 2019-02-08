package com.gazilla.mihail.gazillaj.utils.dagger2.Modules;

import com.gazilla.mihail.gazillaj.model.data.api.ServerApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = RetrofitModule.class)
public class ServerApiModule {

    @Provides
    public ServerApi serverApi(Retrofit retrofit){
        return retrofit.create(ServerApi.class);
    }
}
