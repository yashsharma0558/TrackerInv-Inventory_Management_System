package com.dev.trackerinv.domain.mapper

import com.dev.trackerinv.data.db.entity.PurchaseEntity
import com.dev.trackerinv.data.model.Purchase

fun Purchase.toEntity(): PurchaseEntity {
    return PurchaseEntity(
        id = this.id,
        image = this.image,
        invNo = this.inv_no,
        invDate = this.inv_date,
        supName = this.sup_name,
        supPh = this.sup_ph,
        quantity = this.quantity,
        gst = this.gst,
        pricePerUnit = this.price_per_unit
    )
}

fun PurchaseEntity.toDomainModel(): Purchase {
    return Purchase(
        id = this.id,
        image = this.image, // Assuming image is stored as a string (URL or path)
        inv_no = this.invNo,
        inv_date = this.invDate,
        sup_name = this.supName,
        sup_ph = this.supPh,
        quantity = this.quantity,
        gst = this.gst,
        price_per_unit = this.pricePerUnit
    )
}