package app.sample.mobinttesttask.core.utils

object OffsetSaver {

    private var offset = 0
    private var limit = 10

    fun getOffset(): Int = offset
    fun setOffset(newOffset: Int) {
        offset = newOffset
    }

    fun getLimit(): Int = limit
    fun setLimit(newLimit: Int) {
        limit = newLimit
    }
}