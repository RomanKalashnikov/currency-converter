package ru.kalashnikov.test.currency_converter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kalashnikov.test.currency_converter.entity.History;
import ru.kalashnikov.test.currency_converter.service.HistoryService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/history")
public class HistoryController {
    private HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public String history() {
        return "history";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String startSearch, @RequestParam String endSearch, Map<String, Object> model) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDateSearch = LocalDate.parse(startSearch, format);
        LocalDate endDateSearch = LocalDate.parse(endSearch, format);
        List<History> filter = historyService.filter(startDateSearch, endDateSearch);
        model.put("search", filter);
        return "history";
    }

}
