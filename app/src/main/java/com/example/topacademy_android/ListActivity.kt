package com.example.topacademy_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class ListActivity : AppCompatActivity() {
    
    private lateinit var rvCars: RecyclerView
    private lateinit var toolbar: MaterialToolbar
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        
        setupToolbar()
        setupRecyclerView()
    }
    
    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun setupRecyclerView() {
        rvCars = findViewById(R.id.rvCars)
        rvCars.layoutManager = LinearLayoutManager(this)
        
        val mockCars = createMockCarData()
        val adapter = CarAdapter(mockCars)
        rvCars.adapter = adapter
    }
    
    private fun createMockCarData(): List<Car> {
        return listOf(
            Car(
                brand = "BMW",
                model = "X5",
                year = 2022,
                description = "Luxury SUV with premium features and advanced technology",
                cost = 75000,
                imageResId = R.drawable.ic_launcher_foreground
            ),
            Car(
                brand = "Mercedes-Benz",
                model = "C-Class",
                year = 2021,
                description = "Elegant sedan with sophisticated design and comfort",
                cost = 55000,
                imageResId = R.drawable.ic_launcher_foreground
            ),
            Car(
                brand = "Audi",
                model = "A4",
                year = 2023,
                description = "Sporty sedan with cutting-edge technology and performance",
                cost = 45000,
                imageResId = R.drawable.ic_launcher_foreground
            ),
            Car(
                brand = "Tesla",
                model = "Model 3",
                year = 2022,
                description = "Electric vehicle with autopilot and sustainable energy",
                cost = 50000,
                imageResId = R.drawable.ic_launcher_foreground
            ),
            Car(
                brand = "Toyota",
                model = "Camry",
                year = 2021,
                description = "Reliable and fuel-efficient family sedan",
                cost = 35000,
                imageResId = R.drawable.ic_launcher_foreground
            ),
            Car(
                brand = "Honda",
                model = "Civic",
                year = 2022,
                description = "Compact car with excellent fuel economy and reliability",
                cost = 28000,
                imageResId = R.drawable.ic_launcher_foreground
            ),
            Car(
                brand = "Ford",
                model = "Mustang",
                year = 2023,
                description = "Iconic American muscle car with powerful performance",
                cost = 42000,
                imageResId = R.drawable.ic_launcher_foreground
            ),
            Car(
                brand = "Volkswagen",
                model = "Golf",
                year = 2021,
                description = "Compact hatchback with European engineering excellence",
                cost = 32000,
                imageResId = R.drawable.ic_launcher_foreground
            )
        )
    }
} 