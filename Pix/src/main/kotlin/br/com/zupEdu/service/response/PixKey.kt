package br.com.zupEdu.service.response

data class PixKey(var keyType: String?,
                  var key: String?,
                  var bankAccount: BankAccountResponse?,
                  var owner: OwnerResponse?,
                  var createdAt:String?){
}
