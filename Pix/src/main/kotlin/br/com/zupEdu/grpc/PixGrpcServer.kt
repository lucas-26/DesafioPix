package br.com.zupEdu.grpc

import io.grpc.Status

import org.slf4j.LoggerFactory

import br.com.zupEdu.ChavePixRequest
import br.com.zupEdu.ChavePixResponse
import br.com.zupEdu.PixServiceGrpc
import br.com.zupEdu.grpc.exception.EsseUsuariojaEstaCadastradoNoSistemaException
import br.com.zupEdu.grpc.request.ChavePixRequestGrpc
import io.grpc.stub.StreamObserver
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Validated
class PixGrpcServer(@Inject private val service: NovaChavePixService) : PixServiceGrpc.PixServiceImplBase() {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun gerarChavePix(request: ChavePixRequest?, responseObserver: StreamObserver<ChavePixResponse>?) {
        logger.info("Iniciando a criação do pix")
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
}


