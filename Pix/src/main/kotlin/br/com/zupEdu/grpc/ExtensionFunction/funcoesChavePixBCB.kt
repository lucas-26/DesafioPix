package br.com.zupEdu.grpc.ExtensionFunction

import br.com.zupEdu.model.Pix
import br.com.zupEdu.model.TipoConta
import br.com.zupEdu.model.TipoDeChave
import br.com.zupEdu.service.request.BankAccountRequest
import br.com.zupEdu.service.request.CreatePixKeyRequest
import br.com.zupEdu.service.request.OwnerRequest
import br.com.zupEdu.service.response.ContasResponse

fun gerarpixBcB(response: ContasResponse, chavePixModel: Pix): CreatePixKeyRequest {
    return CreatePixKeyRequest(
        keyType = "${VerificarOTipo(chavePixModel.tipoDeChave)}",
        key = "${chavePixModel.chavePix}",
        bankAccount = BankAccountRequest(
            participant = "${response.instituicao.ispb}",
            branch = "${response.agencia}",
            accountNumber = "${response.numero}",
            accountType = "${verificaTipoConta(response.tipo)}"
        ),
        OwnerRequest(
            type = "NATURAL_PERSON",
            name = "${response.titular.nome}",
            taxIdNumber = "${response.titular.cpf}"
        )
    )
}

fun VerificarOTipo(tipoDeChave: TipoDeChave): String {
    if (tipoDeChave == TipoDeChave.CHAVE_ALEATORIA) {
        return "RANDOM".toUpperCase()
    } else if (tipoDeChave == TipoDeChave.TELEFONE_CELULAR){
        return "PHONE".toUpperCase()
    }
    return tipoDeChave.toString().toUpperCase()
}

fun verificaTipoConta(tipo: String): String {
    if (tipo.toUpperCase() == TipoConta.CONTA_CORRENTE.toString()){
        return "CACC"
    }
    return "SVGS"
}