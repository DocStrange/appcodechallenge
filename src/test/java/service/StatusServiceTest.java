package service;

import com.shaunmccready.dto.StatusDTO;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.mapper.StatusMapper;
import com.shaunmccready.repository.StatusDao;
import com.shaunmccready.service.impl.StatusServiceImpl;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatusServiceTest {

    @InjectMocks
    private StatusServiceImpl statusServiceImpl;

    @Mock
    private StatusDao statusDao;

    @Mock
    private StatusMapper statusMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetStatus() throws EventException {
        when(statusDao.findOne(anyInt())).thenReturn(MockEntities.getStatus(MockEntities.ACTIVE_STRING));
        when(statusMapper.bindDTO(any())).thenReturn(MockEntities.getStatusDTO(MockEntities.ACTIVE_STRING));

        StatusDTO result = statusServiceImpl.getStatus(anyInt());
        assertNotNull(result);
        assertEquals(Integer.valueOf(5), result.getId());
        assertEquals("ACTIVE", result.getName());
    }
}
