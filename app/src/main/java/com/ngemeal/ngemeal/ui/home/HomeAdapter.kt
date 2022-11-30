package com.ngemeal.ngemeal.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.FragmentSignInBinding
import com.ngemeal.ngemeal.databinding.ItemHomeHorizontalBinding
import com.ngemeal.ngemeal.model.dummy.HomeModel
import com.ngemeal.ngemeal.model.response.home.Data
import java.text.FieldPosition
import java.util.Objects

class HomeAdapter (
    private val listData : List<Data>,
    private val itemAdapterCallback: ItemAdapterCallback
    ): RecyclerView.Adapter<HomeAdapter.ViewHolder> () {

//    private var _binding: ItemHomeHorizontalBinding? = null
//    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int): HomeAdapter.ViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
//        val view = layoutInflater.inflate(R.layout.item_home_horizontal, parent, false)
//        return ViewHolder(view)
        var binding = ItemHomeHorizontalBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position],itemAdapterCallback)
    }

    class ViewHolder(itemView:ItemHomeHorizontalBinding) : RecyclerView.ViewHolder(itemView.root){
        private var _binding: ItemHomeHorizontalBinding? = itemView
        private val binding get() = _binding!!
        fun bind(data: Data, itemAdapterCallback: ItemAdapterCallback){
            itemView.apply {
                binding.tvTitleFood.text = if(data.name?.length!! >= 18) data.name.substring(0, 18) + "..."
                                            else data.name
                binding.rbFood.rating = data.rate ?: 0f

                Glide.with(context)
                    .load(data.images?.get(0)?.imagePath?.toUri()?.buildUpon()?.scheme("http")?.build())
                    .apply(
                        RequestOptions().placeholder(R.drawable.iv_sample_product)
                            .error(R.drawable.iv_sample_product)
                    )
                    .into(binding.ivPoster)

                itemView.setOnClickListener{itemAdapterCallback.onClick(it,data)}
            }
        }

    }

    override fun getItemCount(): Int {
        return listData.size
    }

    interface ItemAdapterCallback{
        fun onClick(v:View, data:Data)
    }
}