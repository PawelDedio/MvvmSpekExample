package com.dedio.spekexample.name_input


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.dedio.spekexample.BR

import com.dedio.spekexample.R
import com.dedio.spekexample.base.BaseFragment
import com.dedio.spekexample.databinding.FragmentNameInputBinding
import com.dedio.spekexample.di.components.ActivityComponent
import com.dedio.spekexample.util.ViewModelFactory
import javax.inject.Inject

class NameInputFragment : BaseFragment() {

    private lateinit var viewModel: NameInputViewModel
    override fun getViewModel() = viewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun makeInject(component: ActivityComponent) = component.inject(this)

    override fun initViewModel() {
        viewModel = getViewModel(requireActivity(), viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = getBinding<FragmentNameInputBinding>(
            viewModel = viewModel,
            vmVariable = BR.viewModel, layoutId = R.layout.fragment_name_input,
            inflater = inflater, container = container
        )

        return binding.root
    }

    override fun observeEvents() {
        viewModel.navigateToRepositoriesAction.observe {
            val action = NameInputFragmentDirections.actionNameInputFragmentToUserRepositoriesFragment(it)
            view?.findNavController()?.navigate(action)
        }
    }
}
