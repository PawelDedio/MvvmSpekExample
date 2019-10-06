package com.dedio.spekexample.views.data_binding_lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.dedio.spekexample.models.BaseUiModel
import com.dedio.spekexample.views.base.BaseRecyclerViewAdapter

abstract class DataBindingAdapter<T : BaseUiModel>(
        itemCallback: DiffUtil.ItemCallback<T>) : BaseRecyclerViewAdapter<T, DataBindingViewHolder<T>>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)

        return DataBindingViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return provideLayoutResForViewType(position)
    }

    abstract fun provideLayoutResForViewType(position: Int): Int
}