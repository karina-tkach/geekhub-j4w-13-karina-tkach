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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HistoryController {
    private final HistoryService historyService;
    private static final String HISTORY_PAGE_NAME = "history";
    private static final String HISTORY_DTO_NAME = "historyParamsDTO";
    private static final String HISTORY_ENTRIES_NAME = "entries";

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping(path = "/history")
    public String history(Model model) {
        model.addAttribute(HISTORY_DTO_NAME, new HistoryParamsDTO());
        return "historyMain";
    }

    @GetMapping(path = "/history/{limit}/{pageNumber}")
    public String historyWithPagination(@PathVariable int limit,
                                        @PathVariable int pageNumber,
                                        Model model) {
        List<HistoryEntry> history = historyService.getFullHistoryWithPagination(pageNumber, limit);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", Math.round(historyService.getHistoryRowsCount() / (float) limit));
        model.addAttribute(HISTORY_ENTRIES_NAME, history);
        return "historyPagination";
    }

    @PostMapping("/history/algorithm")
    public String submitFormAlgorithm(@ModelAttribute(HISTORY_DTO_NAME) HistoryParamsDTO historyParamsDTO) {
        return "redirect:/history/" + historyParamsDTO.getAlgorithm();
    }

    @GetMapping(path = "/history/{algorithm}")
    public String historyAlgorithm(@PathVariable String algorithm,
                                   Model model) {
        model.addAttribute(HISTORY_ENTRIES_NAME, historyService.getHistoryByAlgorithm(algorithm));
        return HISTORY_PAGE_NAME;
    }

    @PostMapping("/history/recordId")
    public String submitFormRecordId(@ModelAttribute(HISTORY_DTO_NAME) HistoryParamsDTO historyParamsDTO) {
        return "redirect:/history/recordId/" + historyParamsDTO.getRecordId();
    }

    @GetMapping(path = "/history/recordId/{recordId}")
    public String historyRecordId(@PathVariable int recordId,
                                  Model model) {
        model.addAttribute(HISTORY_ENTRIES_NAME, historyService.getHistoryRecordById(recordId));
        return HISTORY_PAGE_NAME;
    }

    @PostMapping("/history/userId")
    public String submitFormUserId(@ModelAttribute(HISTORY_DTO_NAME) HistoryParamsDTO historyParamsDTO) {
        return "redirect:/history/userId/" + historyParamsDTO.getUserId();
    }

    @GetMapping(path = "/history/userId/{userId}")
    public String historyUserId(@PathVariable int userId,
                                Model model) {
        model.addAttribute(HISTORY_ENTRIES_NAME, historyService.getHistoryRecordsByUserId(userId));
        return HISTORY_PAGE_NAME;
    }

    @PostMapping("/history/date")
    public String submitFormDate(@ModelAttribute(HISTORY_DTO_NAME) HistoryParamsDTO historyParamsDTO,
                                 RedirectAttributes attributes) {
        attributes.addFlashAttribute(HISTORY_DTO_NAME, historyParamsDTO);
        return "redirect:/history/date";
    }

    @GetMapping(path = "/history/date")
    public String historyDate(Model model) {
        HistoryParamsDTO historyParamsDTO = (HistoryParamsDTO) model.asMap().get(HISTORY_DTO_NAME);
        model.addAttribute(HISTORY_ENTRIES_NAME, historyService.getHistoryInDateRange(historyParamsDTO.getDateFrom(), historyParamsDTO.getDateTo()));
        return HISTORY_PAGE_NAME;
    }

    @PostMapping("/history/algorithmAndOperation")
    public String submitFormAlgorithmAndOperation(@ModelAttribute(HISTORY_DTO_NAME) HistoryParamsDTO historyParamsDTO) {
        return "redirect:/history/algorithmAndOperation/" + historyParamsDTO.getAlgorithm() + "/" + historyParamsDTO.getOperationType();
    }

    @GetMapping(path = "/history/algorithmAndOperation/{algorithm}/{operation}")
    public String historyUserId(@PathVariable String algorithm,
                                @PathVariable String operation,
                                Model model) {
        model.addAttribute(HISTORY_ENTRIES_NAME, historyService.getHistoryByAlgorithmAndOperationType(algorithm, operation));
        return HISTORY_PAGE_NAME;
    }
}
