package com.example.practice

import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore


var n =""

//val travelList : MutableMap<String,Travel> = mutableMapOf()

class gamen2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamen2)


        val tuika2: Button = findViewById(R.id.tuika2) //予定追加ボタン
        val modoru : Button = findViewById(R.id.modoru)
        val planList: ListView = findViewById(R.id.travelList) // 予定一覧

        val text1 = intent.getStringExtra("key1")//コレクション名
        val text2 = intent.getStringArrayListExtra("key2")//documentのリストのキーがはいっている
        var text3 = intent.getStringArrayListExtra("key3")//text１の中に入っているdocumentの名前のリストがはいっている
        //val tra = intent.getSerializableExtra("tra")//画面３で登録したデータクラスの値を画面3から2へもってくる

        //text3 = intent.getStringArrayListExtra("text3a")

        val text_list =text3!!.toList()//
        val text_mu = text3!!.toMutableList()//text3はarrayListだけどそれをmutableListにかえた
        var pl : String = ""//ここで入力するプランの名前

        Log.d("aaaaaaaaaafdg", text2.toString())
        Log.d("bbbbbbbbbbbb", text3.toString())


        val adapter = ArrayAdapter<String>(//リストにトラベルのデータを格納
            this,
            android.R.layout.simple_expandable_list_item_1,
            arrayListOf()//最初は空
        )
        val db = FirebaseFirestore.getInstance()

        planList.adapter = adapter
        Log.d("ggggggg",text_mu.toString())
        adapter.addAll(text_mu)


        planList.setOnItemClickListener { parent, view, position, id ->//リストをタップすると予定の詳細を表示suruyounisitai
            val intent = Intent(this, gamen3::class.java)
            val travel :Travel
            for(i in text_mu) {
                intent.putExtra(text2.toString(), i)
            }

            intent.putExtra("keikaku", text1)
            intent.putExtra("plan",pl)
//            intent.putExtra("text3",text3)
//            intent.putExtra("text3pl",text3.indexOf(pl))
            startActivity(intent)
        }
        db.collection(text1!!).document("empty").delete()

        //readFireStore(name1,n)
        tuika2.setOnClickListener {
            val plan = EditText(this)//planを登録するところ（アラートダイアログにて登録し、Travelのなかのplanのみ登録、他はNULLとする
                AlertDialog.Builder(this) // FragmentではActivityを取得して生成
                    .setTitle("add")
                    .setMessage("add")
                    .setView(plan)
                    .setPositiveButton(
                        "add",
                        DialogInterface.OnClickListener { dialogInterface, p ->
                            pl= plan.text.toString()
                            val tr: Travel = Travel("empty", "empty")
                            //(db,text1!!,pl,tr)
                            db.collection(text1!!).document(pl).set(tr)
                            text_mu.add(pl)//text_muに追加
                            adapter.add(pl)//
                        })
                    .setNegativeButton("cancel", null)
                    .show()

                Log.d("rrrrrrrrrrrrrrrr", text3.toString())
            }
        modoru.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


/*fun readFireStore(tokyoryokou : String,hikouki : String){
        val travels = ArrayList<Travel>()
        val db = FirebaseFirestore.getInstance()
        db.collection(tokyoryokou).document(hikouki).get()
            .addOnCompleteListener{
                if(it.isSuccessful){
                    travels.add(Travel("starttime","endtime"))
                    /*for (doc in it.result) {
                        var result = Result(doc.data.getValue("username").toString(), "${doc.data.getValue("score")} / 10")
                        travels.add(result)
                    }*/
                }else{

                }

            }*/

    }

    }

fun getTravel(db:FirebaseFirestore,collection:String,document:String):Travel{
    var travel: Travel = Travel("null", "null")
    db.collection(collection)
        .document(document)
        .get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null && document.data != null) {
                    Log.d(TAG, "getData")
                    Log.d(TAG, "DocumentSnapshot data: " + document.data?.get("startTime"))
                    Log.d(TAG, "DocumentSnapshot data: " + document.data?.get("endTime"))
                    travel = Travel(document.data?.get("startTime").toString(),document.data?.get("endTime").toString())
                } else {
                    Log.d(TAG, "No such document")
                }
            } else {
                Log.d(TAG, "get failed with " + task.exception)
            }
        }
        .addOnFailureListener { e -> Log.d(TAG, "Error adding document" + e)}
    return travel
}





