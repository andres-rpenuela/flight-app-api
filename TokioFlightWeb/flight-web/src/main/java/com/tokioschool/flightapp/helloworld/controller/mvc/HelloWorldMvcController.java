package com.tokioschool.flightapp.helloworld.controller.mvc;

import com.tokioschool.flightapp.helloworld.service.HelloWorldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/helloworld")
@RequiredArgsConstructor
public class HelloWorldMvcController {

    private final HelloWorldService helloWorldService;
    @GetMapping({"","/"})
    public ModelAndView getHelloWorldHandler(@RequestParam(name = "name", required=false) String name) {
        final ModelAndView modelAndView = new ModelAndView("/base/helloworld");
        modelAndView.addObject("message",helloWorldService.getHiMessage(name).getMessage());

        return modelAndView;
    }
}
