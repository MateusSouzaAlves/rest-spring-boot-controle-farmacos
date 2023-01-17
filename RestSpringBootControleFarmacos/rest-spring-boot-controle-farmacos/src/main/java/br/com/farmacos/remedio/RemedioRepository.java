package br.com.farmacos.remedio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemedioRepository extends JpaRepository<Remedio, Long>{

	Page<Remedio> findAllByAtivoTrue(Pageable paginacao);

}
