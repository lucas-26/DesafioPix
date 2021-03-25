package br.com.zupEdu.controller



import br.com.zupEdu.ChavePixRequest
import br.com.zupEdu.PixServiceGrpc
import br.com.zupEdu.TipoChavePix
import br.com.zupEdu.TipoConta
import br.com.zupEdu.grpc.request.ChavePixRequestGrpc
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/api/Pix")
class PixController(private val registraChavePixClient: PixServiceGrpc.PixServiceBlockingStub){

    @Post(value = "/cadastraChave")
    fun create(@Valid @Body request: ChavePixRequestGrpc){
        registraChavePixClient.gerarChavePix(request.toPixRequest()?.build())
    }
}