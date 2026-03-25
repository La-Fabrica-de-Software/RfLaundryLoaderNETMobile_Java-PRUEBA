package com.lafabricadesoftware.rfidlaundry.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.lafabricadesoftware.rfidlaundry.data.data_source.config.ConfigDataCollector
import com.lafabricadesoftware.rfidlaundry.data.data_source.local.LocalDatabase
import com.lafabricadesoftware.rfidlaundry.data.data_source.remote.RemoteDataCollector
import com.lafabricadesoftware.rfidlaundry.data.repository.ConfigRepositoryImpl
import com.lafabricadesoftware.rfidlaundry.data.repository.LocalRepositoryImpl
import com.lafabricadesoftware.rfidlaundry.data.repository.RemoteRepositoryImpl
import com.lafabricadesoftware.rfidlaundry.domain.repository.ConfigRepository
import com.lafabricadesoftware.rfidlaundry.domain.repository.LocalRepository
import com.lafabricadesoftware.rfidlaundry.domain.repository.RemoteRepository
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.AntenaCommonUseCases
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.ClienteCommonUseCases
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.PrendaCommonUseCases
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.antena.GetAntenasByPuestoCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.antena.GetAntenasCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.antena.GetAntenasPuestosCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.cliente.GetClientesCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.movpren.SetMovPrenCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.movpren.SyncPendingMovimientosCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.prenda.GetPrendaByTagCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.prenda.GetPrendaClienteSubClienteByTagCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.common.prenda.GetPrendasCommon
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.config.ConfiguracionUseCases
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.config.GetConfiguracion
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.config.SetConfiguracion
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.cliente.AddCliente
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.cliente.DeleteCliente
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.cliente.GetCliente
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.cliente.GetClientesLocal
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.ClienteLocalUseCases
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.PrendaLocalUseCases
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.local.prenda.GetPrendasLocal
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.ClienteRemoteUseCases
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.PrendaRemoteUseCases
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.SubClienteRemoteUseCases
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.TestUseCases
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.cliente.GetClientesRemote
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.prenda.GetPrendasRemote
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.subcliente.GetSubClientesRemote
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.test.InitConnectionRemote
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.test.TestConnectionRemote
import com.lafabricadesoftware.rfidlaundry.domain.use_cases.remote.test.TestConnectionWithParametersRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //region Local
    @Provides
    @Singleton
    fun provideLocalDatabase(app: Application): LocalDatabase {
        return Room.databaseBuilder(app, LocalDatabase::class.java, LocalDatabase.DATABASE_NAME)
            .addMigrations(LocalDatabase.MIGRATION_13_14)
            .build()
    }
    @Provides
    @Singleton
    fun provideLocalRepository(db: LocalDatabase): LocalRepository {
        return LocalRepositoryImpl(db.localDao)
    }
    @Provides
    @Singleton
    fun provideLocalClienteUseCases(repository: LocalRepository): ClienteLocalUseCases {
        return ClienteLocalUseCases(
            getClientesLocal = GetClientesLocal(repository),
            getCliente = GetCliente(repository),
            addCliente = AddCliente(repository),
            deleteCliente = DeleteCliente(repository)
        )
    }
    @Provides
    @Singleton
    fun provideLocalPrendaUseCases(repository: LocalRepository): PrendaLocalUseCases {
        return PrendaLocalUseCases(
            getPrendasLocal = GetPrendasLocal(repository)
        )
    }
    //endregion

    //region Remote
    @Provides
    @Singleton
    fun provideRemoteRepository(data: RemoteDataCollector): RemoteRepository {
        return RemoteRepositoryImpl(data)
    }
    @Provides
    @Singleton
    fun provideTestConnectionRemoteUseCases(repository: RemoteRepository): TestUseCases {
        return TestUseCases(
            initConnectionRemote = InitConnectionRemote(repository),
            testConnectionRemote = TestConnectionRemote(repository),
            testConnectionWithParametersRemote = TestConnectionWithParametersRemote(repository)
        )
    }
    @Provides
    @Singleton
    fun provideClienteRemoteUseCases(repository: RemoteRepository): ClienteRemoteUseCases {
        return ClienteRemoteUseCases(
            getClientesRemote = GetClientesRemote(repository)
        )
    }
    @Provides
    @Singleton
    fun provideSubClienteRemoteUseCases(repository: RemoteRepository): SubClienteRemoteUseCases {
        return SubClienteRemoteUseCases(
            getSubClientesRemote = GetSubClientesRemote(repository)
        )
    }
    @Provides
    @Singleton
    fun providePrendaRemoteUseCases(repository: RemoteRepository): PrendaRemoteUseCases {
        return PrendaRemoteUseCases(
            getPrendasRemote = GetPrendasRemote(repository)
        )
    }
    //endregion

    //region Common
    @Provides
    @Singleton
    fun provideClienteCommonUseCases(repositoryLocal: LocalRepository, repositoryRemote: RemoteRepository): ClienteCommonUseCases {
        return ClienteCommonUseCases(
            getClientesCommon = GetClientesCommon(repositoryLocal, repositoryRemote)
        )
    }
    @Provides
    @Singleton
    fun providePrendaCommonUseCases(repositoryLocal: LocalRepository, repositoryRemote: RemoteRepository): PrendaCommonUseCases {
        return PrendaCommonUseCases(
            getPrendasCommon = GetPrendasCommon(repositoryLocal, repositoryRemote),
            getPrendaByTagCommon = GetPrendaByTagCommon(repositoryLocal, repositoryRemote),
            getPrendaClienteSubClienteByTagCommon = GetPrendaClienteSubClienteByTagCommon(repositoryLocal, repositoryRemote),
            setMovPrenCommon = SetMovPrenCommon(repositoryLocal, repositoryRemote),
            syncPendingMovimientosCommon = SyncPendingMovimientosCommon(repositoryLocal, repositoryRemote)
        )
    }
    @Provides
    @Singleton
    fun provideAntenasCommonUseCases(repositoryLocal: LocalRepository, repositoryRemote: RemoteRepository): AntenaCommonUseCases {
        return AntenaCommonUseCases(
            getAntenasCommon = GetAntenasCommon(repositoryLocal, repositoryRemote),
            getAntenasPuestosCommon = GetAntenasPuestosCommon(repositoryLocal, repositoryRemote),
            getAntenasByPuestoCommon = GetAntenasByPuestoCommon(repositoryLocal, repositoryRemote)
        )
    }
    //endregion

    //region Configuracion
    @Provides
    @Singleton
    fun provideConfigurationRepository(data: ConfigDataCollector): ConfigRepository {
        return ConfigRepositoryImpl(data)
    }
    @Provides
    @Singleton
    fun provideConfiguracionUseCases(repository: ConfigRepository): ConfiguracionUseCases {
        return ConfiguracionUseCases(
            getConfiguracion = GetConfiguracion(repository),
            setConfiguracion = SetConfiguracion(repository)
        )
    }
    //endregion

    //region Context
    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext
    //endregion

}