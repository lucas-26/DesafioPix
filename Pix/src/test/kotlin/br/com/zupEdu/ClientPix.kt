package br.com.zupEdu


import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import javax.inject.Singleton

@Factory
class ClientPix {

    @Singleton
    fun pixBlockingStub(@GrpcChannel(GrpcServerChannel.NAME) managed: ManagedChannel): PixServiceGrpc.PixServiceBlockingStub{
        return PixServiceGrpc.newBlockingStub(managed)
    }
}