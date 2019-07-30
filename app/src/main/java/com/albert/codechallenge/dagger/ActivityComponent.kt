package com.albert.codechallenge.dagger


import com.albert.codechallenge.ui.MainActivity

import javax.inject.Singleton
import dagger.Component


@Singleton
@Component(modules = [AppModule::class, NetModule::class, ApiModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)
}

