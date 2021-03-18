package br.com.zupEdu.service

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client
import java.net.http.HttpResponse

@Client("http://localhost:9091/api/v1/private/contas/todas")
interface CodigoInternoClient {

    @Get(consumes = [MediaType.APPLICATION_JSON])
    fun validaCodigoInterno(@QueryValue codigoInterno: String): List<ContasResponse>
}
