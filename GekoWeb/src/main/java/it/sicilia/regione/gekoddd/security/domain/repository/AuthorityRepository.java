package it.sicilia.regione.gekoddd.security.domain.repository;

import it.sicilia.regione.gekoddd.security.domain.entity.AuthoritySecur;
import it.sicilia.regione.gekoddd.security.domain.entity.UserSecur;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<AuthoritySecur, Integer> {
	
	
}
