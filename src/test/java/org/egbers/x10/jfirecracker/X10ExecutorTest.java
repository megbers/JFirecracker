package org.egbers.x10.jfirecracker;

import jssc.SerialPort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class X10ExecutorTest {
    @Mock
    private SerialPort mockSerialPort;

    private X10Executor actionExecutorSerial;

    @Before
    public void setUp() {
        actionExecutorSerial = new X10Executor(mockSerialPort);
    }

    @Test
    public void shouldWriteToSerialPort() throws Exception {
        X10Message message = new X10Message("A", 1, Action.ON);

        actionExecutorSerial.execute(message);

        Mockito.verify(mockSerialPort, Mockito.times(80*3)).sendBreak(1);
        Mockito.verify(mockSerialPort, Mockito.times(16*3)).setDTR(false);
        Mockito.verify(mockSerialPort, Mockito.times(64*3)).setDTR(true);
        Mockito.verify(mockSerialPort, Mockito.times(24*3)).setRTS(false);
        Mockito.verify(mockSerialPort, Mockito.times(56*3)).setRTS(true);
    }
}