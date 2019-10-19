package com.udacity.findaflight.utils;

import com.udacity.findaflight.data.FlightRoute;
import com.udacity.findaflight.data.FlightSearchResult;
import com.udacity.findaflight.data.ParcelableArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.udacity.findaflight.utils.DateUtils.getDateTimeFromUnixTime;


public final class JsonUtils {

    public static List getFlightsByPriceFromJson(String jsonStr) throws JSONException, ParseException {
        List searchResults = new ArrayList();

        JSONObject searchResult = new JSONObject(jsonStr);
        JSONArray flights = searchResult.getJSONArray("data");

        for (int i = 0; i < flights.length(); i++) {
            JSONObject flight = flights.getJSONObject(i);

            String idsString = flight.getString("id");
            String[] ids = idsString.split("\\|");

            int price = flight.getInt("price");

            List<FlightRoute> outboundRoutes = new ArrayList<>();
            List<FlightRoute> inboundRoutes = new ArrayList<>();
            JSONArray routeJsonArr = flight.getJSONArray("route");
            for (int routeJsonArrIdx = 0; routeJsonArrIdx < routeJsonArr.length(); routeJsonArrIdx++) {
                JSONObject route = routeJsonArr.getJSONObject(routeJsonArrIdx);

                String routeId = route.getString("id");

                // Unix Time
                Date departureDateTime = getDateTimeFromUnixTime(route.getLong("dTime"));
                Date arrivalDateTime = getDateTimeFromUnixTime(route.getLong("aTime"));

                Date departureDateTimeUTC = getDateTimeFromUnixTime(route.getLong("dTimeUTC"));
                Date arrivalDateTimeUTC = getDateTimeFromUnixTime(route.getLong("aTimeUTC"));

                String arrivalCity = route.getString("cityTo");
                String departureCity = route.getString("cityFrom");

                // IATA Airport
                String arrivalAirport = route.getString("flyTo");
                String departureAirport = route.getString("flyFrom");
                String airline = route.getString("airline");
                String operatingCarrier = route.getString("operating_carrier");
                int flightNum = route.getInt("flight_no");
                String operatingFlightNum = route.getString("operating_flight_no");

                String fareClasses = route.getString("fare_classes");
                String fareBasis = route.getString("fare_basis");
                String fareFamily = route.getString("fare_family");
                String fareCategory = route.getString("fare_category");

                boolean isReturnRoute = route.getInt("return") == 1;

                FlightRoute flightRoute = new FlightRoute(routeId,
                        departureDateTime, arrivalDateTime,
                        departureDateTimeUTC, arrivalDateTimeUTC,
                        departureCity, arrivalCity,
                        departureAirport, arrivalAirport,
                        airline, operatingCarrier,
                        flightNum, operatingFlightNum,
                        fareClasses, fareBasis, fareFamily, fareCategory, isReturnRoute);

                if (isReturnRoute) {
                    inboundRoutes.add(flightRoute);
                } else {
                    outboundRoutes.add(flightRoute);
                }
            }

            List airlinesList = new ArrayList();
            JSONArray airlinesJsonArr = flight.getJSONArray("airlines");
            for (int airlinesJsonArrIdx = 0; airlinesJsonArrIdx < airlinesJsonArr.length(); airlinesJsonArrIdx++) {
                airlinesList.add(airlinesJsonArr.getString(airlinesJsonArrIdx));
            }

            List transfersList = new ArrayList();
            JSONArray transfersJsonArr = flight.getJSONArray("transfers");
            for (int transfersJsonArrIdx = 0; transfersJsonArrIdx < transfersJsonArr.length(); transfersJsonArrIdx++) {
                transfersList.add(transfersJsonArr.getString(transfersJsonArrIdx));
            }

            boolean hasAirportChange = flight.getBoolean("has_airport_change");

            // unix time in the time zone of the departure airport
            Date departureDateTime = getDateTimeFromUnixTime(flight.getLong("dTime"));
            Date arrivalDateTime = getDateTimeFromUnixTime(flight.getLong("aTime"));

            Date departureDateTimeUTC = getDateTimeFromUnixTime(flight.getLong("dTimeUTC"));
            Date arrivalDateTimeUTC = getDateTimeFromUnixTime(flight.getLong("aTimeUTC"));

            // IATA code of airport
            String departureAirport = flight.getString("flyFrom");
            String arrivalAirport = flight.getString("flyTo");
            String departureCity = flight.getString("cityFrom");
            String arrivalCity = flight.getString("cityTo");

            JSONObject countryFrom = flight.getJSONObject("countryFrom");
            String countryFromCode = countryFrom.getString("code");
            String countryFromName = countryFrom.getString("name");

            JSONObject countryTo = flight.getJSONObject("countryTo");
            String countryToCode = countryTo.getString("code");
            String countryToName = countryTo.getString("name");

            List<ParcelableArrayList> routesList = new ArrayList<>();
            JSONArray routesJSONArr = flight.getJSONArray("routes");
            for (int routeIdx = 0; routeIdx < routesJSONArr.length(); routeIdx++) {
                ParcelableArrayList route = new ParcelableArrayList();
                JSONArray routeJSONArr = routesJSONArr.getJSONArray(routeIdx);
                for (int routeJSONArrIdx = 0; routeJSONArrIdx < routeJSONArr.length(); routeJSONArrIdx++) {
                    route.add(routeJSONArr.getString(routeJSONArrIdx));
                }
                routesList.add(route);
            }

            String outboundFlightDuration = flight.getString("fly_duration");
            String inboundFlightDuration = flight.getString("return_duration");

            String linkToKiwi = flight.getString("deep_link");
            searchResults.add(new FlightSearchResult(ids, price,
                    outboundRoutes, inboundRoutes,
                    airlinesList, transfersList, hasAirportChange,
                    departureDateTime, arrivalDateTime,
                    departureDateTimeUTC, arrivalDateTimeUTC,
                    departureAirport, arrivalAirport,
                    departureCity, arrivalCity,
                    countryFromCode, countryFromName,
                    countryToCode, countryToName,
                    routesList,
                    outboundFlightDuration, inboundFlightDuration, linkToKiwi));
        }
        return searchResults;
    }
}
