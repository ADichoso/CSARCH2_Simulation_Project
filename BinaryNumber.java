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


        int nWholePart = Integer.parseInt(sBase.replace("-", "").split("\\.")[0]);
        String sBinaryBaseIntWhole = "";
        do
        {
            //Append modulo 2 of baseIntWhole to string
            sBinaryBaseIntWhole = (nWholePart % 2) + sBinaryBaseIntWhole;
            nWholePart /= 2;
        }while(nWholePart != 0);

        int nMaxFractionalPartLength = 23 - sBinaryBaseIntWhole.length() + 1; //Max Length of Fractional Part

        Double dFractionPart = Double.parseDouble("0." + sBase.replace("-", "").split("\\.")[1]);
        String sBinaryBaseIntFraction = "0.";
        for(int i = 0; i < nMaxFractionalPartLength; i++)
        {
            //Curr Number * 2f
            dFractionPart *= 2.0;

            //Get whole number part
            char cWholePart  = String.valueOf(dFractionPart).charAt(0);

            //If whole part is 1, subtract 1 in fBaseFraction
            if(cWholePart == '1')
            dFractionPart -= 1.0;

            //Add char to binary string
            sBinaryBaseIntFraction += cWholePart;

            if(dFractionPart == 0.0) break;
        }


        binaryNumber.setWholePart(sBinaryBaseIntWhole);
        binaryNumber.setFractionalPart(sBinaryBaseIntFraction);
        binaryNumber.setExpValue(Integer.parseInt(sExp.split("\\^")[1]));
        binaryNumber.setSign(!sBase.contains("-"));

        return binaryNumber;
    }

    public static BinaryNumber valueOf(RealNumber realNumber)
    {
        BinaryNumber binaryNumber = new BinaryNumber();
        //CONVERSION STARTS HERE
        
        String sDenormalizedNumber = realNumber.getDenormalizedValue();

        int nBaseWhole = Integer.parseInt(sDenormalizedNumber.split("\\.")[0].trim());
        Double dBaseFraction = Double.parseDouble("0." + sDenormalizedNumber.split("\\.")[1].trim());
        
        //To keep it easy, Use the denormalized real number value to convert to a binary number
        // Same sign
        binaryNumber.setSign(realNumber.getSign());
        binaryNumber.setExpValue(0); //Act as if there is no exponent value

        //Step 1: Convert base whole number to binary whole number (Continuous Divison)
        String sBinaryBaseIntWhole = "";
        do
        {
            //Append modulo 2 of baseIntWhole to string
            sBinaryBaseIntWhole = (nBaseWhole % 2) + sBinaryBaseIntWhole;
            nBaseWhole /= 2;
        }while(nBaseWhole != 0);

        binaryNumber.setWholePart(sBinaryBaseIntWhole);

        //Step 2: convert base fractional number to binary fractional number (Continuous Multiplication)
        //Notes: Binary32 can only support upto 23 bits for the mantissa
        //Get max length of fractional binary part of number by performing (23 - (sBinaryBaseIntWhole.length() - 1))

        int nMaxFractionalPartLength = 23 - sBinaryBaseIntWhole.length() + 1; //Max Length of Fractional Part

        //Continuous Division (Get current number)
        String sBinaryBaseIntFraction = "0.";
        for(int i = 0; i < nMaxFractionalPartLength; i++)
        {
            //Curr Number * 2f
            dBaseFraction *= 2.0;

            //Get whole number part
            char cWholePart  = String.valueOf(dBaseFraction).charAt(0);

            //If whole part is 1, subtract 1 in fBaseFraction
            if(cWholePart == '1')
                dBaseFraction -= 1.0;

            //Add char to binary string
            sBinaryBaseIntFraction += cWholePart;

            if(dBaseFraction == 0.0) break;
        }

        binaryNumber.setFractionalPart(sBinaryBaseIntFraction);

        return binaryNumber;
    }

    public String getNormalizedValue()
    {
        //Step 10: Create the Normalized Binary Number (Scientific Notation Format)
        String sBinaryBaseIntWhole = String.valueOf(getWholePart());
        String sBinaryBaseIntFraction = String.valueOf(getFractionalPart()).replace("0.", "");

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
            sNormalizedBinaryNumber = "1." + sCutBinaryBase + "x" + "2^" + nBinaryExpPower;

            //Case 2: Both are zero: Set to 0.0x2^0
            if(sBinaryBaseIntFraction.equals("0"))
                sNormalizedBinaryNumber = "0.0x2^0";
        }
        else
        {
            //Case 2: sBinaryBaseIntWhole is not zero (Decimal point will be shifted to the left until last '1' is encountered)
            //Implementation: Exponent value is just sBinaryBaseIntWhole - 1
            //Normalized String will take the form: 1.(sBinaryBaseIntWhole without MSB)(sBinaryBaseIntFraction)

            String sCutBinaryBase = sBinaryBaseIntWhole.substring(1, sBinaryBaseIntWhole.length());
            nBinaryExpPower = sCutBinaryBase.length();

            sNormalizedBinaryNumber = "1." + sCutBinaryBase + sBinaryBaseIntFraction + "x" + "2^" + nBinaryExpPower;
        }

        return sNormalizedBinaryNumber;
    }

    public String getDenormalizedValue() //Applies exponent value, removing that part in the number
    {
        String sBase = String.valueOf(getWholePart()) + "." + String.valueOf(getFractionalPart()).replace("0.", "");
        Double dDenormalizedBase = Double.parseDouble(sBase) * Math.pow(10, getExpValue());
        String sDenormalizedBase = (getSign() ? '\0' : '-') + String.valueOf(dDenormalizedBase);

        return sDenormalizedBase;
    }

    public String getOriginalValue() //Original Input of the user
    {
        return (getSign() ? '\0' : '-') + getBaseString() + "x" + getExpString();
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
