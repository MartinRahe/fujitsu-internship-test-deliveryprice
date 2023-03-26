package martinrahe.fujitsu_internship_test_deliveryprice;

import martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects.WeatherData;
import martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects.WeatherDataController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class ImportData {

    private static final Logger log = LoggerFactory.getLogger(ImportData.class);
    private static final Set<String> stationsOfInterest =
            new HashSet<>(Arrays.asList("Tallinn-Harku", "Tartu-Tõravere", "Pärnu"));

    @Autowired
    private WeatherDataController weatherDataController;

    private static DocumentBuilder builder;
    static {
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static URL url;

    static {
        try {
            url = new URL("https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    String name;
    int wmocode;
    String phenomenon;
    float airtemperature;
    float windspeed;
    long timestamp;

    @Scheduled(cron = "${cron.expression:0 15 * * * *}")
    private void importWeatherData() throws IOException, SAXException {
        log.info("Importing weather data");
        Document doc = builder.parse(url.openStream());
        NodeList observations = doc.getElementsByTagName("observations");

        if (observations.getLength() != 1) {
            throw new RuntimeException("Unsuitable number of observations in xml");
        }
        Node observation = observations.item(0);

        if (!observation.getAttributes().item(0).getNodeName().equals("timestamp")) {
            throw new RuntimeException("The first argument in observation is not \"timestamp\"");
        }
        timestamp = Long.parseLong(observation.getAttributes().item(0).getNodeValue());

        NodeList stations = observation.getChildNodes();
        Node station;
        NodeList stationData;
        Node stationDataItem;

        for (int i = 0; i < stations.getLength(); i++) {
            station = stations.item(i);
            if (station.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            stationData = station.getChildNodes();

            label:
            for (int j = 0; j < stationData.getLength(); j++) {
                stationDataItem = stationData.item(j);
                if (stationDataItem.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }

                switch (stationDataItem.getNodeName()) {
                    case "name" -> {
                        name = stationDataItem.getTextContent();
                        if (!stationsOfInterest.contains(name)) {
                            break label;
                        }
                    }
                    case "wmocode" -> wmocode = Integer.parseInt(stationDataItem.getTextContent());
                    case "phenomenon" -> phenomenon = stationDataItem.getTextContent();
                    case "airtemperature" -> airtemperature = Float.parseFloat(stationDataItem.getTextContent());
                    case "windspeed" -> windspeed = Float.parseFloat(stationDataItem.getTextContent());
                }
            }

            if (stationsOfInterest.contains(name)) {
                weatherDataController.saveWeatherData(new WeatherData(name, wmocode, phenomenon, airtemperature, windspeed, timestamp));
            }
        }
    }
}
