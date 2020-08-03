package ru.kalashnikov.test.currency_converter.service;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.kalashnikov.test.currency_converter.dto.HistoryDto;
import ru.kalashnikov.test.currency_converter.entity.Currency;
import ru.kalashnikov.test.currency_converter.entity.History;
import ru.kalashnikov.test.currency_converter.mapper.CurrencyMapper;
import ru.kalashnikov.test.currency_converter.mapper.HistoryMapper;
import ru.kalashnikov.test.currency_converter.parser.XmlToListParser;
import ru.kalashnikov.test.currency_converter.repository.CurrencyRepository;
import ru.kalashnikov.test.currency_converter.repository.HistoryRepository;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ConverterService {
    private CurrencyRepository currencyRepository;
    private HistoryRepository historyRepository;
    private XmlToListParser xmlToListParser;
   private CurrencyMapper currencyMapper = Mappers.getMapper(CurrencyMapper.class);
    private HistoryMapper historyMapper = Mappers.getMapper(HistoryMapper.class);


    public ConverterService(CurrencyRepository currencyRepository, HistoryRepository historyRepository, XmlToListParser xmlToListParser) {
        this.currencyRepository = currencyRepository;
        this.historyRepository = historyRepository;
        this.xmlToListParser = xmlToListParser;
    }



    public History createConversion(HistoryDto historyDto) {
        History history = historyMapper.toDomain(historyDto);
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
        List<Currency> currencyList = xmlToListParser.getDocumentParseList();

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
