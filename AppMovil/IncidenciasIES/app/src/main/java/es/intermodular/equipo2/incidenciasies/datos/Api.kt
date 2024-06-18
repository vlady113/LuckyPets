package es.intermodular.equipo2.incidenciasies.datos

object Api {

    private val retrofit = RetrofitBuilder.build()
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }

}