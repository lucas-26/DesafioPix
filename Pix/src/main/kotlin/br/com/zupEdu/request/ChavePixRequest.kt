package br.com.zupEdu.request

import br.com.zupEdu.model.ChavePix
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Introspected
class ChavePixRequest(@field:NotBlank @field:Email var codigoInterno: String,
                      @field:NotBlank var chavePix: String
){

    fun toModel(codID: String, chave: String): ChavePix {
        return ChavePix(codID, chave)
    }

}
