package controller;

import com.shaunmccready.controller.SubscriptionController;
import com.shaunmccready.dto.ResponseDTO;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.service.SubscriptionService;
import mock.MockEntities;
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
        responseDTO = MockEntities.getResponseDTO();
    }

    @Test
    public void testCreateSubscription() throws Exception {
        when(subscriptionService.createSubscription(eq(MockEntities.getUrl()))).thenReturn(responseDTO);
        ResponseDTO result = subscriptionController.createSubscription(MockEntities.getUrl());

        verify(subscriptionService, times(1)).createSubscription(eq(MockEntities.getUrl()));
        assertNotNull(result);
    }


    @Test(expected = Exception.class)
    public void testCreateSubscriptionException() throws EventException {
        doThrow(new EventException(anyString(),anyString())).when(subscriptionService.createSubscription(eq(MockEntities.getUrl())));
        subscriptionController.createSubscription(MockEntities.getUrl());
    }


    @Test
    public void testCancelSubscription() throws Exception {
        when(subscriptionService.cancelSubscription(MockEntities.getUrl())).thenReturn(responseDTO);

        ResponseDTO responseDTO = subscriptionController.cancelSubscription(MockEntities.getUrl());

        assertNotNull(responseDTO);
        assertEquals(true,responseDTO.getSuccess());
        assertEquals("123456",responseDTO.getAccountIdentifier());
    }


    @Test
    public void testChangeSubscription() throws Exception {

    }





}