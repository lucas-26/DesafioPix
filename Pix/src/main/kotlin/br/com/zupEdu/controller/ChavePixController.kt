package br.com.zupEdu.controller

import br.com.zupEdu.validation.ValidationPix
import br.com.zupEdu.repository.ChavePixRepository
import br.com.zupEdu.request.ChavePixRequest

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.transaction.Transactional
import javax.validation.Valid


@Validated
@Controller("/ChavePix")
class ChavePixController(val chavePixRepository: ChavePixRepository){

    @Post("/CadastraNovaChave")
    @Transactional
    fun cadastrarChave(@Body @Valid request: ChavePixRequest): HttpResponse<Any> {
        var validacao: ValidationPix? = null

        val validado = validacao?.validacaoDosValores(request.chavePix)

        val inserirPix = request.let { chave ->
            chave.toModel(chave.codigoInterno, validado!!)
        }

        chavePixRepository.save(inserirPix)
        println(validacao)
        return HttpResponse.ok()
    }
}