package com.bank.service.internal;

import com.bank.model.SearchTransactionCriteria;
import com.bank.service.DashboardService;
import com.bank.utils.DateUtil;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DashboardServiceTest {

    private DashboardService service = new DashboardServiceImpl();

    @Test
    public void testGetPieChartSearchHistoryCriteria() {


        Date currentDate = new Date();
        Date expectedFromDate = DateUtil.getFirstDate( currentDate );

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");


        SearchTransactionCriteria searchCriteria = service.getDashboardSearchCriteria("123");
        Assert.assertEquals("123", searchCriteria.getAccountId());
        Assert.assertEquals(expectedFromDate, searchCriteria.getFromDate());
        Assert.assertEquals(sdf.format(currentDate), sdf.format(searchCriteria.getToDate()));
        Assert.assertNull(searchCriteria.getOffset());
        Assert.assertNull(searchCriteria.getLimit());
    }




}
