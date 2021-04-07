package br.com.zupEdu.grpc.annotation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented//ao gerar o java doc, essa classe deve ser documentada.
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE) //vai ser executada em classes ou em Types
@Retention(AnnotationRetention.RUNTIME) // vai executar em tempo de runtime, assim o framework consegue analizar ela.
@Constraint(validatedBy = [ValidPixKeyValidator::class]) //validador ou validadores
annotation class ValidPixKey(
    val message: String = "a chave pix está invalida", //mensagem padrão
    val groups: Array<KClass<Any>> = [],
    val payload:Array<KClass<Payload>> = []
)
