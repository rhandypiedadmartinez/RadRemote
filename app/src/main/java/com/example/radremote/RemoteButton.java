package com.example.radremote;

import org.apache.commons.lang3.StringUtils;

public class RemoteButton {
    String necCode;
    String strRawIrPattern;
    int[] rawIrPattern;

    static int frequency = 38222;
    static String leadIn = "0158 00AC ";
    static String burstPairZero = "0015 0015 ";
    static String burstPairOne = "0015 0040 ";
    static String leadOut = "0015 38A4";

    public RemoteButton(String deviceAddress, String commandCode) {
        this.necCode = deviceAddress + commandCode;
        rawIrPattern = convertNecToRaw(deviceAddress, commandCode);
    }

    public int[] convertNecToRaw(String deviceAddress, String commandCode) {
        String unprocessedRawIR = "";

        // First Half and Second Half isn't always complementary e.g. device addr: 00FE
        String deviceAddrFirstHalf = deviceAddress.substring(0, 1);
        String deviceAddrSecondHalf = deviceAddress.substring(2, 3);

        // First Half and Second Half always add up to 255 or FF
        String commandCodeFirstHalf = deviceAddress.substring(0, 1);
        String commandCodeSecondHalf = deviceAddress.substring(2, 3);

        setStrRawIrPattern(deviceAddrFirstHalf
                +deviceAddrSecondHalf
                +commandCodeFirstHalf
                +commandCodeSecondHalf);
        /*// Construct full String NEC
        unprocessedRawIR += this.leadIn
                + convertHexToBurstPairs(deviceAddrFirstHalf)
                + convertHexToBurstPairs(deviceAddrSecondHalf)
                + convertHexToBurstPairs(commandCodeFirstHalf)
                + convertHexToBurstPairs(commandCodeSecondHalf)
                + this.leadOut;

        // Adds 0x to each values to signify hex values
        unprocessedRawIR = "0x" + unprocessedRawIR;
        unprocessedRawIR = unprocessedRawIR.replace(" ", " 0x");

        // Separates each hex values
        List listData = Arrays.asList(unprocessedRawIR.split(" "));
        int[] rawIrPattern = new int[listData.size()];

        // Iterates in the List, Converts each to int, Adds each to int array
        int i = 0;
        for (Object strHex : listData) {
            int valueInt = Integer.decode(String.valueOf(strHex)) * 1000000 / frequency;
            rawIrPattern[i++] = valueInt;
        }*/

        // Returns Raw IR in int type

        return new int[]{1,2,3}; //rawIrPattern;
    }

    public String convertHexToBurstPairs(String hexPart) {
        // Translates NEC String to Raw IR pattern in hex type
        String burstPairs = "";
        int decValue = Integer.decode(hexPart);
        String binaryString = Integer.toBinaryString(decValue);
        StringUtils.leftPad(binaryString, 8, '0');

        char[] binaryCharArr = binaryString.toCharArray();
        for (int i = binaryCharArr.length - 1; i >= 0; i--) {
            switch (i) {
                case '0':
                    burstPairs += burstPairZero;
                    break;
                case '1':
                    burstPairs += burstPairOne;
            }
        }
        setStrRawIrPattern(burstPairs);
        return burstPairs;
    }

    public void setStrRawIrPattern(String strRawIrPattern){
        this.strRawIrPattern = strRawIrPattern;
    }
}
