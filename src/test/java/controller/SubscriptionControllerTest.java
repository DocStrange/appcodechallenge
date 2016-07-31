package controller;

import com.shaunmccready.controller.SubscriptionController;
import com.shaunmccready.dto.ResponseDTO;
import com.shaunmccready.service.SubscriptionService;
import mock.MockEntities;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionControllerTest {

    @InjectMocks
    private SubscriptionController subscriptionController;

    @Mock
    private SubscriptionService subscriptionService;

    private ResponseDTO successResponseDTO;
    private ResponseDTO failedResponseDTO;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        successResponseDTO = MockEntities.getSuccessResponseDTO();
        failedResponseDTO = MockEntities.getFailedResponseDTO();
    }

    @Test
    public void testCreateSubscription() throws Exception {
        when(subscriptionService.createSubscription(anyString())).thenReturn(successResponseDTO);
        ResponseDTO result = subscriptionController.createSubscription(anyString());

        verify(subscriptionService, times(1)).createSubscription(anyString());
        assertNotNull(result);
        assertTrue(result.getSuccess());
    }

    @Test
    public void testCreateSubscriptionBad() throws Exception {
        when(subscriptionService.createSubscription(anyString())).thenReturn(failedResponseDTO);
        ResponseDTO result = subscriptionController.createSubscription(anyString());

        verify(subscriptionService, times(1)).createSubscription(anyString());
        assertNotNull(result);
        assertFalse(result.getSuccess());
    }

    @Test
    public void testCancelSubscription() throws Exception {
        when(subscriptionService.cancelSubscription(anyString())).thenReturn(successResponseDTO);
        ResponseDTO result = subscriptionController.cancelSubscription(anyString());

        verify(subscriptionService, times(1)).cancelSubscription(anyString());
        assertNotNull(result);
        assertTrue(result.getSuccess());
    }

    @Test
    public void testCancelSubscriptionBad() throws Exception {
        when(subscriptionService.cancelSubscription(anyString())).thenReturn(failedResponseDTO);
        ResponseDTO result = subscriptionController.cancelSubscription(anyString());

        verify(subscriptionService, times(1)).cancelSubscription(anyString());
        assertNotNull(result);
        assertFalse(result.getSuccess());
    }





}