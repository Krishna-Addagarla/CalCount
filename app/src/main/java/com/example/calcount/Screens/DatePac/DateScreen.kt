package com.example.calcount.Screens.DatePac

import android.R.attr.onClick
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calcount.Data.Local.CalDate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.calcount.Screen

//val datess = listOf(CalDate(date = "16/3/2001",totalcal = "200"),CalDate(date = "05/6/2002",totalcal = "300"))
@Composable
fun DateScreen(navController: NavController,
               viewModel : ViewModelDateList = androidx.hilt.navigation.compose.hiltViewModel()){
//    val dates = listOf(CalDate(date = "16/3/2001",totalcal = "200"),CalDate(date = "05/6/2002",totalcal = "300"))
    val dates by viewModel.dates.collectAsState()
    LazyColumn (modifier = Modifier.fillMaxSize()){
        items(dates){
            date ->
            DateCard(date){
                if (date.id == 0) {
                    Log.e("NAV_ERROR", "Attempted navigation with invalid ID!")
                } else {
                    navController.navigate(Screen.ItemsList.createRoute(date.id))
                }
            }
        }
    }
}

@Composable
fun DateCard(date : CalDate,onClick : () -> Unit){
    Card( modifier = Modifier.fillMaxWidth().heightIn(100.dp)
        .padding(horizontal = 16.dp, vertical = 5.dp)
        .border(3.dp, Color.Black,shape = RoundedCornerShape(10.dp))
        .clickable{
            onClick()
        },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically) {
        Text(text = date.date, color = Color.Black,
            fontSize = 20.sp,modifier = Modifier.weight(1f))
        Text(text =
            buildAnnotatedString {
            append("Total Calories : ")
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
            append("${date.totalcal}")
                pop()
            })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DatescreenPreview(){
//    Datescreen(datess)
}