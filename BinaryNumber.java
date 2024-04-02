import java.math.BigDecimal;
import java.math.BigInteger;

public class BinaryNumber {
    private String sWholePart;
    private String sFractionalPart;
    private int nExpValue;
    private boolean isPositive;

    public void setWholePart(String sWholePart){this.sWholePart = sWholePart;}
    public void setFractionalPart(String sFractionalPart){this.sFractionalPart = sFractionalPart;}
    public void setExpValue(int nExpValue){this.nExpValue = nExpValue;}
    public void setSign(boolean isPositive){this.isPositive = isPositive;}

    public String getWholePart(){return sWholePart;}
    public String getFractionalPart(){return sFractionalPart;}
    public int getExpValue(){return nExpValue;}
    public boolean getSign(){return isPositive;}

    public BinaryNumber()
    {
        this.setWholePart("0");
        this.setFractionalPart("0");
        this.setExpValue(0);
        this.setSign(true);
    }

    public BinaryNumber(String sInput)
    {
        BinaryNumber binaryNumber = BinaryNumber.valueOf(sInput);

        this.setWholePart(binaryNumber.getWholePart());
        this.setFractionalPart(binaryNumber.getFractionalPart());
        this.setExpValue(binaryNumber.getExpValue());
        this.setSign(binaryNumber.getSign());
    }

    public static BinaryNumber valueOf(String sString)
    {
        BinaryNumber binaryNumber = new BinaryNumber();
        //Step 2: Split into base & exponent
        String sBase = sString.split("x")[0];
        String sExp = sString.split("x")[1];
        
        BigDecimal dFractionPart = BigDecimal.valueOf(0.0);

        System.out.println(dFractionPart.byteValue());
        BigInteger nWholePart = new BigInteger(sBase.split("\\.")[0].replace("-", "").trim());

        System.out.println("Big Integer Value:" + nWholePart.toString());

        try
        {
            dFractionPart = new BigDecimal("0." + sBase.split("\\.")[1].trim());
        } 
        catch(ArrayIndexOutOfBoundsException e)
        {
            dFractionPart = BigDecimal.valueOf(0.0);
        }


        binaryNumber.setWholePart(nWholePart.toString());
        binaryNumber.setFractionalPart(dFractionPart.toString());
        
        binaryNumber.setExpValue(Integer.parseInt(sExp.split("\\^")[1]));
        binaryNumber.setSign(!sBase.contains("-"));

        return binaryNumber;
    }

    public static BinaryNumber valueOf(RealNumber realNumber)
    {
        BinaryNumber binaryNumber = new BinaryNumber();
        //CONVERSION STARTS HERE
        
        // Same sign
        binaryNumber.setSign(realNumber.getSign());
        binaryNumber.setExpValue(0); //Act as if there is no exponent value

        String sDenormalizedNumber = realNumber.getDenormalizedValue();

        System.out.println("QUirkcfd " +sDenormalizedNumber);
        if(sDenormalizedNumber.equals(" 0") || sDenormalizedNumber.equals("-0"))
        {
            System.out.println("Oi nandito ka");
            binaryNumber.setFractionalPart("0");
            binaryNumber.setWholePart("0");
        }
        else
        {
            BigInteger nBaseWhole = new BigInteger(sDenormalizedNumber.split("\\.")[0].replace("-", "").trim());

            System.out.println("Big Integer Value:" + nBaseWhole.toString());
            BigDecimal dBaseFraction = BigDecimal.valueOf(0.0);
    
            try
            {
                dBaseFraction = new BigDecimal("0." + sDenormalizedNumber.split("\\.")[1].trim());
            } 
            catch(ArrayIndexOutOfBoundsException e)
            {
                dBaseFraction = BigDecimal.valueOf(0.0);
            }
            System.out.println("Faraction: " + dBaseFraction);
            //To keep it easy, Use the denormalized real number value to convert to a binary number
    
            //Step 1: Convert base whole number to binary whole number (Continuous Divison)
            BigInteger nTwo = BigInteger.valueOf(2);
            String sBinaryBaseIntWhole = "";
            do
            {
                //Append modulo 2 of baseIntWhole to string
                sBinaryBaseIntWhole = (nBaseWhole.mod(nTwo)) + sBinaryBaseIntWhole;
                nBaseWhole = nBaseWhole.divide(nTwo);
            }while(nBaseWhole.intValue() != 0);
    
            binaryNumber.setWholePart(sBinaryBaseIntWhole);
    
            //Step 2: convert base fractional number to binary fractional number (Continuous Multiplication)
            //Notes: Binary32 can only support upto 23 bits for the mantissa
            //Get max length of fractional binary part of number by performing (23 - (sBinaryBaseIntWhole.length() - 1))
    
            int nMaxFractionLength = (23 - (sBinaryBaseIntWhole.length() - 1));
    
            //Continuous Division (Get current number)
            String sBinaryBaseIntFraction = "0.";
            BigDecimal dOne = BigDecimal.valueOf(1.0);
            BigDecimal dTwo = BigDecimal.valueOf(2.0);
            
            int i = 0;
            while(i < nMaxFractionLength || !sBinaryBaseIntFraction.contains("1"))
            {
                //Curr Number * 2f
                dBaseFraction = dBaseFraction.multiply(dTwo);
    
                //Get whole number part
                char cWholePart  = dBaseFraction.toPlainString().charAt(0);
    
                //If whole part is 1, subtract 1 in fBaseFraction
                if(cWholePart == '1') dBaseFraction = dBaseFraction.subtract(dOne);
    
                //Add char to binary string
                sBinaryBaseIntFraction += cWholePart;
    
                if(dBaseFraction.doubleValue() == 0.0) break;

                i++;
            }
    
            binaryNumber.setFractionalPart(sBinaryBaseIntFraction);
        }

        return binaryNumber;
    }

    public String getNormalizedValue()
    {
        //Step 10: Create the Normalized Binary Number (Scientific Notation Format)
        String sBinaryBaseIntWhole = getWholePart();
        String sBinaryBaseIntFraction = getFractionalPart().replace("0.", "");

        String sNormalizedBinaryNumber = "";
        int nBinaryExpPower = 0;
        if(sBinaryBaseIntWhole.equals("0"))
        {
            //Case 1: sBinaryBaseIntWhole is zero (Decimal point will be shifted to the right until first '1' is encountered)
            //Implementation: Loop through sBinaryBaseIntFraction till you find a '1'
            int nCutoffIndex = 0;
            for(int i = 0; i < sBinaryBaseIntFraction.length(); i++)
            {
                if(sBinaryBaseIntFraction.charAt(i) == '1')
                {
                    nCutoffIndex = i;
                    break;
                }
            }
            
            String sCutBinaryBase = sBinaryBaseIntFraction.substring(nCutoffIndex, sBinaryBaseIntFraction.length());

            nBinaryExpPower = -1 * nCutoffIndex;
            sNormalizedBinaryNumber = (getSign() ? ' ' : '-') + "1." + sCutBinaryBase + "x" + "2^" + (getExpValue() + nBinaryExpPower);

            //Case 2: Both are zero: Set to 0.0x2^0
            if(sBinaryBaseIntFraction.equals("0"))
                sNormalizedBinaryNumber = (getSign() ? ' ' : '-') + "0.0x2^0";
        }
        else
        {
            //Case 2: sBinaryBaseIntWhole is not zero (Decimal point will be shifted to the left until last '1' is encountered)
            //Implementation: Exponent value is just sBinaryBaseIntWhole - 1
            //Normalized String will take the form: 1.(sBinaryBaseIntWhole without MSB)(sBinaryBaseIntFraction)

            String sCutBinaryBase = sBinaryBaseIntWhole.substring(1, sBinaryBaseIntWhole.length());
            nBinaryExpPower = sCutBinaryBase.length();

            sNormalizedBinaryNumber = (getSign() ? ' ' : '-') + "1." + sCutBinaryBase + sBinaryBaseIntFraction + "x" + "2^" + (getExpValue() + nBinaryExpPower);
        }

        System.out.println("dfskljf: " + sNormalizedBinaryNumber);
        //Denormalize to power of -126 at the minimum
        if((getExpValue() + nBinaryExpPower) < -126)
        {
            String sNewBinaryBaseIntWhole = sNormalizedBinaryNumber.split("x")[0].split("\\.")[0].replace("-", "").trim();
            String sNewBinaryBaseIntFraction = sNormalizedBinaryNumber.split("x")[0].split("\\.")[1];
            //Have to denormalize a bit to accomodate binary 32 representation
            int nZeroesToAppend = ((getExpValue() + nBinaryExpPower) + 126) * -1;
            if(nZeroesToAppend == 1)
                sNormalizedBinaryNumber = (getSign() ? ' ' : '-') + "0." + sNewBinaryBaseIntWhole + sNewBinaryBaseIntFraction + "x2^-126";
            else if(nZeroesToAppend > 1)
            {
                sNormalizedBinaryNumber = (getSign() ? ' ' : '-') + "0.";
                for(int i = 0; i < nZeroesToAppend; i++)
                    sNormalizedBinaryNumber = sNormalizedBinaryNumber + "0";
                
                sNormalizedBinaryNumber = sNormalizedBinaryNumber + sNewBinaryBaseIntWhole + sNewBinaryBaseIntFraction + "x2^-126";
            }
        }


        return sNormalizedBinaryNumber;
    }
    
    public String getDenormalizedValue() //Applies exponent value, removing that part in the number
    {
        String sBase = "";
        String sFractionalPart = String.valueOf(getFractionalPart()).replace("0.", "");
        String sWholePart = String.valueOf(getWholePart());
        
        if((sFractionalPart.contains("1") || sWholePart.contains("1"))) //Has to have a 1 to need denormalization
        {
            int nExpValue = getExpValue(); 
            if(nExpValue > 0)
            {
                //Case 1: No need to zero extend = Length of fractional part is greater than or equal to exp value
                if(sFractionalPart.length() >= nExpValue)
                {
                    //Shift decimal point to the right
                    String sFractionalPart1 = sFractionalPart.substring(0, nExpValue);
                    String sFractionalPart2 = sFractionalPart.substring(nExpValue, sFractionalPart.length());
                    sBase = sWholePart + sFractionalPart1 + "." + sFractionalPart2;
                } 
                //Case 2: Zero extend required (Number of zeroes = (Exp value - length of fractional part))
                else 
                {
                    sBase = sWholePart + sFractionalPart;
                    //Add zeroes after
                    for(int i = 0; i < nExpValue - sFractionalPart.length(); i++)
                        sBase = sBase + "0";
                }
            }
            else if(nExpValue < 0) 
            {
                //Case 1: No need to zero extend = Length of whole part is greater than or equal to exp value
                int nExpMagnitude = nExpValue * -1;
                if(sWholePart.length() >= nExpMagnitude)
                {
                    //Shift decimal point to the left
                    String sWholePart1 = sWholePart.substring(0, sWholePart.length() - nExpMagnitude);
                    String sWholePart2 = sWholePart.substring(sWholePart.length() - nExpMagnitude, sWholePart.length());
                    sBase = sWholePart1 + "." + sWholePart2 + sFractionalPart;
                } 
                //Case 2: Zero extend required (Number of zeroes = (Exp value - length of whole part))
                else
                {
                    sBase = sWholePart + sFractionalPart;
                    //Add zeroes after
                    for(int i = 0; i < nExpMagnitude - sWholePart.length(); i++)
                        sBase = "0" + sBase;
                    sBase = "0." + sBase;
                } 
                
            }
            else if(nExpValue == 0)
                sBase =sWholePart + "." + sFractionalPart;
        } 
        else //Special Case: both fractional and whole part has no 1 (Value of zero)
            sBase = "0";
        

        String sDenormalizedBase = (getSign() ? ' ' : '-') + sBase;

        return sDenormalizedBase;
    }

    public String getOriginalValue() //Original Input of the user
    {
        return (getSign() ? ' ' : '-') + getBaseString() + "x" + getExpString();
    }

    public String getBaseString()
    {
        return String.valueOf(getWholePart()) + "." + String.valueOf(getFractionalPart()).replace("0.", "");
    }

    public String getExpString()
    {
        return "2^" + String.valueOf(getExpValue());
    }
}
