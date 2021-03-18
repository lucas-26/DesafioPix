package br.com.zupEdu.repository

import br.com.zupEdu.model.Pix
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface ChavePixRepository: JpaRepository<Pix, Long> {

    @Query("SELECT a FROM Pix a WHERE a.codigoInternoDoCliente  = :codigo")
    fun buscaSeClienteExiste (codigo: String): Optional<Pix>

    @Query("SELECT b FROM Pix b Where b.chavePix = :chavepix")
    fun buscaChavePixPeloIdChave(chavepix: String): Optional<Pix>

    @Query("Delete FROM Pix as b Where b.chavePix = :chavepix")
    fun deletarPixPorChave(chavepix: String)
}