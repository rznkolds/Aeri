package com.rk.aeri.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rk.aeri.databinding.ActivityQuestBinding

class Quest : AppCompatActivity() {

	private lateinit var binding: ActivityQuestBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityQuestBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}
}