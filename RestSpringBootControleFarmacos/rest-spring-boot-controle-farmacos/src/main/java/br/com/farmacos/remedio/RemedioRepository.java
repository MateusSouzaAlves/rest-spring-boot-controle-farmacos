package br.com.farmacos.remedio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RemedioRepository extends JpaRepository<Remedio, Long>{

	Page<Remedio> findAllByAtivoTrue(Pageable paginacao);

	@Query
	Remedio findByNome(@Param("name") String name);

}

