package com.dev.trackerinv.ui.screens.purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.trackerinv.InventoryApp
import com.dev.trackerinv.R
import com.dev.trackerinv.databinding.FragmentPurchaseBinding
import com.dev.trackerinv.databinding.FragmentSaleBinding
import com.dev.trackerinv.ui.adapter.PurchaseAdapter
import com.dev.trackerinv.ui.adapter.SaleAdapter
import com.dev.trackerinv.ui.viewmodel.InventoryViewModel

class PurchaseFragment : Fragment() {
    private lateinit var viewModel: InventoryViewModel
    private lateinit var binding: FragmentPurchaseBinding
    private lateinit var adapter: PurchaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Access the ViewModel from the Application class
        viewModel = (requireActivity().application as InventoryApp).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        adapter = PurchaseAdapter(emptyList())
        binding.purchasesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.purchasesRecyclerView.adapter = adapter



        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Use your viewModel here
        // For example, observe data or call fetchData()
        viewModel.purchases.observe(viewLifecycleOwner) { purchases ->
            adapter = PurchaseAdapter(purchases)
            binding.purchasesRecyclerView.adapter = adapter
        }
    }
}
