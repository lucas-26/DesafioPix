package br.com.zupEdu.teste

import br.com.zupEdu.PixServiceGrpc
import io.grpc.ManagedChannelBuilder

fun main() {

    val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    var req = br.com.zupEdu.ChavePixApagarRequest.newBuilder()
        .setClientid("2ac09233-21b2-4276-84fb-d83dbd9f8bab")
        .setPixId("933e8a20-1378-4eb6-830c-673b3d4dd894")
        .build()

    val client = PixServiceGrpc.newBlockingStub(channel)
    val response = client.apagarChavePix(req)
    println(response)
}