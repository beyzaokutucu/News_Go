package com.project.newsgo.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.newsgo.R
import com.project.newsgo.data.entity.Article
import com.project.newsgo.databinding.SingleNewsItemBinding
import com.project.newsgo.ui.fragments.HomeFragmentDirections

class NewsRecyclerViewAdapter(var newsList: List<Article>, var mContext: Context,val onItemClick:(article:Article)->Unit ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Header için ViewHolder
    }

    inner class NewsViewHolder(var view: SingleNewsItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: Article) {
            with(view) {
                root.setOnClickListener {
                    Log.e("Item",item.toString())
                    onItemClick(item)
                }
                if(item.urlToImage != null)  Glide.with(mContext).load(item.urlToImage).error(R.drawable.newsplaceholder_ic).into(newsImageView);
                newsTitleTextView.text =item.title
                newsContentTextView.text=item.description
                sourceTextView.text=item.source.name ?: "UnknownSource"
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.header_layout, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = SingleNewsItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
            NewsViewHolder(view)
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsViewHolder) {
            // Header dışındaki her öğe için pozisyon 1 azaltılmalıdır, çünkü 0. pozisyon header'dır.
            val itemPosition = position - 1
            val currentArticle = newsList[itemPosition]
            holder.bind(currentArticle)
        }
    }

    override fun getItemCount(): Int = newsList.size+1
    fun updateData(listArticle: List<Article>?) {
        if (listArticle != null) {
            this.newsList = listArticle
        }
        notifyDataSetChanged()
    }


}