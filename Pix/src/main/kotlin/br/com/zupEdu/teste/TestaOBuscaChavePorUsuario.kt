package br.com.zupEdu.teste

import br.com.zupEdu.PixServiceGrpc
import io.grpc.ManagedChannelBuilder

fun main() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    var req = br.com.zupEdu.ListaChavesPixDoClienteRequest.newBuilder()
        .setClienteId("0d1bb194-3c52-4e67-8c35-a93c0af9284f")
        .build()

    val client = PixServiceGrpc.newBlockingStub(channel)
    val response = client.consultaChavesDeUmCliente(req)
    response.forEach {
        println("----------------")
        println(it.clienteId)
        println(it.pixId)
        println(it.tipoDeChave)
        println(it.valorDaChave)
        println(it.tipoDConta)
        println(it.dataHora)
    }
}