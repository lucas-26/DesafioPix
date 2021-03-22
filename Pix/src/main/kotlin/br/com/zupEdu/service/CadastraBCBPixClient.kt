package br.com.zupEdu.service

import br.com.zupEdu.service.request.CreatePixKeyRequest
import br.com.zupEdu.service.request.DeletePixKeyRequest
import br.com.zupEdu.service.response.DeletePixKeyResponse
import br.com.zupEdu.service.response.PixKeyDetailsResponse
import br.com.zupEdu.service.response.PixKeysListResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client
interface CadastraBCBPixClient {

    @Get("http://localhost:8082/api/v1/pix/keys")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    fun returnTodosAsChavesPix(): HttpResponse<PixKeysListResponse>

    @Get(value = "http://localhost:8082/api/v1/pix/keys/{key}")
    @Consumes(MediaType.APPLICATION_XML)
    fun returnPixbyChave(@PathVariable key: String): HttpResponse<PixKeyDetailsResponse>

    @Post("http://localhost:8082/api/v1/pix/keys")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    fun registerPix(@Body createPixKeyRequest: CreatePixKeyRequest): HttpResponse<PixKeyDetailsResponse>

    @Delete(value = "http://localhost:8082/api/v1/pix/keys/{key}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    fun deletePix(@PathVariable key: String, @Body requestApagarPix: DeletePixKeyRequest): HttpResponse<DeletePixKeyResponse>
}
