package com.bank.controller;

import com.bank.model.SearchTransactionCriteria;
import com.bank.model.TransactionHistoryResult;
import com.bank.model.TransactionSummaryResult;
import com.bank.repository.internal.TransactionRepository;
import com.bank.service.DashboardService;
import com.bank.service.internal.DashboardServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DashboardControllerTest {

    @InjectMocks
    private DashboardController controller;

    @Mock
    private DashboardServiceImpl service;

    @Test
    public void testGetPieChartData()
    {
        String accountId = "1";
        doReturn(new TransactionSummaryResult()).when(service).getPiechartData(accountId);
        controller.getPieChartData(accountId);
        verify( service ).getPiechartData(accountId);
    }

    @Test
    public void testGetTransactionHistory(){
        String accountId = "1";
        int offset = 0;
        int limit = 10;
        doReturn(new TransactionHistoryResult()).when(service).getTransactionHistory(accountId,offset,limit);
        controller.getTransactionHistory(accountId,offset,limit);
        verify(service).getTransactionHistory(accountId,offset,limit);
    }


}
