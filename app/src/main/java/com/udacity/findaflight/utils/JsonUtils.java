package com.udacity.findaflight.utils;

import com.udacity.findaflight.data.OutboundLeg;
import com.udacity.findaflight.data.SearchResultCarrier;
import com.udacity.findaflight.data.SearchResultCurrency;
import com.udacity.findaflight.data.SearchResultDate;
import com.udacity.findaflight.data.SearchResultPlace;
import com.udacity.findaflight.data.SearchResultQuote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.udacity.findaflight.utils.DateUtils.getDateFromString;
import static com.udacity.findaflight.utils.DateUtils.getDateTimeFromString;

public final class JsonUtils {

    public static List getFlightsByPriceFromJson(String jsonStr) throws JSONException, ParseException {
        List<SearchResultDate> searchResultDates = new ArrayList<>();
        List<SearchResultQuote> searchResultQuotes = new ArrayList<>();
        List<SearchResultPlace> searchResultPlaces = new ArrayList<>();
        List<SearchResultCarrier> searchResultCarriers = new ArrayList<>();
        List<SearchResultCurrency> searchResultCurrencies = new ArrayList<>();

        JSONObject flightResult = new JSONObject(jsonStr);

        getDates(searchResultDates, flightResult);
        getQuotes(searchResultQuotes, flightResult);
        getPlaces(searchResultPlaces, flightResult);
        getCarriers(searchResultCarriers, flightResult);
        getCurrencies(searchResultCurrencies, flightResult);

        return null;
    }

    private static void getCurrencies(List<SearchResultCurrency> searchResultCurrencies, JSONObject flightResult) throws JSONException {
        JSONArray currencies = flightResult.getJSONArray("Currencies");
        for (int i = 0; i < currencies.length(); i++) {
            JSONObject currency = currencies.getJSONObject(i);

            String code = currency.getString("Code");
            String symbol = currency.getString("Symbol");
            String thousandsSeparator = currency.getString("ThousandsSeparator");
            String decimalSeparator = currency.getString("DecimalSeparator");
            boolean symbolOnLeft = currency.getBoolean("SymbolOnLeft");
            boolean spaceBetweenAmountAndSymbol = currency.getBoolean("SpaceBetweenAmountAndSymbol");
            int roundingCoefficient = currency.getInt("RoundingCoefficient");
            int decimalDigits = currency.getInt("DecimalDigits");

            searchResultCurrencies.add(new SearchResultCurrency(code, symbol, thousandsSeparator, decimalSeparator, symbolOnLeft, spaceBetweenAmountAndSymbol, roundingCoefficient, decimalDigits));
        }
    }

    private static void getCarriers(List<SearchResultCarrier> searchResultCarriers, JSONObject flightResult) throws JSONException {
        JSONArray carriers = flightResult.getJSONArray("Carriers");
        for (int i = 0; i < carriers.length(); i++) {
            JSONObject carrier = carriers.getJSONObject(i);
            int carrierId = carrier.getInt("CarrierId");
            String name = carrier.getString("Name");

            searchResultCarriers.add(new SearchResultCarrier(carrierId, name));
        }
    }

    private static void getPlaces(List<SearchResultPlace> searchResultPlaces, JSONObject flightResult) throws JSONException {
        JSONArray places = flightResult.getJSONArray("Places");
        for (int i = 0; i < places.length(); i++) {
            JSONObject place = places.getJSONObject(i);

            int placeId = place.getInt("PlaceId");
            String iataCode = place.getString("IataCode");
            String name = place.getString("Name");
            String type = place.getString("Type");
            String skyscannerCode = place.getString("SkyscannerCode");
            String cityName = place.getString("CityName");
            String cityId = place.getString("CityId");
            String countryName = place.getString("CountryName");

            searchResultPlaces.add(new SearchResultPlace(placeId, iataCode, name, type, skyscannerCode, cityName, cityId, countryName));
        }
    }

    private static void getQuotes(List<SearchResultQuote> searchResultQuotes, JSONObject flightResult) throws JSONException, ParseException {
        JSONArray quotes = flightResult.getJSONArray("Quotes");
        for (int i = 0; i < quotes.length(); i++) {
            JSONObject quote = quotes.getJSONObject(i);

            int quoteId = quote.getInt("QuoteId");
            int minPrice = quote.getInt("MinPrice");
            boolean isDirect = quote.getBoolean("Direct");

            JSONObject outboundLegJSONObj = quote.getJSONObject("OutboundLeg");
            JSONArray carrierIds = outboundLegJSONObj.getJSONArray("CarrierIds");
            List<Integer> carrierIdsList = new ArrayList<>();
            for (int j = 0; j < carrierIds.length(); j++) {
                carrierIdsList.add((Integer) carrierIds.get(j));
            }
            int originId = outboundLegJSONObj.getInt("OriginId");
            int destinationId = outboundLegJSONObj.getInt("DestinationId");
            Date departureDate = getDateTimeFromString(outboundLegJSONObj.getString("DepartureDate"));

            OutboundLeg outboundLeg = new OutboundLeg(carrierIdsList, originId, destinationId, departureDate);

            Date quoteDateTime = getDateTimeFromString(quote.getString("QuoteDateTime"));

            searchResultQuotes.add(new SearchResultQuote(quoteId, minPrice, isDirect, outboundLeg, quoteDateTime));
        }
    }

    private static void getDates(List<SearchResultDate> searchResultDates, JSONObject flightResult) throws JSONException, ParseException {
        JSONArray dates = flightResult.getJSONObject("Dates").getJSONArray("OutboundDates");
        for (int i = 0; i < dates.length(); i++) {
            JSONObject outboundDate = dates.getJSONObject(i);
            Date partialDate = getDateFromString(outboundDate.getString("PartialDate"));

            JSONArray quoteIds = outboundDate.getJSONArray("QuoteIds");
            List<Integer> quoteIdsList = new ArrayList<>();
            for (int j = 0; j < quoteIds.length(); j++) {
                quoteIdsList.add((Integer) quoteIds.get(j));
            }

            int price = outboundDate.getInt("Price");
            Date quoteDateTime = getDateTimeFromString(outboundDate.getString("QuoteDateTime"));

            searchResultDates.add(new SearchResultDate(partialDate, quoteIdsList, price, quoteDateTime));
        }
    }
}
