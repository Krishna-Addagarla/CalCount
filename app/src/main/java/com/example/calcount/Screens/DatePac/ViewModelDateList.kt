package com.example.calcount.Screens.DatePac
import java.util.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calcount.Data.Local.CalDate
import com.example.calcount.Data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class ViewModelDateList @Inject constructor(private val repository : Repository) : ViewModel() {
    private val _dates = MutableStateFlow<List<CalDate>>(emptyList())
    val dates : StateFlow<List<CalDate>> = _dates

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    init {
        checkAndCreateMissingDates()
    }

    private fun checkAndCreateMissingDates() {
        viewModelScope.launch {
            val existingDatesList = repository.getDates().firstOrNull() ?: emptyList()
            val existingDatesSorted = existingDatesList.sortedBy {
                try {
                    dateFormat.parse(it.date)
                } catch (e: Exception) {
                    Date(0)
                }
            }

            val today = Calendar.getInstance()
            val todayStr = dateFormat.format(today.time)
            val lastRecordedDateStr = existingDatesSorted.lastOrNull()?.date

            val datesToCreate = mutableListOf<CalDate>()

            if (lastRecordedDateStr == null) {
                if (existingDatesSorted.none { it.date == todayStr }) {
                    datesToCreate.add(CalDate(date = todayStr, totalcal = 0.0))
                }
            }else {
                val lastDateCalendar = Calendar.getInstance()
                try {
                    dateFormat.parse(lastRecordedDateStr)?.let { lastDateCalendar.time = it }
                    lastDateCalendar.add(Calendar.DAY_OF_YEAR, 1)

                    while (lastDateCalendar.before(today) || dateFormat.format(lastDateCalendar.time) == todayStr) {
                        val currentDateStr = dateFormat.format(lastDateCalendar.time)
                        if (existingDatesSorted.none { it.date == currentDateStr }) {
                            datesToCreate.add(CalDate(date = currentDateStr, totalcal = 0.0))
                        }
                        lastDateCalendar.add(Calendar.DAY_OF_YEAR, 1)
                        if (datesToCreate.size > 365) break
                    }
                }catch (e: Exception) {
                    if (existingDatesSorted.none { it.date == todayStr }) {
                        datesToCreate.add(CalDate(date = todayStr, totalcal = 0.0))
                    }
                }
            }

            if (datesToCreate.isNotEmpty()) {
                repository.insertDates(datesToCreate)
            }
            getCalories()
        }
    }

    fun getCalories(){
        viewModelScope.launch {
            repository.getDates()
                .collect { dateList ->
                    _dates.value= dateList
                }
        }
    }
}