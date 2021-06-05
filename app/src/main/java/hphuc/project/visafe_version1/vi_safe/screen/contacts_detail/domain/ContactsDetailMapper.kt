package hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.domain

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import hphuc.project.visafe_version1.core.base.domain.mapper.Mapper
import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.data.ContractsDetailDataIntent
import hphuc.project.visafe_version1.vi_safe.screen.contacts_detail.presentation.model.ContactsDetailHeaderItemViewModel

class ContactsDetailMapper : Mapper<ContractsDetailDataIntent, MutableList<ViewModel>> {
    override fun map(input: ContractsDetailDataIntent): MutableList<ViewModel> {
        val list = mutableListOf<ViewModel>()
        list.add(
            ContactsDetailHeaderItemViewModel(
                id = input.id,
                name = input.name,
                phoneNumber = input.phoneNumber,
                isAdded = input.isAdded,
                avatar = input.avatar
            )
        )
        return list
    }
}