package service;

import com.shaunmccready.dto.AccountDTO;
import com.shaunmccready.dto.ResponseDTO;
import com.shaunmccready.dto.UserDTO;
import com.shaunmccready.service.AccountService;
import com.shaunmccready.service.AppDirectConnectionService;
import com.shaunmccready.service.UserService;
import com.shaunmccready.service.impl.SubscriptionServiceImpl;
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
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionServiceTest {

    @InjectMocks
    private SubscriptionServiceImpl subscriptionServiceImpl;

    @Mock
    private AppDirectConnectionService appDirectConnectionService;

    @Mock
    private AccountService accountService;

    @Mock
    private UserService userService;

    private ResponseDTO successResponseDTO;
    private ResponseDTO failedResponseDTO;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreateSubscription() throws Exception {
        when(appDirectConnectionService.getEventDetails(anyString()))
                .thenReturn(MockEntities.appDirectXmlResponseCreate());
        when(accountService.createAccount(any()))
                .thenReturn(MockEntities.getAccountDTO(MockEntities.ACTIVE_STATUS));
        when(userService.createUser(any(),any()))
                .thenReturn(MockEntities.getUserDTO(MockEntities.getAccountDTO(MockEntities.ACTIVE_STATUS)));


        ResponseDTO subscription = subscriptionServiceImpl.createSubscription(MockEntities.getGoodUrl());
        assertNotNull(subscription);
        assertTrue(subscription.getSuccess());
        assertEquals(subscription.getAccountIdentifier(), "unique-account-id");
        assertEquals(subscription.getMessage(), "Subscription created!");
    }


    @Test
    public void testCancelSubscription() throws Exception {
        when(appDirectConnectionService.getEventDetails(anyString()))
                .thenReturn(MockEntities.appDirectXmlResponseCreate());
        when(accountService.cancelAccount(any()))
                .thenReturn(MockEntities.getAccountDTO(MockEntities.CANCELLED_STATUS));

        ResponseDTO subscription = subscriptionServiceImpl.cancelSubscription(MockEntities.getGoodUrl());
        assertNotNull(subscription);
        assertTrue(subscription.getSuccess());
        assertEquals(subscription.getAccountIdentifier(), "unique-account-id");
        assertEquals(subscription.getMessage(), "Subscription cancelled!");
    }

    @Test
    public void testChangeSubscription() throws Exception {

    }
}