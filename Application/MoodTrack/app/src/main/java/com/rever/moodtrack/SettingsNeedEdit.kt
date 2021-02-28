package com.rever.moodtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rever.moodtrack.Adapters.NeedAdapter
import com.rever.moodtrack.data.NeedStore.NeedViewModel
import kotlinx.android.synthetic.main.activity_settings_need_edit.*

class SettingsNeedEdit() : AppCompatActivity() {
    private lateinit var needAdapter: NeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_need_edit)

        val actionBar = supportActionBar
        actionBar!!.title = "Edit custom needs"
        actionBar.setDisplayHomeAsUpEnabled(true)

        val needViewModel = ViewModelProvider(this).get(NeedViewModel::class.java)

        var check = true //Loop once, avoids duplications when updated
        needAdapter = NeedAdapter(mutableListOf(), needViewModel)
        rvNeedList.layoutManager = LinearLayoutManager(this)
        /*needViewModel.readAllData.observe(this, Observer {
            if (check) {
                it.forEach {
                    check =false
                    needAdapter.addNeed(it)
                }
                rvNeedList.adapter = needAdapter
            }
        })*/
        rvNeedList.adapter = needAdapter
        needViewModel.readAllData.observe(this, Observer {
            //needAdapter.addNeed(it)
            needAdapter.needList = it
            needAdapter.notifyDataSetChanged()
        })

        fabAdd.setOnClickListener {
            AddNeedItemDialog(this, needViewModel).show()
        }
    }
}