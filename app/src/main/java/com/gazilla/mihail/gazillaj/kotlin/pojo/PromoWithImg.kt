package com.gazilla.mihail.gazillaj.kotlin.pojo

import android.os.Parcel
import android.os.Parcelable

class PromoWithImg(val id: Int, val name: String, val promoType: String,val shortDescription: String,  val description: String, val img: ByteArray) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.createByteArray()!!)


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(promoType)
        parcel.writeString(shortDescription)
        parcel.writeString(description)
        parcel.writeByteArray(img)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PromoWithImg> {
        override fun createFromParcel(parcel: Parcel): PromoWithImg {
            return PromoWithImg(parcel)
        }

        override fun newArray(size: Int): Array<PromoWithImg?> {
            return arrayOfNulls(size)
        }
    }

}