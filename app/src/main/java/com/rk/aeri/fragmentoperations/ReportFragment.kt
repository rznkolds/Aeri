package com.rk.aeri.fragmentoperations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.rk.aeri.R
import com.rk.aeri.databinding.FragmentReportBinding
import com.rk.aeri.dboperations.viewmodel.StepViewModel
import com.rk.aeri.sharedoperations.SharedTheme
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.util.*

class ReportFragment : Fragment(R.layout.fragment_report) {

	private val binding by viewBinding(FragmentReportBinding::bind)

	override fun onCreate(savedInstanceState: Bundle?) {

		SharedTheme( this.requireContext() ).rememberedTheme( )

		super.onCreate(savedInstanceState)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

		val viewModel = ViewModelProvider(this)[StepViewModel::class.java]

		val calender = Calendar.getInstance()

		viewModel.weekRead( ( calender.get(Calendar.DAY_OF_MONTH) - 7 ) , ( calender.get(Calendar.MONTH) + 1 ) ).observe(viewLifecycleOwner) {

			val weekList = ArrayList<BarEntry>()

			if ( it.isEmpty() ) {

				weekList.add ( BarEntry( 0f ,0f ) )

			} else {

				for ( i in it) {

					weekList.add ( BarEntry ( i.day.toFloat() , i.step_value.toFloat() ) )
				}
			}

			val barDataSet = BarDataSet ( weekList , " " )

			barDataSet.setColors(ColorTemplate.COLORFUL_COLORS, 250)
			barDataSet.valueTextSize = 10F

			val barData = BarData ( barDataSet )

			binding.weekChart.setFitBars(true)
			binding.weekChart.data = barData
			binding.weekChart.animateY(2000)

			binding.weekChart.setTouchEnabled(false)
			binding.weekChart.description = null

			binding.weekChart.xAxis.apply {

				setDrawGridLines(false)
				setDrawLabels(false)
			}

			binding.weekChart.axisLeft.axisMinimum = 0f
			binding.weekChart.axisRight.axisMinimum = 0f

			binding.weekChart.refreshDrawableState()
			binding.weekChart.invalidate()
		}

		viewModel.monthRead( ( calender.get(Calendar.MONTH) + 1 ) ).observe(viewLifecycleOwner) {

			val monthList = ArrayList<BarEntry>()

			var monthValue = 0

			if ( it.isEmpty() ) {

				monthList.add ( BarEntry( 0f ,0f ) )

			} else {

				for ( i in it ) {

					monthValue += i.step_value
				}
			}

			monthList.add(BarEntry((calender.get(Calendar.MONTH) + 1).toFloat(), monthValue.toFloat()))

			val barDataSet = BarDataSet(monthList, " ")

			barDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 250)
			barDataSet.valueTextSize = 10F

			val barData = BarData(barDataSet)

			binding.mountChart.setFitBars(true)
			binding.mountChart.data = barData
			binding.mountChart.animateY(2000)

			binding.mountChart.setTouchEnabled(false)
			binding.mountChart.description = null

			binding.mountChart.xAxis.apply {

				setDrawGridLines(false)
				setDrawLabels(false)
			}

			binding.mountChart.axisLeft.axisMinimum = 0f
			binding.mountChart.axisRight.axisMinimum = 0f

			binding.mountChart.refreshDrawableState()
			binding.weekChart.invalidate()
		}

		super.onViewCreated(view, savedInstanceState)
	}


}
