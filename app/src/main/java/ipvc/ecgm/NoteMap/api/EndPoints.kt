package ipvc.ecgm.NoteMap.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    //teste ----------- verifica users
    @GET("/myslim/api/users")
    fun getUsers(): Call<List<User>>

    @GET ("/myslim/api/users/{id}")
    fun getUserById(@Path("id") id:Int): Call<User>

    @FormUrlEncoded
    @POST("/myslim/api/login")
    fun login(@Field("username") username: String?, @Field("password") password: String?): Call<OutputLogin>

    //-------------------   Actividade MapsActivity -----------------------------------
    @GET ("/myslim/api/reportes")
    fun getReportes(): Call<List<Reporte>>

    @GET ("/myslim/api/reportes/{id}")
    fun getReporteById(@Path("id") id: String?): Call<Reporte>

    @FormUrlEncoded
    @POST("/smartcity/api/reportes_post")
    fun reportar(@Field("latitude") latitude: String?,
                 @Field("longitude") longitude: String?,
                 @Field("tipo") tipo: String?,
                 @Field("descricao") descricao: String?,
                 @Field("user_id") user_id: Int?): Call<OutputReporte>

}