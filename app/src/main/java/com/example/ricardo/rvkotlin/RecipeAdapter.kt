package com.example.ricardo.rvkotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recipe_view.view.*

class RecipeAdapter (private val dataList : ArrayList<Recipe>, private val listener : Listener) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    interface Listener {

        fun onItemClick(recipe: Recipe)
    }

    private val colors : Array<String> = arrayOf("#EF5350", "#EC407A", "#AB47BC", "#7E57C2", "#5C6BC0", "#42A5F5")

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(dataList[position], listener, colors, position)
    }

    override fun getItemCount(): Int = dataList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_view, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        fun bind(recipe: Recipe, listener: Listener, colors : Array<String>, position: Int) {

            itemView.title.text = recipe.title
            itemView.ingredients.text = recipe.ingredients
            if( recipe.thumbnail != "")
                Picasso.get().load(recipe.thumbnail).fit().into(itemView.image);


        }
    }
}