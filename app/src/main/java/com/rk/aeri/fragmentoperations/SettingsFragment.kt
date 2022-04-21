package com.rk.aeri.fragmentoperations

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.rk.aeri.activity.Quest
import com.rk.aeri.R
import com.rk.aeri.databinding.FragmentSettingsBinding
import com.rk.aeri.sharedoperations.SharedBMI
import com.rk.aeri.sharedoperations.SharedGoal
import com.rk.aeri.sharedoperations.SharedTheme
import com.rk.aeri.sharedoperations.SharedToast
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val prefences = this.activity?.getSharedPreferences("ThemeZone", Context.MODE_PRIVATE)

        val theme = prefences?.getString("theme_value", null)

        when (theme) {

            "light_mode" -> {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                binding.lightMode.isChecked = true
            }

            "dark_mode" -> {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                binding.darkMode.isChecked = true
            }

            else -> {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

                binding.system.isChecked = true
            }
        }

        val goal = SharedGoal(this.requireContext()).getTarget()

        if (goal != 0) {

            binding.goalInfo.setText(goal.toString())

        }else {

            binding.goalInfo.setText(1000.toString())
        }

        binding.goalApply.setOnClickListener {

            if ( binding.goalInfo.text!!.isNotEmpty() ) {

                SharedGoal(this.requireContext()).saveTarget(binding.goalInfo.text.toString().toInt())

                Toast.makeText(this.requireContext(), "Hedef başarıyla uygulandı", Toast.LENGTH_SHORT).show()

            } else {

                Toast.makeText(this.requireContext() , "Bir hedef belirtin.",Toast.LENGTH_SHORT).show()
            }
        }

        binding.themeSelect.setOnCheckedChangeListener { group, checkedId ->

            when (group.checkedRadioButtonId) {

                R.id.lightMode -> {

                    SharedTheme( this.requireContext() ).selectedTheme("light_mode", )

                    SharedToast( this.requireContext() ).saveTarget(1)

                    Toast.makeText(this.requireContext(), "Tema uygulandı", Toast.LENGTH_SHORT).show()

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }

                R.id.darkMode -> {

                    SharedTheme( this.requireContext() ).selectedTheme("dark_mode" )

                    SharedToast( this.requireContext() ).saveTarget(1)

                    Toast.makeText(this.requireContext(), "Tema uygulandı", Toast.LENGTH_SHORT).show()

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }

                R.id.system -> {

                    SharedTheme( this.requireContext() ).selectedTheme("system" )

                    SharedToast( this.requireContext() ).saveTarget(1)

                    Toast.makeText(this.requireContext(), "Tema uygulandı", Toast.LENGTH_SHORT).show()

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }

        val shared_weight = SharedBMI(this.requireContext()).getTarget("weight").toString()

        val shared_size = SharedBMI(this.requireContext()).getTarget("size").toString()

        if ( shared_weight !=null && shared_size != null ) {

            binding.editWeight.setText( shared_weight )

            binding.editSize.setText( shared_size )

        } else {

            binding.editWeight.setText( 0 )

            binding.editSize.setText( 0 )
        }

        binding.indexApply.setOnClickListener {

            if ( binding.editWeight.text!!.isNotEmpty () && binding.editSize.text!!.isNotEmpty() ) {

                    val weight = binding.editWeight.text.toString().toFloat()

                    val size = binding.editSize.text.toString().toFloat()

                    SharedBMI ( this.requireContext() ).saveTarget ("weight" , weight )

                    SharedBMI ( this.requireContext() ).saveTarget ("size" , size )

                    Toast.makeText(this.requireContext(),"BMI Hesaplandı",Toast.LENGTH_SHORT).show()

            } else {

                Toast.makeText(this.requireContext(),"Bir değer belirtin",Toast.LENGTH_SHORT).show()
            }
        }

        binding.questLayout.setOnClickListener {

            questIntent ()
        }

        binding.questTxt.setOnClickListener {

            questIntent()
        }

        binding.followLayout.setOnClickListener {

            followIntent ()
        }

        binding.followMe.setOnClickListener {

            followIntent ()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun questIntent () {

        startActivity ( Intent ( this.requireContext() , Quest::class.java ) )
    }

    private fun followIntent() {

        startActivity (Intent ( Intent.ACTION_VIEW , Uri.parse ("https://www.linkedin.com/in/rznkoldas/" ) ) )
    }


}