package service;

import com.shaunmccready.dto.UserDTO;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.mapper.UserMapper;
import com.shaunmccready.repository.AccountDao;
import com.shaunmccready.repository.UserDao;
import com.shaunmccready.service.impl.UserServiceImpl;
import mock.MockEntities;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void createUserTest() throws EventException {
        when(userMapper.bindDTO(any()))
                .thenReturn(MockEntities.getUserDTO(MockEntities.getAccountDTO(MockEntities.CANCELLED_STATUS)));
        when(userDao.save(MockEntities.getUser(MockEntities.getAccount(MockEntities.CANCELLED_STRING))))
                .thenReturn(MockEntities.getUser(MockEntities.getAccount(MockEntities.CANCELLED_STRING)));
        when(userServiceImpl.checkExistingUser(MockEntities.getCreatedEventDTO(MockEntities.CANCELLED_STATUS)))
                .thenReturn(MockEntities.getUser(MockEntities.getAccount(MockEntities.CANCELLED_STRING)));
        when(accountDao.findOne(anyInt()))
                .thenReturn(MockEntities.getAccount(MockEntities.CANCELLED_STRING));
        when(userDao.findByUuid(MockEntities.CANCELLED_STRING))
                .thenReturn(MockEntities.getUser(MockEntities.getAccount(MockEntities.CANCELLED_STRING)));


        UserDTO user = userServiceImpl.createUser(MockEntities.getCreatedEventDTO(MockEntities.ACTIVE_STATUS), MockEntities.getAccountDTO(MockEntities.CANCELLED_STATUS));

        assertNotNull(user);

    }

}
