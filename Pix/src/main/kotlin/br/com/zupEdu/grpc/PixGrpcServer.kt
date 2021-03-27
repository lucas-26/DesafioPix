package br.com.zupEdu.grpc

import br.com.zupEdu.*
import br.com.zupEdu.grpc.ExtensionFunction.toModel
import br.com.zupEdu.grpc.exception.ChavePixNaoExisteException
import br.com.zupEdu.grpc.exception.EsseClienteNaoCadastrouEssaChavePixException
import io.grpc.Status

import org.slf4j.LoggerFactory

import br.com.zupEdu.grpc.exception.EsseUsuariojaEstaCadastradoNoSistemaException
import br.com.zupEdu.grpc.request.ChavePixRequestGrpc
import br.com.zupEdu.grpc.ExtensionFunction.*
import io.grpc.stub.StreamObserver
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Validated
class PixGrpcServer(@Inject private val service: ChavePixService) : PixServiceGrpc.PixServiceImplBase() {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun gerarChavePix(request: ChavePixRequest?, responseObserver: StreamObserver<ChavePixResponse>?) {
        try {
            val chavePixRequestGrpc = request?.let {
                ChavePixRequestGrpc(
                    it.idCliente, request.chave,
                    request.tipoChavePix.toString(),
                    request.tipoDeConta.toString())
            }

            val registra = chavePixRequestGrpc?.let { service.registra(it) }

            responseObserver?.onNext(ChavePixResponse.newBuilder()
                .setClientid(request?.idCliente)
                .setPixId(registra?.chave)
                .build())

            responseObserver?.onCompleted()

        } catch (e: EsseUsuariojaEstaCadastradoNoSistemaException){
            responseObserver?.onError(Status.ALREADY_EXISTS
                .withDescription("Esse usuário já tem uma chave pix cadastrada")
                .asRuntimeException())
        }
    }

    override fun apagarChavePix(request: ChavePixApagarRequest?, responseObserver: StreamObserver<ChavePixApagadaResponse>?) {
        val apagarPix = request.toModel()

        try {
            if (apagarPix != null) service.apagar(apagarPix)

        } catch (e: EsseClienteNaoCadastrouEssaChavePixException){
            responseObserver?.onError(Status.CANCELLED
                .withDescription("Usuario que cadastrou a chave Pix não é o mesmo que quer apagar ela")
                .asRuntimeException())
        } catch (e: ChavePixNaoExisteException){
            responseObserver?.onError(Status.NOT_FOUND
                .withDescription("Essa chave Pix não está cadastrada no sistemas")
                .asRuntimeException())
        }

        responseObserver?.onNext(ChavePixApagadaResponse.newBuilder()
            .setMessage("Chave pix: ${apagarPix?.pixId} foi apagada com sucesso")
            .build())
        responseObserver?.onCompleted()
    }

    override fun consultaChavePix(request: ChavePixConsultaKeyManagerRequest?, responseObserver: StreamObserver<ChavePixConsultaResponse>?) {
         val buscaPix = request?.toModel()
            try {
                if (buscaPix == null) {
                    responseObserver?.onError(
                        Status.CANCELLED.
                        withDescription("OS Parametros passado estao vazios")
                            .asRuntimeException())
                }
                val buscarChavePix =  buscaPix?.let { service.buscarChavePix(it) }
                responseObserver?.onNext(ChavePixConsultaResponse.newBuilder().
                setPixId(buscarChavePix?.pixId)
                    .setClientId(buscarChavePix?.ClientId)
                    .setTipoChave(buscarChavePix?.tipoChave.gerarEnum())
                    .setValorChave(buscarChavePix?.valorChave)
                    .setCpf(buscarChavePix?.cpf)
                    .setNomeInstituicao(buscarChavePix?.nomeInstituicao)
                    .setAgencia(buscarChavePix?.agencia)
                    .setNumeroConta(buscarChavePix?.numeroConta)
                    .setTipoConta(buscarChavePix?.tipoConta.gerarEnumConta())
                    .setDataHora(buscarChavePix?.dataHora)
                    .build())
                responseObserver?.onCompleted()

            } catch (e: IllegalArgumentException){
                responseObserver?.onError(
                    Status.NOT_FOUND.
                    withDescription("Essa chave Pix não está cadastrada no sistemas")
                        .asRuntimeException())
            }
    }

    override fun consultaChavePixOutrosSistemas(request: ChavePixConsultaParaServicosRequest?, responseObserver: StreamObserver<ChavePixConsultaResponse>?) {
        try {
            val buscarChavePix = service.buscaChavePixOutrosSistemas(request?.pixID)

            responseObserver?.onNext(ChavePixConsultaResponse.newBuilder()
                .setPixId(buscarChavePix?.pixId)
                .setClientId(buscarChavePix?.ClientId)
                .setTipoChave(buscarChavePix?.tipoChave.gerarEnum())
                .setValorChave(buscarChavePix?.valorChave)
                .setCpf(buscarChavePix?.cpf)
                .setNomeInstituicao(buscarChavePix?.nomeInstituicao)
                .setAgencia(buscarChavePix?.agencia)
                .setNumeroConta(buscarChavePix?.numeroConta)
                .setTipoConta(buscarChavePix?.tipoConta.gerarEnumConta())
                .setDataHora(buscarChavePix?.dataHora)
                .build())
            responseObserver?.onCompleted()
        } catch (e: IllegalArgumentException){
            responseObserver?.onError(
                Status.NOT_FOUND.
                withDescription("Essa chave Pix não está cadastrada no sistemas")
                    .asRuntimeException())
        }
    }

    override fun consultaChavesDeUmCliente(request: ListaChavesPixDoClienteRequest?, responseObserver: StreamObserver<ListaChavesPixDoClienteResponse>?) {
        try {
            val buscaChavesUsuario = service.buscaChavesUsuario(request?.clienteId)
            buscaChavesUsuario.forEach {
                responseObserver?.onNext(ListaChavesPixDoClienteResponse.newBuilder()
                    .setPixId(it.pixId)
                    .setClienteId(it.ClienteId)
                    .setTipoDeChave(it.tipoDeChave)
                    .setValorDaChave(it.valorDaChave)
                    .setTipoDConta(it.tipoDConta)
                    .setDataHora(it.dataHora)
                    .build()) }
            responseObserver?.onCompleted()

        } catch (e: Exception){
            e.printStackTrace()
            responseObserver?.onError(
                Status.CANCELLED.
                withDescription("Essa chave Pix não está cadastrada no sistemas")
                    .asRuntimeException())
        }
    }
}