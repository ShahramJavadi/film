package hsj.shahram.film.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import hsj.shahram.film.R
import hsj.shahram.film.data.model.ResponseObject
import hsj.shahram.film.databinding.ContentItemBinding
import java.util.ArrayList
import javax.inject.Inject

class FavoriteListAdapter
@Inject constructor(val listener: OnFavoriteItemListener) :
    RecyclerView.Adapter<FavoriteListAdapter.FavoriteListHolder>() {

    private var contentList: MutableList<ResponseObject.ContentList> = ArrayList()

    fun submitList(list: MutableList<ResponseObject.ContentList>) {

        contentList = list
        notifyDataSetChanged()

    }


    fun deleteItem(position : Int)
    {
        contentList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListHolder {

        val binding: ContentItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.content_item, parent, false
            )

        return FavoriteListHolder(binding)

    }


    override fun onBindViewHolder(holder: FavoriteListHolder, position: Int) {

        holder.bindTo(contentList[position])
        holder.itemView.setOnClickListener {



               listener.onItemClicked(contentList[holder.adapterPosition])



        }

        holder.binding.ivFavorite.setOnClickListener {



            listener.onFAvoriteIconClicked(contentList[holder.adapterPosition].id , holder.adapterPosition)



        }

    }


    class FavoriteListHolder(val binding: ContentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindTo(contentList: ResponseObject.ContentList) {

            binding.model = contentList

        }

    }



    override fun getItemCount(): Int {
        return contentList.size
    }

    interface OnFavoriteItemListener {

        fun onItemClicked(contentList: ResponseObject.ContentList)
        fun onFAvoriteIconClicked(contentId : Int , position : Int)

    }
}