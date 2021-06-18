package hphuc.project.visafe_version1.vi_safe.screen.notify.domain

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import hphuc.project.visafe_version1.core.base.domain.mapper.Mapper
import hphuc.project.visafe_version1.vi_safe.screen.notify.presentation.model.NotifyItemViewModel

class NotifyMapper: Mapper<String, MutableList<ViewModel>> {
    override fun map(input: String): MutableList<ViewModel> {
        val list = mutableListOf<ViewModel>()
        list.add(NotifyItemViewModel(
            content = "Mai Tam's vehicle is in need of help"
        ))
        list.add(NotifyItemViewModel(
            content = "Tran Hoa has just added you to the alerted relatives list"
        ))
        return list
    }
}