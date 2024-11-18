package com.dev.trackerinv.ui.screen.sale

import android.R
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.dev.trackerinv.InventoryApp
import com.dev.trackerinv.data.model.GST
import com.dev.trackerinv.data.model.Platform
import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.databinding.FragmentAddSaleBinding
import com.dev.trackerinv.domain.usecase.ValidateAddSaleUseCase
import com.dev.trackerinv.ui.utils.DatePickerUtil
import com.dev.trackerinv.ui.utils.ImagePickerUtil
import com.dev.trackerinv.ui.viewmodel.SaleViewModel
import java.util.UUID


class AddSaleFragment : Fragment() {
    private lateinit var binding: FragmentAddSaleBinding
    private lateinit var viewModel: SaleViewModel
    private lateinit var imagePickerUtil: ImagePickerUtil
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity().application as InventoryApp).saleViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddSaleBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        setupPlatformDropdown()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabSubmit.setOnClickListener {
            onSubmitButtonClicked()
        }

        binding.etDate.setOnClickListener {
            DatePickerUtil.showDatePicker(requireContext()){
                date ->
                binding.etDate.text = date
            }
        }

        // Initialize image picker
        imagePickerUtil = ImagePickerUtil(this)
        imagePickerUtil.initialize { uri ->
            uri?.let {
                selectedImageUri = it
                // Display selected image
                val imageName = ImagePickerUtil.getFileNameFromUri(requireContext(), it)
                binding.tvChooseImage.text = imageName.toString()
            }
        }

        // Set up button to open gallery
        binding.btnPickImage.setOnClickListener {
            imagePickerUtil.pickImageFromGallery()
        }
    }

    private fun setupPlatformDropdown() {
        val platformNames = viewModel.platformOptions.map { it.displayName }

        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, platformNames)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.etPlatform.adapter = adapter
        binding.etDeliveryChannel.adapter = adapter
    }

    fun onSubmitButtonClicked() {
        // Use selectedPlatform in the Sale object
        val base64Image =
            selectedImageUri?.let { ImagePickerUtil.convertUriToBase64(requireContext(), it) }

        val sale = Sale(
            id = UUID.randomUUID().toString(),
            image = base64Image!!,
            platform = Platform.entries[binding.etPlatform.selectedItemPosition].toString(),
            date = binding.etDate.text.toString(),
            stock_id = binding.etStockId.text.toString(),
            product_name = binding.etProductName.text.toString(),
            cus_name = binding.etCustomerName.text.toString(),
            cus_ph = binding.etCustomerPhone.text.toString(),
            quantity = binding.etQuantity.text.toString().toIntOrNull() ?: 0,
            sp = binding.etSP.text.toString().toDoubleOrNull() ?: 0.0,
            gst = GST(
                cgst = binding.etCGST.text.toString().toDoubleOrNull() ?: 0.0,
                sgst = binding.etSGST.text.toString().toDoubleOrNull() ?: 0.0,
                igst = binding.etIGST.text.toString().toDoubleOrNull() ?: 0.0
            ),
            delivery_channel = Platform.entries[binding.etDeliveryChannel.selectedItemPosition].toString(),
            delivery_and_other_expenses = binding.etDeliveryExpenses.text.toString()
                .toDoubleOrNull() ?: 0.0

        )
        val validate = ValidateAddSaleUseCase().validateAddSaleInput(sale)
        if (validate.isValid) {
            Log.d("AddSaleFragment", "onSubmitButtonClicked: $sale")
            viewModel.createSale(sale)
            viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(context, validate.message, Toast.LENGTH_SHORT)
                .show()
        }

    }

}