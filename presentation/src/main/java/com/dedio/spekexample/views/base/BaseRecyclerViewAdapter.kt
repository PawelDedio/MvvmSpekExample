package com.dedio.spekexample.views.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dedio.spekexample.models.BaseUiModel

abstract class BaseRecyclerViewAdapter<T : BaseUiModel, VH : BaseRecyclerViewHolder<T>>(
        itemCallback: DiffUtil.ItemCallback<T>) : ListAdapter<T, VH>(itemCallback) {

    var onItemClickListener: ((item: T) -> Unit)? = null
    var onItemLongClickListener: ((item: T) -> Unit)? = null

    override fun onBindViewHolder(holder: VH, position: Int) {
        initLayout(holder, getItem(position))
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(getItem(position))
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.invoke(getItem(position))
            onItemLongClickListener != null
        }
    }

    open fun initLayout(holder: VH, item: T) {
        holder.bind(item)
    }

    protected fun inflate(parent: ViewGroup, layoutResId: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
    }
}