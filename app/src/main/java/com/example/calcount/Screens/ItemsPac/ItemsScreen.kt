package com.example.calcount.Screens.ItemsPac

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.calcount.Data.Local.CalItems
import com.example.calcount.BuildConfig

@Composable
fun ItemsScreen(dateid : Int,
                navController: NavController,
                viewModel : ViewModelItems = androidx.hilt.navigation.compose.hiltViewModel()){
    val dateid = dateid
    val apiKey = BuildConfig.USDA_API_KEY
//    var foodItems = listOf(CalItems(itemName = "Pizza",itemCal = 200.0, dateid = 1),CalItems(itemName = "Chicken Burger",itemCal = 250.0, dateid = 1))
    var searchtext by remember { mutableStateOf("") }
    val foodSuggest by viewModel.items.collectAsState()
    val foodItems by viewModel.selectedItems.collectAsState()

    viewModel.loadItems(dateid)
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {
        OutlinedTextField(
            value = searchtext,
            label = { Text("Search Items here") },
            shape = RoundedCornerShape(32.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search") },
            onValueChange =
                {newText ->
                searchtext =newText
                viewModel.searchItems(newText,apiKey)},
            singleLine = true,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .heightIn(40.dp)
//                .border(2.dp, Color.Black,shape = RoundedCornerShape(32.dp))

        )



        if (searchtext.isNotEmpty()){
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)) {
                items(foodSuggest) {item ->
                    Card(modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            viewModel.slectedItem(item.fdcId, apiKey, dateid)
                        })
                    {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(text = item.description,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                fontSize = 16.sp)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        LazyColumn(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(foodItems) { item ->
                FoodCard(item,viewModel)

            }
        }
    }

}

@Composable
fun FoodCard(item: CalItems,viewModel : ViewModelItems) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                item.itemName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.weight(0.5f)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = buildAnnotatedString {
                    pushStyle(SpanStyle(color = Color.Black, fontSize = 14.sp))
                    append("Cal : ")
                    pushStyle(
                        SpanStyle(
                            Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    append("${item.itemCal}")
                    pop()
                },
                modifier = Modifier.weight(0.4f)
            )
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(onClick = {
                viewModel.deleteItems(item.id)
            }) {
                Icon(
                    Icons.Default.Delete, contentDescription = "Delete the Item",
                    modifier = Modifier.weight(0.1f)
                )
            }

        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun ItemsScreenPreview(){
//    ItemsScreen(1)
//}