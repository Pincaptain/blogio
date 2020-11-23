package com.gjorovski.blogio.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(name = "")
public class IndexController {
    @SuppressWarnings("unused")
    private static final String TAG = "IndexController";

    @GetMapping(name = "")
    public String index() {
        return "redirect:/posts";
    }
}
