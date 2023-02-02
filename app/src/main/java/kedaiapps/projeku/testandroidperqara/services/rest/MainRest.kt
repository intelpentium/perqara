package kedaiapps.projeku.testandroidperqara.services.rest

import androidx.annotation.Keep
import kedaiapps.projeku.testandroidperqara.services.Response
import kedaiapps.projeku.testandroidperqara.services.entity.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface MainRest {

    //home
    @GET("games")
    suspend fun home(
        @Query("key") key: String,
    ) : Response<List<ResponseHome>>

    //home
    @GET("games")
    suspend fun paggination(
        @Query("page") page: Int,
        @Query("page_size") page_size: Int,
        @Query("search") search: String,
        @Query("key") key: String,
    ) : Response<List<ResponseHome>>

    //search
    @GET("games")
    suspend fun search(
        @Query("search") search: String,
        @Query("key") key: String,
    ) : Response<List<ResponseHome>>

    //search
    @GET("games/{id}")
    suspend fun homeDetail(
        @Path("id") id: String,
        @Query("key") key: String,
    ) : ResponseHomeDetail
}