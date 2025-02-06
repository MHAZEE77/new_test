package com.app.silky.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.silky.databinding.AdapterNewsItemBinding
import com.app.silky.model.Users
import javax.inject.Inject

class NewsAdapter @Inject constructor() : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: AdapterNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Users>() {
        override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {

            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
            //ivArticle.loadImageFromGlide(article.websiteurlToImage)
            tvTitle.text = article.name
            tvDescription.text = article.email
            tvSource.text = article.website
            tvPublished.text = article.username

        }

        holder.itemView.setOnClickListener {
            setArticleClickListener?.let {
                it(article)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var setArticleClickListener : ((article: Users)->Unit)? =null

    fun onArticleClicked(listener:(Users)->Unit){
        setArticleClickListener = listener
    }
}