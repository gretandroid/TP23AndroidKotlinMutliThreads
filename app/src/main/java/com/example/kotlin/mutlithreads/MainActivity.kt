package com.example.kotlin.mutlithreads

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.mutlithreads.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.net.URL
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // le handler permet de récuperer des android.os.Message provenant
    // d'autre threads dans le thread UI
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val bundle = msg.data
            val message = bundle?.getString(MESSAGE_KEY)
            display(message!!)
        }
    }

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
//        display("code du thread UI 1")
//        display("code du thread UI 2")
//        display("code du thread UI 3")
        //Fin du Traitement dans le thread UI

        // création d'un thread qui est interdit
        // impossibilité de communiquer avec le UI thread
        // à partir d'un thread créé manuellement
        // avec les logs cela fonctionne car on ne solicite
        // pas le UI thread qui est le main thread
//        val runnable = Runnable {
//            Log.d(MAIN_ACTIVITY_LOG, "avant la boucle")
//            for (i in 1..10) {
//                Log.d(MAIN_ACTIVITY_LOG, "loop : $i")
//            }
//            Log.d(MAIN_ACTIVITY_LOG, "après la boucle")
//        }
//        val thread = Thread(runnable)
//        thread.start()

//        // une autre façon d'appeler un nouveau thread
//        thread(start = true) {
//            Log.d(MAIN_ACTIVITY_LOG, "avant la boucle")
//            for (i in 1..10) {
//                val bundle = Bundle()
//                val msgValue = "loop : $i"
//                Log.d(MAIN_ACTIVITY_LOG, msgValue)
//                bundle.putString(MESSAGE_KEY, msgValue)
//                //also fait une reflexion de l'objet sur lui meme
//                // en s'appelant par it
//                Message().also {
//                    it.data = bundle
//                    handler.sendMessage(it)
//                }
//                //équivalent de also avec declaration de variable
//                // pour traitement intermediaire
////                val msg = Message()
////                msg.data = bundle
////                handler.sendMessage(msg)
//
////                Thread.sleep(200)
//            }
//            Log.d(MAIN_ACTIVITY_LOG, "après la boucle")
//        }

        //Coroutines
//        CoroutineScope(Dispatchers.Main).launch {
//            val result = quickProcess()
//            display(result)
//        }
        CoroutineScope(Dispatchers.Main).launch {
            val result = longProcess()
            display(result!!)
        }
    }

    suspend fun quickProcess(): String {
        delay(500)
        return "\nquick message from coroutine"
    }

    suspend fun longProcess(): String? {
        var content: String?
        // Dispatchers.IO est utilisé pour les traitements long
        // exemple remote access, db.
        // Dispatchers.Default pour les traitements
        // qui demandent bcp de resources processeur
        // exemple encodage video, parsing de gros fichiers
        withContext(Dispatchers.IO) {
            val url = URL(POKEREQUEST)
            content = url.readText(Charset.defaultCharset())
        }
        return content
    }

    fun display(msg: String) {
        binding.threadOutputView.append("${msg}\n")
    }
}