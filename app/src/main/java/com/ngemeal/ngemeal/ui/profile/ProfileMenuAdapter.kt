package com.ngemeal.ngemeal.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ngemeal.ngemeal.databinding.ItemMenuProfileBinding
import com.ngemeal.ngemeal.model.dummy.ProfileModel

class ProfileMenuAdapter (
    private val listData : List<ProfileModel>,
    private val itemAdapterCallback: ItemAdapterCallback
    ): RecyclerView.Adapter<ProfileMenuAdapter.ViewHolder> () {

//    private var _binding: ItemHomeHorizontalBinding? = null
//    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int): ProfileMenuAdapter.ViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
//        val view = layoutInflater.inflate(R.layout.item_home_horizontal, parent, false)
//        return ViewHolder(view)
        var binding = ItemMenuProfileBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ProfileMenuAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position],itemAdapterCallback)
    }

    class ViewHolder(itemView:ItemMenuProfileBinding) : RecyclerView.ViewHolder(itemView.root){
        private var _binding: ItemMenuProfileBinding? = itemView
        private val binding get() = _binding!!
        fun bind(data: ProfileModel, itemAdapterCallback: ItemAdapterCallback){
            itemView.apply {
                binding.tvJuduull.text = data.title
                itemView.setOnClickListener{itemAdapterCallback.onClick(it,data)}
            }
        }

    }

    override fun getItemCount(): Int {
        return listData.size
    }

    interface ItemAdapterCallback{
        fun onClick(v:View, data:ProfileModel)
    }
}