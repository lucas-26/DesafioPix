package br.com.zupEdu.service.response

import br.com.zupEdu.service.response.BankAccountResponse
import br.com.zupEdu.service.response.OwnerResponse
import br.com.zupEdu.service.response.PixKey

data class PixKeyDetailsResponse(var keyType: String?,
                                 var key: String?,
                                 var bankAccount: BankAccountResponse?,
                                 var owner: OwnerResponse?,
                                 var createdAt:String?){

}
