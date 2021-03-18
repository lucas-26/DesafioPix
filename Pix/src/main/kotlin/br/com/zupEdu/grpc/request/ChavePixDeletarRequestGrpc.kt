package br.com.zupEdu.grpc.request

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
class ChavePixDeletarRequestGrpc(
    @field:NotBlank
    val pixId: String,
    @field:NotBlank
    val clientid: String){

}
