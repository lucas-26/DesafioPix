package br.com.zupEdu.controller

import br.com.zupEdu.ChavePixConsultaKeyManagerRequest
import br.com.zupEdu.ChavePixConsultaResponse
import br.com.zupEdu.ListaChavesPixDoClienteRequest
import br.com.zupEdu.grpc.ExtensionFunction.*
import br.com.zupEdu.PixServiceGrpc
import br.com.zupEdu.grpc.KeyManagerGrpcFactory
import br.com.zupEdu.grpc.request.ChavePixDeletarRequestGrpc
import br.com.zupEdu.grpc.request.ChavePixRequestGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
@Controller("/api/Pix")
class PixController(@Inject val registraChavePixClient: PixServiceGrpc.PixServiceBlockingStub,
                    @Inject val fabrica: KeyManagerGrpcFactory){

    @Post(value = "/cadastraChave")
    fun create(@Valid @Body request: ChavePixRequestGrpc) : HttpResponse<Any> {
        return try {
            fabrica.registraChave().gerarChavePix(request.toPixRequest()?.build())
            HttpResponse.ok()
        } catch (e: Exception){
            HttpResponse.unprocessableEntity()
        }
    }

    @Delete(value = "/deletaChave")
    fun deletaChave(@Valid @Body request: ChavePixDeletarRequestGrpc) : HttpResponse<Any> {
        try {
            fabrica.deletaChave().apagarChavePix(request.toDeletPix().build())
            return HttpResponse.ok()
        } catch (e: Exception){
            return HttpResponse.notFound(e.message)
        }
    }

    @Post(value = "/buscaChavePix")
    fun buscaChavePix(@Valid @Body buscaChavePix:BuscaChavePix): HttpResponse<ResponseChavePix>{
        try {
            val build = buscaChavePix.toModelConsultaChave()
            val consultaChavePix = fabrica.buscaUmaChavePix().consultaChavePix(build)
            return HttpResponse.ok(consultaChavePix.toObjectResponse())
        } catch (e: Exception){
            return HttpResponse.notFound()
        }
    }

    @Post(value = "/buscaChavesDeUmCliente")
    fun buscaTodasAsChavePix(@Valid @Body buscaChavePix:BuscaChavePix): HttpResponse<MutableList<ResponseChavePix>>{
        return try {
            val consultaChavesDeUmCliente = fabrica.buscaTodasAsChavesPix().consultaChavesDeUmCliente(
                ListaChavesPixDoClienteRequest.newBuilder().setClienteId(buscaChavePix.clientId).build())
            val response: MutableList<ResponseChavePix> = mutableListOf()
            consultaChavesDeUmCliente.forEach {
                response.add(ResponseChavePix(ClientId = it.clienteId, pixId = it.pixId))
            }
            HttpResponse.ok(response)
        } catch (e: Exception){
            e.printStackTrace()
            HttpResponse.notFound()
        }
    }
}