package kedaiapps.projeku.testandroidperqara.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kedaiapps.projeku.testandroidperqara.db.table.FavoriteTable

@Database(entities = [FavoriteTable::class], version = 1)
abstract class PerqaraDB : RoomDatabase(){

    companion object {
        private var INSTANCE: PerqaraDB? = null

        fun getDatabase(context: Context): PerqaraDB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, PerqaraDB::class.java, "PerqaraDB")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as PerqaraDB
        }
    }

    abstract fun daoFavorite() : daoFavorite
}