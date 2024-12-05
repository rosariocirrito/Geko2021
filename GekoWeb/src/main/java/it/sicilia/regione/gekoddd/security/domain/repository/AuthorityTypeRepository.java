package it.sicilia.regione.gekoddd.security.domain.repository;


import org.springframework.data.repository.CrudRepository;

import it.sicilia.regione.gekoddd.security.domain.entity.AuthorityTypeSecur;

public interface AuthorityTypeRepository extends CrudRepository<AuthorityTypeSecur, Integer> {

}
