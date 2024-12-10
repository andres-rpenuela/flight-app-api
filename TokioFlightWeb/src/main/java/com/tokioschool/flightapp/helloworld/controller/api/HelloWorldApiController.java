package com.tokioschool.flightapp.helloworld.controller.api;

import com.tokioschool.flightapp.helloworld.dto.HiMessageResponseDTO;
import com.tokioschool.flightapp.helloworld.service.HelloWorldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloworld/api/hi")
@RequiredArgsConstructor
public class HelloWorldApiController {

    private final HelloWorldService helloWorldService;


    @GetMapping({"","/"})
    public ResponseEntity<HiMessageResponseDTO> getHelloWorldHandler(@RequestParam(value = "name",required = false ) String name) {
        final HiMessageResponseDTO hiMessageResponseDTO = this.helloWorldService.getHiMessage(name);

        return ResponseEntity.ok(hiMessageResponseDTO);
    }
}
