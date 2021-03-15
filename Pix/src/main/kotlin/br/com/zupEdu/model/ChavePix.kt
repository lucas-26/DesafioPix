package br.com.zupEdu.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class ChavePix(
    val codigoInterno: String,
    val chavePix:String
    ){
    @Id
    @GeneratedValue
    var id: Long? = null
}