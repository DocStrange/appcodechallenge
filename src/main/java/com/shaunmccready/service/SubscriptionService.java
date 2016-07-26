package com.shaunmccready.service;

import com.shaunmccready.dto.ResponseDTO;

public interface SubscriptionService {


    /**
     * Creating a brand new subscription
     *
     * @param incomingUrl
     * @return
     */
    public ResponseDTO createSubscription(String incomingUrl);

    /**
     * Cancelling a subscription
     *
     * @param incomingUrl
     * @return
     */
    public ResponseDTO cancelSubscription(String incomingUrl);

    /**
     * Changing a subscription
     *
     * @param imcomingUrl
     * @return
     */
    public ResponseDTO changeSubscription(String imcomingUrl);
}
