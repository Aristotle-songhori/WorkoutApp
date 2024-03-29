/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featurebmi.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.AddBmiUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.DeleteAllBmiUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.GetAllBmiUseCase
import com.rssll971.fitnessassistantapp.featurebmi.calculation.di.DaggerBmiCalculationComponent
import com.rssll971.fitnessassistantapp.featurebmi.history.di.DaggerBmiHistoryComponent
import kotlin.properties.Delegates

/**
 * NOTE: This approach is implemented within the Module Scope, since its separate parts
 * request the same dependencies. However, method can be used for each component separately.
 * */

/**
 * Interface for implicit dependency on AppComponent
 * (due to feature-module doesn't know anything about app-module).
 * As declared properties listed all methods required for further injection
 * in the corresponding feature module.
 *
 * Note: Should be inherited in AppComponent
 * */
interface FeatureBmiDeps{
    val addBmiUseCase: AddBmiUseCase
    val deleteAllBmiUseCase: DeleteAllBmiUseCase
    val getAllBmiUseCase: GetAllBmiUseCase
}

/**
 * Interface provides required instances from appComponent
 * */
interface FeatureBmiDepsProvider{
    //Restrict getter to current module
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: FeatureBmiDeps

    //Real existed instance will be provided from DepsStore
    companion object : FeatureBmiDepsProvider by FeatureBmiDepsStore

}

/**
 * Explicit Singleton which must be assigned in [WorkoutApplication][com.rssll971.fitnessassistantapp.WorkoutApplication]
 * */
object FeatureBmiDepsStore: FeatureBmiDepsProvider {
    override var deps: FeatureBmiDeps by Delegates.notNull()
}


/**
 * ViewModel stores all instances of Components.
 * This implementation simplifies Component lifecycle handling.
 * Note: We can't store instances in the original (related to Fragment) ViewModel,
 * due to it being created by ViewModelFactory which should be injected too.
 * */
internal class FeatureBmiComponentsViewModel : ViewModel(){
    val bmiCalculationComponent = DaggerBmiCalculationComponent.builder().deps(
        FeatureBmiDepsProvider.deps).build()
    val bmiHistoryComponent = DaggerBmiHistoryComponent.builder().deps(FeatureBmiDepsProvider.deps).build()
}