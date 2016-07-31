package com.shaunmccready.service.impl;

import com.shaunmccready.dto.EventDTO;
import com.shaunmccready.entity.ErrorCodes;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.service.AppDirectConnectionService;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Service for Connecting to AppDirect's API
 */
@Service
@Transactional(readOnly = true)
public class AppDirectConnectionServiceImpl implements AppDirectConnectionService {


    private static final Logger LOGGER = LoggerFactory.getLogger(AppDirectConnectionServiceImpl.class);

    @Value("${oauth.key}")
    private String oauthKey;

    @Value("${oauth.secret}")
    private String oauthSecret;


    /**
     * Method used to connected to AppDirect and get the details of the event for processing
     *
     * @param incomingUrl
     * @return
     * @throws Exception
     */
    public String getEventDetails(String incomingUrl) throws EventException {
        if(StringUtils.isBlank(incomingUrl)){
            throw new EventException(ErrorCodes.UNKNOWN_ERROR.getErrorCode(), "Missing complete details for user assigning");
        }

        try {
            OAuthConsumer consumer = new DefaultOAuthConsumer(oauthKey, oauthSecret);
            URL urlAppDirect = new URL(incomingUrl);
            HttpURLConnection request = (HttpURLConnection) urlAppDirect.openConnection();
            request.setRequestProperty("Accept", MediaType.APPLICATION_XML_VALUE);
            request.setRequestMethod("GET");
            consumer.sign(request);
            request.connect();


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(request.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (Exception e) {
            LOGGER.info("There was a problem retrieving the information from the url:[" + incomingUrl + "]", e);
            throw new EventException(ErrorCodes.INVALID_RESPONSE.getErrorCode(), "There was a problem retrieving the information from the url:[" + incomingUrl + "]", e);
        }
    }

    public EventDTO getEventDtoFromString(String eventDetails) throws EventException {
        EventDTO value;
        try {
            StringReader stringReader = new StringReader(eventDetails);
            JAXBContext jaxbContext = JAXBContext.newInstance(EventDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement<EventDTO> eventDtoElement = unmarshaller.unmarshal(new StreamSource(stringReader), EventDTO.class);
            value = eventDtoElement.getValue();
        } catch (JAXBException e) {
            LOGGER.info("There was an issue with processing the data. Please try again later." , e);
            throw new EventException(ErrorCodes.UNKNOWN_ERROR.getErrorCode(), "There was an issue with processing the data. Please try again later." , e);
        }

        return value;
    }

}
