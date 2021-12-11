package connector;

import dto.Currency;
import dto.CurrencyValue;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrencyApiConnector {

    private static final String URL = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/";

    public List<Currency> getAll() {

        List<Currency> result = new ArrayList<>();

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(URL + "latest/currencies.json"))
                    .GET()
                    .build();


            HttpResponse<String> httpResponse = HttpClient.newHttpClient()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(httpResponse.body());
            jsonObject.keys().forEachRemaining(s -> {
                Currency currency = new Currency();
                currency.setCode(s);
                currency.setName(jsonObject.getString(s));

                result.add(currency);
            });

            System.out.println(result);

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<CurrencyValue> getAll(String currencyCode) {
        return getAll(currencyCode, new Date());
    }

    public List<CurrencyValue> getAll(String currencyCode, Date date) {
        List<CurrencyValue> result = new ArrayList<>();

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(date);
            String currDate = dateFormat.format(new Date());

            URI uri;
            if (strDate.equals(currDate)) {
                uri = new URI(URL + "latest/currencies/" + currencyCode + ".json");
            } else {
                uri = new URI(URL + strDate + "/currencies/" + currencyCode + ".json");
            }

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();


            HttpResponse<String> httpResponse = HttpClient.newHttpClient()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(httpResponse.body());
            jsonObject = jsonObject.getJSONObject(currencyCode);
            JSONObject finalJsonObject = jsonObject;
            jsonObject.keys().forEachRemaining(s -> {
                CurrencyValue currency = new CurrencyValue();
                currency.setBaseCurrencyCode(currencyCode);
                currency.setCurrencyCode(s);
                try {
                    currency.setDate(dateFormat.parse(strDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                currency.setValue(finalJsonObject.getBigDecimal(s));

                result.add(currency);
            });

            System.out.println(result);

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
