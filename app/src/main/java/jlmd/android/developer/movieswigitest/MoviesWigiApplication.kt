package jlmd.android.developer.movieswigitest

import android.app.Application
import jlmd.android.developer.movieswigitest.core.di.repositoriesModule
import jlmd.android.developer.movieswigitest.core.di.useCaseModules
import jlmd.android.developer.movieswigitest.data.di.databaseModule
import jlmd.android.developer.movieswigitest.data.di.networkModule
import jlmd.android.developer.movieswigitest.data.di.serviceModule
import jlmd.android.developer.movieswigitest.views.di.viewModelsModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

private val appModules = listOf<Module>() +
        networkModule +
        databaseModule +
        repositoriesModule +
        useCaseModules +
        viewModelsModule +
        serviceModule

class MoviesWigiApplication : Application(){
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        initDI()
    }

    private fun initDI() {
        val applicationCoroutineScopeModule = module {
            single { applicationScope }
        }

        startKoin {
            androidContext(this@MoviesWigiApplication)
            modules(
                appModules + applicationCoroutineScopeModule
            )
        }
    }
}