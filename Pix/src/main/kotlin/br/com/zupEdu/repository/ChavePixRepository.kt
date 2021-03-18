package br.com.zupEdu.repository

import br.com.zupEdu.model.ChavePix
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface ChavePixRepository: JpaRepository<ChavePix, Long> {

    @Query("SELECT a FROM chave_pix as a WHERE a.codigo_interno_do_cliente = :codigo")
    fun buscaSeClienteExiste (codigo: String): Optional<ChavePix>
}