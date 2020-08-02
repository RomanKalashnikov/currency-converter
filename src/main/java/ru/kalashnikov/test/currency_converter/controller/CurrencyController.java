package ru.kalashnikov.test.currency_converter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kalashnikov.test.currency_converter.entity.Currency;
import ru.kalashnikov.test.currency_converter.repository.CurrencyRepository;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class CurrencyController {
    @Autowired
    private CurrencyRepository currencyRepository;

    @GetMapping("/currency")
    public String currency(Map<String, Object> model) {
        List<Currency> currencyList = (List<Currency>) currencyRepository.findAll();
        model.put("currency", currencyList);
        return "currency";
    }

    @PostMapping("/currency")
    public String addCurrency(Currency currency, Map<String, Object> model) {
        if (currency == null) {
            throw new RuntimeException("не");
        }
        try {
            currencyRepository.save(currency);
        } catch (RuntimeException e) {
            log.error("Предмета нет");
        }
        List<Currency> all = (List<Currency>) currencyRepository.findAll();

        model.put("Currency", all);

        return "redirect:/currency";
    }
}
