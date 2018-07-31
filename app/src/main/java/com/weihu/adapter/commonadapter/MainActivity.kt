package com.weihu.adapter.commonadapter

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.weihu.adapter.commonadapter.databinding.ItemLayout2Binding
import com.weihu.adapter.commonadapter.databinding.ItemLayoutBinding
import io.github.armcha.recyclerviewkadapter.kadapter.*

/**
 * Created by hupihuai on 2018/7/27.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        val users = listOf(User("A", "B"),
                User("A", "B"),
                User("A", "B"),
                User("A", "B"),
                User("A", "B"),
                User("A", "B"),
                User("A", "B"))

        val item: (AbstractAdapter.Holder, User) -> Unit = { holder, user ->

        }
        var adaptedUsers = users.mapIndexed { index, user -> ListItemAdapter(user, if (index % 2 == 0) R.layout.item_layout else R.layout.item_layout2) }
        recyclerView.setUp(adaptedUsers, R.layout.item_layout, bindHolder = { holder, item ->
            var binding = DataBindingUtil.getBinding<ItemLayoutBinding>(holder.itemView)
            binding?.nameText?.text = item.data.name
            binding?.surNameText?.text = item.data.surname
        })

        recyclerView.setUP(adaptedUsers,
                ListItem(R.layout.item_layout, { holder, item ->
                    var binding = DataBindingUtil.getBinding<ItemLayoutBinding>(holder.itemView)
                    binding?.nameText?.text = item.data.name
                    binding?.surNameText?.text = item.data.surname
                }, {
                    Snackbar.make(window.decorView, it.data.name, Snackbar.LENGTH_SHORT).show()
                }),
                ListItem(R.layout.item_layout2, { holder, item ->
                    var nameText: TextView = holder.getView(R.id.nameText)
                    var surNameText: TextView = holder.getView(R.id.surNameText)
                    nameText.text = item.data.name
                    surNameText.text = item.data.surname
                }, {

                }))
    }
}

class User(val name: String, val surname: String)
