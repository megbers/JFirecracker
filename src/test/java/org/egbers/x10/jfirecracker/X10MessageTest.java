package org.egbers.x10.jfirecracker;

import au.com.bytecode.opencsv.CSVReader;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStreamReader;
import java.util.List;


public class X10MessageTest {
    @Test
    public void shouldReturnCorrectBytes_A1on() {
        X10Message message = new X10Message("A", 1, Action.ON);
        byte[] bytes = message.serializeMessage();

        Assert.assertThat(bytes[2], CoreMatchers.is((byte) 96));
        Assert.assertThat(bytes[3], CoreMatchers.is((byte) 0));

        //Check Header and Footer
        Assert.assertThat(bytes[0], CoreMatchers.is((byte) 213));
        Assert.assertThat(bytes[1], CoreMatchers.is((byte) 170));
        Assert.assertThat(bytes[4], CoreMatchers.is((byte) 173));
    }

    @Test
    public void shouldReturnCorrectBytes_A1off() {
        X10Message message = new X10Message("A", 1, Action.OFF);
        byte[] bytes = message.serializeMessage();

        Assert.assertThat(bytes[2], CoreMatchers.is((byte) 96));
        Assert.assertThat(bytes[3], CoreMatchers.is((byte) 32));

        //Check Header and Footer
        Assert.assertThat(bytes[0], CoreMatchers.is((byte) 213));
        Assert.assertThat(bytes[1], CoreMatchers.is((byte) 170));
        Assert.assertThat(bytes[4], CoreMatchers.is((byte) 173));
    }

    @Test
    public void shouldReturnCorrectBytes_L10off() {
        X10Message message = new X10Message("L", 10, Action.OFF);
        byte[] bytes = message.serializeMessage();

        Assert.assertThat(bytes[2], CoreMatchers.is((byte) -44));
        Assert.assertThat(bytes[3], CoreMatchers.is((byte) 48));

        //Check Header and Footer
        Assert.assertThat(bytes[0], CoreMatchers.is((byte) 213));
        Assert.assertThat(bytes[1], CoreMatchers.is((byte) 170));
        Assert.assertThat(bytes[4], CoreMatchers.is((byte) 173));
    }

    @Test
    public void shouldReturnCorrectBytes_I10off() {
        X10Message message = new X10Message("I", 10, Action.OFF);
        byte[] bytes = message.serializeMessage();

        Assert.assertThat(bytes[2], CoreMatchers.is((byte) -28));
        Assert.assertThat(bytes[3], CoreMatchers.is((byte) 48));

        //Check Header and Footer
        Assert.assertThat(bytes[0], CoreMatchers.is((byte) 213));
        Assert.assertThat(bytes[1], CoreMatchers.is((byte) 170));
        Assert.assertThat(bytes[4], CoreMatchers.is((byte) 173));
    }

    @Test
    public void shouldMatchTheSpecFile() throws Exception {
        CSVReader csvReader = new CSVReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("X10_import_data.csv")));
        List<String[]> list = csvReader.readAll();
        String header1 = "11010101";
        String header2 = "10101010";
        String footer = "10101101";
        for(String[] line : list) {
            X10Message message = new X10Message(line[0], Integer.valueOf(line[1]), Action.valueOf(line[2]));
            String expectedMessage = header1 + header2 + line[3] + line[4] + footer;
            Assert.assertThat(message.serialize(), CoreMatchers.is(expectedMessage));
        }
    }
}
