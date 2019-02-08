package com.gazilla.mihail.gazillaj.utils.dagger2.Modules;


import com.gazilla.mihail.gazillaj.model.data.api.ServerApi;
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = ServerApiModule.class)
public class RepositoriApiModule {

    @Provides
    public RepositoryApi getRepositoryAPI(ServerApi serverApi){
        return new RepositoryApi(serverApi);
    }
}
