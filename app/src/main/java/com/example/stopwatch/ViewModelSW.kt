package com.example.stopwatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ViewModelSW1 : ViewModel() {

    private val mutableValueFlow = MutableStateFlow(0)

    private val mutableConditionFlow = MutableStateFlow(false)

    private var counter = 0

    private var job: Job? = null

    fun startCounter() {
        if (job == null) {count()}
    }

    private suspend fun suspendPauseCounter() {
        job?.run {
            cancel()
            job = null
        }
        mutableConditionFlow.emit(false)
    }
    fun pauseCounter() = viewModelScope.launch {suspendPauseCounter()}


    private fun count() {
        job = viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                delay(1000)
                counter++
                mutableConditionFlow.emit(true)
                mutableValueFlow.emit(counter)
            }
        }
    }

    fun resetCounter() {
        viewModelScope.launch {
            suspendPauseCounter()
            mutableValueFlow.emit(counter)
            mutableConditionFlow.emit(false)
            counter = 0
        }
    }

    fun getValueFlow(): StateFlow<Int> = mutableValueFlow.asStateFlow()
    fun getConditionFlow(): StateFlow<Boolean> = mutableConditionFlow.asStateFlow()
}




class ViewModelSW2 : ViewModel() {
    private val mutableValueFlow = MutableStateFlow(0)
    val vvalueFlow: StateFlow<Int> = mutableValueFlow.asStateFlow()

    private val mutableConditionFlow = MutableStateFlow(false)
    val cсonditionFlow: StateFlow<Boolean> = mutableConditionFlow.asStateFlow()

    var counter = 0

    var job: Job? = null

    val f = flow<Int> {
        while (true) {
            delay(1000)
            counter++
            emit(counter)
        }
    }

    fun startCounter() {
        if (job == null) {count()}
    }

    fun pauseCounter() {
        if (job != null) {
            job!!.cancel()
            job = null
        }
        viewModelScope.launch {
            mutableConditionFlow.emit(false)
        }
    }


    fun count() {
        job = viewModelScope.launch {
            f.collect {
                mutableConditionFlow.emit(true)
                mutableValueFlow.emit(it)

            }
        }
    }

    fun resetCounter() {
        counter = 0
        viewModelScope.launch {
            mutableConditionFlow.emit(false)
            mutableValueFlow.emit(counter)

        }
    }

    fun getValueFlow(): StateFlow<Int> = vvalueFlow
    fun getConditionFlow(): StateFlow<Boolean> = cсonditionFlow
}






class ViewModelSW3 : ViewModel() {
    private val mutableValueFlow3 = MutableStateFlow(0)
    val vvvalueFlow3: StateFlow<Int> = mutableValueFlow3.asStateFlow()

    private val mutableConditionFlow3 = MutableStateFlow(false)
    val cсconditionFlow3: StateFlow<Boolean> = mutableConditionFlow3.asStateFlow()


    private val mutableValueFlow4 = MutableStateFlow(0)
    val vvvalueFlow4: StateFlow<Int> = mutableValueFlow4.asStateFlow()

    private val mutableConditionFlow4 = MutableStateFlow(false)
    val cсconditionFlow4: StateFlow<Boolean> = mutableConditionFlow4.asStateFlow()


    var counterFor3Row = 0
    var counterFor4Row = 0

    var scope: CoroutineScope? = null

    var job3: Job? = null
    var job4: Job? = null

    fun startCounter(numRow: Int) {
        if (scope == null) {
            scope = CoroutineScope(Dispatchers.Default)
        }
        if (numRow == 3 && job3 == null) {countForThirdRow()}
        if (numRow == 4 && job4 == null) {countForFourthRow()}
        if (numRow == 5 && job4 == null && job3 != null) {countForFourthRow()}
        if (numRow == 5 && job4 != null && job3 == null) {countForThirdRow()}
        if (numRow == 5 && job4 == null && job3 == null) {
            countForThirdRow()
            countForFourthRow()
        }
    }


    fun pauseCounter(numRow: Int) {
        if (numRow == 3) {
            if (job3 != null) {
                job3!!.cancel()
                job3 = null
            }
            viewModelScope.launch {
                mutableConditionFlow3.emit(false)
            }
        }
        else {
            if (job4 != null) {
                job4!!.cancel()
                job4 = null
            }
            viewModelScope.launch {
                mutableConditionFlow4.emit(false)
            }
        }

        if (job3 != null && job4 != null) {
            if (job3!!.isCancelled && job4!!.isCancelled) {
                scope!!.cancel()
                scope = null
            }
        }
    }


    fun countForThirdRow() {

        job3 = scope!!.launch(Dispatchers.Default) {
            while (true) {
                delay(1000)
                counterFor3Row++
                mutableConditionFlow3.emit(true)
                mutableValueFlow3.emit(counterFor3Row)

            }
        }
    }

    fun countForFourthRow() {

        job4 = scope!!.launch(Dispatchers.Default) {
            while (true) {
                delay(1000)
                counterFor4Row++
                mutableConditionFlow4.emit(true)
                mutableValueFlow4.emit(counterFor4Row)

            }
        }
    }

    fun resetCounter(numRow: Int) {
        if (numRow == 3) {
            counterFor3Row = 0
            viewModelScope.launch {
                mutableConditionFlow3.emit(false)
                mutableValueFlow3.emit(counterFor3Row)

            }
        }
        else {
            counterFor4Row = 0
            viewModelScope.launch {
                mutableConditionFlow4.emit(false)
                mutableValueFlow4.emit(counterFor4Row)

            }
        }
    }

    fun getValueFlow(numRow: Int): StateFlow<Int> {
        if (numRow == 3) {
            return vvvalueFlow3
        }
        else return vvvalueFlow4
    }

    fun getConditionFlow(numRow: Int): StateFlow<Boolean> {
        if (numRow == 3) {
            return cсconditionFlow3
        }
        else return cсconditionFlow4
    }
}






class ViewModelSW5 : ViewModel() {
    private val mutableValueFlow5 = MutableStateFlow("РАЗ")
    val vvalueFlow5: StateFlow<String> = mutableValueFlow5.asStateFlow()

    private val mutableConditionFlow5 = MutableStateFlow(false)
    val cсonditionFlow5: StateFlow<Boolean> = mutableConditionFlow5.asStateFlow()

    var counter = 0

    var job: Job? = null

    val list = listOf<String>("РАЗ", "ДВА", "ТРИ", "ЧЕТЫРЕ")
    
    val f = flow<String> {
        while (true) {
            emit(list[counter])
            delay(1000)
            counter++
            if (counter == 4) {counter = 0}
        }
    }

    fun startCounter() {
        if (job == null) {count()}
    }

    fun pauseCounter() {
        if (job != null) {
            job!!.cancel()
            job = null
        }
        viewModelScope.launch {
            mutableConditionFlow5.emit(false)
        }
    }


    fun count() {
        job = viewModelScope.launch {
            f.collect {
                mutableConditionFlow5.emit(true)
                mutableValueFlow5.emit(it)
            }
        }
    }

    fun resetCounter() {
        counter = 0
        viewModelScope.launch {
            mutableValueFlow5.emit(list[counter])
            mutableConditionFlow5.emit(false)
        }
    }

    fun getValueFlow(): StateFlow<String> = vvalueFlow5
    fun getConditionFlow(): StateFlow<Boolean> = cсonditionFlow5
}

