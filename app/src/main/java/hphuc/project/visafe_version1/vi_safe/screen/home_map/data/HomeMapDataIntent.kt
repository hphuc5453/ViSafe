package hphuc.project.visafe_version1.vi_safe.screen.home_map.data

import android.os.Parcelable
import hphuc.project.visafe_version1.core.base.bus.EventBusData
import kotlinx.android.parcel.Parcelize

@Parcelize
class HomeMapDataIntent(
    val accidentType: Int
) : Parcelable, EventBusData