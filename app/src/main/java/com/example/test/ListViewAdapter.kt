package com.example.test

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.TextView

class ListViewAdapter(val context: Activity, val arrayList: ArrayList<ListViewItem>) : ArrayAdapter<ListViewItem>(context,
    R.layout.custom_listview_item,arrayList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.custom_listview_item,null)

        val destination : TextView = view.findViewById(R.id.destination)
        val start_date : TextView = view.findViewById(R.id.from)
        val end_date : TextView = view.findViewById(R.id.to)

        destination.text = arrayList[position].destination
        start_date.text = arrayList[position].from
        end_date.text = arrayList[position].to

        return view
    }

    override fun getItem(position: Int): ListViewItem? {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return arrayList.size
    }

}