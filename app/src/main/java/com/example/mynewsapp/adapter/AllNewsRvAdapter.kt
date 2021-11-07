package com.example.mynewsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.databinding.AllNewRvItemBinding
import com.example.mynewsapp.models.Article
import com.squareup.picasso.Picasso

class AllNewsRvAdapter(private val list:ArrayList<Article>,val listener:OnItemClickListener):RecyclerView.Adapter<AllNewsRvAdapter.Vh>() {

    inner class Vh(private val itemAllNewRvItemBinding: AllNewRvItemBinding):RecyclerView.ViewHolder(itemAllNewRvItemBinding.root){
        fun onBind(article: Article){
            itemAllNewRvItemBinding.apply {
                itemNewsTheme.text = article.title
                itemNewsBy.text = article.author
                itemNewsDate.text = article.publishedAt
                Picasso.get().load(article.urlToImage).into(itemNewsImg)
            }
            itemView.setOnClickListener {
                listener.onItemClick(article)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(AllNewRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener{
        fun onItemClick(article: Article)
    }
}