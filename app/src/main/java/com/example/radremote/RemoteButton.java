package com.example.radremote;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class RemoteButton {
    protected String buttonName;
    protected String necCode;
    protected String strRawIrPattern;
    protected int[] rawIrPattern;
    protected String strFullIrRawInBits;

    protected static int frequency = 38222;
    protected static String leadIn = "0158 00AC ";          // signifies the start of IR pattern
    protected static String burstPairZero = "0015 0015 ";
    protected static String burstPairOne = "0015 0040 ";
    protected static String leadOut = "0015 38A4";          // signifies the end of IR Pattern

    public RemoteButton(String buttonName, String deviceAddress, String commandCode) {
        this.buttonName = buttonName;
        this.necCode = deviceAddress + commandCode;
        this.strFullIrRawInBits = "";
        rawIrPattern = convertNecToRaw(deviceAddress, commandCode);
    }

    public int[] convertNecToRaw(String deviceAddress, String commandCode) {
        String unprocessedRawIR = "";

        // First Half and Second Half isn't always complementary e.g. device addr: 00FE
        String deviceAddrFirstHalf = deviceAddress.substring(0, 2);
        String deviceAddrSecondHalf = deviceAddress.substring(2, 4);

        // First Half and Second Half always add up to 255 or FF
        String commandCodeFirstHalf = commandCode.substring(0, 2);
        String commandCodeSecondHalf = commandCode.substring(2, 4);

        // Construct full String NEC
        unprocessedRawIR += this.leadIn
                + convertHexToBurstPairs(deviceAddrFirstHalf,false)
                + convertHexToBurstPairs(deviceAddrSecondHalf,false)
                + convertHexToBurstPairs(commandCodeFirstHalf,false)
                + convertHexToBurstPairs(commandCodeSecondHalf,false)
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
        }
        // Returns Raw IR in int type*/

        //setStrRawIrPattern(strFullIrRawInBits);
        setStrRawIrPattern(String.valueOf(Arrays.toString(rawIrPattern)));

        return rawIrPattern; //rawIrPattern;
    }

    public String convertHexToBurstPairs(String hexPart, boolean isReverse) {
        // Translates NEC String to Raw IR pattern in hex type
        String burstPairs = "";
        int decValue = Integer.decode("0x" + hexPart);
        String binaryString = Integer.toBinaryString(decValue);
        binaryString = StringUtils.leftPad(binaryString, 8, '0');
        strFullIrRawInBits += binaryString + "\n";

        char[] binaryCharArr = binaryString.toCharArray();

        // Sent in Reverse Order e.g. 50 is 01010000 but send in IR as 00001010
        if (isReverse==true){
            for (int i = binaryCharArr.length - 1; i >= 0; i--) {
                switch (binaryCharArr[i]) {
                    case '0':
                        burstPairs += burstPairZero;
                        break;
                    case '1':
                        burstPairs += burstPairOne;
                }
            }
        }
        // Not sent in Reverse Order
        else {
            for (int i = 0; i < binaryCharArr.length; i++) {
                switch (binaryCharArr[i]) {
                    case '0':
                        burstPairs += burstPairZero;
                        break;
                    case '1':
                        burstPairs += burstPairOne;
                }
            }
        }

        return burstPairs;
    }

    public void setStrRawIrPattern(String strRawIrPattern){
        this.strRawIrPattern = strRawIrPattern;
    }

}
