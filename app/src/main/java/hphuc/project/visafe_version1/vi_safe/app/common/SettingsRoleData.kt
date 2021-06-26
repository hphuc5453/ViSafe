package hphuc.project.visafe_version1.vi_safe.app.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SettingsRoleData(
    val accidentType: Int,
    val type: String,
    var isChoose: Boolean = false
) : Parcelable