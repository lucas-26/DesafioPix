package br.com.zupEdu.teste

import br.com.zupEdu.PixServiceGrpc
import io.grpc.ManagedChannelBuilder

fun main() {

    val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    var req = br.com.zupEdu.ChavePixApagarRequest.newBuilder()
        .setClientid("ae93a61c-0642-43b3-bb8e-a17072295955")
        .setPixId("40764442058")
        .build()

    val client = PixServiceGrpc.newBlockingStub(channel)
    val response = client.apagarChavePix(req)
    println(response)
}