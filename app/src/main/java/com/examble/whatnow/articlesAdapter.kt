package com.examble.whatnow

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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

            viewBinding.articletitleTv.text= item?.title+"...."
            viewBinding.articleauthorTv.text=item?.author
            viewBinding.articledescriptionTv.text= "${item?.description}..."
            viewBinding.root.setOnClickListener {
                onItemClick(item!!)

            }
            viewBinding.shareFab.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, item?.url)
                }
                viewBinding.root.context.startActivity(
                    Intent.createChooser(shareIntent, "Share via")
                )
            }

            Glide.with(viewBinding.articleimg.context)
                .load(item?.urlToImage)
                .placeholder(R.drawable.broken_image)
                .error(R.drawable.error_ic)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .into(viewBinding.articleimg)

        }
    }
}