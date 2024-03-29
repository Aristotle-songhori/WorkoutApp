/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.GetAllExercisesUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic.AddStatisticUseCase
import kotlin.properties.Delegates


/**
 * Interface for implicit dependency on AppComponent
 * (due to feature-module doesn't know anything about app-module).
 * As declared properties listed all methods required for further injection
 * in the corresponding feature module.
 *
 * Note: Should be inherited in AppComponent
 * */
interface OptionsFSDeps {
    val getAllExercisesUseCase: GetAllExercisesUseCase
    val addStatisticUseCase: AddStatisticUseCase
}
/**
 * Interface provides required instances from appComponent
 * */
interface OptionsFSDepsProvider{
    //Restrict getter to current module
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: OptionsFSDeps

    //Real existed instance will be provided from DepsStore
    companion object: OptionsFSDepsProvider by OptionsFSDepsStore
}
/**
 * Explicit Singleton which must be assigned in [WorkoutApplication][com.rssll971.fitnessassistantapp.WorkoutApplication]
 * */
object OptionsFSDepsStore: OptionsFSDepsProvider{
    override var deps: OptionsFSDeps by Delegates.notNull()
}
/**
 * ViewModel stores all instances of Components.
 * This implementation simplifies Component lifecycle handling.
 * Note: We can't store instances in the original (related to Fragment) ViewModel,
 * due to it being created by ViewModelFactory which should be injected too.
 * */
internal class OptionsFSComponentViewModel: ViewModel(){
    val optionsFSComponent = DaggerOptionsFSComponent.builder().deps(OptionsFSDepsProvider.deps).build()
}