package br.com.zupEdu


import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel

@Factory
class ClientPix {

    @Bean
    fun pixBlockingStub(): PixServiceGrpc.PixServiceBlockingStub{
        @GrpcChannel("ChannelTest")
        val managed: ManagedChannel? = null
        return PixServiceGrpc.newBlockingStub(managed)
    }
}