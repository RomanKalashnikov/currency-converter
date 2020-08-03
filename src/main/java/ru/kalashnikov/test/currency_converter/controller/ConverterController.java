package ru.kalashnikov.test.currency_converter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kalashnikov.test.currency_converter.dto.HistoryDto;
import ru.kalashnikov.test.currency_converter.entity.History;
import ru.kalashnikov.test.currency_converter.service.ConverterService;

import java.util.Map;

@Controller
@RequestMapping("/converter")
@Slf4j
public class ConverterController {
    private ConverterService converterService;

    public ConverterController(ConverterService converterService) {
        this.converterService = converterService;
    }

    @GetMapping()
    public String converter() {
        return "converter";
    }

    @PostMapping()
    public String calculate(HistoryDto historyDto, Map<String, Object> model) {
        History conversion = converterService.createConversion(historyDto);
        model.put("convert", conversion);


        return "converter";
    }

}
