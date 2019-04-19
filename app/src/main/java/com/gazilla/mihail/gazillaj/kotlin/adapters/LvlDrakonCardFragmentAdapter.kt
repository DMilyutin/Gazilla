package com.gazilla.mihail.gazillaj.kotlin.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.helps.AppDialogs


class LvlDrakonCardFragmentAdapter(private val mContext: Context, private val mapLvl: Map<Int, Int>) : BaseAdapter() {

    private val myLvl = App.userWithKeys.level

    private lateinit var imgDracon: ImageView
    private lateinit var txtName :TextView
    private lateinit var txtDeckription :TextView

    override fun getCount(): Int
        = mapLvl.size

    override fun getItem(position: Int): Int
        = mapLvl.getValue(position+1)

    override fun getItemId(position: Int): Long
        = (position +1).toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView2 = inflater.inflate(R.layout.for_list_lvl_user, null)

        txtName = convertView2.findViewById(R.id.tvNameLvlForList)
        txtDeckription = convertView2.findViewById(R.id.tvDicriptionLvlForList)
        imgDracon = convertView2.findViewById(R.id.imgDragonLvl)

        imgDracon.setImageResource(getImage(getItemId(position).toInt()))
        txtName.text = getName(getItemId(position).toInt())
        txtDeckription.text = getDescription(getItemId(position).toInt())

        colorUserDracon(position)

        return convertView2
    }

    private fun getName(lvl: Int): String {
        return when (lvl) {
            1 -> "1-й уровень"
            2 -> "2-й уровень"
            3 -> "3-й уровень"
            4 -> "4-й уровень"
            5 -> "5-й уровень"
            else -> "Нет уровня"
        }
    }

    private fun getDescription(lvl: Int): String {
        return when (lvl) {
                1 -> "Начальный"
                2 -> "от " + mapLvl.getValue(2).toString() + "р"
                3 -> "от " + mapLvl.getValue(3).toString() + "р "
                4 -> "от " + mapLvl.getValue(4).toString() + "р "
                5 -> "от " + mapLvl.getValue(5).toString() + "р "
                else -> "Нет описания"
            }
    }

    private fun getImage(lvl: Int): Int {
        return when (lvl) {
            2 -> R.drawable.dragon2
            3 -> R.drawable.dragon3
            4 -> R.drawable.dragon4
            5 -> R.drawable.dragon5
            else -> R.drawable.dragon1
        }
    }

    private fun colorUserDracon(poss: Int){
        if (myLvl != getItemId(poss).toInt()){
            imgDracon.setColorFilter(-0x67000000)
            txtName.setTextColor(Color.rgb(151, 151, 151))
            txtDeckription.setTextColor(Color.rgb(151, 151, 151))
        }
        else{
            imgDracon.setColorFilter(Color.TRANSPARENT)
            txtName.setTextColor(Color.WHITE)
            txtDeckription.setTextColor(Color.WHITE)
        }
    }

}