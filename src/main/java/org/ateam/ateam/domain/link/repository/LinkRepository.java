package org.ateam.ateam.domain.link.repository;

import org.ateam.ateam.domain.link.entity.Link;
import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.ateam.ateam.domain.linko.model.Linko;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
    boolean existsByLinkerAndLinko(Linker linker, Linko linko);
}
