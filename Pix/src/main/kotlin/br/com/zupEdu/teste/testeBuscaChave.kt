package br.com.zupEdu.teste

import br.com.zupEdu.PixServiceGrpc
import io.grpc.ManagedChannelBuilder

fun main() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    var req = br.com.zupEdu.ChavePixConsultaKeyManagerRequest.newBuilder()
        .setClientId("2ac09233-21b2-4276-84fb-d83dbd9f8bab")
        .setPixID("85f63561-11e4-4d08-b83c-cb0fe0a2a26f")
        .build()

    val client = PixServiceGrpc.newBlockingStub(channel)
    val response = client.consultaChavePix(req)
    println(response)
}