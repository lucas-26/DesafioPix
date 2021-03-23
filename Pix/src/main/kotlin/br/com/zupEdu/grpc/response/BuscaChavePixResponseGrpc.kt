package br.com.zupEdu.grpc.response

data class BuscaChavePixResponseGrpc(val pixId: String,
                                val ClientId: String,
                                val tipoChave:String,
                                val valorChave:String,
                                val cpf:String,
                                val nomeInstituicao:String,
                                val agencia:String,
                                val numeroConta:String,
                                val tipoConta:String,
                                val dataHora:String){}
