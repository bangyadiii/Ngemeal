package com.ngemeal.ngemeal.ui.order.pastorders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.content.ContentProviderCompat.requireContext
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
import com.ngemeal.ngemeal.databinding.ItemInprogressBinding
import com.ngemeal.ngemeal.databinding.ItemPastOrdersBinding
import com.ngemeal.ngemeal.model.dummy.HomeModel
import com.ngemeal.ngemeal.model.response.home.Data
import com.ngemeal.ngemeal.model.response.transaction.Transaction
import com.ngemeal.ngemeal.utils.Helpers.convertLongToTime
import com.ngemeal.ngemeal.utils.Helpers.formatPrice
import java.text.FieldPosition
import java.util.Objects

class PastOrderAdapter (
    private val listData : List<Transaction>,
    private val itemAdapterCallback: ItemAdapterCallback
    ): RecyclerView.Adapter<PastOrderAdapter.ViewHolder> () {

//    private var _binding: ItemHomeHorizontalBinding? = null
//    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int): PastOrderAdapter.ViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)

        var binding = ItemPastOrdersBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PastOrderAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position],itemAdapterCallback)
    }

    class ViewHolder(itemView:ItemPastOrdersBinding) : RecyclerView.ViewHolder(itemView.root){
        private var _binding: ItemPastOrdersBinding? = itemView
        private val binding get() = _binding!!
        fun bind(data: Transaction, itemAdapterCallback: ItemAdapterCallback){
            itemView.apply {
                binding.tvTitle.text = if(data.food?.name?.length!! >= 18) data.food?.name.substring(0, 18).trim() + "..."
                                            else data.food?.name
                binding.tvPrice.formatPrice(data.total!!.toString())
                binding.tvHargaaa.text = data.updatedAt?.convertLongToTime("MMM dd, HH:mm")
                binding.tvPaymentStatus.text = data.trxStatus
                data.trxStatus?.let {
                    if(it.equals("pending", true) || it.equals("failed", true) || it.equals("cancelled")){
                        binding.tvPaymentStatus.setTextColor(resources.getColor(R.color.red_accent))
                    }else{
                        binding.tvPaymentStatus.setTextColor(resources.getColor(R.color.teal_700))
                    }
                }
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
        fun onClick(v:View, data: Transaction)
    }
}