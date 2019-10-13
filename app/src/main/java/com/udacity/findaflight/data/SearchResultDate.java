package com.udacity.findaflight.data;

import java.util.Date;
import java.util.List;

public class SearchResultDate {

    private Date partialDate;
    private List<Integer> quoteIds;
    private int price;
    private Date quoteDateTime;

    public SearchResultDate(Date partialDate, List<Integer> quoteIds, int price, Date quoteDateTime) {
        this.partialDate = partialDate;
        this.quoteIds = quoteIds;
        this.price = price;
        this.quoteDateTime = quoteDateTime;
    }

    public Date getPartialDate() {
        return partialDate;
    }

    public List<Integer> getQuoteIds() {
        return quoteIds;
    }

    public int getPrice() {
        return price;
    }

    public Date getQuoteDateTime() {
        return quoteDateTime;
    }
}
