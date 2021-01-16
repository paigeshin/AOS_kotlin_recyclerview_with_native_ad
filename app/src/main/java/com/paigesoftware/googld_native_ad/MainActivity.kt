package com.paigesoftware.googld_native_ad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayList = ArrayList<String?>()

        for(i in 0..200) {
            if(i % 30 == 0) {
                arrayList.add(null)
            } else {
                arrayList.add("text $i")
            }
        }

        val adapter = MyAdapter(this, arrayList)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

    }

}