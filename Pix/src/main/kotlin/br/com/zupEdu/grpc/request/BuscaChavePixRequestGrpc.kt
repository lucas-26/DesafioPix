package br.com.zupEdu.grpc.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class BuscaChavePixRequestGrpc(
                                @field:NotNull
                                @field:NotBlank
                                @field:NotEmpty
                                val clientID:String,
                                @field:NotNull
                                @field:NotBlank
                                @field:NotEmpty
                                val chavePix: String){

}
