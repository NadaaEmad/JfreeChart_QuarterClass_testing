package org.jfree.data.test;

import org.jfree.data.time.Quarter;
import org.jfree.data.time.TimePeriodFormatException;
import org.jfree.data.time.Year;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class QuarterClassTest {
    Quarter quarter;

    private void arrange(Integer quart, Integer year) {
        quarter = new Quarter(quart, year);
    }

    private void arrange() {
        quarter = new Quarter();
    }
    @Test
    public void testQuarterDefaultCtor() {
        arrange();
        assertEquals(2023, quarter.getYear().getYear());
        System.out.println(quarter.getQuarter());
        assertEquals(2, quarter.getQuarter());
    }

    @Test
    public void testGetQuarter(){
        arrange();
        assertEquals(2, quarter.getQuarter());
    }

    @Test
    public void testGetYear(){
        arrange();
        assertEquals("2023", quarter.getYear().toString());
    }

    @Test
    public void testFirstParametrizedCtr1(){
        arrange(1,2023);
        assertEquals("Q1/2023", quarter.toString());
    }


    @Test(expected = IllegalArgumentException.class) //year out of lower range
    public void testFirstParametrizedCtr2(){
        arrange(1,1800);
    }

    @Test(expected = IllegalArgumentException.class) //year out of upper range
    public void testFirstParametrizedCtr3(){
        arrange(1, 99999);
    }

    @Test //quarter bigger than 4
    public void testFirstParametrizedCtr4(){
       arrange(6, 2000);
        assertNotEquals("Q6/2000", quarter.toString());
    }

    @Test //quarter smaller than 1
    public void testFirstParametrizedCtr5(){
        arrange(-1, 2000);
        assertNotEquals("Q-1/2000", quarter.toString());
    }

    @Test
    public void testSecondParametrizedCtr1(){
        Date time = new Date();
        quarter = new Quarter(time);
        assertEquals("Q2/2023", quarter.toString());
    }
    @Test(expected = IllegalArgumentException.class) //documentation not showing the IllegalArgumentException
    public void testSecondParametrizedCtr2(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1800); //year out of lower range
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 5);
        Date dateRepresentation = cal.getTime();
        quarter = new Quarter(dateRepresentation);
    }

    @Test(expected = IllegalArgumentException.class) //documentation not showing the IllegalArgumentException
    public void testSecondParametrizedCtr3(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 999999); //year out of upper range
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 5);
        Date dateRepresentation = cal.getTime();
        quarter = new Quarter(dateRepresentation);
    }

    @Test
    public void testThirdParametrizedCtr1(){
        Year year = new Year(2018);
        Quarter quarter = new Quarter(3,year);
        assertEquals("Q3/2018", quarter.toString());
    }
    @Test //quarter smaller than 1
    public void testThirdParametrizedCtr2(){
        Year year = new Year(2018);
        Quarter quarter = new Quarter(-1,year);
        assertNotEquals("Q-1/2018", quarter.toString());
    }

    @Test //quarter bigger than 4
    public void testThirdParametrizedCtr3(){
        Year year = new Year(2018);
        Quarter quarter = new Quarter(9,year);
        assertNotEquals("Q9/2018", quarter.toString());
    }

    @Test
    public void testFourthParametrizedCtr1(){
        Date time = new Date();
        TimeZone timeZone = TimeZone.getDefault();
        Quarter quarter = new Quarter(time, timeZone);
        assertEquals("Q2/2023", quarter.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFourthParametrizedCtr2(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 999999); //year out of upper range
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 5);
        Date time = cal.getTime();
        TimeZone timeZone = TimeZone.getDefault();
        Quarter quarter = new Quarter(time, timeZone);
        System.out.println(quarter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFourthParametrizedCtr3(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1800); //year out of lower range
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 5);
        Date time = cal.getTime();
        TimeZone timeZone = TimeZone.getDefault();
        Quarter quarter = new Quarter(time, timeZone);
        System.out.println(quarter);
    }


    @Test //current date
    public void testPrevious1(){
        arrange();
        System.out.println( quarter.previous());
        Quarter prevQuarter = new Quarter(1,2023);
        assertEquals(prevQuarter, quarter.previous());
    }

    @Test //case of the same year
    public void testPrevious2(){
        Quarter prevQuarter = new Quarter(3,1995);
        Quarter newQuarter = new Quarter(4,1995);
        assertEquals(prevQuarter, newQuarter.previous());
    }

    @Test //case of the edge
    public void testPrevious3(){
        Quarter newQuarter = new Quarter(1,1900);
        assertEquals(null, newQuarter.previous());
    }

    @Test //case of a new year
    public void testPrevious4(){
        Quarter prevQuarter = new Quarter(4,1999);
        Quarter newQuarter = new Quarter(1,2000);
        assertEquals(prevQuarter, newQuarter.previous());
    }

    @Test //current time
    public void testNext1(){
        arrange();
        Quarter nextQuarter = new Quarter(3,2023);
        System.out.println(quarter.next());
        assertEquals(nextQuarter, quarter.next());
    }

    @Test //case of the same year
    public void testNext2(){
        Quarter quarter = new Quarter(3,2007);
        Quarter nextQuarter = new Quarter(4,2007);
        System.out.println(quarter.next());
        assertEquals(nextQuarter, quarter.next());
    }

    @Test //case of a new year
    public void testNext3(){
        Quarter quarter = new Quarter(4,2007);
        Quarter nextQuarter = new Quarter(1,2008);
        System.out.println(quarter.next());
        assertEquals(nextQuarter, quarter.next());
    }

    @Test //case of the edge
    public void testNext4(){
        Quarter quarter = new Quarter(4,9999);
        System.out.println(quarter.next());
        assertEquals(null, quarter.next());
    }


    @Test //documentation doesn't show how it is calculated
    public void testSerialIndex(){ //year *4 + quarter
        arrange(1, 2000);
        //System.out.println(quarter.getSerialIndex());
        assertEquals(8001L, quarter.getSerialIndex());
    }

    @Test
    public void testEquals1(){
        arrange();
        Object obj = new Quarter(2,2023);
        assertTrue(quarter.equals(obj));
    }

    @Test //random object
    public void testEquals2(){
        arrange();
        Object obj = 5;
        assertFalse(quarter.equals(obj));
    }

    @Test
    public void testEquals3(){
        arrange();
        Object obj = new Quarter(4,1998);
        assertFalse(quarter.equals(obj));
    }

    @Test //equal self
    public void testEquals4(){
        arrange();
        assertTrue(quarter.equals(quarter));
    }


    @Test
    public void testHashcode(){
        Quarter q1 = new Quarter(2, 2003);
        Quarter q2 = new Quarter(2, 2003);
        assertTrue(q1.equals(q2));
        int h1 = q1.hashCode();
        int h2 = q2.hashCode();
        assertEquals(h1, h2);
        //System.out.println(h1);
        //System.out.println(h2);
    }

    @Test
    public void testCompareTo1(){
        arrange();
        Object newQuarter = new Quarter(3,2023); //current (2) before 3
        int comp = quarter.compareTo(newQuarter);
        System.out.println(comp);
        assertTrue(comp<0); // -ve
    }

    @Test
    public void testCompareTo2(){
        arrange();
        Object newQuarter = new Quarter(1,2023); // current (2) after 1
        int comp = quarter.compareTo(newQuarter);
        System.out.println(comp);
        assertTrue(comp>0); // +ve
    }

    @Test
    public void testCompareTo3(){
        arrange();
        Object newQuarter = new Quarter(2,2023); //same quarter
        int comp = quarter.compareTo(newQuarter);
        System.out.println(comp);
        assertTrue(comp==0);
    }

    @Test
    public void testCompareTo4(){
        arrange();
        Object newQuarter = new Quarter(4,2019); //current (2-2023) after (4-2019)
        int comp = quarter.compareTo(newQuarter);
        System.out.println(comp);
        assertTrue(comp>0); // +ve
    }


    @Test //documentation not showing how the code handles the wrong object case
    public void testCompareTo6(){
        arrange();
        Object o1 = 5; //wrong object
        int comp = quarter.compareTo(o1);
        System.out.println(comp);
        assertEquals(1,comp);
    }

    @Test
    public void testToString1(){
        arrange();
        String str = quarter.toString();
        assertEquals("Q2/2023", str);
    }

    @Test(expected = IllegalArgumentException.class) //wrong year
    public void testToString2(){
        Quarter quarter = new Quarter(2,1800);
        String str = quarter.toString();
        System.out.println(str);
    }

    @Test //wrong quarter
    public void testToString3(){
        Quarter quarter = new Quarter(99,2016);
        String str = quarter.toString();
        System.out.println(str);
        assertNotEquals("Q99/2016", str);
    }

    @Test
    public void testFirstMillisecond(){
        arrange();
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.MONTH, (quarter.getQuarter() - 1) * 3);
        cal.set(Calendar.DATE, 1);

        // Set the time to the first millisecond of the day
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        // Get the first millisecond of the quarter
        long firstMillisecondOfQuarter = cal.getTimeInMillis();
        System.out.println(firstMillisecondOfQuarter);

        Date dateRepresentation = cal.getTime();

        quarter = new Quarter(dateRepresentation);

        long mill = quarter.getFirstMillisecond(cal);
        System.out.println(mill);
        assertEquals(firstMillisecondOfQuarter, mill);
    }

    @Test
    public void testLastMillisecond(){
        arrange();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, (quarter.getQuarter() - 1) * 3);
        cal.set(Calendar.DATE, 1);

        // Set the time to the last millisecond of the day
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        // Get the last millisecond of the quarter
        long LastMillisecondOfQuarter = cal.getTimeInMillis();
        System.out.println(LastMillisecondOfQuarter);

        Date dateRepresentation = cal.getTime();

        quarter = new Quarter(dateRepresentation);

        long mill = quarter.getLastMillisecond(cal);
        System.out.println(mill);
        assertEquals(LastMillisecondOfQuarter, mill);
    }

    @Test // YYYY-QN
    public void testParseQuarter1(){
        String strQuarter = "2016-Q3";
        System.out.println(Quarter.parseQuarter(strQuarter));
        assertEquals("Q3/2016", Quarter.parseQuarter(strQuarter).toString());
    }

    @Test // QN-YYYY
    public void testParseQuarter2(){
        String strQuarter = "Q3-2016";
        System.out.println(Quarter.parseQuarter(strQuarter));
        assertEquals("Q3/2016", Quarter.parseQuarter(strQuarter).toString());
    }

    @Test // QN YYYY
    public void testParseQuarter3(){
        String strQuarter = "Q3 2016";
        System.out.println(Quarter.parseQuarter(strQuarter));
        assertEquals("Q3/2016", Quarter.parseQuarter(strQuarter).toString());
    }

    @Test // YYYY QN
    public void testParseQuarter4(){
        String strQuarter = "2016 Q3";
        System.out.println(Quarter.parseQuarter(strQuarter));
        assertEquals("Q3/2016", Quarter.parseQuarter(strQuarter).toString());
    }

    @Test // YYYY/QN
    public void testParseQuarter5(){
        String strQuarter = "2016/Q3";
        System.out.println(Quarter.parseQuarter(strQuarter));
        assertEquals("Q3/2016", Quarter.parseQuarter(strQuarter).toString());
    }

    @Test // QN/YYYY
    public void testParseQuarter6(){
        String strQuarter = "Q3/2016";
        System.out.println(Quarter.parseQuarter(strQuarter));
        assertEquals("Q3/2016", Quarter.parseQuarter(strQuarter).toString());
    }

    @Test(expected = TimePeriodFormatException.class) // wrong input : Documentation not showing this case
    public void testParseQuarter7(){
        String strQuarter = "Q3*2016";
        System.out.println(Quarter.parseQuarter(strQuarter));
    }








}
