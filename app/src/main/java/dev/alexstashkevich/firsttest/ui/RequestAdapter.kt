package dev.alexstashkevich.firsttest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import dev.alexstashkevich.firsttest.common.BaseListAdapter
import dev.alexstashkevich.firsttest.databinding.ItemRequestBinding
import dev.alexstashkevich.firsttest.listener.TextToSpeech
import dev.alexstashkevich.firsttest.models.RequestItem

class RequestAdapter(val listener: TextToSpeech) : BaseListAdapter<RequestItem, ItemRequestBinding>(RequestItem.DIFF_CALLBACK) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ItemRequestBinding {
        val inflater = LayoutInflater.from(parent.context)
        return ItemRequestBinding.inflate(inflater, parent, false)
    }

    override fun bind(binding: ItemRequestBinding, item: RequestItem) {
        binding.tvRequest.text = item.request
        binding.tvResponse.text = item.response

        // click play
        binding.imgBtPlay.setOnClickListener {
            listener.speech(item.response)
        }
    }
}