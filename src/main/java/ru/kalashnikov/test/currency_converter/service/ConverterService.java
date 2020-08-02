package ru.kalashnikov.test.currency_converter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import ru.kalashnikov.test.currency_converter.entity.Currency;
import ru.kalashnikov.test.currency_converter.entity.History;
import ru.kalashnikov.test.currency_converter.parser.XmlTolist;
import ru.kalashnikov.test.currency_converter.repository.CurrencyRepository;
import ru.kalashnikov.test.currency_converter.repository.HistoryRepository;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ConverterService {
    private CurrencyRepository currencyRepository;
    private HistoryRepository historyRepository;
    private XmlTolist xmlTolist;


    public ConverterService(CurrencyRepository currencyRepository, HistoryRepository historyRepository, XmlTolist xmlTolist) {
        this.currencyRepository = currencyRepository;
        this.historyRepository = historyRepository;
        this.xmlTolist = xmlTolist;
    }

    public History createConversion(History history) {
        Double calculateResults = calculate(history);
        return saveResultCalculationOnHistory(history, calculateResults);
    }


    private Double calculate(History history) {
        Currency currencyFromRepo = currencyRepository.findByName(history.getSourceCurrency());
        Currency currencyToRepo = currencyRepository.findByName(history.getTargetCurrency());

        if (currencyFromRepo == null || currencyToRepo == null
                || currencyFromRepo.getDate().isBefore(LocalDate.now()) || currencyToRepo.getDate().isBefore(LocalDate.now())) {
            log.info("Отреагировал в контроллере на дату");
            updateCurrency();

            currencyFromRepo = currencyRepository.findByName(history.getSourceCurrency());
            currencyToRepo = currencyRepository.findByName(history.getTargetCurrency());
        }
        return Double.parseDouble((new DecimalFormat("#0.0000")
                .format((history.getSourceAmount() / currencyFromRepo.getNominal() * currencyFromRepo.getValue()) / currencyToRepo.getValue()))
                .replace(",", "."));
    }

    private void updateCurrency() {
        List<Currency> currencyList = new ArrayList<>();
        try {
            currencyList = xmlTolist.creteDocument();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            log.warn("Невозможно обновить Валюты");
        }

        currencyList.forEach(currency -> {
            Currency byValuteID = currencyRepository.findByValuteID(currency.getValuteID());
            if (byValuteID != null) {
                byValuteID.setDate(currency.getDate());
                currencyRepository.save(byValuteID);
            } else {
                currencyRepository.save(currency);
            }
        });
    }

    private History saveResultCalculationOnHistory(History history, Double calculationResult) {
        history.setTargetAmount(calculationResult);
        history.setDate(LocalDate.now());

        return historyRepository.save(history);
    }


}
