package com.example.mynewsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.databinding.ItemNewRvBinding
import com.example.mynewsapp.models.Article
import com.squareup.picasso.Picasso

class LatestNewsRvAdapter(private val list:ArrayList<Article>, val listener:OnItemClickListener):RecyclerView.Adapter<LatestNewsRvAdapter.Vh>() {

    inner class Vh(private val itemNewRvBinding: ItemNewRvBinding):RecyclerView.ViewHolder(itemNewRvBinding.root){

        fun onBind(article: Article){
            itemNewRvBinding.apply {
                Picasso.get().load(article.urlToImage).into(latestImg)
                nameTv.text = article.author
                themeTv.text = article.title
                descriptionTv.text = article.description

            }
            itemView.setOnClickListener {
                listener.onItemClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemNewRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return 10
    }

    interface OnItemClickListener{
        fun onItemClick(article: Article)
    }
}