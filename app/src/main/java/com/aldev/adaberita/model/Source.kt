package com.aldev.adaberita.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
	val name: String? = null,
	val id: String? = null
) : Parcelable