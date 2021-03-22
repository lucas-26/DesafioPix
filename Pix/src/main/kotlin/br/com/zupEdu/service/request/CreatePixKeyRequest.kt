package br.com.zupEdu.service.request

class CreatePixKeyRequest(val keyType:String,
                          val key: String,
                          val bankAccount: BankAccountRequest,
                          val owner: OwnerRequest,
                          ){
}