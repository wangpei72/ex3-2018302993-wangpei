package client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangpei
 */
public class test {
    public static void main(String args[]) throws IOException, ParseException {
        Date date = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy/MM/dd/HH:mm");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String test = bufferedReader.readLine();
        date = myFmt.parse(test);
        Date date1 = new Date(date.getTime());
        System.out.println(date.toString());
    }
}
