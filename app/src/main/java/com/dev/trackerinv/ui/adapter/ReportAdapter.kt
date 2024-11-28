package com.dev.trackerinv.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.trackerinv.databinding.CardReportItemBinding
import com.dev.trackerinv.domain.model.ReportItem

class ReportAdapter(
    private val reportItems: List<ReportItem>, // List of enum values
    private val onItemClick: (ReportItem) -> Unit // Lambda for click handling
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    inner class ReportViewHolder(private val binding: CardReportItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reportItem: ReportItem) {
            binding.text.text = reportItem.title
            binding.root.setOnClickListener {
                onItemClick(reportItem) // Trigger the click callback
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = CardReportItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val reportItem = reportItems[position]
        holder.bind(reportItem)
    }

    override fun getItemCount(): Int = reportItems.size
}

