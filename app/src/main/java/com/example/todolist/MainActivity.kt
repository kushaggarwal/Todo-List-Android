package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer


class MainActivity : AppCompatActivity() {


    val list = arrayListOf<ToDoModel>()
    var adapter = TodoAdapter(list)

    val db by lazy {
        AppDatabase.getDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        todoRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        db.todoDao().getTask().observe(this,Observer {
            if (!it.isNullOrEmpty()) {
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        val item = menu.findItem(R.id.search)
        val searchView = item.actionView as SearchView

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.history->{
                startActivity(Intent(this,HistoryActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun openNewTask(view: View) {
        startActivity(Intent(this, TaskActivity::class.java))
    }
}