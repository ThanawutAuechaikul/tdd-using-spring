package com.bank.service.internal;

import com.bank.model.SearchTransactionCriteria;
import com.bank.repository.internal.TransactionRepository;
import com.bank.service.DashboardService;
import com.bank.utils.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DashboardServiceTest {

    @InjectMocks
    @Spy
    private DashboardService service = new DashboardServiceImpl();

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void testGetDashboardSearchCriteria() {


        Date currentDate = new Date();
        Date expectedFromDate = DateUtil.getFirstDate(currentDate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");


        SearchTransactionCriteria searchCriteria = service.getDashboardSearchCriteria("123");
        Assert.assertEquals("123", searchCriteria.getAccountId());
        Assert.assertEquals(expectedFromDate, searchCriteria.getFromDate());
        Assert.assertEquals(sdf.format(currentDate), sdf.format(searchCriteria.getToDate()));
        Assert.assertNull(searchCriteria.getOffset());
        Assert.assertNull(searchCriteria.getLimit());
    }

    @Test
    public void testGetCountTotalTransactions() {
        String accountId = "1";

        SearchTransactionCriteria mockSearchTransactionCriteria = new SearchTransactionCriteria();
        when(service.getDashboardSearchCriteria(accountId)).thenReturn(mockSearchTransactionCriteria);

        doReturn(new Integer(5)).when(transactionRepository).getTotalTransactionHistoryByAccountId(mockSearchTransactionCriteria);

        Integer actualTotal = service.getCountTotalTransactions(accountId);

        Assert.assertEquals(5, actualTotal.intValue());
    }


}
