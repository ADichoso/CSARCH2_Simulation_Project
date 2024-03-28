public class RealNumber {
    private int nWholePart;
    private Double dFractionalPart;
    private int nExpValue;
    private boolean isPositive;

    public void setWholePart(int nWholePart){this.nWholePart = nWholePart;}
    public void setFractionalPart(Double dFractionalPart){this.dFractionalPart = dFractionalPart;}
    public void setExpValue(int nExpValue){this.nExpValue = nExpValue;}
    public void setSign(boolean isPositive){this.isPositive = isPositive;}

    public int getWholePart(){return nWholePart;}
    public Double getFractionalPart(){return dFractionalPart;}
    public int getExpValue(){return nExpValue;}
    public boolean getSign(){return isPositive;}

    public RealNumber()
    {
        this.setWholePart(0);
        this.setFractionalPart(0.0);
        this.setExpValue(0);
        this.setSign(true);
    }

    public RealNumber(String sInput)
    {
        RealNumber realNumber = RealNumber.valueOf(sInput);

        this.setWholePart(realNumber.getWholePart());
        this.setFractionalPart(realNumber.getFractionalPart());
        this.setExpValue(realNumber.getExpValue());
        this.setSign(realNumber.getSign());
    }

    public static RealNumber valueOf(String sString)
    {
        RealNumber realNumber = new RealNumber();
        
        //Step 1: Split into base & exponent
        String sBase = sString.split("x")[0];
        String sExp = sString.split("x")[1];

        //Step 2: Get Sign of the number
        realNumber.setSign(!sBase.contains("-"));

        //Step 3: Get power of exponent
        realNumber.setExpValue(Integer.parseInt(sExp.split("\\^")[1]));

        //Step 4: Separate Whole and Fractional Part of Number
        realNumber.setWholePart(Integer.parseInt(sBase.replace("-", "").split("\\.")[0]));
        realNumber.setFractionalPart(Double.parseDouble("0." + sBase.replace("-", "").split("\\.")[1]));

        return realNumber;
    }

    public String getDenormalizedValue() //Applies exponent value, removing that part in the number
    {
        String sBase = "";
        String sFractionalPart = String.valueOf(getFractionalPart()).replace("0.", "");
        String sWholePart = String.valueOf(getWholePart());


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

        String sDenormalizedBase = (getSign() ? '\0' : '-') + sBase;

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
        return "10^" + String.valueOf(getExpValue());
    }
}
