package com.dedio.spekexample.repository_commits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.dedio.spekexample.BR
import com.dedio.spekexample.R
import com.dedio.spekexample.base.BaseFragment
import com.dedio.spekexample.databinding.FragmentNameInputBinding
import com.dedio.spekexample.databinding.FragmentRepositoryCommitsBinding
import com.dedio.spekexample.di.components.ActivityComponent
import com.dedio.spekexample.models.RepositoryCommitUiModel
import com.dedio.spekexample.util.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_repository_commits.*
import javax.inject.Inject

class RepositoryCommitsFragment : BaseFragment() {

    private lateinit var viewModel: RepositoryCommitsViewModel
    override fun getViewModel() = viewModel

    private val args by navArgs<RepositoryCommitsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var repositoryCommitsAdapter: RepositoryCommitsAdapter

    override fun makeInject(component: ActivityComponent) = component.inject(this)

    override fun initViewModel() {
        viewModel = getViewModel(requireActivity(), viewModelFactory)
        viewModel.repository.value = args.repository
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = getBinding<FragmentRepositoryCommitsBinding>(
                viewModel = viewModel,
                vmVariable = BR.viewModel, layoutId = R.layout.fragment_repository_commits,
                inflater = inflater, container = container
        )

        return binding.root
    }

    override fun observeEvents() {
        viewModel.commits.observe {
            showRepositories(it.repositories)
        }
        viewModel.errorMessage.observe {
            showSnackbar(it)
        }
    }

    private fun showRepositories(list: List<RepositoryCommitUiModel>) {
        repositoryCommitsAdapter.submitList(list)

        if(repositoryCommitsRecycler.adapter == null) {
            repositoryCommitsRecycler.adapter = repositoryCommitsAdapter
        }
    }
}
