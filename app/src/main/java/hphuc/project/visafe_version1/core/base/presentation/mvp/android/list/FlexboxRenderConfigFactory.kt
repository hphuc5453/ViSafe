package hphuc.project.visafe_version1.core.base.presentation.mvp.android.list

import android.content.Context
import com.google.android.flexbox.FlexboxLayoutManager

class FlexboxRenderConfigFactory(val input: Input) : ListViewMvp.RenderConfigFactory {
    override fun create(): ListViewMvp.RenderConfig {

        val layoutManager = FlexboxLayoutManager(input.context)
        if (input.flexWrap != null) {
            layoutManager.flexWrap = input.flexWrap
        }
        if (input.flexDirection != null) {
            layoutManager.flexDirection = input.flexDirection
        }
        if (input.alignItems != null) {
            layoutManager.alignItems = input.alignItems
        }
        if (input.alignContent != null) {
            layoutManager.alignContent = input.alignContent
        }
        return ListViewMvp.RenderConfig(layoutManager)
    }


    class Input(
        val context: Context,
        val flexWrap: Int? = null,
        val flexDirection: Int? = null,
        val alignItems: Int? = null,
        val alignContent: Int? = null
    )

}