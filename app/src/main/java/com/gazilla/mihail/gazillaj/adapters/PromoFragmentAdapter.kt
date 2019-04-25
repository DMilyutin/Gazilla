package com.gazilla.mihail.gazillaj.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.pojo.PromoWithImg

class PromoFragmentAdapter(private val promoWithImg: List<PromoWithImg>,private val mContext: Context) : BaseAdapter(){


    override fun getCount(): Int
        = promoWithImg.size


    override fun getItem(position: Int): PromoWithImg
        = promoWithImg[position]


    override fun getItemId(position: Int): Long
       = promoWithImg[position].id.toLong()


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView2 = inflater.inflate(R.layout.for_list_promo_item, null)

        val tvNamePromo = (convertView2.findViewById(R.id.tvNamePromoList) as TextView)

        if (getItem(position).promoType== "new") tvNamePromo.setTextColor(Color.RED)

        tvNamePromo.text = getItem(position).name
        (convertView2.findViewById<View>(R.id.tvDecriptionPromoList) as TextView).text = getItem(position).shortDescription
        val imgMiniItemMenu = (convertView2.findViewById(R.id.imgStockPromoList) as ImageView)
        imgMiniItemMenu.setImageBitmap(BitmapFactory.decodeByteArray(getItem(position).img, 0, getItem(position).img.size))

        return convertView2
    }

}