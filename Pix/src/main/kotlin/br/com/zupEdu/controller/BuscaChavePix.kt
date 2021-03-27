package br.com.zupEdu.controller

import br.com.zupEdu.ChavePixConsultaKeyManagerRequest
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Introspected
data class BuscaChavePix(@field:NotBlank
                         val pixId: String,
                         @field:NotBlank
                         val clientId:String){}

fun BuscaChavePix.toModelConsultaChave(): ChavePixConsultaKeyManagerRequest {
    return ChavePixConsultaKeyManagerRequest.newBuilder().setClientId(this.clientId)
        .setPixID(this.pixId).build()
}