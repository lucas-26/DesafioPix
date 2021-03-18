package br.com.zupEdu.grpc

import br.com.zupEdu.grpc.exception.EsseUsuariojaEstaCadastradoNoSistemaException
import br.com.zupEdu.grpc.request.ChavePixRequestGrpc
import br.com.zupEdu.model.ChavePix
import br.com.zupEdu.repository.ChavePixRepository
import br.com.zupEdu.service.CodigoInternoClient
import io.micronaut.validation.Validated
import java.lang.IllegalStateException
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class NovaChavePixService(@Inject val chavePixRepository: ChavePixRepository,
                          @Inject val codigoInternoClient: CodigoInternoClient) {

    @Transactional
    fun registra(@Valid novaChave: ChavePixRequestGrpc): ChavePixResponseGrpc {

        val chavePixModel: ChavePix = novaChave.toModel()

        val response = codigoInternoClient.validaCodigoInterno(chavePixModel.codigoInternoDoCliente.toString())
        if (response.isEmpty()){
            throw IllegalStateException("Cliente não foi encontrado nos Registros do itau")
        }
        val econtrado = chavePixRepository.buscaSeClienteExiste(response[0].titular.id).toString()
        if (econtrado.isNullOrEmpty()){
            throw EsseUsuariojaEstaCadastradoNoSistemaException()
        }

        println("cadastrado")
        chavePixRepository.save(chavePixModel)
        println(ChavePixResponseGrpc( chavePixModel.chavePix))
        return ChavePixResponseGrpc( chavePixModel.chavePix)
    }
}