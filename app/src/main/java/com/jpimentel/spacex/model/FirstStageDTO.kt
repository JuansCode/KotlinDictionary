package com.jpimentel.spacex.model

import android.os.Parcelable
import com.jpimentel.spacex.model.CoresDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FirstStageDTO(
    var cores: List<CoresDTO>? = null
) : Parcelable