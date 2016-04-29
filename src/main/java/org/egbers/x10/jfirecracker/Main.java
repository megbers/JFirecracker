package org.egbers.x10.jfirecracker;

public class Main {
    public static void main(String [] args) {
        if(args == null || args.length < 4) {
            System.out.println("Usage: $> java -jar JFirecracker <com port> <house code> <unit code> <action>");
        }

        String comPort = args[0];
        String houseCode = args[1];
        Integer unitCode = Integer.parseInt(args[2]);
        Action action = Action.valueOf(args[3]);

        X10Message message = new X10Message(houseCode, unitCode, action);
        X10Executor executor = new X10Executor("COM" + comPort);

        try {
            executor.execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

//import jssc.SerialPortList;
//
//public class Main {
//
//    public static void main(String[] args) {
//        //Method getPortNames() returns an array of strings. Elements of the array is already sorted.
//        String[] portNames = SerialPortList.getPortNames();
//        for(int i = 0; i < portNames.length; i++){
//            System.out.println(portNames[i]);
//        }
//    }
//}