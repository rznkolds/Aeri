package com.rk.aeri.fragmentoperations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.rk.aeri.R
import com.rk.aeri.databinding.FragmentMainBinding
import com.rk.aeri.eventbus.StepSendBus
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import com.rk.aeri.sharedoperations.SharedBMI
import com.rk.aeri.sharedoperations.SharedGoal
import com.rk.aeri.sharedoperations.SharedTheme
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class MainFragment : Fragment(R.layout.fragment_main) {

	private val binding by viewBinding(FragmentMainBinding::bind)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

		SharedTheme( this.requireContext() ).rememberedTheme( )

		val goal = SharedGoal(this.requireContext()).getTarget()

		binding.stepProgress.progressMax = if (goal != 0) {

			goal.toFloat()

		} else {

			1000.toFloat()
		}

		val shared_weight = SharedBMI(this.requireContext()).getTarget("weight")

		val shared_size = SharedBMI(this.requireContext()).getTarget("size")

		if (shared_weight != null && shared_size != null) {

			val sizeBMI = shared_size * shared_size

			val indeks = shared_weight / sizeBMI

			if (indeks.isNaN() || indeks == null || indeks == 0f) {

				binding.binfo.text = "BMI : 0"

			} else {

				binding.binfo.text = "BMI : ${indeks.toInt()}"
			}

		} else {

			binding.binfo.text = "BMI : 0"
		}

		return super.onViewCreated(view, savedInstanceState)
	}

	override fun onStart() {

		EventBus.getDefault().register(this)

		super.onStart()
	}

	@Subscribe(sticky = true)
	fun infoGet(event: StepSendBus) {

		binding.stepInfo.text = event.step_info.toString()

		binding.stepProgress.progress = event.step_info.toFloat()
	}

	override fun onStop() {

		EventBus.getDefault().unregister(this)

		super.onStop()
	}
}