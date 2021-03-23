package br.com.zupEdu.grpc.ExtensionFunction

import br.com.zupEdu.ChavePixConsultaKeyManagerRequest
import br.com.zupEdu.grpc.request.BuscaChavePixRequestGrpc

fun ChavePixConsultaKeyManagerRequest.toModel(): BuscaChavePixRequestGrpc {
    return  BuscaChavePixRequestGrpc(this.clientId, this.pixID)
}