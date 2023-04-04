package com.example.codereviewtask_jc51.presentation.breeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codereviewtask_jc51.R
import com.example.codereviewtask_jc51.databinding.CatBreedItemBinding
import com.example.codereviewtask_jc51.domain.model.CatPreview

class CatBreedsAdapter(
    private val onClickListener: (CatPreview) -> Unit,
    private val onAddToFavoriteListener: (CatPreview) -> Unit
): ListAdapter<CatPreview, CatBreedsAdapter.ViewHolder>(CatDiff()) {

    fun setItems(newList: List<CatPreview>) {
        submitList(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatBreedsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CatBreedItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  = holder.bind(getItem(position))

    inner class ViewHolder(val binding: CatBreedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CatPreview) {
            Glide.with(binding.root).load(item.picture).into(binding.imageThumb)
            binding.breedName.text = item.breed_name
            binding.catContainer.setOnClickListener {
                onClickListener.invoke(item)
            }
            if (item.isFavorite()) {
                binding.imageFavorite.setImageResource(R.drawable.ic_far_heart_selected)
            } else {
                binding.imageFavorite.setImageResource(R.drawable.ic_far_heart)
            }
            binding.imageFavorite.setOnClickListener {
                onAddToFavoriteListener.invoke(item)
            }
        }
    }

    private fun CatPreview.isFavorite() = this.favorite

    private class CatDiff : DiffUtil.ItemCallback<CatPreview>() {
        override fun areItemsTheSame(
            oldItem: CatPreview,
            newItem: CatPreview
        ): Boolean {
            return oldItem.breed_name == newItem.breed_name
        }

        override fun areContentsTheSame(
            oldItem: CatPreview,
            newItem: CatPreview
        ): Boolean {
            return oldItem == newItem
        }
    }
}