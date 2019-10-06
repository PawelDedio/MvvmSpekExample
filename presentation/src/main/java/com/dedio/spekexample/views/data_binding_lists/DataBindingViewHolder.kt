package com.dedio.spekexample.views.data_binding_lists

import androidx.databinding.ViewDataBinding
import com.dedio.spekexample.BR
import com.dedio.spekexample.models.BaseUiModel
import com.dedio.spekexample.views.base.BaseRecyclerViewHolder

class DataBindingViewHolder<T : BaseUiModel>(private val binding: ViewDataBinding) : BaseRecyclerViewHolder<T>(binding.root) {

    override fun bind(item: T) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}