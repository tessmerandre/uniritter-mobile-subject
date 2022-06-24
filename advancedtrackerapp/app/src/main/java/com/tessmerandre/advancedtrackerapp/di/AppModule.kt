package com.tessmerandre.advancedtrackerapp.di

import com.tessmerandre.advancedtrackerapp.data.database.AppDatabase
import com.tessmerandre.advancedtrackerapp.data.location.LocationRepository
import com.tessmerandre.advancedtrackerapp.data.login.LoginRepository
import com.tessmerandre.advancedtrackerapp.data.logout.LogoutUseCase
import com.tessmerandre.advancedtrackerapp.data.remote.LocationRemoteDataSource
import com.tessmerandre.advancedtrackerapp.data.splash.SplashRepository
import com.tessmerandre.advancedtrackerapp.data.splash.SplashResponse
import com.tessmerandre.advancedtrackerapp.ui.admin.AdminViewModel
import com.tessmerandre.advancedtrackerapp.ui.login.LoginViewModel
import com.tessmerandre.advancedtrackerapp.ui.operator.OperatorViewModel
import com.tessmerandre.advancedtrackerapp.ui.splash.SplashViewModel
import com.tessmerandre.advancedtrackerapp.worker.UploadPendingLocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object Named {
    const val COROUTINE_SCOPE = "coroutine_scope"
}

val roomModule = module {
    single { AppDatabase.getDatabase(androidApplication()) }
    factory { get<AppDatabase>().userDao() }
    factory { get<AppDatabase>().locationDao() }
}

val commonModule = module {
    factory { LogoutUseCase(get()) }
    factory { LocationRepository(get()) }
    factory(named(Named.COROUTINE_SCOPE)) { CoroutineScope(Job() + Dispatchers.IO) }
    factory { LocationRemoteDataSource() }
    factory { UploadPendingLocationRepository(get(), get()) }
}

val splashModule = module {
    factory { SplashRepository(get()) }
    viewModel { SplashViewModel(get()) }
}

val loginModule = module {
    factory { LoginRepository(get()) }
    viewModel { LoginViewModel(get()) }
}

val adminModule = module {
    viewModel { AdminViewModel(get()) }
}

val operatorModule = module {
    viewModel { OperatorViewModel(get()) }
}

val appModule = module {
    includes(
        roomModule,
        commonModule,
        splashModule,
        loginModule,
        adminModule,
        operatorModule
    )
}
