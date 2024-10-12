package com.dev.trackerinv.ui.screens.sale
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.trackerinv.InventoryApp
import com.dev.trackerinv.R
import com.dev.trackerinv.databinding.FragmentSaleBinding
import com.dev.trackerinv.ui.adapter.SaleAdapter
import com.dev.trackerinv.ui.viewmodel.InventoryViewModel

class SaleFragment : Fragment() {
    private lateinit var viewModel: InventoryViewModel
    private lateinit var binding: FragmentSaleBinding
    private lateinit var adapter: SaleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Access the ViewModel from the Application class
        viewModel = (requireActivity().application as InventoryApp).viewModel
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
    }
}
