package com.orozcoder.jj.coroutines_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val RESULT = "Method Result!"
    private val OTHER_RESULT = "Other Method Result!"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnClick.setOnClickListener{
            // IO, Main, Default
            CoroutineScope(IO).launch {
                fakeApiRequest()
            }
        }
    }

    private fun setNewText(input: String){
        val newText = txtMsg.text.toString() + "\n$input"
        txtMsg.text = newText
    }

    private suspend fun setTextOnMainThread(input: String){
        withContext(Main){
            setNewText(input)
        }
    }

    private suspend fun fakeApiRequest(){
        val resultVar = getFirstResult()
        println("debug: ${resultVar}")
        setTextOnMainThread(resultVar)

        val OtherResult = getSecondResult()
        println("debug: ${OtherResult}")
        setTextOnMainThread(OtherResult)
    }

    private suspend fun getFirstResult() : String{
        logThread("getFirstResult")
        delay(1000)
        return RESULT
    }

    private suspend fun getSecondResult() : String{
        logThread("getSecondResult")
        delay(1500)
        return OTHER_RESULT
    }

    private fun logThread(methodName: String){
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }
}
