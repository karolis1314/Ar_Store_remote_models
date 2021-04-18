package com.example.clickeventciew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_layout.view.*

class MainActivity : AppCompatActivity(), RecyclerAdapter.OnItemClickListener {

    private val titlesList = mutableListOf<String>()
    private val detailsList = mutableListOf<String>()
    private val imagesList = mutableListOf<Int>()
    private val urlList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        postToList()


        rv_recyclerView.layoutManager = LinearLayoutManager(this)
        rv_recyclerView.adapter = RecyclerAdapter(titlesList, detailsList, imagesList,this)
    }

    private fun addToList(title:String, description: String, image: Int, url: String){
        titlesList.add(title)
        detailsList.add(description)
        imagesList.add(image)
        urlList.add(url)

}

    private fun postToList(){
        addToList("Sofa-> €199.99","This sofa is very comfortable.\nMade by D.Bittman", R.drawable.sofa ,"https://firebasestorage.googleapis.com/v0/b/finalarstorage.appspot.com/o/sofa.glb?alt=media&token=e4c77770-9560-4845-81b7-fd760b6a5831")
        addToList("Wooden Table-> €59.99","Wooden table.\nMade by Google", R.drawable.woodentable ,"https://firebasestorage.googleapis.com/v0/b/finalarstorage.appspot.com/o/table.glb?alt=media&token=af963e66-3943-475e-b56a-0dc54d1be8a3")
        addToList("Armchair-> €99.99","Armchair.\nMade by Google", R.drawable.armchair ,"https://firebasestorage.googleapis.com/v0/b/finalarstorage.appspot.com/o/armchair.glb?alt=media&token=b82e15e3-50ed-4a6c-a3c8-2f6f747c986f")
        addToList("Pc Desk-> €89.99","Pc Desk.\nMade by Google", R.drawable.pcdesk ,"https://firebasestorage.googleapis.com/v0/b/finalarstorage.appspot.com/o/pcdesk.glb?alt=media&token=470505aa-46f7-496d-b27c-819e35f0d884")
        addToList("Computer-> €2199.99","Computer.\nMade by Google", R.drawable.computer ,"https://firebasestorage.googleapis.com/v0/b/finalarstorage.appspot.com/o/computer.glb?alt=media&token=b9306107-380d-4b61-866c-e8f10f791b93")
        addToList("Bookcase-> 23.99","Bookcase.\nMade by Google", R.drawable.bookcase ,"https://firebasestorage.googleapis.com/v0/b/finalarstorage.appspot.com/o/bookcase.glb?alt=media&token=c0f53ea5-fe3e-45cf-8cb6-3425f509d0a4")

    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, arScene::class.java)
        //intent.putExtra("url","https://firebasestorage.googleapis.com/v0/b/finalarstorage.appspot.com/o/out.glb?alt=media&token=153bd401-e60d-4025-aedc-436962aa9496")
        intent.putExtra("url", urlList.get(position))
        //intent.putExtra("url",rv_recyclerView.get(position).tv_title.toString())
        startActivity(intent)
    }
}
