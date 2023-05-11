package com.example.smlr.model.repositories

import com.example.smlr.model.Link
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.Repository
import java.util.*
@EnableJpaRepositories
interface LinkRepository: Repository<Link, Long> {
    fun findById(id: Long?): Optional<Link>
    fun save(link: Link): Link
    fun findAll(): List<Link>
}