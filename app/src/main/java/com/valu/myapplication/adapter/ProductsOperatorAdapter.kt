package com.valu.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.valu.myapplication.R
import com.valu.myapplication.databinding.RowProductBinding

import com.valu.myapplication.models.ProductsResponse

class ProductsOperatorAdapter(private var items: List<ProductsResponse>) :
    RecyclerView.Adapter<ProductsOperatorAdapter.ViewHolder>() {

    var onItemClick: ((ProductsResponse) -> Unit)? = null

    fun setProductClick(action: (ProductsResponse) -> Unit) {
        this.onItemClick = action

    }

    inner class ViewHolder(@NonNull val itemBinding: RowProductBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            onItemClick?.let {
                itemBinding.root.setOnClickListener {
                    it(items[adapterPosition])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemBinding.tvTitle.text=item.title.toString()
        holder.itemBinding.tvPrice.text = holder.itemView.context.getString(R.string.price,item.price.toString())

        Glide.with(holder.itemView.context).load(item.image).into(holder.itemBinding.ivProduct)

    } // onBindViewHolder


    override fun getItemCount(): Int {
        return items.size
    }

}