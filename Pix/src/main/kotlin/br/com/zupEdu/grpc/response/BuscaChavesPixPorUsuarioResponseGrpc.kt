package br.com.zupEdu.grpc.response

data class BuscaChavesPixPorUsuarioResponseGrpc(val pixId: String,
val ClienteId:String,
val tipoDeChave: String,
val valorDaChave:String,
val tipoDConta: String,
val dataHora: String ){
}
