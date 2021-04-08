package br.com.zupEdu


import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*
import javax.inject.Inject


@MicronautTest
class GrpcTestes(@Inject val blockingStub: PixServiceGrpc.PixServiceBlockingStub) {

    //repository.deletAll(); limpar o bando antes de rodar o teste

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
    fun `Teste do cadastro de uma chave pix com o tipo de chave pix cpf`() {

        val chavePixCadastro = ChavePixRequest
            .newBuilder()
            .setIdCliente("ae93a61c-0642-43b3-bb8e-a17072295955")
            .setTipoChavePix(TipoChavePix.CPF)
            .setChave("96225554781")
            .setTipoDeConta(TipoConta.CONTA_CORRENTE)
            .build()
        Assertions.assertNotNull(blockingStub.gerarChavePix(chavePixCadastro))
    }

    @Test
    fun `Teste do cadastro de uma chave pix com o tipo de chave pix email`() {

        val chavePixCadastro = ChavePixRequest
            .newBuilder()
            .setIdCliente("ae93a61c-0642-43b3-bb8e-a17072295955")
            .setTipoChavePix(TipoChavePix.EMAIL)
            .setChave("Lucas.rocha@zup.com.br")
            .setTipoDeConta(TipoConta.CONTA_CORRENTE)
            .build()
        Assertions.assertNotNull(blockingStub.gerarChavePix(chavePixCadastro))
    }

    @Test
    fun `Teste do cadastro de uma chave pix com o tipo de chave pix Telefone Celular`() {

        val chavePixCadastro = ChavePixRequest
            .newBuilder()
            .setIdCliente("ae93a61c-0642-43b3-bb8e-a17072295955")
            .setTipoChavePix(TipoChavePix.TELEFONE_CELULAR)
            .setChave("+5585988714077")
            .setTipoDeConta(TipoConta.CONTA_CORRENTE)
            .build()
        Assertions.assertNotNull(blockingStub.gerarChavePix(chavePixCadastro))
    }

    @Test
    fun `Testando deletar a chave pix`() {
        val deletarChavePix = ChavePixApagarRequest
            .newBuilder()
            .setPixId("+5585988714077")
            .setClientid("ae93a61c-0642-43b3-bb8e-a17072295955")
            .build()
        Assertions.assertNotNull(blockingStub.apagarChavePix(deletarChavePix))
    }

    @Test
    fun `Testando a busca de uma chave pix atraves do metodo do grpc`() {

        val build = ListaChavesPixDoClienteRequest
            .newBuilder()
            .setClienteId("ae93a61c-0642-43b3-bb8e-a17072295955")
            .build()
        Assertions.assertNotNull(blockingStub.consultaChavesDeUmCliente(build))
    }

    @Test
    fun `Testando a busca de chave pix passando o id do cliente e pix`(){

        val consulta: br.com.zupEdu.ChavePixConsultaKeyManagerRequest = ChavePixConsultaKeyManagerRequest
            .newBuilder()
            .setClientId("ae93a61c-0642-43b3-bb8e-a17072295955")
            .setPixID("96225554781")
            .build()
        Assertions.assertNotNull(blockingStub.consultaChavePix(consulta))
    }

    @Test
    fun `Testa busca de chave pix em outros sistemas`(){
        val req = ChavePixConsultaParaServicosRequest
            .newBuilder()
            .setPixID("d68338ac-5d68-4b4e-b2fa-8b6f218f9951")
            .build()
        Assertions.assertNotNull(blockingStub.consultaChavePixOutrosSistemas(req))
    }
  }