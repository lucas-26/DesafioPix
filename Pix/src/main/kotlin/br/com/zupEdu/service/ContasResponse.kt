package br.com.zupEdu.service

data class ContasResponse(val tipo: String,
                          val instituicao: InstituicaoResponse,
                          val agencia: String,
                          val numero: String,
                          val titular: TitularResponse
                          ){
}
