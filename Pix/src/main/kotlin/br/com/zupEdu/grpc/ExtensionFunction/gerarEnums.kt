package br.com.zupEdu.grpc.ExtensionFunction

import br.com.zupEdu.TipoChavePix

fun String?.gerarEnumConta(): br.com.zupEdu.TipoConta? {
    if (this?.toUpperCase().equals("CONTA_POUPANCA")){
        return br.com.zupEdu.TipoConta.CONTA_POUPANCA
    }
    return br.com.zupEdu.TipoConta.CONTA_CORRENTE
}

fun String?.gerarEnum(): TipoChavePix? {
    return when {
        this?.toUpperCase().equals("CPF") -> {
            TipoChavePix.CPF
        }
        this?.toUpperCase().equals("TELEFONE_CELULAR") -> {
            TipoChavePix.TELEFONE_CELULAR

        }
        this?.toUpperCase().equals("EMAIL") -> {
            TipoChavePix.EMAIL
        }
        this?.toUpperCase().equals("CHAVE_ALEATORIA") -> {
            TipoChavePix.CHAVE_ALEATORIA
        } else -> {
            return  TipoChavePix.DESCONHECIDO_TIPO_CHAVE_PIX
        }
    }
}