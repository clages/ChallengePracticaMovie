package com.example.challengepractica.core

import android.view.View
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

//ViewHolder abstracto para re-usar
//generico <T> para pasar cualquier tipo de dato
//una vista y un recyclerView de la vista que le pasamos
abstract class BaseViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}