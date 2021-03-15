package br.com.zupEdu.validation

class ValidationPix {

    fun validacaoDosValores(chave: String): String{
        var valorFinal: String = ""
        val validacaoChavepix = chave

        if (validacaoChavepix.matches("^[0-9]{11}\$".toRegex())) { //se é um cpf valido
            valorFinal = validacaoChavepix
            println("é um cpf e é valido")
        }

        if (validacaoChavepix.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())) { // telefone valido
            valorFinal = validacaoChavepix
            println("é um telefone valido")
        }

        if (validacaoChavepix.matches("/^[a-z0-9.]+@[a-z0-9]+\\.[a-z]+\\.([a-z]+)?\$/i".toRegex())) {//email valido
            valorFinal = validacaoChavepix
            println("é um email valido")
        }
        return valorFinal
    }
}