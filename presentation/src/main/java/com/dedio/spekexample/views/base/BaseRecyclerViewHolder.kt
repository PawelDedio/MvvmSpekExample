package com.dedio.spekexample.views.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dedio.spekexample.models.BaseUiModel

abstract class BaseRecyclerViewHolder<in T : BaseUiModel>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: T)
}