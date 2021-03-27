package br.com.zupEdu.grpc

import br.com.zupEdu.PixServiceGrpc

import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("keyManager") val channel: ManagedChannel){

    @Singleton
    fun registraChave() = PixServiceGrpc.newBlockingStub(channel)

    fun deletaChave() = PixServiceGrpc.newBlockingStub(channel)

    fun buscaUmaChavePix() = PixServiceGrpc.newBlockingStub(channel)

    fun buscaTodasAsChavesPix() = PixServiceGrpc.newBlockingStub(channel)
}