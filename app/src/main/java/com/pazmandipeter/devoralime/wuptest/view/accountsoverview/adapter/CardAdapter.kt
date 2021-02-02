package com.pazmandipeter.devoralime.wuptest.view.accountsoverview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pazmandipeter.devoralime.wuptest.databinding.LayoutCardItemBinding
import com.pazmandipeter.devoralime.wuptest.model.Account
import com.pazmandipeter.devoralime.wuptest.utils.Utilities

class CardAdapter : ListAdapter<Account, CardAdapter.CardViewHolder>(
    AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<Account>() {
        override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
            return oldItem.cardId == newItem.cardId
        }

        override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
            return oldItem == newItem
        }
    }).build())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = LayoutCardItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        val currentItem = getItem(position)
        holder.bind(currentItem.cardImage.toIntOrNull())

    }

    inner class CardViewHolder(val binding: LayoutCardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cardImageIndex: Int?) {
            cardImageIndex?.let {
                Glide.with(binding.root)
                    .load(Utilities.listOfCards[cardImageIndex - 1])
                    .into(binding.ivCard)
            }
        }

    }
}