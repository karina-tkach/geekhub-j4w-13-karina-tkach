package org.geekhub.encryption.controllers;

import org.geekhub.encryption.history.HistoryEntry;
import org.geekhub.encryption.history.HistoryParamsDTO;
import org.geekhub.encryption.history.HistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(path = "/historyList")
    public String historyWithPagination(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "8") int pageSize,
                                        Model model) {
        List<HistoryEntry> history = historyService.getFullHistoryWithPagination(page, pageSize);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", Math.round(historyService.getHistoryRowsCount() / (float) pageSize));
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
