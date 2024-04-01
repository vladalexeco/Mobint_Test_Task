package app.sample.mobinttesttask.core.utils

object OffsetSaver {

    private var offset = 0

    fun getOffset(): Int = offset
    fun setOffset(newOffset: Int) {
        offset = newOffset
    }
}