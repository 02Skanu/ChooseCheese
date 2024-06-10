package com.sknau.choosecheese

import android.os.Parcel
import android.os.Parcelable


data class QrData(
    val qrImageUrl: String
)

data class ImageResponse(
    val pdb_paths: List<String>,
    val imageUrl: List<String>
)


data class ResponseData(
    val originalS3: String
)

data class sendTogetherData(
    val user_uuid: String
)

data class ClickResponseData(
    val original_url: String?,
    val smile_url: String?,
    val miso_point: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(original_url)
        parcel.writeString(smile_url)
        parcel.writeInt(miso_point)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ClickResponseData> {
        override fun createFromParcel(parcel: Parcel): ClickResponseData {
            return ClickResponseData(parcel)
        }

        override fun newArray(size: Int): Array<ClickResponseData?> {
            return arrayOfNulls(size)
        }
    }
}