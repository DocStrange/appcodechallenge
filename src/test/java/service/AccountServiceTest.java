package service;

import com.shaunmccready.dto.AccountDTO;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.mapper.AccountMapper;
import com.shaunmccready.repository.AccountDao;
import com.shaunmccready.repository.StatusDao;
import com.shaunmccready.repository.UserDao;
import com.shaunmccready.service.UserService;
import com.shaunmccready.service.impl.AccountServiceImpl;
import mock.MockEntities;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    @Mock
    private AccountDao accountDao;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private StatusDao statusDao;

    @Mock
    private UserService userService;

    @Mock
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreateAccount() throws EventException {
        when(userService.checkExistingUser(any())).thenReturn(MockEntities.getUser(MockEntities.getAccount(MockEntities.ACTIVE_STRING)));
        when(statusDao.findByName(MockEntities.ACTIVE_STRING)).thenReturn(MockEntities.getStatus(MockEntities.ACTIVE_STRING));
        when(accountDao.save(MockEntities.getAccount(MockEntities.ACTIVE_STRING))).thenReturn(MockEntities.getAccount(MockEntities.ACTIVE_STRING));
        when(accountMapper.bindDTO(any())).thenReturn(MockEntities.getAccountDTO(MockEntities.ACTIVE_STATUS));

        AccountDTO result = accountServiceImpl.createAccount(MockEntities.getCreatedEventDTO(MockEntities.ACTIVE_STATUS));

        assertNotNull(result);
        assertEquals(result.getPricingDuration(), "MONTHLY");
        assertEquals(result.getEditionCode(), "FREE");
        assertEquals(result.getAccountIdentifier(), "unique-account-id");
        assertEquals(result.getNumberOfUsers(), Integer.valueOf(-1));
    }

}
