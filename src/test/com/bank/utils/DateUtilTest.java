package com.bank.utils;

import com.bank.model.SearchTransactionCriteria;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilTest {

    @Test
    public void testGetFirstDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateInString = "1982-08-31 10:20:55";
        Date inputDate = sdf.parse( dateInString );
        Date actualDate = DateUtil.getFirstDate( inputDate );

        Assert.assertEquals("1982-08-01 00:00:00", sdf.format( actualDate ));
    }

}
