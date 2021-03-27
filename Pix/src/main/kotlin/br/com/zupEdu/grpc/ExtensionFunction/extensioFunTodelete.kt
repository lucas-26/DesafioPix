package br.com.zupEdu.grpc.ExtensionFunction

import br.com.zupEdu.ChavePixApagarRequest
import br.com.zupEdu.grpc.request.ChavePixDeletarRequestGrpc

fun ChavePixDeletarRequestGrpc.toDeletPix(): ChavePixApagarRequest.Builder {
    return ChavePixApagarRequest.newBuilder()
        .setPixId(this.pixId)
        .setClientid(this.clientid)}