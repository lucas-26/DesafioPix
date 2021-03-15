package br.com.zupEdu.repository

import br.com.zupEdu.model.ChavePix
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface ChavePixRepository: JpaRepository<ChavePix, Long> {
}