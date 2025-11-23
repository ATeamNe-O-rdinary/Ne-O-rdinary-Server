package org.ateam.ateam.domain.link.converter;

import org.ateam.ateam.domain.link.entity.Link;
import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.ateam.ateam.domain.linko.model.Linko;

public class LinkConverter {
<<<<<<< HEAD
=======

  public static Link toLinkEntity(Linker linker, Linko linko) {
    return Link.builder().linker(linker).linko(linko).build();
  }
>>>>>>> master
}
