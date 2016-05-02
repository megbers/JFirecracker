package org.egbers.x10.jfirecracker;

import java.util.HashMap;
import java.util.Map;

public class X10Message {
    private String houseCode;
    private Integer unitCode;
    private Action action;

    public X10Message() {

    }

    public X10Message(String houseCode, Integer unitCode, Action action) {
        this.houseCode = houseCode;
        this.unitCode = unitCode;
        this.action = action;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public Integer getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(Integer unitCode) {
        this.unitCode = unitCode;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    protected String serialize() {
        byte[] bytes = serializeMessage();
        StringBuilder builder = new StringBuilder();
        builder
                .append(String.format("%8s", Integer.toBinaryString(bytes[0] & 0xFF)).replace(' ', '0'))
                .append(String.format("%8s", Integer.toBinaryString(bytes[1] & 0xFF)).replace(' ', '0'))
                .append(String.format("%8s", Integer.toBinaryString(bytes[2] & 0xFF)).replace(' ', '0'))
                .append(String.format("%8s", Integer.toBinaryString(bytes[3] & 0xFF)).replace(' ', '0'))
                .append(String.format("%8s", Integer.toBinaryString(bytes[4] & 0xFF)).replace(' ', '0'));
        return builder.toString();
    }

    protected byte[] serializeMessage() {
        byte[] bytes = new byte[5];

        //Set Header and footer
        bytes[0] = (byte) 213;
        bytes[1] = (byte) 170;
        bytes[4] = (byte) 173;

        bytes[2] = (byte) (valueMapper.get(this.getHouseCode()) + (this.getUnitCode() > 8 ? 4 : 0));
        if(this.getAction() == Action.OFF || this.action == Action.ON) {
            bytes[3] = (byte) (valueMapper.get(this.getUnitCode()) + (this.getAction() == Action.ON ? 0 : 32));
        } else {
            bytes[3] = (byte) (136 + (this.getAction() == Action.DIM ? 16 : 0));
        }

        return bytes;
    }

    private static Map<Object, Integer> valueMapper;

    static {
        valueMapper = new HashMap<>();
        //House Codes
        valueMapper.put("A", 96);
        valueMapper.put("B", 112);
        valueMapper.put("C", 64);
        valueMapper.put("D", 80);
        valueMapper.put("E", 128);
        valueMapper.put("F", 144);
        valueMapper.put("G", 160);
        valueMapper.put("H", 176);
        valueMapper.put("I", 224);
        valueMapper.put("J", 240);
        valueMapper.put("K", 192);
        valueMapper.put("L", 208);
        valueMapper.put("M", 0);
        valueMapper.put("N", 16);
        valueMapper.put("O", 32);
        valueMapper.put("P", 48);

        //Unit Codes
        valueMapper.put(1, 0);
        valueMapper.put(2, 16);
        valueMapper.put(3, 8);
        valueMapper.put(4, 24);
        valueMapper.put(5, 64);
        valueMapper.put(6, 80);
        valueMapper.put(7, 72);
        valueMapper.put(8, 88);
        valueMapper.put(9, 0);
        valueMapper.put(10, 16);
        valueMapper.put(11, 8);
        valueMapper.put(12, 24);
        valueMapper.put(13, 64);
        valueMapper.put(14, 80);
        valueMapper.put(15, 72);
        valueMapper.put(16, 88);
    }
}
