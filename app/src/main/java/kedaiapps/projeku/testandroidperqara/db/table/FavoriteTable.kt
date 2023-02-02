package kedaiapps.projeku.testandroidperqara.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val fav_id: String,
    val background_image: String,
    val name: String,
    val released: String,
    val rating: String,
    val status: String,
)