package com.example.practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class gamen3 : AppCompatActivity() {
    companion object {
        val KEY_STATE = "key_state"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamen3)

        var start_t : EditText = findViewById(R.id.start_t)
        var startbutton : Button = findViewById(R.id.start)
        var end_t : EditText = findViewById(R.id.end_t)
        var tuikaplan : Button = findViewById(R.id.tuikaplan)
        var planname : TextView = findViewById(R.id.plan)

        var db = FirebaseFirestore.getInstance()

//        val state = intent.getSerializableExtra(KEY_STATE)
        val text1 = intent.getStringExtra("keikaku")
        val plan = intent.getStringExtra("plan")
        var text3 = intent.getStringArrayExtra("text3")
        //val text3pl = intent.getIntExtra("text3pl")//いんでっくすをかえす
        var text_list =text3!!.toList()//
        //val text_mu = text3!!.toMutableList()//text3はarrayListだけどそれをmutableListにかえた
        var start :String = ""
        var goal :String = ""
//        if(state is Travel){
//            start = state.startTime
//            println(state.startTime)
//            goal = state.endTime
//            println(state.endTime)
//        }

        startbutton.setOnClickListener{//コレクションからｄｏｃｕｍｅｎｔをさくじょしたい
            //db.collection(text1!!).document(plan!!).delete()
            //text_mu.removeAt(text3pl)
            //text_list = text_mu.toList()
            //text3 = text_list.toTypedArray()
            val intent = Intent(this,gamen2::class.java)
            startActivity(intent)
        }
        planname.text =start

        //Log.d("travellllll", travel.toString())
        Log.d("plannnnnnn", plan.toString())
        Log.d("text111111", text1.toString())

        tuikaplan.setOnClickListener{
            val travel = Travel( start_t.text.toString(),end_t.text.toString())
            val intent = Intent(this, gamen2::class.java)
            addData(db,text1!!,plan!!,travel)
            //db.collection(text1!!).document(plan!!).set(travel)
//            Log.d("travellllll", travel.toString())
//            Log.d("plannnnnnn", plan.toString())
//            Log.d("text111111", text1.toString())
            //intent.putExtra("Travel",travel)
            //intent.putExtra("text3a",text3)
            startActivity(intent)
        }
    }
}