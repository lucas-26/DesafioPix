package br.com.zupEdu.service.response

data class BankAccountResponse(var participant:String?,
                               var branch:String?,
                               var accountNumber:String?,
                               var accountType: String?){
}