package org.ateam.ateam.domain.link.controller;

import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.link.dto.req.LinkReqDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LinkController {

    @PostMapping("/api/links")
    public ResponseDto<> doLink(@RequestBody LinkReqDTO dto){


    }
}
