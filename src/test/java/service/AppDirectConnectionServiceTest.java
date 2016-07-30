package service;

import com.shaunmccready.exception.EventException;
import com.shaunmccready.service.AppDirectConnectionService;
import com.shaunmccready.service.impl.AppDirectConnectionServiceImpl;
import jdk.nashorn.internal.ir.annotations.Ignore;
import mock.MockEntities;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppDirectConnectionServiceTest {

    @InjectMocks
    private AppDirectConnectionServiceImpl appDirectConnectionServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void testGetEventDetails() throws EventException {
//        when(appDirectConnectionServiceImpl.getEventDetails(MockEntities.getGoodUrl())).thenReturn(MockEntities.appDirectXmlResponseCreate());
//
//        //todo:finish
//       String result = appDirectConnectionServiceImpl.getEventDetails(MockEntities.getGoodUrl());

    }


}
