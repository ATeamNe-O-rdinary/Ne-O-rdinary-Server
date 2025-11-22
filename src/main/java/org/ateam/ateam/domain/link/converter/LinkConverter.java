package org.ateam.ateam.domain.link.converter;

import org.ateam.ateam.domain.link.dto.req.LinkReqDTO;
import org.ateam.ateam.domain.link.entity.Link;
import org.ateam.ateam.domain.member.entity.Linker;
import org.ateam.ateam.domain.member.entity.Linko;

public class LinkConverter {

    public static Link toLinkEntity(Linker linker, Linko linko){
        return Link.builder()
                .linker(linker)
                .linko(linko)
                .build();
    }
}
