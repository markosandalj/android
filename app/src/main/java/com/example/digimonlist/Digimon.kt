package com.example.digimonlist

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentId

data class Digimon(
    @DocumentId val id: String = "",
    val name: String = "",
    val type: String = "",
    val imageResId: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(imageResId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Digimon> {
        override fun createFromParcel(parcel: Parcel): Digimon {
            return Digimon(parcel)
        }

        override fun newArray(size: Int): Array<Digimon?> {
            return arrayOfNulls(size)
        }
    }
}
