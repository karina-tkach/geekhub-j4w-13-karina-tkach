package org.geekhub.encryption.controllers;

import org.geekhub.encryption.service.HistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping(path="/history/{limit}/{pageNumber}")
    public String historyWithPagination(@PathVariable int limit,
                                        @PathVariable int pageNumber,
                                        Model model) {
        model.addAttribute("entries", historyService.getFullHistoryWithPagination(pageNumber, limit));
        return "history";
    }
}
