package hsj.shahram.film.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import hsj.shahram.film.R
import hsj.shahram.film.data.model.ResponseObject
import hsj.shahram.film.databinding.ContentItemBinding
import hsj.shahram.film.util.Const
import javax.inject.Inject

class ContentListAdapter
@Inject constructor(val listener : MyClickListener)
    : PagedListAdapter<ResponseObject.ContentList,
        ContentListAdapter.ContentListHolder>(ResponseObject.ContentList.DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentListHolder {

        val binding: ContentItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.content_item, parent, false
            )

        return ContentListHolder(binding)

    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onBindViewHolder(holder: ContentListHolder, position: Int) {

        getItem(position)?.let { holder.bindTo(it) }
        holder.itemView.setOnClickListener{

            listener.onClicked(getItem(holder.adapterPosition) as ResponseObject.ContentList)

        }

        holder.binding.ivFavorite.setOnClickListener{

            when(it.tag.toString()){

                 Const.FAVORITE -> {
                     getItem(holder.adapterPosition)?.favoriteStatus = false
                     listener.onUnFavoriteClicked(getItem(holder.adapterPosition))
                     notifyItemChanged(holder.adapterPosition)
                 }
                Const.UNFAVORITE ->{
                    getItem(holder.adapterPosition)?.favoriteStatus = true
                    listener.onFavoriteClicked(getItem(holder.adapterPosition))
                    notifyItemChanged(holder.adapterPosition)

                }
            }



        }

    }



    class ContentListHolder(val binding: ContentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindTo(contentList: ResponseObject.ContentList) {

            binding.model = contentList

        }

    }



    interface MyClickListener{

        fun onClicked(model : ResponseObject.ContentList?)
        fun onFavoriteClicked(model : ResponseObject.ContentList?)
        fun onUnFavoriteClicked(model : ResponseObject.ContentList?)

    }
}