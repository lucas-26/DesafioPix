package br.com.zupEdu.controller

import br.com.zupEdu.ChavePixConsultaResponse

data class ResponseChavePix(val pixId:String,
                            val ClientId:String){
}
fun ChavePixConsultaResponse.toObjectResponse(): ResponseChavePix {
    return ResponseChavePix(pixId = this.pixId,  ClientId = this.clientId)
}
