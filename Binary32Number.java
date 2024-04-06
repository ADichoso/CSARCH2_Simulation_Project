import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class Binary32Number {
    private int nSign;
    private int nExponent;
    private String sMantissa;
    //for special cases
    private String sCase = "Normal";


    public void setSign(int nSign){this.nSign = nSign;}
    public void setExponent(int nExponent){this.nExponent = nExponent;}
    public void setMantissa(String sMantissa){this.sMantissa = sMantissa;}
    public void setCase(String sCase){this.sCase = sCase;}

    public int getSign(){return this.nSign;}
    public int getExponent(){return this.nExponent;}
    public String getMantissa(){return this.sMantissa;}
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
            sExponentString = "0" + sExponentString;

        return sExponentString;
    }

    public Binary32Number()
    {
        this.setSign(0);
        this.setExponent(0);
        this.setMantissa("0");
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

        int nEPrime = 0;
        String sMantissa = "00000000000000000000000";

        //Step 1: Get the sign of the binary number
        binary32Number.setSign(binaryNumber.getSign() ? 0 : 1);
        

        //Special Case: check denormalized value and see if binary number actually has a value not just zero before continuing on.
        if(binaryNumber.getDenormalizedValue().equals(" 0") || binaryNumber.getDenormalizedValue().equals("-0"))
        {
            //Special Case: Just 0
            binary32Number.setExponent(0);
            binary32Number.setMantissa("00000000000000000000000");
        }
        else
        {
            //Step 2: Get exponent VALUE of binaryNumber (e' = e + 127)
            String sBinaryNumberString = binaryNumber.getNormalizedValue();
            
            String sExponentString = sBinaryNumberString.split("x")[1];
            sExponentString = sExponentString.split("\\^")[1];
            
            nEPrime = Integer.parseInt(sExponentString.trim());
            
            if(nEPrime > 127)
            {
                nEPrime = 255;
                binary32Number.setMantissa("00000000000000000000000");    
                binary32Number.setExponent(nEPrime);
            } 
            else 
            {
                if(nEPrime <= -126 && (sBinaryNumberString.split("\\.")[0].trim().equals("0") || sBinaryNumberString.split("\\.")[0].trim().equals("-0")))
                    nEPrime = 0;
                else
                    nEPrime += 127;
            
                //Continuous Divison Step
                binary32Number.setExponent(nEPrime);

                //Step 3: Get mantissa part of binary number (Derived from denormalized value of binary number)
                sMantissa = sBinaryNumberString.split("x")[0];
                sMantissa = sMantissa.split("\\.")[1].trim(); 

                if(sMantissa.length() > 23)
                    sMantissa = sMantissa.substring(0, 23); //limit to 23 bits only
                else if(sMantissa.length() < 23)
                    while(sMantissa.length() != 23)
                        sMantissa += "0";
                
                binary32Number.setMantissa(sMantissa);
            }

        }
        
        //Checking exponent to see if it is a special case
        //If nEPrime is 0 or 255 (0000 0000 or 1111 1111), then it is a special case
        if(nEPrime == 0 || nEPrime == 255)
        {
            
            if(nEPrime == 0) {
                //if nEPrime is 0, it could be a zero or denormalized number
                if (!sMantissa.contains("1")) {
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
                if(!sMantissa.contains("1")) {
                    //if mantissa is 0, it is infinity
                    if(binary32Number.getSign() == 0) {
                        binary32Number.setCase("Positive Infinity");
                    } else {
                        binary32Number.setCase("Negative Infinity");
                    }
                } else {
                    String sSignificand = binary32Number.getMantissa();
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
        String sFullNumber = getSignString() + getExponentString() + getMantissa();
        
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
        String sFullNumber = getSignString() + getExponentString() + getMantissa();

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
            myWriter.write("Mantissa: " + binary32Number.getMantissa() + "\n");
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
