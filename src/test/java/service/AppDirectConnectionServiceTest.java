package service;

import com.shaunmccready.dto.EventDTO;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.service.impl.AppDirectConnectionServiceImpl;
import mock.MockEntities;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class AppDirectConnectionServiceTest {

    @InjectMocks
    private AppDirectConnectionServiceImpl appDirectConnectionServiceImpl;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testEventDtoFromString() throws EventException {
        EventDTO eventDtoFromString = appDirectConnectionServiceImpl.getEventDtoFromString(MockEntities.appDirectXmlResponseCreate());
        assertNotNull(eventDtoFromString);
    }


}
