package com.dev.trackerinv.domain.usecase

import com.dev.trackerinv.data.model.Purchase

class ValidateAddPurchaseUseCase {

    // Function to validate the purchase details
    fun validateAddPurchaseInput(purchase: Purchase): ValidationResult {
        // Validate mandatory fields
        if (purchase.inv_no.isEmpty()) {
            return ValidationResult(false, "Invoice number is required.")
        }
        if (purchase.inv_date.isEmpty()) {
            return ValidationResult(false, "Invoice date is required.")
        }
        if (purchase.sup_name.isEmpty()) {
            return ValidationResult(false, "Supplier name is required.")
        }
        if (purchase.sup_ph.isEmpty() || purchase.sup_ph.length != 10) {
            return ValidationResult(false, "Supplier phone number must be 10 digits.")
        }
        if (purchase.quantity <= 0) {
            return ValidationResult(false, "Quantity must be greater than 0.")
        }
        if (purchase.price_per_unit <= 0.0) {
            return ValidationResult(false, "Price per unit must be greater than 0.")
        }

        // GST validation (exclude IGST if CGST/SGST entered and vice versa)
        if ((purchase.gst.cgst > 0.0 || purchase.gst.sgst > 0.0) && purchase.gst.igst > 0.0) {
            return ValidationResult(false, "Cannot enter both IGST and CGST/SGST together.")
        }

        // If CGST is entered, SGST is mandatory, and vice versa
        if ((purchase.gst.cgst > 0.0 && purchase.gst.sgst == 0.0) ||
            (purchase.gst.sgst > 0.0 && purchase.gst.cgst == 0.0)
        ) {
            return ValidationResult(false, "Both CGST and SGST must be filled together.")
        }

        return ValidationResult(true, "Validation successful.")
    }


}
