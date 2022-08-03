package com.zeira.fuelua.presentation.menu

import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseListAdapter
import com.zeira.fuelua.core.presentation.BindingHolder
import com.zeira.fuelua.databinding.MenuItemBinding
import com.zeira.fuelua.domain.models.MenuItem

class MenuAdapter(
    onClick: (MenuItem) -> Unit,
) : BaseListAdapter<MenuItem, MenuItemBinding>(onClick, MenuDiffCallback) {
    override val layoutId: Int = R.layout.menu_item

    override fun onBindViewHolder(holder: BindingHolder<MenuItemBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.menuTv.text = holder.binding.root.context.getString(getItem(position).titleResId)
    }
}