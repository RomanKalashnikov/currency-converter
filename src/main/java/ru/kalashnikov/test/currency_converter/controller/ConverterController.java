package ru.kalashnikov.test.currency_converter.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kalashnikov.test.currency_converter.entity.Currency;
import ru.kalashnikov.test.currency_converter.entity.History;
import ru.kalashnikov.test.currency_converter.parser.XmlTolist;
import ru.kalashnikov.test.currency_converter.repository.CurrencyRepository;
import ru.kalashnikov.test.currency_converter.repository.HistoryRepository;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/converter")
@Slf4j
public class ConverterController {
    @Autowired
    HistoryRepository historyRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    XmlTolist xmlTolist;

    @SneakyThrows
    @GetMapping()
    public String converter(Map<String, Object> model) {
        Map<Integer, Currency> currencyMap = xmlTolist.creteDocument();
        for (Currency value : currencyMap.values()) {
            Currency byValuteID = currencyRepository.findByValuteID(value.getValuteID());
            if (byValuteID == null) {
                currencyRepository.save(value);
            } else if (value.getValue() > byValuteID.getValue()) {
                byValuteID.setValue(value.getValue());
                byValuteID.setDate(value.getDate());
                currencyRepository.save(byValuteID);

            }
        }
        model.put("convert", new Object());
        return "converter";
    }

    @SneakyThrows
    @PostMapping()
    public String calculate(@ModelAttribute History history, Double amount, String currencyFrom, String currencyTo, Map<String, Object> model) {
        Currency currencyFromRepo = currencyRepository.findByName(currencyFrom);
        Currency currencyToRepo = currencyRepository.findByName(currencyTo);
        if (currencyFromRepo.getDate().isBefore(LocalDate.now()) || currencyToRepo.getDate().isBefore(LocalDate.now())) {
            log.info("Отреагировал в контроллере на дату");
            xmlTolist.creteDocument();
        }
        String resultAmount = new DecimalFormat("#0.0000")
                .format((((amount / currencyFromRepo.getNominal()) * currencyFromRepo.getValue())) / currencyToRepo.getValue());

        history.setSourceCurrency(currencyFrom);
        history.setTargetCurrency(currencyTo);
        //TODO Тут ещё должен быть рассчёт номинала
        history.setSourceAmount(amount);
        history.setTargetAmount(Double.valueOf(resultAmount.replace(",", ".")));
        history.setDate(LocalDate.now());
        model.put("convert", history);

        historyRepository.save(history);


        return "converter";
    }

}
