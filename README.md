# Inventory Management System

A scalable inventory management application for tracking and analyzing sales and purchases efficiently. This app integrates a clean UI, secure backend, and data visualization to provide actionable insights.

## Features

1. **Transaction Tracking**: Manage 200+ monthly sales and purchase transactions with 95% accuracy.
2. **Modern UI**: Built with Kotlin and XML, following MVVM architecture for clean and maintainable code.
3. **Data Synchronization**: Room database for offline access and seamless sync with MongoDB Atlas via Flask API.
4. **Insights & Analytics**: Interactive graphs for tracking sales, purchases, and generating financial reports.

## Technologies Used

- **Frontend**: Kotlin, XML, MVVM architecture
- **Backend**: Flask, Python, MongoDB Atlas
- **Database**: Room (local), MongoDB Atlas (cloud)
- **Libraries**: MPAndroidChart, Retrofit, Coroutine, Navigation Component
- **APIs**: Custom Flask API
  
## Architecture Overview 🏗️

The app follows MVVM architecture pattern with clear separation of concerns:

```
trackerInv/
├── data/
│   ├── api/
│   │   ├── ApiResponse.kt
│   │   ├── ApiService.kt
│   │   └── RetrofitClient.kt
│   ├── db/
│   │   ├── dao/
│   │   │   └── SaleDao.kt
│   │   │   └── PurchaseDao.kt
│   │   ├── entity/
│   │   |   └── SaleEntity.kt
│   │   |   └── PurchaseEntity.kt
│   │   ├── AppDatabase.kt
│   │   └── Converters.kt
│   ├── model/
│   │   ├── Purchase.kt
│   │   ├── GST.kt
│   │   └── Sale.kt
│   └── repository/
│   │   ├── PurchaseRepository.kt
│   │   ├── ReportRepository.kt
│   |   └── StockRepository.kt
├── domain/
│   ├── mapper/
│   │   ├── SaleMapper.kt
│   │   ├── PurchaseMapper.kt
│   │   └── ChartDataMapper.kt
│   ├── model/
│   |   ├── ChartType.kt
│   |   ├── FinancialSummary.kt
│   |   ├── PieChartData.kt
│   |   ├── Platform.kt
│   |   ├── ReportItem.kt
│   |   ├── RevenueData.kt
│   |   ├── RevenueTrend.kt
│   |   └── BarChartData.kt
│   └── usecase/
│       ├── FilterPurchasesByDateUseCase.kt
│       ├── FilterSalesByDateUseCase.kt
│       ├── GenerateReportUseCase.kt
│       ├── ValidateAddPurchaseUseCase.kt
│       ├── ValidateAddSaleUseCase.kt
│       └── ValidationResult.kt
└── ui/
    ├── adapter/
    │   ├── PurhcaseAdapter.kt
    │   ├── ReportAdapter.kt
    │   └── SaleAdapter.kt
    ├── screen/
    │   ├── purchase/
    │   |   ├── AddPurchaseFragment.kt
    │   |   └── PurchaseFragment.kt
    │   ├── report/
    │   |   ├── financial/
    │   |   |   └── FinancialReportFragment.kt
    │   |   ├── generate/
    │   |   |   └── GenerateReportFragment.kt
    │   |   └── invhealth/
    │   |       └── InventoryHealthFragment.kt
    │   ├── sale/
    │   |   ├── AddSaleFragment.kt
    │   |   └── SaleFragment.kt
    │   └── DetailFragment.kt
    ├── utils/
    │   ├── DatePickerUtil.kt
    │   └── ImagePickerUtil.kt
    └── viewmodel/
        ├── PurchaseViewModel.kt
        ├── ReportViewModel.kt
        ├── SaleViewModel.kt
        ├── ReportViewModelFactory.kt
        └── InventoryViewModelFactory.kt
```

### Prerequisites

- Android Studio Koala or newer
- Android SDK 24 or higher

### Installation
#### Method 1 - Download apk form Google Drive
TO BE UPDATED

#### Method 2 - Manual Installation
1. Clone the repository:
```bash
git clone https://github.com/yashsharma0558/TrackerInv-Inventory_Management_System.git
```

2. Open the project in Android Studio

3. Build and run the project

## Usage 📱

1. Launch the app.
2. Add a sale or purchase.
3. The added sales/purchases appear on home screen.
4. Download reports in xls format, check your inventory health, check your financial insights.

## Dependencies 📚

```gradle
dependencies {
    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    
    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    //Room
    implementation("androidx.room:room-runtime:roomRuntime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:roomRuntime:2.6.1")

    //ApachePOI
    implementation("org.apache.poi:poi-ooxml:5.2.3")
    implementation("org.apache.poi:poi:5.2.3")

    //Gemini AI
    implementation("com.google.ai.client.generativeai:generativeai:0.1.2")

    //MPAndroidChart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}
```

## Acknowledgments 🙏

- Thanks to the open-source community for amazing libraries like MPAndroidChart.
- [Android Documentation](https://developer.android.com) for development guidelines
- [Material Design](https://material.io) for design inspiration


