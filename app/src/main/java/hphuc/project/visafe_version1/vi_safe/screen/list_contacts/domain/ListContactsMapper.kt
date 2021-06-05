package hphuc.project.visafe_version1.vi_safe.screen.list_contacts.domain

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import hphuc.project.visafe_version1.core.base.domain.mapper.Mapper
import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.presentation.model.ListContactsAlphabetItemViewModel

class ListContactsMapper : Mapper<String, MutableList<ViewModel>> {
    override fun map(input: String): MutableList<ViewModel> {
        val list = mutableListOf<ViewModel>()
        AppConstants.LIST_ALPHABET.forEachIndexed { index, s ->
            list.add(
                ListContactsAlphabetItemViewModel(
                    index = index,
                    alphabet = s
                )
            )
        }
        return list
    }
}