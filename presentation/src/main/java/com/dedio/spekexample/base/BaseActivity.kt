package com.dedio.spekexample.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.dedio.spekexample.common.features.HasComponent
import com.dedio.spekexample.di.Injector
import com.dedio.spekexample.di.components.ActivityComponent
import com.dedio.spekexample.util.ViewModelFactory

abstract class BaseActivity : AppCompatActivity(), HasComponent<ActivityComponent> {

    protected val activityComponent by lazy {
        Injector.getInstance(this).activityComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeInject(activityComponent!!)
    }

    inline fun <reified T : ViewModel> getViewModel(factory: ViewModelFactory) = ViewModelProviders.of(this, factory).get(T::class.java)

    override fun getComponent() = activityComponent

    abstract fun makeInject(component: ActivityComponent)

    fun <DataBinding : ViewDataBinding> getBinding(
        viewModel: ViewModel? = null,
        layoutId: Int,
        vmVariable: Int? = null
    ) = getBinding<DataBinding>(
        viewModel = viewModel,
        layoutId = layoutId,
        bindingVariable = vmVariable
    )

    fun <T> LiveData<T>.observe(onChanged: (T) -> Unit) {
        this.observe(this@BaseActivity, onChanged)
    }
}

fun <DataBinding : ViewDataBinding> FragmentActivity.getBinding(
    viewModel: ViewModel?,
    layoutId: Int,
    bindingVariable: Int? = null
): DataBinding {
    val viewDataBinding: DataBinding = DataBindingUtil.setContentView(this, layoutId)
    viewDataBinding.lifecycleOwner = this

    if (viewModel != null && bindingVariable != null) {
        viewDataBinding.setVariable(bindingVariable, viewModel)
    }

    return viewDataBinding
}