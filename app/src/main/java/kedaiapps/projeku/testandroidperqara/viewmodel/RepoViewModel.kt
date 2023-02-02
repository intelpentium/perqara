package kedaiapps.projeku.testandroidperqara.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kedaiapps.projeku.testandroidperqara.db.PerqaraDB
import kedaiapps.projeku.testandroidperqara.db.table.FavoriteTable
import kedaiapps.projeku.testandroidperqara.ext.ioThread
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    val application: Application
): ViewModel(){

    val db = PerqaraDB.getDatabase(this.application)

    //======================= Local Database Favorite ===================
    fun setFavorite(fav_id: String, background_image: String, name: String, released: String, rating: String, status: String){
        ioThread {
            db.daoFavorite().insert(FavoriteTable(0, fav_id, background_image, name, released, rating, status))
        }
    }

    fun getFavorite(): LiveData<List<FavoriteTable>> {
        return db.daoFavorite().getAll("1")
    }

    fun getFavoriteId(fav_id: String) : LiveData<FavoriteTable> {
        return db.daoFavorite().getById(fav_id)
    }

    fun updateFavorite(fav_id: String, status: String){
        ioThread {
            db.daoFavorite().update(fav_id, status)
        }
    }

    fun deleteFavorite(){
        ioThread {
            db.daoFavorite().deleteAll()
        }
    }
}