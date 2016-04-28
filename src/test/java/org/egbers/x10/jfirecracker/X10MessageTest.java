package org.egbers.x10.jfirecracker;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;


public class X10MessageTest {
    @Test
    public void shouldReturnCorrectBytes_A1on() {
        X10Message message = new X10Message("A", 1, Action.ON);
        byte[] bytes = message.serializeMessage();

        printBytes(bytes);
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

        printBytes(bytes);
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

        printBytes(bytes);
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

        printBytes(bytes);
        Assert.assertThat(bytes[2], CoreMatchers.is((byte) -28));
        Assert.assertThat(bytes[3], CoreMatchers.is((byte) 48));

        //Check Header and Footer
        Assert.assertThat(bytes[0], CoreMatchers.is((byte) 213));
        Assert.assertThat(bytes[1], CoreMatchers.is((byte) 170));
        Assert.assertThat(bytes[4], CoreMatchers.is((byte) 173));
    }

    private void printBytes(byte[] bytes) {
        System.out.println(bytes[2] & 0xFF);
        String s0 = String.format("%8s", Integer.toBinaryString(bytes[2] & 0xFF)).replace(' ', '0');
        System.out.println(s0);

        System.out.println(bytes[3]);
        String s1 = String.format("%8s", Integer.toBinaryString(bytes[3] & 0xFF)).replace(' ', '0');
        System.out.println(s1);

        System.out.println(s0 + "|" + s1);
    }
}
