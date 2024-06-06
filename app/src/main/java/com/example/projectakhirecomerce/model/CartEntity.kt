package com.example.projectakhirecomerce.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "idUser")
    val idUser: String,

    @ColumnInfo(name = "idProduct")
    val idProduct:String,

    @ColumnInfo(name = "nameProduct")
    val nameProduct: String,

    @ColumnInfo(name = "shopProduct")
    val shopProduct: String,

    @ColumnInfo(name = "imgProduct")
    val imgProduct: String,

    @ColumnInfo(name = "priceProduct")
    val priceProduct: String,

    @ColumnInfo(name = "qtyProduct")
    val qtyProduct: String,

    @ColumnInfo(name = "subTotal")
    val subTotal: String,

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(idUser)
        parcel.writeString(idProduct)
        parcel.writeString(nameProduct)
        parcel.writeString(shopProduct)
        parcel.writeString(imgProduct)
        parcel.writeString(priceProduct)
        parcel.writeString(qtyProduct)
        parcel.writeString(subTotal)
    }

    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<CartEntity> {
        override fun createFromParcel(parcel: Parcel): CartEntity {
            return CartEntity(parcel)
        }

        override fun newArray(size: Int): Array<CartEntity?> {
            return arrayOfNulls(size)
        }
    }
}