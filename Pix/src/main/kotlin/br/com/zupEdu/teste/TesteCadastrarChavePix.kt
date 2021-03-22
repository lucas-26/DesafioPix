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
        .setIdCliente("2ac09233-21b2-4276-84fb-d83dbd9f8bab")
        .setChave("83082363083")
        .setTipoChavePix(TipoChavePix.CHAVE_ALEATORIA)
        .setTipoDeConta(TipoConta.CONTA_POUPANCA)
        .build()

    val client = PixServiceGrpc.newBlockingStub(channel)
    val response = client.gerarChavePix(req)
    println(response)
}