package org.geekhub.encryption.controllers;

import org.geekhub.encryption.service.HistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping(path="/history")
    public String history(Model model) {
        model.addAttribute("entries", historyService.getFullHistory());
        return "history";
    }
}
