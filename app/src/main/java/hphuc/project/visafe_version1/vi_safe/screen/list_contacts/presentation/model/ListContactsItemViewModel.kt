package hphuc.project.visafe_version1.vi_safe.screen.list_contacts.presentation.model

import android.os.Parcelable
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class ListContactsItemViewModel(
    val id: String,
    val name: String,
    val phone: String,
    val avatar: String? = null,
    var isAdded: Boolean = false
) : Parcelable, ViewModel