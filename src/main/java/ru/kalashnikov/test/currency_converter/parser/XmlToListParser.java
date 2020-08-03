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

@Service
@Slf4j
public class XmlToListParser {

    private static final String HTTP_WWW_CBR_RU_SCRIPTS_XML_DAILY_ASP = "http://www.cbr.ru/scripts/XML_daily.asp";

    public List<Currency> getDocumentParseList() {
        Document document = getDocument();
        Node root = document.getDocumentElement();
        NodeList books = root.getChildNodes();
        List<Currency> currencyList = new ArrayList<>();

        for (int i = 0; i < books.getLength(); i++) {
            Node book = books.item(i);

            if (book.getNodeType() != Node.TEXT_NODE) {
                NodeList bookProps = book.getChildNodes();
                Currency currency = new Currency();

                for (int j = 0; j < bookProps.getLength(); j++) {
                    Node bookProp = bookProps.item(j);
                    Node attributeRootItem = root.getAttributes().item(0);
                    Node childNodesItem = bookProp.getChildNodes().item(0);
                    setValuteIdInCurrency(book, currency);
                    setDateInCurrency(attributeRootItem, currency);
                    setTextContentInCurrency(childNodesItem, currency);
                }
                currencyList.add(currency);
            }
        }
        return currencyList;
    }

    private Document getDocument() {
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.warn("Невозможно создать DocumentBuilder", e);
        }
        return getParseDocument(documentBuilder);
    }

    private Document getParseDocument(DocumentBuilder documentBuilder) {
        Document parse = null;
        try {
            parse = documentBuilder.parse(HTTP_WWW_CBR_RU_SCRIPTS_XML_DAILY_ASP);
        } catch (SAXException | IOException e) {
            log.warn("Ошибка парсинга страницы", e);
        }
        return parse;
    }

    private void setDateInCurrency(Node item, Currency currency) {
        if (item.getNodeName().equalsIgnoreCase("Date")) {

            String date = item.getNodeValue();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDate = LocalDate.parse(date, format);

            currency.setDate(localDate);
        }
    }

    private void setValuteIdInCurrency(Node item, Currency currency) {
        if (item.getNodeName().equalsIgnoreCase("ID")) {
            currency.setValuteID(item.getNodeValue());
        }
    }

    private void setTextContentInCurrency(Node childNodesItem, Currency currency) {
        if (childNodesItem.getNodeType() != Node.TEXT_NODE) {
            switch (childNodesItem.getNodeName()) {
                case "ID":
                    currency.setValuteID((childNodesItem.getTextContent()));
                    break;
                case "NumCode":
                    currency.setNumCode(Integer.valueOf(childNodesItem.getTextContent()));
                    break;
                case "CharCode":
                    currency.setCharCode(childNodesItem.getTextContent());
                    break;
                case "Nominal":
                    currency.setNominal(Integer.valueOf(childNodesItem.getTextContent()));
                    break;
                case "Name":
                    currency.setName(childNodesItem.getTextContent());
                    break;
                case "Value":
                    String s = childNodesItem.getTextContent();
                    currency.setValue(Double.valueOf(s.replace(",", ".")));
                    break;
            }
        }
    }


}
