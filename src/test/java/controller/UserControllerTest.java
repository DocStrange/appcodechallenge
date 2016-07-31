package controller;

import com.shaunmccready.controller.UserController;
import com.shaunmccready.dto.ResponseDTO;
import com.shaunmccready.service.UserService;
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
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private ResponseDTO successResponseDTO;
    private ResponseDTO failedResponseDTO;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        successResponseDTO = MockEntities.getSuccessResponseDTO();
        failedResponseDTO = MockEntities.getFailedResponseDTO();
    }


    @Test
    public void testAssignUser() throws Exception {
        when(userService.assignUser(anyString())).thenReturn(successResponseDTO);
        ResponseDTO result = userController.assignUser(anyString());

        verify(userService, times(1)).assignUser(anyString());
        assertNotNull(result);
        assertTrue(result.getSuccess());

    }

    @Test
    public void testAssignUserBad() throws Exception {
        when(userService.assignUser(anyString())).thenReturn(failedResponseDTO);
        ResponseDTO result = userController.assignUser(anyString());

        verify(userService, times(1)).assignUser(anyString());
        assertNotNull(result);
        assertFalse(result.getSuccess());

    }

    @Test
    public void testUnassignUser() throws Exception {
        when(userService.unassignUser(anyString())).thenReturn(successResponseDTO);
        ResponseDTO result = userController.unassignUser(anyString());

        verify(userService, times(1)).unassignUser(anyString());
        assertNotNull(result);
        assertTrue(result.getSuccess());

    }

    @Test
    public void testUnassignUserBad() throws Exception {
        when(userService.unassignUser(anyString())).thenReturn(failedResponseDTO);
        ResponseDTO result = userController.unassignUser(anyString());

        verify(userService, times(1)).unassignUser(anyString());
        assertNotNull(result);
        assertFalse(result.getSuccess());

    }
}
