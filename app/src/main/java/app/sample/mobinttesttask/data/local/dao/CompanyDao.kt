package app.sample.mobinttesttask.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.sample.mobinttesttask.data.local.model.CompanyEntity

@Dao
interface CompanyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(companies: List<CompanyEntity>)

    @Query("SELECT * FROM companyentity")
    fun pagingSource(): PagingSource<Int, CompanyEntity>

    @Query("DELETE FROM companyentity")
    suspend fun clearAll()
}