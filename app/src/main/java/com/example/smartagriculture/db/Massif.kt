package com.example.smartagriculture.db

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
class Massif(content: String?) {

    @PrimaryKey(autoGenerate = true)
    var id :Int = 0

    @ColumnInfo(name = "massif_name")
    var content: String? = content

}

@Dao
interface MassifDao {
    @Insert
    fun insertMassif(vararg massif: Massif?)

    @Update
    fun update(vararg massif: Massif?)

    @Delete
    fun delete(vararg massif: Massif?)

    @Query("DELETE FROM MASSIF")
    fun deleteAllMassif()

    @Query("SELECT * FROM MASSIF ORDER BY ID DESC")
    fun getMassifLive(): LiveData<MutableList<Massif>>?
}

@Database(entities = [Massif::class], version = 1, exportSchema = false)
abstract class MassifDatabase : RoomDatabase() {
    companion object {
        private var massifDatabase: MassifDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): MassifDatabase {
            if (massifDatabase == null) {
                massifDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    MassifDatabase::class.java,
                    "massif_database"
                )
                    //.fallbackToDestructiveMigration()//清空数据库 破坏式的升级数据库
//                .addMigrations(MIGRATION) //非破坏式
                    .build()
            }
            return massifDatabase as MassifDatabase
        }
    }


    abstract fun getMassifDao(): MassifDao
}


class MassifRepository {
    private var listLiveData: LiveData<MutableList<Massif>>? = null
    private var massifDao: MassifDao? = null

    constructor(context: Context) {
        var massifDatabase = MassifDatabase.getDatabase(context)
        massifDao = massifDatabase.getMassifDao()
        listLiveData = massifDao!!.getMassifLive()
    }

    fun getListLiveData(): LiveData<MutableList<Massif>>? {
        return listLiveData
    }

    fun insert(vararg massif: Massif?) {
        massifDao?.let { insertAsyncTask(it).execute(*massif) }
    }

    fun update(vararg massif: Massif?) {
        massifDao?.let { updateAsyncTask(it).execute(*massif) }
    }

    fun delete(vararg massif: Massif?) {
        massifDao?.let { deleteAsyncTask(it).execute(*massif) }
    }

    fun clear() {
        massifDao?.let { clearAsyncTask(it).execute() }
    }


    internal class insertAsyncTask(massifDao: MassifDao) :
        AsyncTask<Massif?, Void?, Void?>() {
        private var massifDao: MassifDao = massifDao

        override fun doInBackground(vararg massif: Massif?): Void? {
            massifDao.insertMassif(*massif)
            return null
        }

    }

    internal class deleteAsyncTask(massifDao: MassifDao) :
        AsyncTask<Massif?, Void?, Void?>() {
        private val massifDao: MassifDao=massifDao
        override fun doInBackground(vararg massif: Massif?): Void? {
            massifDao.delete(*massif)
            return null
        }
    }

    internal class updateAsyncTask(massifDao: MassifDao) :
        AsyncTask<Massif?, Void?, Void?>() {
        private val massifDao: MassifDao=massifDao
        override fun doInBackground(vararg massif: Massif?): Void? {
            massifDao.update(*massif)
            return null
        }

    }

    internal class clearAsyncTask(massifDao: MassifDao) :
        AsyncTask<Void?, Void?, Void?>() {
        private val massifDao: MassifDao=massifDao

        override fun doInBackground(vararg params: Void?): Void? {
            massifDao.deleteAllMassif()
            return null
        }
    }
}