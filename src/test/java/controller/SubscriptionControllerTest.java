package controller;

import com.shaunmccready.controller.SubscriptionController;
import com.shaunmccready.dto.ResponseDTO;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.service.SubscriptionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionControllerTest {

    @InjectMocks
    private SubscriptionController subscriptionController;

    @Mock
    private SubscriptionService subscriptionService;

    private ResponseDTO responseDTO;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        responseDTO = getResponseDTO();
    }

    @Test
    public void testCreateSubscription() throws Exception {
        when(subscriptionService.createSubscription(eq(getUrl()))).thenReturn(responseDTO);
        ResponseDTO result = subscriptionController.createSubscription(getUrl());

        verify(subscriptionService, times(1)).createSubscription(eq(getUrl()));
        assertNotNull(result);
    }


    @Test(expected = Exception.class)
    public void testCreateSubscriptionException() throws EventException {
        when(subscriptionService.createSubscription(eq(getUrl())))
                .thenThrow(new EventException(anyString(),anyString()));

        subscriptionController.createSubscription(getUrl());
    }


    @Test
    public void testCancelSubscription() throws Exception {
        when(subscriptionService.cancelSubscription(getUrl())).thenReturn(responseDTO);

        ResponseDTO responseDTO = subscriptionController.cancelSubscription(getUrl());

        assertNotNull(responseDTO);
        assertEquals(true,responseDTO.getSuccess());
        assertEquals("123456",responseDTO.getAccountIdentifier());
    }


    @Test
    public void testChangeSubscription() throws Exception {

    }


    public String getUrl(){
        return "https://www.testurl.com";
    }


    public ResponseDTO getResponseDTO(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setAccountIdentifier("123456");

        return responseDTO;
    }


}