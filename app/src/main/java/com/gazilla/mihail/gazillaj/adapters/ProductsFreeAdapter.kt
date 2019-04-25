package com.gazilla.mihail.gazillaj.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.helps.MenuImg
import com.gazilla.mihail.gazillaj.pojo.SmartMenuItem
import com.squareup.picasso.Picasso

class ProductsFreeAdapter(private val menuItems: List<SmartMenuItem>,  private val mContext: Context ): BaseAdapter() {

    private val menuImg = MenuImg()


    override fun getCount(): Int
       = menuItems.size

    override fun getItem(position: Int): SmartMenuItem
        = menuItems[position]

    override fun getItemId(position: Int): Long
        = menuItems[position].id.toLong()

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView2 = inflater.inflate(R.layout.for_list_my_gifts, null)

        (convertView2.findViewById<View>(R.id.tvNameGift) as TextView).text = getItem(position).name
        (convertView2.findViewById<View>(R.id.tvDecriptionGift) as TextView).text = getItem(position).description
        (convertView2.findViewById<View>(R.id.imageView18) as ImageView).setImageResource(R.drawable.gaz)

        if (getItem(position).count>1)
        (convertView2.findViewById<View>(R.id.tvCountGifts) as TextView).text = "x ${getItem(position).count}"


        val imgMiniItemMenu = (convertView2.findViewById<View>(R.id.imgPhotoPresent) as ImageView)

        Picasso.with(mContext).load(menuImg.getImg(getItem(position).id)).into(imgMiniItemMenu)

        return convertView2
    }

}