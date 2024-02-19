package org.geekhub.encryption.controllers;

import org.geekhub.encryption.models.HistoryEntry;
import org.geekhub.encryption.models.HistoryParamsDTO;
import org.geekhub.encryption.service.HistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping(path="/history")
    public String history(Model model) {
        model.addAttribute("historyParamsDTO",new HistoryParamsDTO());
        return "historyMain";
    }

    @GetMapping(path="/history/{limit}/{pageNumber}")
    public String historyWithPagination(@PathVariable int limit,
                                        @PathVariable int pageNumber,
                                        Model model) {
        List<HistoryEntry> history = historyService.getFullHistoryWithPagination(pageNumber, limit);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages",history.size());
        model.addAttribute("entries", history);
        return "historyPagination";
    }
    @PostMapping("/history/algorithm")
    public String submitFormAlgorithm(@ModelAttribute("historyParamsDTO") HistoryParamsDTO historyParamsDTO) {
        return "redirect:/history/" + historyParamsDTO.getAlgorithm();
    }
    @GetMapping(path="/history/{algorithm}")
    public String historyAlgorithm(@PathVariable String algorithm,
                                                 Model model) {
        model.addAttribute("entries", historyService.getHistoryByAlgorithm(algorithm));
        return "history";
    }
    @PostMapping("/history/recordId")
    public String submitFormRecordId(@ModelAttribute("historyParamsDTO") HistoryParamsDTO historyParamsDTO) {
        return "redirect:/history/recordId/" + historyParamsDTO.getRecordId();
    }
    @GetMapping(path="/history/recordId/{recordId}")
    public String historyRecordId(@PathVariable int recordId,
                                                 Model model) {
        model.addAttribute("entries", historyService.getHistoryRecordById(recordId));
        return "history";
    }
    @PostMapping("/history/userId")
    public String submitFormUserId(@ModelAttribute("historyParamsDTO") HistoryParamsDTO historyParamsDTO) {
        return "redirect:/history/userId/" + historyParamsDTO.getUserId();
    }
    @GetMapping(path="/history/userId/{userId}")
    public String historyUserId(@PathVariable int userId,
                                                 Model model) {
        model.addAttribute("entries", historyService.getHistoryRecordsByUserId(userId));
        return "history";
    }
    @PostMapping("/history/date")
    public String submitFormDate(@ModelAttribute("historyParamsDTO") HistoryParamsDTO historyParamsDTO) {
        return "redirect:/history/date";
    }
    @GetMapping(path="/history/date")
    public String historyDate(Model model) {
        HistoryParamsDTO historyParamsDTO = (HistoryParamsDTO) model.getAttribute("historyParamsDTO");
        model.addAttribute("entries", historyService.getHistoryInDateRange(historyParamsDTO.getDateFrom(), historyParamsDTO.getDateTo()));
        return "history";
    }
    @PostMapping("/history/algorithmAndOperation")
    public String submitFormAlgorithmAndOperation(@ModelAttribute("historyParamsDTO") HistoryParamsDTO historyParamsDTO) {
        return "redirect:/history/algorithmAndOperation/" + historyParamsDTO.getAlgorithm() +"/" + historyParamsDTO.getOperationType();
    }
    @GetMapping(path="/history/algorithmAndOperation/{algorithm}/{operation}")
    public String historyUserId(@PathVariable String algorithm,
                                @PathVariable String operation,
                                Model model) {
        model.addAttribute("entries", historyService.getHistoryByAlgorithmAndOperationType(algorithm,operation));
        return "history";
    }
}
