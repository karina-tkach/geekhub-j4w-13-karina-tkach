package org.geekhub.encryption.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.geekhub.encryption.ciphers.EncodeDataDTO;
import org.geekhub.encryption.ciphers.EncryptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EncodingController {
    private final EncryptionService encryptionService;

    public EncodingController(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Operation(description = "Get main page")
    @GetMapping
    public String index() {
        return "index";
    }

    @Operation(description = "Get encoding page")
    @GetMapping("/encoding")
    public String showForm(Model model) {
        EncodeDataDTO encodeDataDTO = new EncodeDataDTO();
        model.addAttribute("encodeDataDTO", encodeDataDTO);
        return "encoding";
    }

    @Operation(description = "Submit encoding form")
    @PostMapping("/encoding")
    public ModelAndView submitForm(@ModelAttribute("encodeDataDTO") EncodeDataDTO encodeDataDTO) {
        ModelAndView view = new ModelAndView("encoding");
        String encodedMessage = encryptionService.performOperation(encodeDataDTO);
        view.addObject("encodedMessage",encodedMessage);
        return view;
    }
}
