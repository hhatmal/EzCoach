package com.example.ezcoach

import android.app.Application
import com.example.auth.data.di.authDataModule
import com.example.auth.presentation.di.loginViewModelModule
import com.example.core.data.di.coreDataModule
import com.example.core.presentation.designsystem.BuildConfig
import com.example.ezcoach.di.appModule
import com.example.workout.data.di.workoutDataModule
import com.example.workout.presentation.overview.di.overviewViewModelModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class EzCoachApp: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@EzCoachApp)
            modules(
                appModule,
                coreDataModule,
                loginViewModelModule,
                authDataModule,
                overviewViewModelModule,
                workoutDataModule,
            )
        }
    }
}