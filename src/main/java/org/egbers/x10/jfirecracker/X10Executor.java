package org.egbers.x10.jfirecracker;

import jssc.SerialPort;
import static jssc.SerialPort.*;

public class X10Executor {
    private SerialPort serialPort;

    public X10Executor(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public X10Executor() {
        serialPort = new SerialPort("COM1");
    }

    public X10Executor(String portName) {
        serialPort = new SerialPort(portName);
    }

    public void execute(X10Message message) throws Exception {
        byte[] bytes = message.serializeMessage();
        printMessage(bytes);

        serialPort.openPort();
        serialPort.setParams(BAUDRATE_9600, DATABITS_8, STOPBITS_1, PARITY_NONE);
        serialPort.writeBytes(bytes);
        serialPort.closePort();
    }

    private void printMessage(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        builder
                .append(serialPort.getPortName()).append(" ")
                .append(String.format("%8s", Integer.toBinaryString(bytes[0] & 0xFF)).replace(' ', '0')).append(" ")
                .append(String.format("%8s", Integer.toBinaryString(bytes[1] & 0xFF)).replace(' ', '0')).append(" ")
                .append(String.format("%8s", Integer.toBinaryString(bytes[2] & 0xFF)).replace(' ', '0')).append(" ")
                .append(String.format("%8s", Integer.toBinaryString(bytes[3] & 0xFF)).replace(' ', '0')).append(" ")
                .append(String.format("%8s", Integer.toBinaryString(bytes[4] & 0xFF)).replace(' ', '0'));
        System.out.println(builder.toString());
    }
}
