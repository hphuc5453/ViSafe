package hphuc.project.visafe_version1.vi_safe.screen.list_contacts.presentation.renderer

import android.content.Context
import android.view.View
import hphuc.project.visafe_version1.R
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.model.ViewRenderer
import hphuc.project.visafe_version1.vi_safe.screen.list_contacts.presentation.model.ListContactsAlphabetItemViewModel
import kotlinx.android.synthetic.main.item_layout_list_contacts_alphabet_vholder.view.*

class ListContactsAlphabetItemViewRenderer(context: Context): ViewRenderer<ListContactsAlphabetItemViewModel>(context) {
    override fun getLayoutId(): Int {
        return R.layout.item_layout_list_contacts_alphabet_vholder
    }

    override fun getModelClass(): Class<ListContactsAlphabetItemViewModel> = ListContactsAlphabetItemViewModel::class.java

    override fun bindView(model: ListContactsAlphabetItemViewModel, viewRoot: View) {
        viewRoot.tvAlphabet.text = model.alphabet
    }
}