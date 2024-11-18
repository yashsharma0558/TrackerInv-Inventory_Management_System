package com.dev.trackerinv.ui.screen.sale
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.trackerinv.InventoryApp
import com.dev.trackerinv.R
import com.dev.trackerinv.databinding.FragmentSaleBinding
import com.dev.trackerinv.ui.adapter.SaleAdapter
import com.dev.trackerinv.ui.utils.DatePickerUtil
import com.dev.trackerinv.ui.viewmodel.SaleViewModel
import org.w3c.dom.Text

class SaleFragment : Fragment() {
    private lateinit var viewModel: SaleViewModel
    private lateinit var binding: FragmentSaleBinding
    private lateinit var adapter: SaleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Access the ViewModel from the Application class
        viewModel = (requireActivity().application as InventoryApp).saleViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaleBinding.inflate(inflater, container, false)
        adapter = SaleAdapter(emptyList())
        binding.salesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.salesRecyclerView.adapter = adapter
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Use your viewModel here
        // For example, observe data or call fetchData()
        viewModel.sales.observe(viewLifecycleOwner) { sales ->
            adapter = SaleAdapter(sales)
            binding.salesRecyclerView.adapter = adapter
        }

        viewModel.filteredSales.observe(viewLifecycleOwner) {
                sales ->
            adapter = SaleAdapter(sales)
            binding.salesRecyclerView.adapter = adapter
        }

        binding.addSaleButton.setOnClickListener {
            // Handle the click event for the add button
            findNavController().navigate(R.id.action_saleFragment_to_addSaleFragment)
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
                filterSalesByDate(startDate, endDate)
                dialog.dismiss()
            }
        }

        resetButton.setOnClickListener {
            filterSalesByDate("", "")
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun filterSalesByDate(startDate: String, endDate: String) {
        // Replace this with your filtering logic
        viewModel.filterSalesByDate(startDate, endDate)
        Toast.makeText(requireContext(), "Filtering sales from $startDate to $endDate", Toast.LENGTH_SHORT).show()
    }

}
