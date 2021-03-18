package br.com.zupEdu.grpc.ExtensionFunction

import br.com.zupEdu.ChavePixApagarRequest
import br.com.zupEdu.grpc.request.ChavePixDeletarRequestGrpc

fun ChavePixApagarRequest?.toModel(): ChavePixDeletarRequestGrpc? {
    return this?.let { ChavePixDeletarRequestGrpc(it.pixId, this.clientid) }
}