package com.dedio.spekexample.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.dedio.spekexample.common.features.HasComponent
import com.dedio.spekexample.common.features.HasLoader
import com.dedio.spekexample.di.components.ActivityComponent
import com.dedio.spekexample.util.ViewModelFactory

abstract class BaseFragment : Fragment() {

    protected val activityComponent by lazy {
        (activity as HasComponent<ActivityComponent>).getComponent()
    }

    inline fun <reified T : ViewModel> getViewModel(factory: ViewModelFactory) = ViewModelProviders.of(this, factory).get(T::class.java)
    inline fun <reified T : ViewModel> getViewModel(activity: FragmentActivity, factory: ViewModelFactory) = ViewModelProviders.of(activity, factory).get(T::class.java)

    fun <DataBinding : ViewDataBinding> getBinding(
        viewModel: ViewModel? = null,
        layoutId: Int,
        vmVariable: Int? = null,
        inflater: LayoutInflater, container: ViewGroup?
    ) = getBinding<DataBinding>(
        viewModel = viewModel,
        layoutId = layoutId,
        bindingVariable = vmVariable,
        inflater = inflater,
        container = container
    )

    fun <T> LiveData<T>.observe(onChanged: (T) -> Unit) {
        this.observe(viewLifecycleOwner, onChanged)
    }

    fun <T> LiveData<T>.observe(observer: Observer<T>) {
        this.observe(viewLifecycleOwner, observer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeInject(activityComponent!!)
        initViewModel()
        configureLoading()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents()
    }

    private fun configureLoading() {
        (activity as? HasLoader)?.observeLoading(getViewModel().isLoading)
    }

    protected open fun observeEvents() {
    }

    abstract fun makeInject(component: ActivityComponent)
    abstract fun initViewModel()
    abstract fun getViewModel(): BaseViewModel
}

fun <DataBinding : ViewDataBinding> Fragment.getBinding(
    viewModel: ViewModel?,
    layoutId: Int,
    bindingVariable: Int? = null,
    inflater: LayoutInflater,
    container: ViewGroup?
): DataBinding {
    val viewDataBinding: DataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
    viewDataBinding.lifecycleOwner = this

    if (viewModel != null && bindingVariable != null) {
        viewDataBinding.setVariable(bindingVariable, viewModel)
    }

    return viewDataBinding
}

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, onChanged: (T) -> Unit) {
    this.observe(lifecycleOwner, Observer {
        onChanged(it)
    })
}