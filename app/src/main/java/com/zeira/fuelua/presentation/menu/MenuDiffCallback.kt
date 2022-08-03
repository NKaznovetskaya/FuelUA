package com.zeira.fuelua.presentation.menu

import androidx.recyclerview.widget.DiffUtil
import com.zeira.fuelua.domain.models.MenuItem

val MenuDiffCallback = object : DiffUtil.ItemCallback<MenuItem>() {
    override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
        return oldItem == newItem
    }
}
