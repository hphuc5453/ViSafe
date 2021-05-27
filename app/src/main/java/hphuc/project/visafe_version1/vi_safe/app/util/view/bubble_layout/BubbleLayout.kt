package hphuc.project.visafe_version1.vi_safe.app.util.view.bubble_layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.widget.FrameLayout
import hphuc.project.visafe_version1.R

class BubbleLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {

    private var mArrowDirection: ArrowDirection
    private var mBubble: Bubble? = null
    private var arrowWidth: Float
    private var cornersRadius: Float
    private var arrowHeight: Float
    private var arrowPosition: Float
    private var bubbleColor: Int
    var strokeWidth: Float
    var strokeColor: Int

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
        initDrawable(0, width, 0, height)
    }

    override fun dispatchDraw(canvas: Canvas) {
        mBubble?.draw(canvas)
        super.dispatchDraw(canvas)
    }

    private fun initDrawable(left: Int, right: Int, top: Int, bottom: Int) {
        if (right < left || bottom < top) return
        val rectF = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
        when (mArrowDirection) {
            ArrowDirection.LEFT_CENTER, ArrowDirection.RIGHT_CENTER -> arrowPosition = (bottom - top) / 2 - arrowHeight / 2
            ArrowDirection.TOP_CENTER, ArrowDirection.BOTTOM_CENTER -> arrowPosition = (right - left) / 2 - arrowWidth / 2
            else -> {
            }
        }
        mBubble = Bubble(
            rectF, arrowWidth, cornersRadius, arrowHeight, arrowPosition,
            strokeWidth, strokeColor, bubbleColor, mArrowDirection
        )
    }

    private fun initPadding() {
        var paddingLeft = paddingLeft
        var paddingRight = paddingRight
        var paddingTop = paddingTop
        var paddingBottom = paddingBottom
        when (mArrowDirection) {
            ArrowDirection.LEFT, ArrowDirection.LEFT_CENTER -> paddingLeft += arrowWidth.toInt()
            ArrowDirection.RIGHT, ArrowDirection.RIGHT_CENTER -> paddingRight += arrowWidth.toInt()
            ArrowDirection.TOP, ArrowDirection.TOP_CENTER -> paddingTop += arrowHeight.toInt()
            ArrowDirection.BOTTOM, ArrowDirection.BOTTOM_CENTER -> paddingBottom += arrowHeight.toInt()
        }
        if (strokeWidth > 0) {
            paddingLeft += strokeWidth.toInt()
            paddingRight += strokeWidth.toInt()
            paddingTop += strokeWidth.toInt()
            paddingBottom += strokeWidth.toInt()
        }
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    private fun resetPadding() {
        var paddingLeft = paddingLeft
        var paddingRight = paddingRight
        var paddingTop = paddingTop
        var paddingBottom = paddingBottom
        when (mArrowDirection) {
            ArrowDirection.LEFT, ArrowDirection.LEFT_CENTER -> paddingLeft -= arrowWidth.toInt()
            ArrowDirection.RIGHT, ArrowDirection.RIGHT_CENTER -> paddingRight -= arrowWidth.toInt()
            ArrowDirection.TOP, ArrowDirection.TOP_CENTER -> paddingTop -= arrowHeight.toInt()
            ArrowDirection.BOTTOM, ArrowDirection.BOTTOM_CENTER -> paddingBottom -= arrowHeight.toInt()
        }
        if (strokeWidth > 0) {
            paddingLeft -= strokeWidth.toInt()
            paddingRight -= strokeWidth.toInt()
            paddingTop -= strokeWidth.toInt()
            paddingBottom -= strokeWidth.toInt()
        }
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    fun setArrowDirection(arrowDirection: ArrowDirection): BubbleLayout {
        resetPadding()
        mArrowDirection = arrowDirection
        initPadding()
        return this
    }

    fun setArrowWidth(arrowWidth: Float): BubbleLayout {
        resetPadding()
        this.arrowWidth = arrowWidth
        initPadding()
        return this
    }

    fun setCornersRadius(cornersRadius: Float): BubbleLayout {
        this.cornersRadius = cornersRadius
        requestLayout()
        return this
    }

    fun setArrowHeight(arrowHeight: Float): BubbleLayout {
        resetPadding()
        this.arrowHeight = arrowHeight
        initPadding()
        return this
    }

    fun setArrowPosition(arrowPosition: Float): BubbleLayout {
        resetPadding()
        this.arrowPosition = arrowPosition
        initPadding()
        return this
    }

    fun setBubbleColor(bubbleColor: Int): BubbleLayout {
        this.bubbleColor = bubbleColor
        requestLayout()
        return this
    }

    fun setStrokeWidth(strokeWidth: Float): BubbleLayout {
        resetPadding()
        this.strokeWidth = strokeWidth
        initPadding()
        return this
    }

    fun setStrokeColorLayout(strokeColor: Int): BubbleLayout {
        this.strokeColor = strokeColor
        requestLayout()
        return this
    }

    val arrowDirection: ArrowDirection
        get() = mArrowDirection

    companion object {
        var DEFAULT_STROKE_WIDTH = -1f
        fun convertDpToPixel(dp: Float, context: Context): Float {
            val metrics = context.resources.displayMetrics
            return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
        }
    }

    init {
        val a = getContext().obtainStyledAttributes(attrs, R.styleable.BubbleLayout)
        arrowWidth = a.getDimension(
            R.styleable.BubbleLayout_bl_arrowWidth,
            convertDpToPixel(8f, context)
        )
        arrowHeight = a.getDimension(
            R.styleable.BubbleLayout_bl_arrowHeight,
            convertDpToPixel(8f, context)
        )
        cornersRadius = a.getDimension(R.styleable.BubbleLayout_bl_cornersRadius, 0f)
        arrowPosition = a.getDimension(
            R.styleable.BubbleLayout_bl_arrowPosition,
            convertDpToPixel(12f, context)
        )
        bubbleColor =
            a.getColor(R.styleable.BubbleLayout_bl_bubbleColor, Color.WHITE)
        strokeWidth = a.getDimension(
            R.styleable.BubbleLayout_bl_strokeWidth,
            DEFAULT_STROKE_WIDTH
        )
        strokeColor =
            a.getColor(R.styleable.BubbleLayout_bl_strokeColor, Color.GRAY)
        val location =
            a.getInt(R.styleable.BubbleLayout_bl_arrowDirection, ArrowDirection.LEFT.value)
        mArrowDirection = ArrowDirection.fromInt(location)
        a.recycle()
        initPadding()
    }
}