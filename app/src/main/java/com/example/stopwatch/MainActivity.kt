package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.stopwatch.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val viewModel1: ViewModelSW1 by viewModels()
        val viewModel2: ViewModelSW2 by viewModels()
        val viewModel34: ViewModelSW3 by viewModels()
        val viewModel5: ViewModelSW5 by viewModels()



        binding.btn12.setOnClickListener {
            if (binding.btn12.text == "СТАРТ") {
            viewModel1.startCounter()
        } else {
            viewModel1.pauseCounter()
        }

        }
        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel1.getValueFlow().collect {
                        binding.tx13.text = it.toString()
                    }
                }

                launch {
                    viewModel1.getConditionFlow().collect {
                        if (it == true) {
                            binding.btn12.text = "СТОП"
                        }
                        else binding.btn12.text = "СТАРТ"
                    }
                }
            }
        }
        binding.btn11.setOnClickListener {
            viewModel1.pauseCounter()
            viewModel1.resetCounter()
        }






        binding.btn22.setOnClickListener {
            if (binding.btn22.text == "СТАРТ") {
            binding.btn22.text = "СТОП"
            viewModel2.startCounter()
        } else {
            binding.btn22.text = "СТАРТ"
            viewModel2.pauseCounter()
        }

        }
        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel2.getValueFlow().collect {
                        binding.tx23.text = it.toString()
                    }
                }
                launch {
                    viewModel2.getConditionFlow().collect {
                        if (it == true) {
                            binding.btn22.text = "СТОП"
                        }
                        else binding.btn22.text = "СТАРТ"
                    }
                }
            }
        }

        binding.btn21.setOnClickListener {
            viewModel2.resetCounter()
            viewModel2.pauseCounter()
        }






        binding.btn32.setOnClickListener {
            if (binding.btn32.text == "СТАРТ") {
                binding.btn32.text = "СТОП"
                viewModel34.startCounter(3)
            } else {
                binding.btn32.text = "СТАРТ"
                viewModel34.pauseCounter(3)
            }
        }

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel34.getValueFlow(3).collect {
                        binding.tx33.text = it.toString()
                    }
                }
                launch {
                    viewModel34.getConditionFlow(3).collect {
                        if (it == true) {
                            binding.btn32.text = "СТОП"
                        }
                        else binding.btn32.text = "СТАРТ"
                    }
                }
            }
        }

        binding.btn31.setOnClickListener {
            viewModel34.resetCounter(3)
            viewModel34.pauseCounter(3)
        }





        binding.btn42.setOnClickListener {
            if (binding.btn42.text == "СТАРТ") {
                binding.btn42.text = "СТОП"
                viewModel34.startCounter(4)
            } else {
                binding.btn42.text = "СТАРТ"
                viewModel34.pauseCounter(4)
            }
        }

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel34.getValueFlow(4).collect {
                        binding.tx43.text = it.toString()
                    }
                }
                launch {
                    viewModel34.getConditionFlow(4).collect {
                        if (it == true) {
                            binding.btn42.text = "СТОП"
                        }
                        else binding.btn42.text = "СТАРТ"
                    }
                }
            }
        }

        binding.btn41.setOnClickListener {
            viewModel34.pauseCounter(4)
            viewModel34.resetCounter(4)
        }





        binding.btn52.setOnClickListener {
            if (binding.btn52.text == "СТАРТ") {
                viewModel5.startCounter()
            } else {
                viewModel5.pauseCounter()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel5.getValueFlow().collect {
                        binding.tx53.text = it
                    }
                }

                launch {
                    viewModel5.getConditionFlow().collect {
                        if (it == true) {
                            binding.btn52.text = "СТОП"
                        }
                        else binding.btn52.text = "СТАРТ"
                    }
                }
            }
        }

        binding.btn51.setOnClickListener {
            viewModel5.pauseCounter()
            viewModel5.resetCounter()
        }




        binding.btnGeneralReset.setOnClickListener {
            viewModel1.resetCounter()
            viewModel2.resetCounter()
            viewModel34.resetCounter(3)
            viewModel34.resetCounter(4)
            viewModel5.resetCounter()

            viewModel1.pauseCounter()
            viewModel2.pauseCounter()
            viewModel34.pauseCounter(3)
            viewModel34.pauseCounter(4)
            viewModel5.pauseCounter()
        }



        binding.btnGeneralStart.setOnClickListener {
            viewModel1.startCounter()
            viewModel2.startCounter()
            viewModel34.startCounter(5)
            viewModel5.startCounter()
        }


    }
}