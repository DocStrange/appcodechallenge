package service;

import com.shaunmccready.dto.ResponseDTO;
import com.shaunmccready.service.AccountService;
import com.shaunmccready.service.AppDirectConnectionService;
import com.shaunmccready.service.SubscriptionService;
import com.shaunmccready.service.UserService;
import com.shaunmccready.service.impl.SubscriptionServiceImpl;
import mock.MockEntities;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SubscriptionServiceTest {

    @InjectMocks
    private SubscriptionServiceImpl subscriptionServiceImpl;

    @Mock
    private AppDirectConnectionService appDirectConnectionService;

    @Mock
    private AccountService accountService;

    @Mock
    private UserService userService;

    private ResponseDTO responseDTO;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        responseDTO = MockEntities.getResponseDTO();
    }


    @Test
    public void testCreateSubscription() throws Exception {
        when(appDirectConnectionService.getEventDetails(eq(MockEntities.getUrl()))).thenReturn(MockEntities.appDirectXmlResponseCreate());
        //verify(appDirectConnectionService, times(1)).getEventDetails(eq(MockEntities.getUrl()));
//        when(accountService.createAccount(eq(MockEntities.))).thenReturn(MockEntities.getAccount("ACTIVE"));


        ResponseDTO subscription = subscriptionServiceImpl.createSubscription(MockEntities.getUrl());

        assertNotNull(subscription);

    }

    @Test
    public void testCancelSubscription() throws Exception {

    }

    @Test
    public void testChangeSubscription() throws Exception {

    }
}