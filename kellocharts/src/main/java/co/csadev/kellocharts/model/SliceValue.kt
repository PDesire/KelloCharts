package co.csadev.kellocharts.model

import co.csadev.kellocharts.util.ChartUtils
import co.csadev.kellocharts.view.Chart
import java.util.*

/**
 * Model representing single slice on PieChart.
 */
class SliceValue(value: Float = 0f, private var originValue: Float = value, private var diff: Float = 0f, color: Int = ChartUtils.DEFAULT_COLOR, var label: CharArray? = null) {
    var darkenColor = ChartUtils.darkenColor(color)
        private set

    fun update(scale: Float) {
        value = originValue + diff * scale
    }

    fun finish() {
        value = originValue + diff
    }

    var value: Float = value
        set(value) {
            field = value
            this.originValue = value
            this.diff = 0f
        }

    var color: Int = color
        set(value) {
            field = value
            this.darkenColor = ChartUtils.darkenColor(field)
        }

    /**
     * Set target value that should be reached when data animation finish then call [Chart.startDataAnimation]
     *
     * @param target
     * @return
     */
    fun setTarget(target: Float): SliceValue {
        value = value
        this.diff = target - originValue
        return this
    }

    override fun toString(): String {
        return "SliceValue [value=$value]"
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val that = o as SliceValue?

        if (color != that!!.color) return false
        if (darkenColor != that.darkenColor) return false
        if (that.diff.compareTo(diff) != 0) return false
        if (that.originValue.compareTo(originValue) != 0) return false
        if (that.value.compareTo(value) != 0) return false
        return Arrays.equals(label, that.label)

    }

    override fun hashCode(): Int {
        var result = if (value != +0.0f) java.lang.Float.floatToIntBits(value) else 0
        result = 31 * result + if (originValue != +0.0f) java.lang.Float.floatToIntBits(originValue) else 0
        result = 31 * result + if (diff != +0.0f) java.lang.Float.floatToIntBits(diff) else 0
        result = 31 * result + color
        result = 31 * result + darkenColor
        result = 31 * result + if (label != null) Arrays.hashCode(label) else 0
        return result
    }

    fun copy() = SliceValue(this.value, color = this.color, label = this.label)
}
