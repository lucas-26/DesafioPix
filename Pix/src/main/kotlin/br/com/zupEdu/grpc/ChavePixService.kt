package br.com.zupEdu.grpc

import br.com.zupEdu.grpc.ExtensionFunction.gerarpixBcB
import br.com.zupEdu.grpc.exception.ChavePixNaoExisteException
import br.com.zupEdu.grpc.exception.EsseClienteNaoCadastrouEssaChavePixException
import br.com.zupEdu.grpc.exception.EsseUsuariojaEstaCadastradoNoSistemaException
import br.com.zupEdu.grpc.request.BuscaChavePixRequestGrpc
import br.com.zupEdu.grpc.request.ChavePixDeletarRequestGrpc
import br.com.zupEdu.grpc.request.ChavePixRequestGrpc
import br.com.zupEdu.grpc.response.BuscaChavePixResponseGrpc
import br.com.zupEdu.grpc.response.ChaveApagadaPixReponseGrpc
import br.com.zupEdu.grpc.response.ChavePixResponseGrpc
import br.com.zupEdu.model.Pix
import br.com.zupEdu.model.TipoConta
import br.com.zupEdu.model.TipoDeChave
import br.com.zupEdu.repository.ChavePixRepository
import br.com.zupEdu.service.CadastraBCBPixClient
import br.com.zupEdu.service.CodigoInternoClient
import br.com.zupEdu.service.request.BankAccountRequest
import br.com.zupEdu.service.request.CreatePixKeyRequest
import br.com.zupEdu.service.request.DeletePixKeyRequest
import br.com.zupEdu.service.request.OwnerRequest
import br.com.zupEdu.service.response.ContasResponse
import io.micronaut.validation.Validated
import java.lang.IllegalStateException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class ChavePixService(@Inject val chavePixRepository: ChavePixRepository,
                      @Inject val codigoInternoClient: CodigoInternoClient,
                      @Inject val bancoDoBrasilService: CadastraBCBPixClient
                          ) {

    @Transactional
    fun registra(@Valid novaChave: ChavePixRequestGrpc): ChavePixResponseGrpc {

        val chavePixModel: Pix = novaChave.toModel()

        val response = codigoInternoClient.validaCodigoInterno(chavePixModel.codigoInternoDoCliente.toString())

        if (response[0].titular.id.isEmpty()){
            return throw IllegalStateException("Cliente não foi encontrado nos Registros do itau")
        }

        val econtrado = chavePixRepository.buscaSeClienteExiste(response[0].titular.id)

        if (econtrado.isPresent){
            return throw EsseUsuariojaEstaCadastradoNoSistemaException()
        }

        val request21 = gerarpixBcB(response[0], chavePixModel)
        val registerPix = bancoDoBrasilService.registerPix(request21)
        chavePixModel.chaveBcb =  registerPix.body().key
        chavePixRepository.save(chavePixModel)
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

        val returnPixbyChave = bancoDoBrasilService.returnPixbyChave(apagarChavePix.get().chaveBcb.toString())

        val deletePixKeyRequest =
            DeletePixKeyRequest(returnPixbyChave.body().key.toString(),
                returnPixbyChave.body().bankAccount?.participant.toString())

        chavePixRepository.deletarPixPorChave(apagarChavePix.get().chavePix)
        bancoDoBrasilService.deletePix(apagarChavePix.get().chaveBcb.toString(),deletePixKeyRequest)
        return ChaveApagadaPixReponseGrpc("PIX ${apagarChavePix.get().chavePix} apagado")
    }

    @Transactional
    fun buscarChavePix(req: BuscaChavePixRequestGrpc): BuscaChavePixResponseGrpc {

        val buscaBancoInterno = chavePixRepository.buscaChavePixPeloIdChave(req.chavePix)
            if(buscaBancoInterno.get().chavePix.isNullOrEmpty()){
                throw IllegalArgumentException("Esse clienteid não existe no sistema ERP do Itaú")
            }

        val buscaBcb = bancoDoBrasilService.returnPixbyChave(buscaBancoInterno.get().chaveBcb.toString())
        val codigoInterno = codigoInternoClient.validaCodigoInterno(buscaBancoInterno.get().codigoInternoDoCliente)

        return BuscaChavePixResponseGrpc(pixId = buscaBancoInterno.get().chavePix,
        ClientId = buscaBancoInterno.get().codigoInternoDoCliente,
            tipoChave = buscaBcb.body().key.toString()  ,
            valorChave = buscaBcb.body().keyType.toString(),
            cpf = codigoInterno[0].titular.cpf ,
            nomeInstituicao = codigoInterno[0].instituicao.nome,
            agencia = codigoInterno[0].agencia ,
            numeroConta = codigoInterno[0].numero,
            tipoConta = buscaBancoInterno.get().tipoConta.toString(),
            dataHora =  buscaBcb.body().createdAt.toString() )
    }
}