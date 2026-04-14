package com.examble.whatnow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.examble.whatnow.API.ArticlesItem
import com.examble.whatnow.databinding.ArticleitemBinding

class articlesAdapter(
   val  articleList:List<ArticlesItem?>,
   val  onItemClick:(ArticlesItem)->Unit
): RecyclerView.Adapter<articlesAdapter.viewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): viewHolder {
        val binding = ArticleitemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return viewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: viewHolder,
        position: Int
    ) {
        holder.bind(articleList[position])


    }

    override fun getItemCount()=articleList.size


   inner class viewHolder(val viewBinding: ArticleitemBinding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(item: ArticlesItem?) {

            viewBinding.articletitleTv.text= item?.title
            viewBinding.articleauthorTv.text=item?.author
            viewBinding.articledescriptionTv.text= item?.description
            viewBinding.root.setOnClickListener {
                onItemClick(item!!)

            }

            Glide.with(viewBinding.articleimg.context)
                .load(item?.urlToImage)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error_ic)
                .into(viewBinding.articleimg)

        }
    }
}