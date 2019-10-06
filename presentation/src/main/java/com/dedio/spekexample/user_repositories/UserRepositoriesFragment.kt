package com.dedio.spekexample.user_repositories


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.dedio.spekexample.BR
import com.dedio.spekexample.R
import com.dedio.spekexample.base.BaseFragment
import com.dedio.spekexample.databinding.FragmentUserRepositoriesBinding
import com.dedio.spekexample.di.components.ActivityComponent
import com.dedio.spekexample.models.UserRepositoryUiModel
import com.dedio.spekexample.util.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_user_repositories.*
import javax.inject.Inject

class UserRepositoriesFragment : BaseFragment() {

    private lateinit var viewModel: UserRepositoriesViewModel
    override fun getViewModel() = viewModel

    private val args by navArgs<UserRepositoriesFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var repositoriesAdapter: RepositoriesAdapter

    override fun makeInject(component: ActivityComponent) = component.inject(this)

    override fun initViewModel() {
        viewModel = getViewModel(requireActivity(), viewModelFactory)
        viewModel.userName.value = args.userName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = getBinding<FragmentUserRepositoriesBinding>(
            viewModel = viewModel,
            vmVariable = BR.viewModel, layoutId = R.layout.fragment_user_repositories,
            inflater = inflater, container = container
        )

        return binding.root
    }

    override fun observeEvents() {
        viewModel.userRepositories.observe {
            showRepositories(it.repositories)
        }
    }

    private fun showRepositories(list: List<UserRepositoryUiModel>) {
        repositoriesAdapter.submitList(list)

        if(userRepositoriesRecycler.adapter == null) {
            userRepositoriesRecycler.adapter = repositoriesAdapter
        }
    }
}
