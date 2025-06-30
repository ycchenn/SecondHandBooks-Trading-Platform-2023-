package com.example.trade;

import org.springframework.web.bind.annotation.*;

@RestController
public class TradeController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello from backend!";
    }
}