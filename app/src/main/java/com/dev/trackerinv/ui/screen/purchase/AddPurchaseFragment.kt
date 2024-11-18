package com.dev.trackerinv.ui.screen.purchase

import android.net.Uri
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
import com.dev.trackerinv.ui.utils.DatePickerUtil
import com.dev.trackerinv.ui.utils.ImagePickerUtil
import com.dev.trackerinv.ui.viewmodel.PurchaseViewModel
import java.util.UUID

class AddPurchaseFragment : Fragment() {
    private lateinit var binding: FragmentAddPurchaseBinding
    private lateinit var viewModel: PurchaseViewModel
    private lateinit var imagePickerUtil: ImagePickerUtil
    private var selectedImageUri: Uri? = null

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
        // Initialize image picker
        imagePickerUtil = ImagePickerUtil(this)
        imagePickerUtil.initialize { uri ->
            uri?.let {
                selectedImageUri = it
                // Display selected image
                val imageName = ImagePickerUtil.getFileNameFromUri(requireContext(), it)
                binding.tvChooseImage.text = imageName
            }
        }
        // Set up button to open gallery
        binding.btnPickImage.setOnClickListener {
            imagePickerUtil.pickImageFromGallery()
        }

        binding.etInvoiceDate.setOnClickListener {
            DatePickerUtil.showDatePicker(requireContext()){
                    date ->
                binding.etInvoiceDate.text = date
            }
        }

    }

    fun onSubmitButtonClicked() {
        // Use selectedPlatform in the Sale object
        val base64Image =
            selectedImageUri?.let { ImagePickerUtil.convertUriToBase64(requireContext(), it) }
        val purchase = Purchase(
            id = UUID.randomUUID().toString(),
            image = base64Image!!,
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