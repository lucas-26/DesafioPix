package br.com.zupEdu.service.request

class BankAccountRequest(
    val participant:String,
    val branch:String,
    val accountNumber:String,
    val accountType: String
){
}