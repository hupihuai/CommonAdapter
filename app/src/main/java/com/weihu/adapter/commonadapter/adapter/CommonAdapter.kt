package io.github.armcha.recyclerviewkadapter.kadapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
/**
 * Created by hupihuai on 2018/7/27.
 */
class SingleAdapter<ITEM>(items: List<ITEM>,
                          private val layoutResId: Int,
                          private val bindHolder: (Holder, ITEM) -> Unit)
    : AbstractAdapter<ITEM>(items) {

    private var itemClick: (ITEM) -> Unit = {}

    constructor(items: List<ITEM>,
                layoutResId: Int,
                bindHolder: (Holder, ITEM) -> Unit,
                itemClick: (ITEM) -> Unit = {}) : this(items, layoutResId, bindHolder) {
        this.itemClick = itemClick
    }

    override fun createItemView(parent: ViewGroup, viewType: Int): View {
        var view = parent inflate layoutResId
        if (view.tag?.toString()?.contains("layout/") == true) {
            DataBindingUtil.bind<ViewDataBinding>(view)
        }
        return view
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        bindHolder(holder, itemList[position])
    }

    override fun onItemClick(itemView: View, position: Int) {
        itemClick(itemList[position])
    }
}


class MultiAdapter<ITEM : ListItemI>(private val items: List<ITEM>,
                                     private val bindHolder: (Holder, ITEM) -> Unit)
    : AbstractAdapter<ITEM>(items) {

    private var itemClick: (ITEM) -> Unit = {}
    private lateinit var listItems: Array<out ListItem<ITEM>>

    constructor(items: List<ITEM>,
                listItems: Array<out ListItem<ITEM>>,
                bindHolder: (Holder, ITEM) -> Unit,
                itemClick: (ITEM) -> Unit = {}) : this(items, bindHolder) {
        this.itemClick = itemClick
        this.listItems = listItems
    }

    override fun createItemView(parent: ViewGroup, viewType: Int): View {
        var view = parent inflate getLayoutId(viewType)
        if (view.tag?.toString()?.contains("layout/") == true) {
            DataBindingUtil.bind<ViewDataBinding>(view)
        }
        return view
    }

    private fun getLayoutId(viewType: Int): Int {
        var layoutId = -1
        listItems.forEach {
            if (it.layoutResId == viewType) {
                layoutId = it.layoutResId
                return@forEach
            }
        }
        return layoutId
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getType()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        bindHolder(holder, itemList[position])
    }

    override fun onItemClick(itemView: View, position: Int) {
        itemClick(itemList[position])
    }
}


fun <ITEM> RecyclerView.setUp(items: List<ITEM>,
                              layoutResId: Int,
                              bindHolder: (AbstractAdapter.Holder, ITEM) -> Unit,
                              itemClick: (ITEM) -> Unit = {},
                              manager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)): AbstractAdapter<ITEM> {
    val singleAdapter by lazy {
        SingleAdapter(items, layoutResId, { holder, item ->
            bindHolder(holder, item)
        }, {
            itemClick(it)
        })
    }
    layoutManager = manager
    adapter = singleAdapter
    return singleAdapter
}


fun <ITEM : ListItemI> RecyclerView.setUP(items: List<ITEM>,
                                          manager: RecyclerView.LayoutManager = LinearLayoutManager(this.context),
                                          vararg listItems: ListItem<ITEM>): AbstractAdapter<ITEM> {

    val multiAdapter by lazy {
        MultiAdapter(items, listItems, { holder, item ->
            var listItem: ListItem<ITEM>? = getListItem(listItems, item)
            listItem?.bindHolder?.invoke(holder, item)
        }, { item ->
            var listItem: ListItem<ITEM>? = getListItem(listItems, item)
            listItem?.itemClick?.invoke(item)
        })
    }
    layoutManager = manager
    adapter = multiAdapter
    return multiAdapter
}

private fun <ITEM : ListItemI> getListItem(listItems: Array<out ListItem<ITEM>>, item: ITEM): ListItem<ITEM>? {
    var listItem: ListItem<ITEM>? = null
    listItems.forEach {
        if (it.layoutResId == item.getType()) {
            listItem = it
            return@forEach
        }
    }
    return listItem
}

class ListItem<ITEM>(val layoutResId: Int,
                     val bindHolder: (holder: AbstractAdapter.Holder, item: ITEM) -> Unit,
                     val itemClick: (item: ITEM) -> Unit = {})


interface ListItemI {
    fun getType(): Int
}