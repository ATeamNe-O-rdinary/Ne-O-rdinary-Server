package org.ateam.ateam.domain.link.service.impl;

import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.link.converter.LinkConverter;
import org.ateam.ateam.domain.link.dto.req.LinkReqDTO;
import org.ateam.ateam.domain.link.repository.LinkRepository;
import org.ateam.ateam.domain.link.service.LinkService;
import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.ateam.ateam.domain.linker.repository.LinkerRepository;
import org.ateam.ateam.domain.linko.model.Linko;
import org.ateam.ateam.domain.linko.repository.LinkoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {
    private final LinkRepository linkRepository;
    private final LinkoRepository linkoRepository;
    private final LinkerRepository linkerRepository;

    @Override
    @Transactional
    public void doLink(LinkReqDTO.linkDTO dto){
//        Linker linker = linkerRepository.findById(dto.linkerId());
//        Linko linko = linkoRepository.findById(dto.linkoId());
//        linkRepository.save(LinkConverter.toLinkEntity(linker, linko));

    }
}
