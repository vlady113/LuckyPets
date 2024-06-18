package es.intermodular.equipo2.incidenciasies.datos

import es.intermodular.equipo2.incidenciasies.modelo.ComentarioResponse
import es.intermodular.equipo2.incidenciasies.modelo.CrearIncidencia
import es.intermodular.equipo2.incidenciasies.modelo.EquipoResponse
import es.intermodular.equipo2.incidenciasies.modelo.IncidenciaResponse
import es.intermodular.equipo2.incidenciasies.modelo.PerfilReponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //region Perfiles
    @GET("perfiles/login")
    fun login(
        @Query("dominio") dominio: String, @Query("password") password: String
    ): Call<PerfilReponse>
    //endregion

    //region Incidencias

    //Obtenemos incidencias por id del creador (profesor)
    @GET("incidencias/creador/{id}")
    suspend fun getIncidenciasUsuario(
        @Path("id") usuarioId: Int
    ): Response<List<IncidenciaResponse>>


    //Obtener id del tipo de incidencia
    @GET("incidencias-subtipos/id")
    fun obtenerIdPorTipoSubtipoYSubsubtipo(
        @Query("tipo") tipo: String,
        @Query("subtipo") subtipo: String,
        @Query("subsubtipo") subsubtipo: String?
    ): Call<Int>


    //Crear incidencia
    @POST("incidencias")
    fun crearIncidencia(@Body nuevaIncidencia: CrearIncidencia): Call<IncidenciaResponse>

    //Modificar incidencia
    @PUT("incidencias/{num}")
    fun editarIncidencia(
        @Body incidenciaEditada: IncidenciaResponse,
        @Path("num") num: Int
    ): Call<IncidenciaResponse>


    //Eliminar una incidencia
    @DELETE("incidencias/{num}")
    fun borrarIncidencia(@Path("num") num: Int): Call<IncidenciaResponse>
    //endregion


    //region EQUIPOS
    @GET("equipos/{id}")
    fun obtenerEquipoPorId(@Path("id") id: Int): Call<EquipoResponse>


    //endregion

    //region COMENTARIOS

    //Obtener los comentarios de una incidencia
    @GET("comentarios/incidencia/{incidenciaId}")
    suspend fun obtenerComentariosPorIncidencia(@Path("incidenciaId") incidenciaId: Int): Response<List<ComentarioResponse>>

    //Crear un comentario
    @POST("comentarios")
    fun crearComentario(@Body nuevoComentario: ComentarioResponse): Call<ComentarioResponse>


    //endregion
}