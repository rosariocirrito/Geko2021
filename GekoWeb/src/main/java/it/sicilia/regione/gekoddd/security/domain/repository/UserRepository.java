package it.sicilia.regione.gekoddd.security.domain.repository;


import org.springframework.data.repository.CrudRepository;

import it.sicilia.regione.gekoddd.security.domain.entity.UserSecur;

public interface UserRepository extends CrudRepository<UserSecur, Integer> {
	public UserSecur findByUsername(String username);
	public UserSecur findByPfID(Integer pfID);
}
