package org.egbers.x10.jfirecracker;

import jssc.SerialPort;

import static jssc.SerialPort.*;

public class X10Executor {
    static final Integer REPEAT_COMMAND = 1;
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
        String commandLine = message.serialize();

        serialPort.openPort();
        serialPort.setParams(BAUDRATE_9600, DATABITS_8, STOPBITS_1, PARITY_NONE);
        for(int t=0;t<REPEAT_COMMAND;t++) {
            for (int i = 0; i < commandLine.length(); i++) {
                if (commandLine.charAt(i) == '1') {
                    serialPort.setRTS(true);
                    serialPort.setDTR(false);
                } else if (commandLine.charAt(i) == '0') {
                    serialPort.setRTS(false);
                    serialPort.setDTR(true);
                }

                serialPort.sendBreak(1);
                serialPort.setRTS(true);
                serialPort.setDTR(true);
                serialPort.sendBreak(1);
            }
        }
        serialPort.closePort();
    }


}
