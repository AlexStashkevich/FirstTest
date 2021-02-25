package dev.alexstashkevich.firsttest.common

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<T, V: ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
): ListAdapter<T, BaseViewHolder<V>>(diffCallback) {

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        bind(holder.binding, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        val binding = createBinding(parent, viewType)
        return BaseViewHolder(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup, viewType: Int = 0): V
    protected abstract fun bind(binding: V, item: T)
}