package ru.kalashnikov.test.currency_converter.service;

import org.springframework.stereotype.Service;
import ru.kalashnikov.test.currency_converter.entity.History;
import ru.kalashnikov.test.currency_converter.repository.HistoryRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class HistoryService {
    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    private HistoryRepository historyRepository;


    public List<History> filter(LocalDate startSearch, LocalDate endSearch) {
        return historyRepository.findByDateBetween(startSearch, endSearch);
    }


}
