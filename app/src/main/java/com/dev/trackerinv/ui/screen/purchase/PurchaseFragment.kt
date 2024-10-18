package com.dev.trackerinv.ui.screen.purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.trackerinv.InventoryApp
import com.dev.trackerinv.R
import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.databinding.FragmentPurchaseBinding
import com.dev.trackerinv.ui.adapter.PurchaseAdapter
import com.dev.trackerinv.ui.viewmodel.PurchaseViewModel
import com.dev.trackerinv.ui.viewmodel.SaleViewModel

class PurchaseFragment : Fragment() {
    private lateinit var viewModel: PurchaseViewModel
    private lateinit var binding: FragmentPurchaseBinding
    private lateinit var adapter: PurchaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Access the ViewModel from the Application class
        viewModel = (requireActivity().application as InventoryApp).purchaseViewModel
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
        binding.addPurchaseButton.setOnClickListener {
            // Handle the click event for the add button
            findNavController().navigate(R.id.action_purchaseFragment_to_addPurchaseFragment)
        }
        binding.addPurchaseButton.setOnClickListener {
            // Handle the click event for the add button
            findNavController().navigate(R.id.action_purchaseFragment_to_addPurchaseFragment)
        }
    }
}
