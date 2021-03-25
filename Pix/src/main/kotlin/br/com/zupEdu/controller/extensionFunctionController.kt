package br.com.zupEdu.controller

import br.com.zupEdu.ChavePixRequest
import br.com.zupEdu.TipoChavePix
import br.com.zupEdu.TipoConta
import br.com.zupEdu.grpc.request.ChavePixRequestGrpc

fun ChavePixRequestGrpc.toPixRequest(): ChavePixRequest.Builder? {
    return ChavePixRequest.newBuilder()
        .setIdCliente(this.idInternoClient)
        .setTipoChavePix(this.tipoChavePix.toChaveEnum())
        .setChave(this.chavePix)
        .setTipoDeConta(this.tipoConta.toContaEnum())
}

private fun String.toContaEnum(): TipoConta? {
    return when {
        this.toUpperCase() == TipoConta.CONTA_CORRENTE.toString() -> {
            TipoConta.CONTA_CORRENTE
        }
        this.toUpperCase() == TipoConta.CONTA_POUPANCA.toString() -> {
            TipoConta.CONTA_POUPANCA
        } else -> {
            TipoConta.DESCONHECIDO_TIPO_CONTA
        }
    }
}

private fun String.toChaveEnum(): TipoChavePix? {

    return when {
        this.toUpperCase() == TipoChavePix.CHAVE_ALEATORIA.toString() -> {
            TipoChavePix.CHAVE_ALEATORIA
        }
        this.toUpperCase() == TipoChavePix.CPF.toString() -> {
            TipoChavePix.CPF
        }
        this.toUpperCase() == TipoChavePix.EMAIL.toString() -> {
            TipoChavePix.EMAIL
        }
        this.toUpperCase() == TipoChavePix.TELEFONE_CELULAR.toString() -> {
            TipoChavePix.TELEFONE_CELULAR
        }
        else -> {
            TipoChavePix.DESCONHECIDO_TIPO_CHAVE_PIX
        }
    }
}