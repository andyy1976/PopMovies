package com.example.popmovies.utilities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppUtils {
    private static final String DEFAULT_UI_DATE_FORMAT = "MMMM yyyy";
    private static final String DEFAULT_DB_DATE_FORMAT = "yyyy-MM-dd";

    public static Date parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DB_DATE_FORMAT, Locale.getDefault());
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDate(String releaseDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_UI_DATE_FORMAT, Locale.getDefault());
        Date releaseDate = parseDate(releaseDateStr);
        return sdf.format(releaseDate);
    }

    public static String formatDouble(double value) {
        NumberFormat nf = new DecimalFormat("##.#", DecimalFormatSymbols.getInstance(Locale.US));
        return nf.format(value);
    }

    public static String formatLanguage(String language) {
        Locale locale = new Locale(language);
        return locale.getDisplayLanguage(Locale.ENGLISH);
    }
}
