package com.app.silky.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Geo (

  val lat : Double,
  val lng : Double
) : Parcelable