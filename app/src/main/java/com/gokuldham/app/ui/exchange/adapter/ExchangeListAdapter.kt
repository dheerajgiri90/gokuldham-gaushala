package com.gokuldham.app.ui.exchange.adapter

import android.content.Context
import android.view.ViewGroup
import com.gokuldham.app.R
import com.gokuldham.app.databinding.ItemExchangeListBinding
import com.gokuldham.app.ui.base.BaseRecyclerAdapter
import com.gokuldham.app.ui.exchange.response.ExchangeListResponse.ExchangeData
import com.gokuldham.app.util.CommonUtils

class ExchangeListAdapter(
    private val context: Context,
    private val list: ArrayList<ExchangeData>,
    private val onItemClick: (position: Int) -> Unit
) :
    BaseRecyclerAdapter<ItemExchangeListBinding, Any, ExchangeListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        viewDataBinding: ItemExchangeListBinding,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, type: Int) {
        holder.bindToDataVM(holder.bindingVariable, holder.viewModel)
        holder.bindTo(position)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(position)
        }

        holder.viewDataBinding.textIndexNumber.text = (position + 1).toString()
        holder.viewDataBinding.textAmount.text = "₹" + list[position].Amount + "/-"
        var date = CommonUtils.getDateInFormat(
            "yyyy-MM-dd'T'HH:mm:ss",
            "dd MMM yyyy",
            list[position].ExchangeDate
        )

        holder.viewDataBinding.textMortgageDate.text = date

    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_exchange_list
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(mViewDataBinding: ItemExchangeListBinding) :
        BaseViewHolder(mViewDataBinding) {

        override val viewModel: Any
            get() = Any()

        override val bindingVariable: Int
            get() = 0

    }

}