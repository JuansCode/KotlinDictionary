package com.jpimentel.spacex.viewmodel.spacexdetails

import com.jpimentel.spacex.model.SpaceXModel
import com.jpimentel.spacex.util.Utils.dateFormatYYYYMMDD
import com.jpimentel.spacex.util.formatTo
import com.jpimentel.spacex.util.toDate
import com.jpimentel.spacex.viewmodel.CustomViewModel

class SpaceXDetailsViewModel : CustomViewModel() {

    var selectedData: SpaceXModel? = null

    val formattedDate: String
        get() = selectedData?.launch_date_utc?.toDate()?.formatTo(dateFormatYYYYMMDD) ?: ""

}