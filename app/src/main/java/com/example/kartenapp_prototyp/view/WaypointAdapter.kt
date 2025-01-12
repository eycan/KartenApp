package com.example.kartenapp_prototyp.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kartenapp_prototyp.R
import com.example.kartenapp_prototyp.data.Waypoint
import com.example.kartenapp_prototyp.activity.MainActivity

class WaypointAdapter(val waypointList: MutableList<Waypoint>) : RecyclerView.Adapter<WaypointAdapter.WaypointViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaypointViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.route_item, parent, false)
        return WaypointViewHolder(view)
    }

    override fun onBindViewHolder(holder: WaypointViewHolder, position: Int) {
        val waypoint = waypointList[position]
        holder.bind(waypoint)
    }

    override fun getItemCount(): Int = waypointList.size


    class WaypointViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val route_item: LinearLayout = itemView.findViewById(R.id.route_item)
        private val route_item_text_date: TextView = itemView.findViewById(R.id.route_item_text_date)
        private val route_item_text_id: TextView = itemView.findViewById(R.id.route_item_text_id)

        fun bind(waypoint: Waypoint) {
            this.route_item.setOnClickListener{
                val context = itemView.context
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("CURRENT_ROUTE", waypoint.route)
                context.startActivity(intent)
            }
            route_item_text_date.text = waypoint.getTimestampAsDate()
            route_item_text_id.text = waypoint.route.toString()
        }
    }
}