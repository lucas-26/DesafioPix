package br.com.zupEdu.grpc

import br.com.zupEdu.grpc.exception.ChavePixNaoExisteException
import br.com.zupEdu.grpc.exception.EsseClienteNaoCadastrouEssaChavePixException
import br.com.zupEdu.grpc.exception.EsseUsuariojaEstaCadastradoNoSistemaException
import br.com.zupEdu.grpc.request.ChavePixDeletarRequestGrpc
import br.com.zupEdu.grpc.request.ChavePixRequestGrpc
import br.com.zupEdu.grpc.response.ChaveApagadaPixReponseGrpc
import br.com.zupEdu.grpc.response.ChavePixResponseGrpc
import br.com.zupEdu.model.Pix
import br.com.zupEdu.model.TipoConta
import br.com.zupEdu.model.TipoDeChave
import br.com.zupEdu.repository.ChavePixRepository
import br.com.zupEdu.service.CodigoInternoClient
import io.micronaut.validation.Validated
import java.lang.IllegalStateException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.persistence.Column
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.transaction.Transactional
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Validated
@Singleton
class NovaChavePixService(@Inject val chavePixRepository: ChavePixRepository,
                          @Inject val codigoInternoClient: CodigoInternoClient) {

    @Transactional
    fun registra(@Valid novaChave: ChavePixRequestGrpc): ChavePixResponseGrpc {
        val chavePixModel: Pix = novaChave.toModel()

        val response = codigoInternoClient.validaCodigoInterno(chavePixModel.codigoInternoDoCliente.toString())
        if (response[0].titular.id.isEmpty()){
            return throw IllegalStateException("Cliente não foi encontrado nos Registros do itau")
        }
        val econtrado = chavePixRepository.buscaSeClienteExiste(response[0].titular.id)

        if (econtrado.get().chavePix.isNotEmpty()){
            return throw EsseUsuariojaEstaCadastradoNoSistemaException()
        }

        chavePixRepository.save(chavePixModel)
        println(ChavePixResponseGrpc( chavePixModel.chavePix))
        return ChavePixResponseGrpc( chavePixModel.chavePix)
    }

    @Transactional
    fun apagar(@Valid novaChaveApagar: ChavePixDeletarRequestGrpc): ChaveApagadaPixReponseGrpc {

        val verificaSeExisteNoSistemaERPitau = codigoInternoClient.validaCodigoInterno(novaChaveApagar.clientid)
        if (verificaSeExisteNoSistemaERPitau.isNullOrEmpty()){
            throw IllegalArgumentException("Esse clienteid não existe no sistema ERP do Itaú")
        }

        val apagarChavePix: Optional<Pix> = chavePixRepository.buscaChavePixPeloIdChave(novaChaveApagar.pixId)
        apagarChavePix.let { chave ->
            if(chave.isPresent){
                if (chave.get().codigoInternoDoCliente != novaChaveApagar.clientid){
                    return throw EsseClienteNaoCadastrouEssaChavePixException()
                }
            } else {
                return throw ChavePixNaoExisteException()
            }
        }

        chavePixRepository.deletarPixPorChave(apagarChavePix.get().chavePix)
        return ChaveApagadaPixReponseGrpc("PIX ${apagarChavePix.get().chavePix} apagado")
    }
}

