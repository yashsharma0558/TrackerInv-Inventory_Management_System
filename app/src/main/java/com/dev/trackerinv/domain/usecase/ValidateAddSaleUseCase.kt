package com.dev.trackerinv.domain.usecase

import com.dev.trackerinv.data.model.Sale

class ValidateAddSaleUseCase {

    fun validateAddSaleInput(
        sale: Sale
    ): ValidationResult {

        // 1. Check if all fields are filled
        if (sale.image.isBlank() || sale.platform.isBlank() || sale.date.isBlank() ||
            sale.stock_id.isBlank() || sale.product_name.isBlank() ||
            sale.cus_name.isBlank() || sale.cus_ph.isBlank() ||
            sale.quantity <= 0 || sale.sp <= 0.0 ||
            sale.delivery_channel.isBlank()) {
            return ValidationResult(false, "All fields are mandatory.")
        }
        // 2. Validate customer phone number (10 digits)
        if (sale.cus_ph.length != 10 || !sale.cus_ph.all { it.isDigit() }) {
            return ValidationResult(false, "Customer phone number must be exactly 10 digits.")
        }
        // 3. Validate quantity > 0
        if (sale.quantity <= 0) {
            return ValidationResult(false, "Quantity must be greater than 0.")
        }
        // 4. GST validation
        val cgstFilled = sale.gst.cgst > 0.0
        val sgstFilled = sale.gst.sgst > 0.0
        val igstFilled = sale.gst.igst > 0.0

        // 4.1. Exclude IGST if CGST or SGST is entered and vice versa
        if ((cgstFilled || sgstFilled) && igstFilled) {
            return ValidationResult(false, "Cannot enter IGST if CGST or SGST is filled.")
        }

        if (igstFilled && (cgstFilled || sgstFilled)) {
            return ValidationResult(false, "Cannot enter CGST or SGST if IGST is filled.")
        }

        // 4.2. If CGST is opted, then SGST is mandatory and vice versa
        if (cgstFilled && !sgstFilled) {
            return ValidationResult(false, "SGST is mandatory if CGST is entered.")
        }
        if (sgstFilled && !cgstFilled) {
            return ValidationResult(false, "CGST is mandatory if SGST is entered.")
        }

        // Everything is valid
        return ValidationResult(true, "Sale data is valid.")
    }
}