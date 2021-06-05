package hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ContractsDetailDataIntent(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val avatar: String? = null,
    val isAdded: Boolean = false
) : Parcelable