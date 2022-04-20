package com.example.kotlin.mutlithreads

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.mutlithreads.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun onClickButtonEvent(view: View) {
        // Traitement dans le thread UI
//        //old fashion
//        val runnable = Runnable { display("code du runnable deprecated") }
//        val handler = Handler()
//        handler.post(runnable)

//        //placer du code à executer dans la pile du main thread
//        // qui sera executé en dernier par rapport au flow synchrone
//        val mainExecutor = ContextCompat.getMainExecutor(this)
//        mainExecutor.execute { display("code du runnable") }

//        // plus besoin
//        binding.threadOutputView.text = ""
//        display("code du thread UI")
//        display("code du thread UI")
//        display("code du thread UI")
        //Fin du Traitement dans le thread UI

        // création d'un thread qui est interdit
        // impossibilité de communiquer avec le UI thread
        // à partir d'un thread créé manuellement
        val runnable = Runnable {
            display("avant la boucle")
            for (i in 1..10) {
                display("loop : $i")
            }
            display("après la boucle")
        }


        val thread = Thread(runnable)
        thread.start()
    }

    fun display(msg: String) {
        binding.threadOutputView.append("${msg}\n")
    }
}