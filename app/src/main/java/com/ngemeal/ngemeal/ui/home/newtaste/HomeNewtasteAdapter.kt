package com.ngemeal.ngemeal.ui.home.newtaste

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.bumptech.glide.Glide
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.ItemHomeVerticalBinding
import com.ngemeal.ngemeal.model.dummy.HomeVerticalModel
import com.ngemeal.ngemeal.utils.Helpers.formatPrice
import java.text.FieldPosition

class HomeNewtasteAdapter (
    private val listData : List<HomeVerticalModel>,
    private val itemAdapterCallback: ItemAdapterCallback
    ): RecyclerView.Adapter<HomeNewtasteAdapter.ViewHolder> () {

//    private var _binding: ItemHomeHorizontalBinding? = null
//    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int): HomeNewtasteAdapter.ViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
//        val view = layoutInflater.inflate(R.layout.item_home_horizontal, parent, false)
//        return ViewHolder(view)
        var binding = ItemHomeVerticalBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: HomeNewtasteAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position],itemAdapterCallback)
    }

    class ViewHolder(itemView:ItemHomeVerticalBinding) : RecyclerView.ViewHolder(itemView.root){
        private var _binding: ItemHomeVerticalBinding? = itemView
        private val binding get() = _binding!!
        fun bind(data: HomeVerticalModel, itemAdapterCallback: ItemAdapterCallback){
            itemView.apply {
                binding.tvTitle.text = data.title
                binding.tvPrice.formatPrice(data.price)
                binding.rbFood.rating = data.rating

//                Glide.with(context)
//                    .load(data.src)
//                    .into(binding.ivPoster)

                itemView.setOnClickListener{itemAdapterCallback.onClick(it,data)}
            }
        }

    }

    override fun getItemCount(): Int {
        return listData.size
    }

    interface ItemAdapterCallback{
        fun onClick(v:View, data:HomeVerticalModel)
    }
}