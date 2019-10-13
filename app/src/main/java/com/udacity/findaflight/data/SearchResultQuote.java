package com.udacity.findaflight.data;

import java.util.Date;

public class SearchResultQuote {
    private int quoteId;
    private int minPrice;
    private boolean direct;
    private OutboundLeg outboundLeg;
    private Date quoteDateTime;

    public SearchResultQuote(int quoteId, int minPrice, boolean direct, OutboundLeg outboundLeg, Date quoteDateTime) {
        this.quoteId = quoteId;
        this.minPrice = minPrice;
        this.direct = direct;
        this.outboundLeg = outboundLeg;
        this.quoteDateTime = quoteDateTime;
    }

    public int getQuoteId() {
        return quoteId;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public boolean isDirect() {
        return direct;
    }

    public OutboundLeg getOutboundLeg() {
        return outboundLeg;
    }

    public Date getQuoteDateTime() {
        return quoteDateTime;
    }
}
