package ru.kalashnikov.test.currency_converter.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.kalashnikov.test.currency_converter.entity.Currency;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Service
@Slf4j
public class XmlTolist {

    public List<Currency> creteDocument() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse("http://www.cbr.ru/scripts/XML_daily.asp");
        Node root = document.getDocumentElement();
        List<Currency> currencyMap = new ArrayList<>();
        NodeList books = root.getChildNodes();

        for (int i = 0; i < books.getLength(); i++) {
            Node book = books.item(i);

            if (book.getNodeType() != Node.TEXT_NODE) {
                NodeList bookProps = book.getChildNodes();
                Currency currency = new Currency();

                for (int j = 0; j < bookProps.getLength(); j++) {
                    Node bookProp = bookProps.item(j);
                    if (book.getAttributes().item(0).getNodeName().equalsIgnoreCase("ID")) {
                        currency.setValuteID(book.getAttributes().item(0).getNodeValue());
                    }
                    if (root.getAttributes().item(0).getNodeName().equalsIgnoreCase("Date")) {
                        String date = root.getAttributes().item(0).getNodeValue();
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        LocalDate localDate = LocalDate.parse(date, format);
                        currency.setDate(localDate);
                    }
                    // Если нода не текст, то это один из параметров книги - печатаем

                    if (bookProp.getNodeType() != Node.TEXT_NODE) {
                        switch (bookProp.getNodeName()) {
                            case "ID":
                                currency.setValuteID((bookProp.getChildNodes().item(0).getTextContent()));
                                break;
                            case "NumCode":
                                currency.setNumCode(Integer.valueOf(bookProp.getChildNodes().item(0).getTextContent()));
                                break;
                            case "CharCode":
                                currency.setCharCode(bookProp.getChildNodes().item(0).getTextContent());
                                break;
                            case "Nominal":
                                currency.setNominal(Integer.valueOf(bookProp.getChildNodes().item(0).getTextContent()));
                                break;
                            case "Name":
                                currency.setName(bookProp.getChildNodes().item(0).getTextContent());
                                break;
                            case "Value":
                                String s = bookProp.getChildNodes().item(0).getTextContent();
                                currency.setValue(Double.valueOf(s.replace(",", ".")));
                                break;
                        }
                    }
                }
                currencyMap.add(currency);
            }
        }
        return currencyMap;
    }


}
