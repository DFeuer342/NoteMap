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
    @GET ("/myslim/api/problemas")
    fun getProblemas(): Call<List<Problema>>
}