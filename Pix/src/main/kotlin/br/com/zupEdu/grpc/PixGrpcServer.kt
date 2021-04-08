package br.com.zupEdu.grpc

import br.com.zupEdu.*
import br.com.zupEdu.grpc.ExtensionFunction.toModel
import io.grpc.Status

import org.slf4j.LoggerFactory

import br.com.zupEdu.grpc.request.ChavePixRequestGrpc
import br.com.zupEdu.grpc.ExtensionFunction.*
import br.com.zupEdu.grpc.annotation.ErrorHandler
import br.com.zupEdu.grpc.exception.*
import br.com.zupEdu.grpc.request.ChavePixDeletarRequestGrpc
import io.grpc.stub.StreamObserver
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton

@ErrorHandler
@Singleton
@Validated
class PixGrpcServer(@Inject private val service: ChavePixService) : PixServiceGrpc.PixServiceImplBase() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun gerarChavePix(request: ChavePixRequest?, responseObserver: StreamObserver<ChavePixResponse>?) {

            val chavePixRequestGrpc = request?.let {
                ChavePixRequestGrpc(
                    it.idCliente,
                    request.chave,
                    request.tipoChavePix.toString(),
                    request.tipoDeConta.toString())
            }

            if (service.chavePixRepository.buscaChavePixPeloIdChave(chavePixRequestGrpc!!.chavePix).isPresent) throw EsseClienteNaoCadastrouEssaChavePixException()

            val registra = chavePixRequestGrpc?.let { service.registra(it) }

            responseObserver?.onNext(ChavePixResponse.newBuilder()
                .setClientid(request?.idCliente)
                .setPixId(registra?.chave)
                .build())

            responseObserver?.onCompleted()
    }

    override fun apagarChavePix(request: ChavePixApagarRequest?, responseObserver: StreamObserver<ChavePixApagadaResponse>?) {
        val apagarPix: ChavePixDeletarRequestGrpc? = request.toModel()

        if (apagarPix?.clientid.isNullOrEmpty()) throw ChavePixNaoEncontradaException()

        if (service.chavePixRepository.buscaSeClienteExiste(apagarPix!!.clientid).isEmpty) throw ClienteNaoEncontradoException()

        if (service.chavePixRepository.buscaChavePixPeloIdChave(apagarPix.pixId).isEmpty) throw EsseClienteNaoCadastrouEssaChavePixException()

        service.apagar(apagarPix)

        responseObserver?.onNext(ChavePixApagadaResponse.newBuilder()
                .setMessage("Chave pix: ${apagarPix?.pixId} foi apagada com sucesso")
                .build())
            responseObserver?.onCompleted()


    }

    override fun consultaChavePix(request: ChavePixConsultaKeyManagerRequest?, responseObserver: StreamObserver<ChavePixConsultaResponse>?) {
         val buscaPix = request?.toModel() ?: throw ParametrosNaoForamPassadosCorretamenteException()

         val buscarChavePix =  buscaPix?.let { service.buscarChavePix(it) }

         if (buscarChavePix.ClientId.isNullOrEmpty()) throw ChavePixNaoEncontradaException()

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
    }

    override fun consultaChavePixOutrosSistemas(request: ChavePixConsultaParaServicosRequest?, responseObserver: StreamObserver<ChavePixConsultaResponse>?) {
            val buscarChavePix = service.buscaChavePixOutrosSistemas(request?.pixID)

            if(buscarChavePix.ClientId.isNullOrEmpty()){
                throw ChavePixNaoEncontradaException()
            }

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
    }

    override fun consultaChavesDeUmCliente(request: ListaChavesPixDoClienteRequest?, responseObserver: StreamObserver<ListaChavesPixDoClienteResponse>?) {
            val buscaChavesUsuario = service.buscaChavesUsuario(request?.clienteId)

            if (buscaChavesUsuario.isNullOrEmpty()){
                throw ChavePixNaoEncontradaException()
            }

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
    }
}