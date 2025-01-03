package com.gokuldham.app.ui.dailyexpenses.adapter

import android.content.Context
import android.view.ViewGroup
import com.gokuldham.app.R
import com.gokuldham.app.databinding.ItemDailyExpensesListBinding
import com.gokuldham.app.ui.base.BaseRecyclerAdapter
import com.gokuldham.app.ui.dailyexpenses.response.ExpensesListResponse.DailyExpenseItem

class ExpensesListAdapter(
    private val context: Context,
    private val list: ArrayList<DailyExpenseItem>,
    private val onItemClick: (position: Int) -> Unit
) :
    BaseRecyclerAdapter<ItemDailyExpensesListBinding, Any, ExpensesListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        viewDataBinding: ItemDailyExpensesListBinding,
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

        holder.viewDataBinding.textAmount.text =
            "₹" + list[position].Amount + "/- " + list[position].Description
        //holder.viewDataBinding.textDescription.text = list[position].Description
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_daily_expenses_list
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(mViewDataBinding: ItemDailyExpensesListBinding) :
        BaseViewHolder(mViewDataBinding) {

        override val viewModel: Any
            get() = Any()

        override val bindingVariable: Int
            get() = 0

    }

}