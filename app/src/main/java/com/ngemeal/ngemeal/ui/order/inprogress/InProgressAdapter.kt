package com.ngemeal.ngemeal.ui.order.inprogress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.ItemInprogressBinding
import com.ngemeal.ngemeal.model.response.transaction.Transaction
import com.ngemeal.ngemeal.utils.Helpers.formatPrice


class InProgressAdapter (
    private val listData : List<Transaction>,
    private val itemAdapterCallback: ItemAdapterCallback
    ): RecyclerView.Adapter<InProgressAdapter.ViewHolder> () {

//    private var _binding: ItemHomeHorizontalBinding? = null
//    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int): InProgressAdapter.ViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)

        var binding = ItemInprogressBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: InProgressAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position],itemAdapterCallback)
    }

    class ViewHolder(itemView:ItemInprogressBinding) : RecyclerView.ViewHolder(itemView.root){
        private var _binding: ItemInprogressBinding? = itemView
        private val binding get() = _binding!!
        fun bind(data: Transaction, itemAdapterCallback: ItemAdapterCallback){
            itemView.apply {
                binding.tvName.text = if(data.food?.name?.length!! >= 18) data.food?.name?.substring(0, 18)?.trim() + "..."
                                            else data.food?.name
                binding.tvPrice.formatPrice(data?.total.toString())

                Glide.with(itemView.context)
                    .load(data.food.images?.get(0)?.imageUrl)
                    .centerCrop()
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
        fun onClick(v:View, data:Transaction)
    }
}