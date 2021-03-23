package br.com.zupEdu.teste

import br.com.zupEdu.PixServiceGrpc
import io.grpc.ManagedChannelBuilder


fun main() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    var req = br.com.zupEdu.ChavePixConsultaParaServicosRequest.newBuilder()
        .setPixID("85f63561-11e4-4d08-b83c-cb0fe0a2a26f")
        .build()

    val client = PixServiceGrpc.newBlockingStub(channel)
    val response = client.consultaChavePixOutrosSistemas(req)
    println(response)
}