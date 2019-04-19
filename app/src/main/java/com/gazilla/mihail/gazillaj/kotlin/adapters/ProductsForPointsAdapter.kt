package com.gazilla.mihail.gazillaj.kotlin.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.helps.MenuImg
import com.gazilla.mihail.gazillaj.kotlin.pojo.MenuCategory
import com.gazilla.mihail.gazillaj.kotlin.pojo.MenuItem
import com.gazilla.mihail.gazillaj.kotlin.presenters.ProductsMenuAdapterPresenter
import com.gazilla.mihail.gazillaj.kotlin.views.ProductsForPointsAdapterView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.for_exlist_name_child_group_presents.view.*

class ProductsForPointsAdapter(private val menuCategores: List<MenuCategory>, private val context: Context): BaseExpandableListAdapter(), ProductsForPointsAdapterView {

    private val menuImg = MenuImg()
    private val productsMenuAdapterPresenter = ProductsMenuAdapterPresenter(this)

    private lateinit var imgFavorite: ImageView
    private var favoriteList = productsMenuAdapterPresenter.favorite()

    init {
        val favor = App.userWithKeys.favorites
        Log.i("Loog", "favorite - ${favor.size}")
    }

    override fun getGroupCount(): Int
       = menuCategores.size

    override fun getChildrenCount(groupPosition: Int): Int
        = menuCategores[groupPosition].items.size


    override fun getGroup(groupPosition: Int): MenuCategory
        = menuCategores[groupPosition]


    override fun getChild(groupPosition: Int, childPosition: Int): MenuItem
        = menuCategores[groupPosition].items[childPosition]


    override fun getGroupId(groupPosition: Int): Long
        = menuCategores[groupPosition].id.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long
        = menuCategores[groupPosition].items[childPosition].id.toLong()

    override fun hasStableIds(): Boolean
            = false

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView2 = inflater.inflate(R.layout.for_exlist_name_group_presents, null)

        (convertView2.findViewById<View>(R.id.tvNameGroupPresentsExList) as TextView).text = getGroup(groupPosition).name

        return convertView2
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView2 = inflater.inflate(R.layout.for_exlist_name_child_group_presents, null)

        val menuItem = getChild(groupPosition, childPosition)

        (convertView2.findViewById<View>(R.id.tvNameChildPresentsExList) as TextView).text = menuItem.name
        (convertView2.findViewById<View>(R.id.tvDescriptionChildPresentsExList) as TextView).text = menuItem.weight
        (convertView2.findViewById<View>(R.id.tvCoastChildPresentsExLists) as TextView).text = menuItem.price.toString()
        (convertView2.findViewById<View>(R.id.imageView) as ImageView).setImageResource(R.drawable.gaz)
        val imgMiniItemMenu = (convertView2.findViewById<View>(R.id.imgMiniItemMenu) as ImageView)

        Picasso.with(context).load(menuImg.getImg(menuItem.id)).into(imgMiniItemMenu)

        imgFavorite = (convertView2.findViewById<View>(R.id.imgFavoritIcon) as ImageView)

        if (favoriteList[menuItem.id] == 1)
            setImageFavorite(true)
        else
            setImageFavorite(false)

        imgFavorite.setOnClickListener {
            if (favoriteList[menuItem.id] == 1){
                it.imgFavoritIcon.setImageResource(R.drawable.ic_grade_grey24dp)
                productsMenuAdapterPresenter.favoriteProduct(true, menuItem.id)
            }
            else{
                it.imgFavoritIcon.setImageResource(R.drawable.ic_grade_gold_24dp)
                productsMenuAdapterPresenter.favoriteProduct(false, menuItem.id)
            }
        }
        return convertView2
    }


    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean
        = true

    private fun setImageFavorite(isFavorite: Boolean) {
        if (isFavorite)
            imgFavorite.setImageResource(R.drawable.ic_grade_gold_24dp)
        else
            imgFavorite.setImageResource(R.drawable.ic_grade_grey24dp)
    }

    override fun updateList(addFavorite: Boolean, id: Int) {
        if (addFavorite){
            favoriteList[id] = 1

        }

        else{
            favoriteList[id] = 0

        }
        App.menuFromServer.favoriteMenu = favoriteList
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}