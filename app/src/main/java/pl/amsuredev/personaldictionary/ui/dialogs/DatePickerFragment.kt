package pl.amsuredev.personaldictionary.ui.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARG_DATE = "date"

class DatePickerFragment : DialogFragment() {
    interface CallBacks {
        fun onDateSelected(date: Date)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?):
            Dialog{
                val dateListener = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    val resultDate: Date = GregorianCalendar(year, month, day).time
                    (activity as CallBacks).onDateSelected(date = resultDate)
                }
                val calendar = Calendar.getInstance()
        calendar.time = arguments?.getSerializable(ARG_DATE) as Date
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireContext(),
            dateListener,
            initialYear,
            initialMonth,
            initialDay
        )
    }

    companion object{
        fun newInstance(date: Date): DatePickerFragment{
            val args = Bundle().apply {
                putSerializable(ARG_DATE, date)
            }
            val fragment = DatePickerFragment().apply {
                arguments = args
            }
            return fragment
        }
    }
}