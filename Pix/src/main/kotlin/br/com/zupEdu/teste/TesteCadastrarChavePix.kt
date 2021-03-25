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
        .setIdCliente("0d1bb194-3c52-4e67-8c35-a93c0af9284f")
        .setChave("83082363083")
        .setTipoChavePix(TipoChavePix.CHAVE_ALEATORIA)
        .setTipoDeConta(TipoConta.CONTA_POUPANCA)
        .build()

    val client = PixServiceGrpc.newBlockingStub(channel)
    val response = client.gerarChavePix(req)
    println(response)
}