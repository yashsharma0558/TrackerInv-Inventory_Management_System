package com.dev.trackerinv.ui.screen.purchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dev.trackerinv.InventoryApp
import com.dev.trackerinv.data.model.GST
import com.dev.trackerinv.data.model.Platform
import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.databinding.FragmentAddPurchaseBinding
import com.dev.trackerinv.domain.usecase.ValidateAddPurchaseUseCase
import com.dev.trackerinv.ui.viewmodel.PurchaseViewModel
import java.util.UUID

class AddPurchaseFragment : Fragment() {
    private lateinit var binding: FragmentAddPurchaseBinding
    private lateinit var viewModel: PurchaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity().application as InventoryApp).purchaseViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPurchaseBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabSubmit.setOnClickListener {
            onSubmitButtonClicked()
        }
    }

    fun onSubmitButtonClicked() {
        // Use selectedPlatform in the Sale object

        val purchase = Purchase(
            id = UUID.randomUUID().toString(),
            image = "Something",
            inv_no = binding.etInvoiceNo.text.toString(),
            inv_date = binding.etInvoiceDate.text.toString(),
            sup_name = binding.etSupplierName.text.toString(),
            sup_ph = binding.etSupplierPhone.text.toString(),
            quantity = binding.etQuantity.text.toString().toIntOrNull() ?: 0,
            gst = GST(
                cgst = binding.etCGST.text.toString().toDoubleOrNull() ?: 0.0,
                sgst = binding.etSGST.text.toString().toDoubleOrNull() ?: 0.0,
                igst = binding.etIGST.text.toString().toDoubleOrNull() ?: 0.0
            ),
            price_per_unit = binding.etPricePerUnit.text.toString().toDoubleOrNull() ?: 0.0
        )
        val validate = ValidateAddPurchaseUseCase().validateAddPurchaseInput(purchase)
        if (validate.isValid) {
            viewModel.createPurchase(purchase)
            viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }


        } else {
            Toast.makeText(context, validate.message, Toast.LENGTH_SHORT)
                .show()
        }

    }

}