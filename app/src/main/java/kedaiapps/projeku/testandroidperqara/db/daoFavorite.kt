package kedaiapps.projeku.testandroidperqara.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kedaiapps.projeku.testandroidperqara.db.table.FavoriteTable

@Dao
interface daoFavorite {
    @Query("SELECT * FROM FavoriteTable WHERE status=:status")
    fun getAll(status: String) : LiveData<List<FavoriteTable>>

    @Query("SELECT * FROM FavoriteTable WHERE fav_id=:fav_id")
    fun getById(fav_id: String): LiveData<FavoriteTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg data: FavoriteTable)

    @Query("UPDATE FavoriteTable SET status=:status WHERE fav_id=:fav_id")
    fun update(fav_id: String, status: String)

    @Query("DELETE FROM FavoriteTable")
    fun deleteAll()
}