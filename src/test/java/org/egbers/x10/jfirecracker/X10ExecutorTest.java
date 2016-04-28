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
        byte[] bytes = message.serializeMessage();

        actionExecutorSerial.execute(message);

        Mockito.verify(mockSerialPort).writeBytes(bytes);
    }
}