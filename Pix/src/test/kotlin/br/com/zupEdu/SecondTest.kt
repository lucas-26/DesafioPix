package br.com.zupEdu

import br.com.zupEdu.grpc.request.BuscaChavePixRequestGrpc
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*
import javax.inject.Inject

@MicronautTest
class SecondTest(
    @Inject
    val blockingStub: PixServiceGrpc.PixServiceBlockingStub
) {

    @Test
    fun `Testando o cadastro de uma nova chave pix com a chave aleatoria`() {

        val chavePixCadastro = ChavePixRequest
            .newBuilder()
            .setIdCliente("c56dfef4-7901-44fb-84e2-a2cefb157890")
            .setTipoChavePix(TipoChavePix.CHAVE_ALEATORIA)
            .setChave(UUID.randomUUID().toString())
            .setTipoDeConta(TipoConta.CONTA_CORRENTE)
            .build()
        Assertions.assertNotNull(blockingStub.gerarChavePix(chavePixCadastro))
    }

    @Test
    fun `Testando deletar a chave pix`() {

        val deletarChavePix = ChavePixApagarRequest
            .newBuilder()
            .setPixId("f3b92f50-e871-4553-a278-cdd7a7b292c9")
            .setClientid("c56dfef4-7901-44fb-84e2-a2cefb157890")
            .build()
        Assertions.assertNotNull(blockingStub.apagarChavePix(deletarChavePix))
    }

    @Test
    fun `Testando a busca de uma chave pix atraves do metodo do grpc`() {

        val build = ListaChavesPixDoClienteRequest
            .newBuilder()
            .setClienteId("0d1bb194-3c52-4e67-8c35-a93c0af9284f")
            .build()
        Assertions.assertNotNull(blockingStub.consultaChavesDeUmCliente(build))
    }

    @Test
    fun `Testando a busca de chave pix passando o id do cliente e pix`(){

        val consulta: br.com.zupEdu.ChavePixConsultaKeyManagerRequest = ChavePixConsultaKeyManagerRequest
            .newBuilder()
            .setClientId("c56dfef4-7901-44fb-84e2-a2cefb157890")
            .setPixID("132d29e5-a42c-4522-8d94-0ba7ffa0c76a")
            .build()
        Assertions.assertNotNull(blockingStub.consultaChavePix(consulta))
    }

    @Test
    fun `Testa busca de chave pix em outros sistemas`(){
        val req = ChavePixConsultaParaServicosRequest
            .newBuilder()
            .setPixID("132d29e5-a42c-4522-8d94-0ba7ffa0c76a")
            .build()
        Assertions.assertNotNull(blockingStub.consultaChavePixOutrosSistemas(req))
    }

    @Test
    fun `Teste a b`() {

    }
}