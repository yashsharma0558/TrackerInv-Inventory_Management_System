package com.dev.trackerinv.domain.mapper

import com.dev.trackerinv.data.db.entity.SaleEntity
import com.dev.trackerinv.data.model.Sale

fun Sale.toEntity(): SaleEntity {
    return SaleEntity(
        id = this.id,
        image = this.image,
        platform = this.platform,
        date = this.date,
        stockId = this.stock_id,
        productName = this.product_name,
        cusName = this.cus_name,
        cusPh = this.cus_ph,
        quantity = this.quantity,
        sp = this.sp,
        gst = this.gst,
        deliveryChannel = this.delivery_channel,
        deliveryAndOtherExpenses = this.delivery_and_other_expenses
    )
}

fun SaleEntity.toDomainModel(): Sale {
    return Sale(
        id = this.id,
        image = this.image,
        platform = this.platform,
        date = this.date,
        stock_id = this.stockId,
        product_name = this.productName,
        cus_name = this.cusName,
        cus_ph = this.cusPh,
        quantity = this.quantity,
        sp = this.sp,
        gst = this.gst,
        delivery_channel = this.deliveryChannel,
        delivery_and_other_expenses = this.deliveryAndOtherExpenses
    )
}
