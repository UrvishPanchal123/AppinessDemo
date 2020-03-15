package com.appiness.urvish.ui.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.appiness.urvish.databinding.RowDataBinding
import com.appiness.urvish.network.model.DataModel
import com.appiness.urvish.ui.activity.MainActivity

class RecyclerDataAdapter(
    private val mContext: MainActivity,
    val mList: List<DataModel>?
) :
    RecyclerView.Adapter<RecyclerDataAdapter.DataViewHolder>(), Filterable {

    var mListFiltered: List<DataModel>

    init {
        mListFiltered = mList!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val mBinding = RowDataBinding.inflate(mContext.layoutInflater, parent, false)
        return DataViewHolder(mBinding)
    }

    override fun getItemCount(): Int {
        return mListFiltered.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bindData(mListFiltered[holder.adapterPosition])
    }

    class DataViewHolder(var mBinding: RowDataBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bindData(data: DataModel) {
            mBinding.data = data
            mBinding.executePendingBindings()
        }
    }

    override fun getFilter(): Filter {

        return object : Filter() {

            @SuppressLint("DefaultLocale")
            override fun performFiltering(charSequence: CharSequence): FilterResults {

                val charString = charSequence.toString()

                if (charString.isEmpty()) {

                    mListFiltered = mList!!

                } else {

                    val filteredList = arrayListOf<DataModel>()

                    for (row in mList!!) {

                        if (row.title?.toLowerCase()?.contains(charString.toLowerCase())!!) {
                            filteredList.add(row)
                        }
                    }

                    mListFiltered = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = mListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                mListFiltered = filterResults.values as List<DataModel>
                notifyDataSetChanged()
            }
        }
    }
}