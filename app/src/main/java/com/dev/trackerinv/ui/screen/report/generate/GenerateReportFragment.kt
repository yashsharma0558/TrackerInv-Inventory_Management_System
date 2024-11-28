package com.dev.trackerinv.ui.screen.report.generate

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.dev.trackerinv.InventoryApp
import com.dev.trackerinv.databinding.FragmentGenerateReportBinding
import com.dev.trackerinv.ui.utils.DatePickerUtil
import com.dev.trackerinv.ui.viewmodel.ReportViewModel
import java.io.File

class GenerateReportFragment : Fragment() {

    private lateinit var binding: FragmentGenerateReportBinding
    private lateinit var viewModel: ReportViewModel

    private var startDate: String = ""
    private var endDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity().application as InventoryApp).reportViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGenerateReportBinding.inflate(inflater, container, false)

        // Set up date pickers
        binding.startDatePicker.setOnClickListener {
            showDatePicker { date ->
                startDate = date
                binding.startDatePicker.text = date
            }
        }

        binding.endDatePicker.setOnClickListener {
            showDatePicker { date ->
                endDate = date
                binding.endDatePicker.text = date
            }
        }

        // Handle report generation
        binding.generateReportButton.setOnClickListener {
            if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                generateExcelReport(startDate, endDate)
            } else {
                Toast.makeText(requireContext(), "Please select both dates", Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }

    private fun generateExcelReport(startDate: String, endDate: String) {
        viewModel.generateReport(
            startDate = startDate,
            endDate = endDate,
            context = requireContext(),
            onSuccess = { file ->
                Toast.makeText(requireContext(), "Report generated at: ${file.path}", Toast.LENGTH_LONG).show()
//                openExcelFile(file)
            },
            onError = { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        )
    }

//    private fun openExcelFile(file: File) {
//        val uri: Uri = FileProviderUtils.getFileUri(requireContext(), file)
//        val intent = Intent(Intent.ACTION_VIEW).apply {
//            setDataAndType(uri, "application/vnd.ms-excel")
//            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        }
//        if (intent.resolveActivity(requireContext().packageManager) != null) {
//            startActivity(intent)
//        } else {
//            Toast.makeText(requireContext(), "No app available to open this file", Toast.LENGTH_LONG).show()
//        }
//    }


    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        DatePickerUtil.showDatePicker(requireContext(), onDateSelected)
    }

    private fun openFile(file: File) {
        val uri = FileProvider.getUriForFile(requireContext(), "com.dev.trackerinv.fileprovider", file)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(intent)
    }
}