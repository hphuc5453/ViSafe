package hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.presentation.model

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel

class ContactsDetailHeaderItemViewModel(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val avatar: String? = null,
    var isAdded: Boolean = false
) : ViewModel