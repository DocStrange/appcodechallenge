package service;

import com.shaunmccready.dto.ResponseDTO;
import com.shaunmccready.dto.UserDTO;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.mapper.UserMapper;
import com.shaunmccready.repository.AccountDao;
import com.shaunmccready.repository.UserDao;
import com.shaunmccready.service.AppDirectConnectionService;
import com.shaunmccready.service.impl.UserServiceImpl;
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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserDao userDao;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AccountDao accountDao;

    @Mock
    private AppDirectConnectionService appDirectConnectionService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreateUser() throws EventException {
        when(userMapper.bindDTO(any()))
                .thenReturn(MockEntities.getUserDTO(MockEntities.getAccountDTO(MockEntities.CANCELLED_STATUS)));
        when(userDao.save(MockEntities.getUser(MockEntities.getAccount(MockEntities.CANCELLED_STRING))))
                .thenReturn(MockEntities.getUser(MockEntities.getAccount(MockEntities.CANCELLED_STRING)));
        when(userServiceImpl.checkExistingInactiveUser(MockEntities.getCreatedEventDTO(MockEntities.CANCELLED_STATUS)))
                .thenReturn(MockEntities.getUser(MockEntities.getAccount(MockEntities.CANCELLED_STRING)));
        when(accountDao.findOne(anyInt()))
                .thenReturn(MockEntities.getAccount(MockEntities.CANCELLED_STRING));
        when(userDao.findByUuid(MockEntities.CANCELLED_STRING))
                .thenReturn(MockEntities.getUser(MockEntities.getAccount(MockEntities.CANCELLED_STRING)));

        UserDTO result = userServiceImpl.createUser(MockEntities.getCreatedEventDTO(MockEntities.ACTIVE_STATUS), MockEntities.getAccountDTO(MockEntities.CANCELLED_STATUS));
        assertNotNull(result);
    }


    @Test
    public void testAssignUser() throws EventException {
        when(appDirectConnectionService.getEventDetails(anyString()))
                .thenReturn(MockEntities.appDirectXmlResponseAssign());
        when(appDirectConnectionService.getEventDtoFromString(anyString()))
                .thenReturn(MockEntities.getCreatedEventDTO(MockEntities.ACTIVE_STATUS));
        when(accountDao.findByAccountIdentifierIgnoreCase(anyString()))
                .thenReturn(MockEntities.getAccount(MockEntities.ACTIVE_STRING));
        when(userDao.findByUuid(anyString()))
                .thenReturn(MockEntities.getUser(MockEntities.getAccount(MockEntities.ACTIVE_STRING)));


        ResponseDTO result = userServiceImpl.assignUser(MockEntities.getGoodUrl());
        assertNotNull(result);
        assertEquals("User assigned!", result.getMessage());
    }


    @Test
    public void testUnassignUser() throws EventException {
        when(appDirectConnectionService.getEventDetails(anyString()))
                .thenReturn(MockEntities.appDirectXmlResponseAssign());
        when(appDirectConnectionService.getEventDtoFromString(anyString()))
                .thenReturn(MockEntities.getCreatedEventDTO(MockEntities.ACTIVE_STATUS));
        when(accountDao.findByAccountIdentifierIgnoreCase(anyString()))
                .thenReturn(MockEntities.getAccount(MockEntities.ACTIVE_STRING));
        when(userDao.findByUuid(anyString()))
                .thenReturn(MockEntities.getUser(MockEntities.getAccount(MockEntities.ACTIVE_STRING)));


        ResponseDTO result = userServiceImpl.unassignUser(MockEntities.getGoodUrl());
        assertNotNull(result);
        assertEquals("User unassigned!", result.getMessage());
    }
}
