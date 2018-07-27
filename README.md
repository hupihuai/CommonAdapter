# CommonAdapter
封装的RecyclerView adapter
使用简单，只封装不变部分，不过度封装，易于扩展

使用示例

1.单ViewType

 recyclerView.setUp(users, R.layout.item_layout, { holder, item ->
            var binding = DataBindingUtil.getBinding<ItemLayoutBinding>(holder.itemView)
            binding?.nameText?.text = item.name
            binding?.surNameText?.text = item.surname
        })
  
  
  2.多ViewType
  
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
                
                
   同时支持普通layout和DataBinding Layout


感谢

https://github.com/armcha/Kadapter
