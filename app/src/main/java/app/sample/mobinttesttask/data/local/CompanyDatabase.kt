package app.sample.mobinttesttask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import app.sample.mobinttesttask.data.local.dao.CompanyDao
import app.sample.mobinttesttask.data.local.model.CompanyEntity

@Database(
    entities = [CompanyEntity::class],
    version = 1
)
abstract class CompanyDatabase: RoomDatabase() {

    abstract fun companyDao(): CompanyDao
}