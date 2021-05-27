package hphuc.project.visafe_version1.vi_safe.app.custom_view

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView

class CustomTextView : AppCompatTextView {

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context,attributeSet,0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context,attributeSet,defStyleAttr)

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        when (visibility) {
            View.VISIBLE -> {
                // Co nghia la view dang duoc hien thi, nen ko can chay animation cho no nua
                if(alpha == 1.0f){
                    return
                }
                val valueAnimator = ValueAnimator.ofFloat(0.0f,1.0f)
                valueAnimator.duration = 1500
                valueAnimator.addUpdateListener {
                    CustomTextView@this.alpha = it.animatedValue as Float
                }
                valueAnimator.repeatCount = 0
                valueAnimator.start()
            }
            View.INVISIBLE -> {
                // Co nghi la view dang duoc an roi, nen ko can chay animation cho no nua
                if(alpha == 0.0f){
                    return
                }
                val valueAnimator = ValueAnimator.ofFloat(1.0f,0.0f)
                valueAnimator.duration = 1500
                valueAnimator.addUpdateListener {
                    CustomTextView@this.alpha = it.animatedValue as Float
                }
                valueAnimator.repeatCount = 0
                valueAnimator.start()
            }
            View.GONE -> {

            }
        }
    }
}