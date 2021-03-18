package br.com.zupEdu.grpc.request

import br.com.zupEdu.model.Pix
import br.com.zupEdu.model.TipoConta
import br.com.zupEdu.model.TipoDeChave
import io.micronaut.core.annotation.Introspected
import java.lang.IllegalArgumentException
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
class ChavePixRequestGrpc(
    @field:NotNull
    val idInternoClient: String,
    @field:NotNull
    @field:Size(max = 77)
    var chavePix:String,
    @field:NotNull
    val tipoChavePix: String,
    @field:NotNull
    val tipoConta: String
){
    fun toModel(): Pix {
        var tipoChavePixEnum: TipoDeChave =
            when {
                tipoChavePix.toUpperCase() == TipoDeChave.TELEFONE_CELULAR.toString() -> {
                    if (!chavePix.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())){
                        throw IllegalArgumentException("o telefone não está no formato correto")
                    }
                    TipoDeChave.TELEFONE_CELULAR
                }
                tipoChavePix.toUpperCase() == TipoDeChave.CPF.toString() -> {
                    if (!chavePix.matches("^[0-9]{11}\$".toRegex())){
                        throw IllegalArgumentException("o cpf não está no formato correto")
                    }
                    TipoDeChave.CPF
                }
                tipoChavePix.toUpperCase() == TipoDeChave.EMAIL.toString() -> {
                    if (!chavePix.matches("/^[a-z0-9.]+@[a-z0-9]+\\.[a-z]+\\.([a-z]+)?\$/i".toRegex())){
                        throw IllegalArgumentException("o email não está no formato correto")
                    }
                    TipoDeChave.EMAIL
                }
                tipoChavePix.toUpperCase() == TipoDeChave.CHAVE_ALEATORIA.toString() -> {
                    this.chavePix = UUID.randomUUID().toString()
                    TipoDeChave.CHAVE_ALEATORIA
                }
                else -> {
                    throw IllegalArgumentException("o tipo de chave não foi passado")
                }
            }

        var tipoContaPixEnum: TipoConta =
            if (tipoConta.toUpperCase() == TipoConta.CONTA_POUPANCA.toString()){
                    TipoConta.CONTA_POUPANCA
                } else {
                TipoConta.CONTA_CORRENTE
                }

        return Pix(codigoInternoDoCliente = idInternoClient,
                        chavePix = chavePix,
                        tipoDeChave = tipoChavePixEnum,
                        tipoConta = tipoContaPixEnum
            )
    }
}