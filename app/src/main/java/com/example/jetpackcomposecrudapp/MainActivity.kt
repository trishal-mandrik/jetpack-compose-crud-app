package com.example.jetpackcomposecrudapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposecrudapp.model.Product
import com.example.jetpackcomposecrudapp.repository.ProductRepository
import com.example.jetpackcomposecrudapp.ui.theme.JetpackComposeCrudAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeCrudAppTheme {
                CrudApp()
            }
        }
    }
}

@Composable
fun CrudApp() {
    var productName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var productList by remember { mutableStateOf<List<Product>>(emptyList()) }

    var isLoading by remember { mutableStateOf(false) }
    val repository = remember { ProductRepository() }
    val coroutineScope = rememberCoroutineScope()

    fun fetchProducts() {
        coroutineScope.launch {
            isLoading = true
            try {
                val response = repository.getAllProducts()
                if (response.isSuccessful) {
                    productList = response.body() ?: emptyList()
                }
            } catch (_: Exception) {

            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        fetchProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Jetpack Compose CRUD App",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        TextField(
            value = productName,
            onValueChange = {
                productName = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, Color.Black, shape = RectangleShape),
            label = { Text("Enter Product Name") },
            singleLine = true
        )
        TextField(
            value = quantity,
            onValueChange = {
                quantity = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, Color.Black, shape = RectangleShape),
            label = { Text("Enter Quantity") },
            singleLine = true
        )
        TextField(
            value = price,
            onValueChange = {
                price = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, Color.Black, shape = RectangleShape),
            label = { Text("Enter Price") },
            singleLine = true
        )
        Button(
            onClick = {
                coroutineScope.launch {
                    isLoading = true
                    try {
                        val quantityInt = quantity.toIntOrNull()
                        val priceDouble = price.toDoubleOrNull()
                        if (productName.isBlank() || quantityInt == null || priceDouble == null) {
                            // Show error or return early
                            isLoading = false
                            return@launch
                        }
                        val product = Product(productName, quantityInt, priceDouble)
                        val response = repository.createProduct(product)
                        if (response.isSuccessful) {
                            productName = ""
                            quantity = ""
                            price = ""
                            fetchProducts()
                        }
                    } catch (_: Exception) {

                    } finally {
                        isLoading = false
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            enabled = !isLoading && productName.isNotBlank() && quantity.isNotBlank() && price.isNotBlank()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp))
            } else {
                Text("Submit")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn {
            items(productList) { product ->
                ProductCard(
                    product = product,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CrudAppPreview() {
    CrudApp()
}