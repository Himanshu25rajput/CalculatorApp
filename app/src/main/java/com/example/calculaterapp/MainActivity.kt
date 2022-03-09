package com.example.calculaterapp

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.appbar.AppBarLayout
import java.io.IOException
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    var textView:TextView?=null
    var lastnumeric:Boolean=false
    var lastdot:Boolean=false
    var url:String="https://www.storyblocks.com/audio/stock/click-double-digital-soft-sgvbx6nnldsk0wxuvel.html"
    private var mediaplayer:MediaPlayer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //for removing status bar cool

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView=findViewById(R.id.textView)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        // for restrict the screenshot on app

    }
    fun ondigit(view: View){
        textView?.append((view as Button).text)
        lastnumeric=true
        lastdot=false
        playSong()

        //Toast.makeText(this,"working yes",Toast.LENGTH_SHORT)
       /* var sentence="Harry is a coder"
        if (sentence.contains("Harry"))
        {
            textView?.append("Harry")
        }*/
    }
    fun onclr(view:View){
        textView?.text=" "
        playSong()
    }

    fun onDecimalpoint(view:View){
        if (lastnumeric && !lastdot){
            textView?.append(".")
            lastnumeric=false
            lastdot=true
            playSong()
        }

    }
     private fun playSong(){
        try {
            val soundURI =
                Uri.parse("android.resource://com.example.calculaterapp/"
                        + R.raw.music2)
            mediaplayer = MediaPlayer.create(applicationContext, soundURI)
            mediaplayer?.isLooping = false // Sets the player to be looping or non-looping.
            mediaplayer?.start() // Starts Playback.
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }
    fun onOperater(view:View){
        textView?.text?.let {
            if (lastnumeric && !isOperaterAdd(it.toString())){
                textView?.append((view as Button).text)
                playSong()
            }
        }
    }
    private fun isOperaterAdd(value:String):Boolean{
        return if(value.startsWith("-")) {
            false
        }
        else {
            value.contains("-")
                    || value.contains("+")
                    || value.contains("/")
                    || value.contains("*")
        }
    }
    fun onEqual(view: View){
        if (lastnumeric){
            var prefix=""
            var tvvalue=textView?.text.toString()

            try {
                if (tvvalue.startsWith("-")) {
                    prefix="-"
                    tvvalue.substring(1)
                }
                if (tvvalue.contains("-")) {
                    var tvsplit = tvvalue.split("-")
                    var one = tvsplit[0]
                    var two = tvsplit[1]
                    textView?.text = onNozero((one.toDouble() - two.toDouble()).toString())
                }
                else if (tvvalue.contains("+")) {
                    var tvsplit = tvvalue.split("+")
                    var one = tvsplit[0]
                    var two = tvsplit[1]
                    textView?.text = onNozero((one.toDouble() + two.toDouble()).toString())
                }
                else if (tvvalue.contains("/")) {
                    var tvsplit = tvvalue.split("/")
                    var one = tvsplit[0]
                    var two = tvsplit[1]
                    textView?.text = onNozero((one.toDouble() / two.toDouble()).toString())
                }
                else if (tvvalue.contains("*")) {
                    var tvsplit = tvvalue.split("*")
                    var one = tvsplit[0]
                    var two = tvsplit[1]
                    textView?.text = onNozero((one.toDouble() * two.toDouble()).toString())
                }

            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    fun onNozero(result: String):String{
        var value=result
        if (result.contains(".0"))
           value= result.substring(0 ,result.length -2)
        return value
    }
}