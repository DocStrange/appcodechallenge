package com.shaunmccready.dto;


public class EventDTO {


    private String type;

    private MarketplaceDTO marketplace;

    private CreatorDTO creator;

    private PayloadDTO payload;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MarketplaceDTO getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(MarketplaceDTO marketplace) {
        this.marketplace = marketplace;
    }

    public CreatorDTO getCreator() {
        return creator;
    }

    public void setCreator(CreatorDTO creator) {
        this.creator = creator;
    }

    public PayloadDTO getPayload() {
        return payload;
    }

    public void setPayload(PayloadDTO payload) {
        this.payload = payload;
    }


    /**
     * Helper method to make sure there are no null fields which cause exceptions
     *
     * @param dtos
     * @return
     */
    public static boolean eventDTOContainsNulls(Object... dtos) {
        for(Object obj : dtos){
            if(null == obj){
                return true;
            }
        }
        return false;
    }
}
