package com.gazilla.mihail.gazillaj.utils.dagger2;

import com.gazilla.mihail.gazillaj.model.data.db.AppDatabase;
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi;
import com.gazilla.mihail.gazillaj.utils.dagger2.Modules.AppDatabaseModule;
import com.gazilla.mihail.gazillaj.utils.dagger2.Modules.RepositoriApiModule;

import dagger.Component;

@Component(modules = {RepositoriApiModule.class, AppDatabaseModule.class})
public interface InitializationComponent {
    RepositoryApi repositoryApiComponent();
    AppDatabase getAppDatabase();

}
