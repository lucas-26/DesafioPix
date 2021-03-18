package br.com.zupEdu.teste

import br.com.zupEdu.PixServiceGrpc
import br.com.zupEdu.TipoChavePix
import br.com.zupEdu.TipoConta
import io.grpc.ManagedChannelBuilder

fun main() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    var req = br.com.zupEdu.ChavePixRequest.newBuilder()
        .setIdCliente("1")
        .setChave("2")
        .setTipoChavePix(TipoChavePix.CHAVE_ALEATORIA)
        .setTipoDeConta(TipoConta.CONTA_POUPANCA)
        .build()

    val client = PixServiceGrpc.newBlockingStub(channel)
    val response = client.gerarChavePix(req)
    println(response)
}