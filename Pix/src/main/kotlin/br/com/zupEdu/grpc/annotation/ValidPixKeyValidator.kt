package br.com.zupEdu.grpc.annotation

import br.com.zupEdu.grpc.request.ChavePixRequestGrpc
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton


@Singleton
class ValidPixKeyValidator : ConstraintValidator<ValidPixKey, ChavePixRequestGrpc> {
    override fun isValid(
        value: ChavePixRequestGrpc?,
        annotationMetadata: AnnotationValue<ValidPixKey>,
        context: ConstraintValidatorContext
    ): Boolean {
        if (value?.tipoChavePix == null){
            return false
        }
        return value.tipoChavePix.valida(value.chavePix)
    }
}

private fun String.valida(chavePix: String): Boolean {
    return true
}


