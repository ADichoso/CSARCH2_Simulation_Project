import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class Binary32Number {
    private int nSign;
    private int nExponent;
    private BigInteger nMantissa;
    //for special cases
    private String sCase = "Normal";


    public void setSign(int nSign){this.nSign = nSign;}
    public void setExponent(int nExponent){this.nExponent = nExponent;}
    public void setMantissa(BigInteger nMantissa){this.nMantissa = nMantissa;}
    public void setCase(String sCase){this.sCase = sCase;}

    public int getSign(){return this.nSign;}
    public int getExponent(){return this.nExponent;}
    public BigInteger getMantissa(){return this.nMantissa;}
    public String getCase(){return this.sCase;}
    
    public String getSignString(){return String.valueOf(this.nSign);}
    public String getExponentString()
    {
        int nExponent = this.nExponent;
        
        //Continuous Divison Step
        String sExponentString = "";
        do
        {
            //Append modulo 2 of baseIntWhole to string
            sExponentString = (nExponent % 2) + sExponentString;
            nExponent /= 2;
        }while(nExponent != 0);

        while(sExponentString.length() != 8)
            sExponentString += "0";

        return sExponentString;
    }

    public String getMantissaString()
    {
        BigInteger nMantissa = this.nMantissa;
        BigInteger nTwo = new BigInteger("2");
        BigInteger nZero = new BigInteger("0");

        //Continuous Divison Step
        String sMantissaString = "";
        do
        {
            //Append modulo 2 of baseIntWhole to string
            sMantissaString = nMantissa.mod(nTwo).toString() + sMantissaString;
            nMantissa = nMantissa.divide(nTwo);
        }while(!nMantissa.equals(nZero));

        if(sMantissaString.length() < 23)
            //Sign extend upto 23 bits
            while(sMantissaString.length() != 23)
                sMantissaString += "0";
        else if(sMantissaString.length() > 23)
            //Cut off rest
            sMantissaString = sMantissaString.substring(0, 23);

        return sMantissaString;
    }

    public Binary32Number()
    {
        this.setSign(0);
        this.setExponent(0);
        this.setMantissa(new BigInteger("0"));
    }

    public Binary32Number(BinaryNumber binaryNumber)
    {
        Binary32Number binary32Number = Binary32Number.valueOf(binaryNumber);

        this.setSign(binary32Number.getSign());
        this.setExponent(binary32Number.getExponent());
        this.setMantissa(binary32Number.getMantissa());
        this.setCase(binary32Number.getCase());
    }

    public static Binary32Number valueOf(BinaryNumber binaryNumber)
    {
        Binary32Number binary32Number = new Binary32Number();

        //Step 1: Get the sign of the binary number
        binary32Number.setSign(binaryNumber.getSign() ? 0 : 1);

        //Step 2: Get exponent VALUE of binaryNumber (e' = e + 127)
        String sBinaryNumberString = binaryNumber.getNormalizedValue();
        
        String sExponentString = sBinaryNumberString.split("x")[1];
        sExponentString = sExponentString.split("\\^")[1];

        int nEPrime = Integer.parseInt(sExponentString.trim()) + 127;

        //Continuous Divison Step
        binary32Number.setExponent(nEPrime);

        //Step 3: Get mantissa part of binary number (Derived from denormalized value of binary number)
        String sMantissaString = sBinaryNumberString.split("x")[0];
        sMantissaString = sMantissaString.split("\\.")[1].trim();

        String sFlippedMantissaString = "";
        for(int i = 0; i < sMantissaString.length(); i++)
        {
            sFlippedMantissaString = sMantissaString.charAt(i) + sFlippedMantissaString;
        }

        BigInteger nMantissa = new BigInteger("0");
        BigInteger nTwo = new BigInteger("2");
        for(int i = 0; i < sFlippedMantissaString.length(); i++)
        {
            BigInteger nValueToAdd = sFlippedMantissaString.charAt(i) == '1' ? nTwo.pow(i) : new BigInteger("0");
            System.out.println("Adding:" + nValueToAdd.toString());
            nMantissa = nMantissa.add(nValueToAdd);
        }

        binary32Number.setMantissa(nMantissa);
        
        //Checking exponent to see if it is a special case
        //If nEPrime is 0 or 255 (0000 0000 or 1111 1111), then it is a special case
        System.out.println("nEPrime: " + nEPrime);
        System.out.println("nMantissa: " + nMantissa.intValue());
        if(nEPrime == 0 || nEPrime == 255)
        {
            
            if(nEPrime == 0) {
                //if nEPrime is 0, it could be a zero or denormalized number
                if (nMantissa.intValue() == 0) {
                    //if mantissa is 0, it is zero
                    if (binary32Number.getSign() == 0) {
                        binary32Number.setCase("Positive Zero");
                    } else {
                        binary32Number.setCase("Negative Zero");
                    }
                } else {
                    binary32Number.setCase("Denormalized");
                }
            } else {
                //if nEPrime is 255, it could be a infinity or NaN
                if(nMantissa.intValue() == 0) {
                    //if mantissa is 0, it is infinity
                    if(binary32Number.getSign() == 0) {
                        binary32Number.setCase("Positive Infinity");
                        System.out.println("Positive Infinity");
                    } else {
                        binary32Number.setCase("Negative Infinity");
                    }
                } else {
                    String sSignificand = binary32Number.getMantissaString();
                    if(sSignificand.charAt(0) == '0') {
                        if(sSignificand.charAt(1) == '1') {
                            //if the first two bits are 01, it is a sNaN
                            binary32Number.setCase("sNaN");
                        }
                    } else {
                        //if the first bit is 1, it is a qNaN
                        binary32Number.setCase("qNaN");
                    }
                }
            }
        }
        return binary32Number;
    }

    @Override
    public String toString()
    {
        String sFullNumber = getSignString() + getExponentString() + getMantissaString();
        
        String sSpacedNumber = "";
        for(int i = 0; i < sFullNumber.length(); i++)
        {
            sSpacedNumber += sFullNumber.charAt(i);
            if((i+1) % 4 == 0)
                sSpacedNumber += " ";
        }
        return sSpacedNumber;
    }

    public String toStringHex()
    {
        String sFullNumber = getSignString() + getExponentString() + getMantissaString();

        String sHexNumber = "";
        for(int i = 0; i < sFullNumber.length(); i += 4)
        {
            String sCurrNibble = sFullNumber.substring(i, i + 4);

            switch(sCurrNibble)
            {
                case "0000":
                    sHexNumber += "0";
                    break;
                case "0001":
                    sHexNumber += "1";
                    break;
                case "0010":
                    sHexNumber += "2";
                    break;
                case "0011":
                    sHexNumber += "3";
                    break;
                case "0100":
                    sHexNumber += "4";
                    break;
                case "0101":
                    sHexNumber += "5";
                    break;
                case "0110":
                    sHexNumber += "6";
                    break;
                case "0111":
                    sHexNumber += "7";
                    break;
                case "1000":
                    sHexNumber += "8";
                    break;
                case "1001":
                    sHexNumber += "9";
                    break;
                case "1010":
                    sHexNumber += "A";
                    break;
                case "1011":
                    sHexNumber += "B";
                    break;
                case "1100":
                    sHexNumber += "C";
                    break;
                case "1101":
                    sHexNumber += "D";
                    break;
                case "1110":
                    sHexNumber += "E";
                    break;
                case "1111":
                    sHexNumber += "F";
                    break;
            }
        }

        return sHexNumber;
    }
    public void outputToFile(BinaryNumber binaryNumber, Binary32Number binary32Number){
        try {
            FileWriter myWriter = new FileWriter("output.txt");
            myWriter.write("Original Value: " + binaryNumber.getOriginalValue() + "\n");
            myWriter.write("Normalized Value: " + binaryNumber.getNormalizedValue() + "\n");
            myWriter.write("Sign: " + binary32Number.getSignString() + "\n");
            myWriter.write("Exponent: " + binary32Number.getExponentString() + "\n");
            myWriter.write("Mantissa: " + binary32Number.getMantissaString() + "\n");
            myWriter.write("Binary Representation: 0b" + binary32Number.toString() + "\n");
            myWriter.write("Hex Representation: 0x" + binary32Number.toStringHex() + "\n");
            myWriter.write("Case: " + binary32Number.getCase() + "\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file: output.txt");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
