import connector.CurrencyApiConnector;
import dto.Currency;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Application {

    public static void main(String[] args) throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2021-12-01");

        CurrencyApiConnector currencyApiConnector = new CurrencyApiConnector();
        currencyApiConnector.getAll();
        currencyApiConnector.getAll("eur");
        currencyApiConnector.getAll("eur", date);

        System.out.println("");

        HttpRequest httpRequest =  HttpRequest.newBuilder()
                .uri(new URI("https://api.exchangerate.host/latest"))
                .GET().build();

        HttpResponse<String> httpResponse = HttpClient.newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(httpResponse.body());
        JSONObject motd =  jsonObject.getJSONObject("motd");
        Boolean success = jsonObject.getBoolean("success");
        String base = jsonObject.getString("base");
        String date2 = jsonObject.getString("date");
        JSONObject rates = jsonObject.getJSONObject("rates");
        rates.keys().forEachRemaining(s  -> {
            BigDecimal value = rates.getBigDecimal(s);

            System.out.println(s + " -> " + value);
        });

        // System.out.println(httpResponse.body());

        Currency currency = new Currency();
        currency.setName("European Euro");
        currency.setCode("EUR");

        Currency currency1 = new Currency();
        currency1.setName("polish zloty");
        currency1.setCode("PLN");

        List<Currency> currencyList = new ArrayList<>();
        currencyList.add(currency);
        currencyList.add(currency1);

        JSONObject jsonObject1 = new JSONObject(currency);
        String json = jsonObject1.toString();

        System.out.println(json);

        JSONArray jsonObject2 = new JSONArray(currencyList);
        json = jsonObject2.toString();

        System.out.println(json);
    }
}
