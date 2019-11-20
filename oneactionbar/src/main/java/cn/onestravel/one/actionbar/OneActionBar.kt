package cn.onestravel.one.actionbar

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.*
import androidx.core.graphics.drawable.DrawableCompat
import com.example.actionbar.R
import java.lang.Exception


/**
 * Created by onestravel on 2019-08-05
 */
public class OneActionBar @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr) {
    private var mActionBtnIconHeight: Int = 1
    private var mActionBtnIconWidth: Int = 1
    private lateinit var backClickListener: () -> Unit
    private lateinit var actionBtnClickListener: () -> Unit
    private lateinit var actionSearchListener: (keyword: String?) -> Unit
    private var mBackIconHeight: Float = 0.0f
    private var mBackIconWidth: Float = 0.0f
    private var mTitle: String? = null
    private val mBackLayout: LinearLayout by lazy { LinearLayout(context) }
    private val mBackLayoutParams: LayoutParams by lazy { LayoutParams(WRAP_CONTENT, MATCH_PARENT) }
    private val mSearchView: EditText by lazy { EditText(context) }
    private val mSearchViewParams: LayoutParams by lazy { LayoutParams(0, MATCH_PARENT, 2f) }
    private val mTitleView: TextView by lazy { TextView(context) }
    private val mTitleLayoutParams: LayoutParams by lazy { LayoutParams(0, MATCH_PARENT, 2f) }
    private val mActionBtnLayout: LinearLayout by lazy { LinearLayout(context) }
    private val mActionBtnLayoutParams: LayoutParams by lazy {
        LayoutParams(
            WRAP_CONTENT,
            MATCH_PARENT
        )
    }
    private val mBackIconView: ImageView by lazy { ImageView(context) }
    private val mBackIconViewParams: LayoutParams by lazy {
        LayoutParams(
            WRAP_CONTENT,
            WRAP_CONTENT
        )
    }
    private val mBackTextView: TextView by lazy { TextView(context) }
    private val mActionBtnIconView: ImageView by lazy { ImageView(context) }
    private val mActionBtnIconViewParams: LayoutParams by lazy {
        LayoutParams(
            WRAP_CONTENT,
            WRAP_CONTENT
        )
    }
    private val mActionBtnTextView: TextView by lazy { TextView(context) }
    private val mSpaceView: View by lazy { View(context) }
    private val mBackIconDrawable: Drawable by lazy {
        getDrawable(context!!, R.mipmap.back)!!
    }
    private val linePaint: Paint by lazy {
        Paint()
    }

    private var mActionBtnIconDrawable: Drawable? = null
    private var mActionBtnTextSize: Float = 0.0f
    private var mActionBtnColor: Int = 0
    private var mActionBtnText: String? = null
    private var mBottomLineColor: Int = 0
    private var mBottomLineWidth: Float = 0.0f
    private var mBackTextSize: Float = 0.0f
    private var mBackText: String? = null
    private var mBackColor: Int = Color.BLACK
    private var mType: String? = null
    private var mTitleSize: Float = 0.0f
    private var mTitleColor: Int = 0
    private var mSearchBackground: Drawable? = null
    private var mSearchHint: String? = null
    private var mSearchText: String? = null
    private var mSearchHintColor: Int = Color.DKGRAY
    private var mSearchTextColor: Int = Color.BLACK
    private var mSearchTextSize: Float = sp2px(16f).toFloat()


    private var onBackClickListener: OnBackClickListener? = null

    private var onActionBtnClickListener: OnActionBtnClickListener? = null

    private var onActionSearchListener: OnActionSearchListener? = null

    /**
     * 右侧点击区域按钮图标
     */
    var barActionBtnIconDrawable: Drawable? = null
        set(value) {
            this.mActionBtnIconDrawable = value
            resetLayout()
        }
    /**
     * ActionBar 底部线条颜色
     */
    var barBottomLineColor: Int = 0
        set(value) {
            this.mBottomLineColor = value
            postInvalidate()
        }
    /**
     * ActionBar 底部线条宽度
     */
    var barBottomLineWidth: Float = 0.0f
        set(value) {
            this.mBottomLineWidth = value
            postInvalidate()
        }
    /**
     * 右侧点击区域按钮文字大小
     */
    var barActionBtnTextSize: Float = 0.0f
        set(value) {
            this.mActionBtnTextSize = value
            mActionBtnTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActionBtnTextSize)
        }
    /**
     * 右侧点击区域按钮文字颜色
     */
    var barActionBtnColor: Int = 0
        set(value) {
            this.mActionBtnColor = value
            mActionBtnTextView.setTextColor(mActionBtnColor)
        }
    /**
     * 右侧点击区域按钮文字
     */
    var barActionBtnText: String? = null
        set(value) {
            this.mActionBtnText = value
            resetLayout()
        }
    /**
     * ActionBar 类型
     * Type.TYPE_BACK
     * Type.TYPE_BACK_SEARCH
     * Type.TYPE_NORMAL
     * Type.TYPE_SEARCH
     */
    var barType: String? = null
        set(value) {
            this.mType = value
            resetLayout()
        }

    /**
     * 标题文字
     */
    var barTitle: String? = null
        set(value) {
            this.mTitle = value
            mTitleView.setText(mTitle ?: "")
        }
    /**
     * 标题文字
     */
    @StringRes
    var barTitleRes: Int = 0
        set(value) {
            this.mTitle = context.getString(value)
            mTitleView.setText(value)
        }
    /**
     * 标题文字大小
     */
    var barTitleSize: Float = 0.0f
        set(value) {
            this.mTitleSize = value
            mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize)
        }
    /**
     * 标题文字颜色
     */
    var barTitleColor: Int = 0
        set(value) {
            this.mTitleColor = value
            mTitleView.setTextColor(mTitleColor)
        }
    /**
     * 返回键文字大小
     */
    var barBackTextSize: Float = 0.0f
        set(value) {
            this.mBackTextSize = value
            mBackTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBackTextSize)
        }
    /**
     * 返回键文字
     */
    var barBackText: String? = null
        set(value) {
            this.mBackText = value
            resetLayout()
        }
    /**
     * 返回键按钮，文字颜色
     */
    var barBackColor: Int = Color.BLACK
        set(value) {
            mBackColor = value
            changeBackColor()
            mBackTextView.setTextColor(mBackColor)
        }

    /**
     * 搜索框背景
     */
    var barSearchBackground: Drawable? = null
        set(value) {
            mSearchBackground = value
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mSearchView.background = mSearchBackground
            } else {
                mSearchView.setBackgroundDrawable(mSearchBackground)
            }
        }
    /**
     * 搜索框提示文字
     */
    var barSearchHint: String? = null
        set(value) {
            mSearchHint = value
            mSearchView.hint = mSearchHint
        }
    /**
     * 搜索框文字
     */
    var barSearchText: String? = null
        set(value) {
            mSearchText = value
            mSearchView.setText(mSearchText)
        }
    /**
     * 搜索框提示文字颜色
     */
    var barSearchHintColor: Int = Color.DKGRAY
        set(value) {
            mSearchHintColor = value
            mSearchView.setHintTextColor(mSearchHintColor)
        }
    /**
     * 搜索框文字颜色
     */
    var barSearchTextColor: Int = Color.BLACK
        set(value) {
            mSearchTextColor = value
            mSearchView.setTextColor(mSearchTextColor)
        }
    /**
     * 搜索框文字大小
     */
    var barSearchTextSize: Float = sp2px(16f).toFloat()
        set(value) {
            mSearchTextSize = value
            mSearchView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBackTextSize)
        }

    /**
     * 搜索框背景
     * @param drawableId 资源文件id
     */
    fun barSetSearchBackgroundResources(@DrawableRes drawableId: Int) {
        val drawable = getDrawable(context, drawableId)
        drawable?.let {
            barSearchBackground = drawable
        }
    }


    /**
     * 设置ActionBtn的图标大小
     * @param width 图标宽度
     * @param height 图标高度
     */
    fun setActionBtnIconSize(width: Int, height: Int) {
        this.mActionBtnIconWidth = width
        this.mActionBtnIconHeight = height
        mActionBtnIconViewParams.width = width
        mActionBtnIconViewParams.height = height
    }

    /**
     * 返回搜索输入框View
     */
    fun getSearchView(): EditText {
        return mSearchView
    }

    /**
     * 返回键按钮，文字颜色
     */
    fun setBackColorResource(@ColorRes colorRes: Int) {
        barBackColor = getColor(context, colorRes)
    }

    /**
     * 返回键点击回调事件
     */
    @JvmOverloads
    fun setOnBackClickListener(listener: OnBackClickListener) {
        this.onBackClickListener = listener
    }

    /**
     * 返回键点击回调事件
     */
    fun setOnBackClickListener(listener: () -> Unit) {
        backClickListener = listener
    }


    /**
     * 右侧确认键点击回调事件
     */
    @JvmOverloads
    fun setOnActionBtnClickListener(listener: OnActionBtnClickListener) {
        this.onActionBtnClickListener = listener
    }

    /**
     * 右侧确认键点击回调事件
     */
    fun setOnActionBtnClickListener(listener: () -> Unit) {
        actionBtnClickListener = listener
    }

    init {
        initAttr(context, attrs)
        initLayout()
    }

    /**
     * 初始化获取属性值
     */
    private fun initAttr(context: Context?, attrs: AttributeSet?) {
        var arr: TypedArray = context!!.obtainStyledAttributes(attrs, R.styleable.OneActionBar)
        mBackColor = arr.getColor(R.styleable.OneActionBar_barBackColor, Color.BLACK)
        mBackIconWidth =
            arr.getDimension(R.styleable.OneActionBar_barBackIconWidth, dp2px(15f).toFloat())
        mBackIconHeight =
            arr.getDimension(R.styleable.OneActionBar_barBackIconHeight, dp2px(15f).toFloat())
        mBackText = arr.getString(R.styleable.OneActionBar_barBackText)
        mBackTextSize =
            arr.getDimension(R.styleable.OneActionBar_barBackTextSize, sp2px(14f).toFloat())
        mTitle = arr.getString(R.styleable.OneActionBar_barTitle)
        mTitleColor = arr.getColor(R.styleable.OneActionBar_barTitleColor, Color.BLACK)
        mTitleSize = arr.getDimension(R.styleable.OneActionBar_barTitleSize, sp2px(20f).toFloat())
        mType = arr.getString(R.styleable.OneActionBar_barType)
        if (TextUtils.isEmpty(mType)) {
            mType = Type.TYPE_BACK
        }
        mActionBtnColor = arr.getColor(R.styleable.OneActionBar_barActionBtnColor, Color.BLUE)
        mActionBtnText = arr.getString(R.styleable.OneActionBar_barActionBtnText)
        mActionBtnTextSize =
            arr.getDimension(R.styleable.OneActionBar_barActionBtnTextSize, sp2px(12f).toFloat())
        mBottomLineWidth = arr.getDimension(R.styleable.OneActionBar_barBottomLineWidth, 0f)
        mBottomLineColor = arr.getColor(R.styleable.OneActionBar_barBottomLineColor, Color.DKGRAY)
        mActionBtnIconDrawable = arr.getDrawable(R.styleable.OneActionBar_barActionBtnIcon)
        mActionBtnIconWidth =
            arr.getDimension(R.styleable.OneActionBar_barActionBtnIconWidth, 0f).toInt()
        mActionBtnIconHeight =
            arr.getDimension(R.styleable.OneActionBar_barActionBtnIconHeight, 0f).toInt()
        mSearchBackground = arr.getDrawable(R.styleable.OneActionBar_barSearchBackground)
        if (mSearchBackground == null) {
            mSearchBackground = getDrawable(context, R.drawable.bg_search_input)
        }
        mSearchHint = arr.getString(R.styleable.OneActionBar_barSearchHint)
        mSearchText = arr.getString(R.styleable.OneActionBar_barSearchText)
        mSearchHintColor =
            arr.getColor(R.styleable.OneActionBar_barSearchHintColor, Color.parseColor("#e1e1e1"))
        mSearchTextColor = arr.getColor(R.styleable.OneActionBar_barSearchTextColor, Color.BLACK)
        mSearchTextSize =
            arr.getDimension(R.styleable.OneActionBar_barSearchTextSize, sp2px(16f).toFloat())
    }

    /**
     * 初始化布局
     */
    private fun initLayout() {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        try {
            if (context is Activity) {
                (context as Activity).actionBar?.hide()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mBackLayout.layoutParams = mBackLayoutParams
        mBackLayout.setBackgroundColor(Color.TRANSPARENT)
        mBackLayout.gravity = Gravity.CENTER_VERTICAL
        mBackLayout.setPadding(dp2px(8f), 0, dp2px(8f), 0)
        changeBackColor()
        mBackIconViewParams.width = mBackIconWidth.toInt()
        mBackIconViewParams.height = mBackIconHeight.toInt()
        mBackIconView.layoutParams = mBackIconViewParams
        mBackIconView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        mBackLayout.addView(mBackIconView)
        mTitleView.layoutParams = mTitleLayoutParams
        mTitleView.setBackgroundColor(Color.TRANSPARENT)
        mTitleView.gravity = Gravity.CENTER
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize)
        mTitleView.setTextColor(mTitleColor)
        mTitleView.setLines(1)
        mTitleView.setSingleLine()
        mTitleView.ellipsize = TextUtils.TruncateAt.END
        mActionBtnLayout.layoutParams = mActionBtnLayoutParams
        mActionBtnLayout.setBackgroundColor(Color.TRANSPARENT)
        mActionBtnLayout.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
        mActionBtnLayout.setPadding(dp2px(8f), 0, dp2px(8f), 0)
        addView(mBackLayout)
        addView(mSpaceView)
        addView(mTitleView)
        addView(mActionBtnLayout)

        resetLayout()

    }

    /**
     * 改变返回图标颜色
     */
    private fun changeBackColor() {
        val mBackIconDrawable = changeColor(mBackIconDrawable, mBackColor)
        mBackIconView.setImageDrawable(mBackIconDrawable)
    }

    /**
     * 重置View
     */
    private fun resetLayout() {

        when (mType) {
            Type.TYPE_NORMAL -> {
                mBackLayout.visibility = View.INVISIBLE
            }
            Type.TYPE_SEARCH -> {
                mBackLayout.visibility = View.INVISIBLE
                mBackLayoutParams.width = dp2px(10f)
                titleToSearch()
            }
            Type.TYPE_BACK_SEARCH -> {
                titleToSearch()
                mBackLayout.setOnClickListener {
                    if (::backClickListener.isInitialized) {
                        backClickListener.invoke()
                    } else {
                        onBackClickListener?.let {
                            it.onBackClick()
                        }
                    }
                }
            }
            else -> {
                mBackLayout.setOnClickListener {
                    if (::backClickListener.isInitialized) {
                        backClickListener.invoke()
                    } else {
                        onBackClickListener?.let {
                            it.onBackClick()
                        }
                    }
                }
            }
        }
        mBackText?.let {
            mBackTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBackTextSize)
            mBackTextView.setTextColor(mBackColor)
            mBackTextView.text = it
            mBackLayout.removeView(mBackTextView)
            mBackLayout.addView(mBackTextView)
        }

        mActionBtnIconDrawable?.let {
            if (mActionBtnIconHeight > 5 && mActionBtnIconWidth > 5) {
                mActionBtnIconView.layoutParams = mActionBtnIconViewParams
                mActionBtnIconViewParams.width = mActionBtnIconWidth
                mActionBtnIconViewParams.height = mActionBtnIconHeight
                mActionBtnIconViewParams.leftMargin = dp2px(5f)
                mActionBtnIconViewParams.rightMargin = dp2px(5f)
                mActionBtnIconView.setImageDrawable(it)
                mActionBtnLayout.removeView(mActionBtnIconView)
                mActionBtnLayout.addView(mActionBtnIconView)
            }
        }
        mActionBtnText?.let {
            mActionBtnTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActionBtnTextSize)
            mActionBtnTextView.setTextColor(mActionBtnColor)
            mActionBtnTextView.text = it
            mActionBtnTextView.setPadding(0, 0, 0, 0)
            mActionBtnLayout.removeView(mActionBtnTextView)
            mActionBtnLayout.addView(mActionBtnTextView)
        }
        if (mActionBtnLayout.childCount > 0) {
            mActionBtnLayout.setOnClickListener {
                if (::actionBtnClickListener.isInitialized) {
                    actionBtnClickListener.invoke()
                } else {
                    onActionBtnClickListener?.let {
                        it.onActionBtnClick()
                    }
                }
            }
        }
        mTitle?.let {
            mTitleView.text = it
        }
    }

    /**
     * 标题替换成搜索输入框
     */
    private fun titleToSearch() {
        mSearchView.setSingleLine()
        mSearchView.setLines(1)
        mSearchView.imeOptions = EditorInfo.IME_ACTION_SEARCH
        mSearchView.layoutParams = mSearchViewParams
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mSearchView.background = mSearchBackground
        } else {
            mSearchView.setBackgroundDrawable(mSearchBackground)
        }
        val top = dp2px(8f)
        val leftPadding = dp2px(15f)
        val topPadding = dp2px(5f)
        mSearchView.hint = mSearchHint
        mSearchView.setText(mSearchText)
        mSearchView.setHintTextColor(mSearchHintColor)
        mSearchView.setTextColor(mSearchTextColor)
        mSearchView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSearchTextSize)
        mSearchViewParams.setMargins(0, top, 0, top)
        mSearchView.setPadding(leftPadding, topPadding, leftPadding, topPadding)
        removeView(mTitleView)
        addView(mSearchView, 2)
        mSearchView.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(tv: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideInput(mSearchView)
                    if (::actionSearchListener.isInitialized) {
                        actionSearchListener.invoke(tv!!.text.trim().toString())
                    } else {
                        onActionSearchListener?.let {
                            it.onActionSearch(tv!!.text.trim().toString())
                        }
                    }
                    return true
                }
                return false
            }
        })
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mBottomLineWidth > 0) {
            linePaint.color = mBottomLineColor
            linePaint.style = Paint.Style.FILL
            linePaint.strokeWidth = mBottomLineWidth
            linePaint.flags = ANTI_ALIAS_FLAG
            canvas?.drawLine(
                0f,
                height - mBottomLineWidth / 2,
                width.toFloat(),
                height - mBottomLineWidth / 2,
                linePaint
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        for (i in 0 until childCount) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        resetChildLayoutWidth()
    }


    /**
     * 重置子View宽度
     */
    private fun resetChildLayoutWidth() {
        if (mType == Type.TYPE_SEARCH && mActionBtnLayout.childCount == 0) {
            mActionBtnLayoutParams.width = mBackLayout.measuredWidth
        }
        val spaceLayoutParams = LayoutParams(0, WRAP_CONTENT)
        mSpaceView.layoutParams = spaceLayoutParams
        if (mType == Type.TYPE_SEARCH || mType == Type.TYPE_BACK_SEARCH) {
            return
        }
        //获取左右可点击区域的最小宽度
        val minWidth = if (mType == Type.TYPE_SEARCH) {
            dp2px(10f)
        } else {
            dp2px(40f)
        }
        var width = if (mActionBtnLayout.measuredWidth > mBackLayout.measuredWidth) {
            mActionBtnLayout.measuredWidth
        } else {
            mBackLayout.measuredWidth
        }
        width = if (width < minWidth) {
            minWidth
        } else {
            width
        }
        //左右可点击区域的最大宽度
        val maxWidth = measuredWidth * 1 / 4
        width = if (width > maxWidth) {
            maxWidth
        } else {
            width
        }
        var backLayoutWidth = if (mBackLayout.measuredWidth > minWidth) {
            mBackLayout.measuredWidth
        } else {
            minWidth
        }
        //计算空白view的宽度
        val spaceWidth = width - backLayoutWidth
        spaceLayoutParams.width = spaceWidth
        mActionBtnLayoutParams.width = width
        mBackLayoutParams.width = backLayoutWidth
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private fun dp2px(dpValue: Float): Int {
        val scale = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     */
    private fun sp2px(spValue: Float): Int {
        val fontScale = resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * 对图片资源文件进行改色
     */
    private fun changeColor(drawable: Drawable, color: Int): Drawable? {
        val drawable = drawable.mutate()
        drawable.let {
            val wrappedDrawable: Drawable = DrawableCompat.wrap(drawable)
            val colors: ColorStateList = ColorStateList.valueOf(color)
            DrawableCompat.setTintList(wrappedDrawable, colors)
            return wrappedDrawable
        }
        return drawable
    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    private fun showInput(et: EditText) {
        et.requestFocus()
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * 隐藏键盘
     */
    private fun hideInput(v: EditText) {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        if (null != v) {
            imm!!.hideSoftInputFromWindow(v!!.windowToken, 0)
        }
    }


    class Type {
        companion object {
            val TYPE_NORMAL = "1"
            val TYPE_BACK = "2"
            val TYPE_SEARCH = "3"
            val TYPE_BACK_SEARCH = "4"
        }
    }

    interface OnBackClickListener {
        fun onBackClick()
    }

    interface OnActionBtnClickListener {
        fun onActionBtnClick()
    }

    interface OnActionSearchListener {
        fun onActionSearch(keyword: String?)
    }
}
