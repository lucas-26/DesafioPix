package br.com.zupEdu.model

import br.com.zupEdu.TipoChavePix
import javax.persistence.*

@Entity
@Table(name = "Pix")
class Pix(
    val codigoInternoDoCliente: String,

    @Column(unique = true)
    val chavePix:String,

    @Enumerated(EnumType.STRING)
    val tipoDeChave: TipoDeChave,

    @Enumerated(EnumType.STRING)
    val tipoConta: TipoConta,

    var chaveBcb: String?
    ){
    @Id
    @GeneratedValue
    var id: Long? = null
}