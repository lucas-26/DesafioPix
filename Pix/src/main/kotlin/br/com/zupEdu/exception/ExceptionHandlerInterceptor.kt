package br.com.zupEdu.exception

import br.com.zupEdu.grpc.PixGrpcServer
import br.com.zupEdu.grpc.annotation.ErrorHandler
import br.com.zupEdu.grpc.exception.*
import io.grpc.BindableService
import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.micronaut.aop.InterceptorBean
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
@InterceptorBean(ErrorHandler::class)
class ExceptionHandlerInterceptor : MethodInterceptor<PixGrpcServer, Any?>{
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun intercept(context: MethodInvocationContext<PixGrpcServer, Any?>?): Any? {
        return try {
            context?.proceed()

        } catch (e: Exception){
            logger.error("${e.message}")

            val status =
                when(e) {
                    is EsseUsuariojaEstaCadastradoNoSistemaException -> Status.ALREADY_EXISTS.withDescription("Esse usuário já tem uma chave pix cadastrada").asRuntimeException()
                    is EsseClienteNaoCadastrouEssaChavePixException -> Status.CANCELLED.withDescription("Usuario que cadastrou a chave Pix não é o mesmo que quer apagar ela").asRuntimeException()
                    is ChavePixNaoExisteException -> Status.NOT_FOUND.withDescription("Essa chave Pix não está cadastrada no sistemas").asRuntimeException()
                    is ClienteNaoEncontradoException -> Status.NOT_FOUND.withDescription("Esse cliente não foi encontrado, assim não pode efetuar a operação que foi pedida").asRuntimeException()
                    is ParametrosNaoForamPassadosCorretamenteException -> Status.CANCELLED.withDescription("OS Parametros passado estao vazios").asRuntimeException()
                    is ChavePixNaoEncontradaException ->  Status.NOT_FOUND.withDescription("Essa chave Pix não está cadastrada no sistemas").asRuntimeException()
                    is ClienteNãoExisteNoItauException -> Status.NOT_FOUND.withDescription("Não foi encontrado no itau").asRuntimeException()
                    else -> Status.UNKNOWN.withDescription("erro inesperado ${e.message}").asRuntimeException()
                }
            val responseObserver = context?.parameterValues?.get(1) as StreamObserver<*>
            responseObserver.onError(status)
            return null
        }
    }
}