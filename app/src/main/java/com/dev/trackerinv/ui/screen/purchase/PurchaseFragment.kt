package com.dev.trackerinv.ui.screen.purchase

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.trackerinv.InventoryApp
import com.dev.trackerinv.R
import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.databinding.FragmentPurchaseBinding
import com.dev.trackerinv.ui.adapter.PurchaseAdapter
import com.dev.trackerinv.ui.adapter.SaleAdapter
import com.dev.trackerinv.ui.utils.DatePickerUtil
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
        viewModel.filteredPurchases.observe(viewLifecycleOwner) {
                sales ->
            adapter = PurchaseAdapter(sales)
            binding.purchasesRecyclerView.adapter = adapter
        }
        binding.addPurchaseButton.setOnClickListener {
            // Handle the click event for the add button
            findNavController().navigate(R.id.action_purchaseFragment_to_addPurchaseFragment)
        }
        binding.addFilterButton.setOnClickListener {
            showFilterDialog()
        }

    }

    private fun showFilterDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_filter_sales, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)
            .create()

        // Access dialog views
        val startDateInput = dialogView.findViewById<TextView>(R.id.tvStartDate)
        val endDateInput = dialogView.findViewById<TextView>(R.id.tvEndDate)
        val submitButton = dialogView.findViewById<Button>(R.id.submitButton)
        val resetButton = dialogView.findViewById<Button>(R.id.resetButton)

        startDateInput.setOnClickListener {
            DatePickerUtil.showDatePicker(requireContext()){
                    date ->
                startDateInput.text = date
            }
        }
        endDateInput.setOnClickListener {
            DatePickerUtil.showDatePicker(requireContext()){
                    date ->
                endDateInput.text = date
            }
        }
        // Set button click listener
        submitButton.setOnClickListener {
            val startDate = startDateInput.text.toString()
            val endDate = endDateInput.text.toString()

            if (startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Handle the submit action
                filterPurchasesByDate(startDate, endDate)
                dialog.dismiss()
            }
        }

        resetButton.setOnClickListener {
            filterPurchasesByDate("", "")
            dialog.dismiss()
        }

        dialog.show()
    }
    private fun filterPurchasesByDate(startDate: String, endDate: String) {
        // Replace this with your filtering logic
        viewModel.filterPurchasesByDate(startDate, endDate)
        Toast.makeText(requireContext(), "Filtering purchases from $startDate to $endDate", Toast.LENGTH_SHORT).show()
    }
}
