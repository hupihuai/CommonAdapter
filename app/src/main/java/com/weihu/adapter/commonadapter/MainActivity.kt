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
        val users = listOf(User("A", "B", R.layout.item_layout),
                User("A", "B", R.layout.item_layout2),
                User("A", "B", R.layout.item_layout),
                User("A", "B", R.layout.item_layout2),
                User("A", "B", R.layout.item_layout),
                User("A", "B", R.layout.item_layout2),
                User("A", "B", R.layout.item_layout))

        val item: (AbstractAdapter.Holder, User) -> Unit = { holder, user ->

        }

//        recyclerView.setUp(users, R.layout.item_layout, { holder, item ->
//            var binding = DataBindingUtil.getBinding<ItemLayoutBinding>(holder.itemView)
//            binding?.nameText?.text = item.name
//            binding?.surNameText?.text = item.surname
//        })

        recyclerView.setUP(users,
                listItems = *arrayOf(
                        ListItem(R.layout.item_layout, { holder, item ->
                            var binding = DataBindingUtil.getBinding<ItemLayoutBinding>(holder.itemView)
                            binding?.nameText?.text = item.name
                            binding?.surNameText?.text = item.surname
                        }, {
                            Snackbar.make(window.decorView, it.name, Snackbar.LENGTH_SHORT).show()
                        }),
                        ListItem(R.layout.item_layout2, { holder, item ->
                            var nameText: TextView = holder.getView(R.id.nameText)
                            var surNameText: TextView = holder.getView(R.id.surNameText)
                            nameText.text = item.name
                            surNameText.text = item.surname
                        }, {

                        })
                ))
    }
}

class User(val name: String, val surname: String, private val itemType: Int) : ListItemI {
    override fun getType(): Int {
        return itemType
    }
}
