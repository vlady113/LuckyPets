package com.vladyslav.luckypets.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class ImagenTarjetaController {

    @Value("${custom.server.url}")
    private String serverUrl;

    @GetMapping("/card-logos")
    public Map<String, String> getCardLogos() {
        Map<String, String> cardLogos = new HashMap<>();
        cardLogos.put("Visa", serverUrl + "/images/visa_logo.png");
        cardLogos.put("MasterCard", serverUrl + "/images/mc_logo.png");
        cardLogos.put("AmericanExpress", serverUrl + "/images/ae_logo.png");
        cardLogos.put("Others", serverUrl + "/images/otros_logo.png");
        return cardLogos;
    }
}
