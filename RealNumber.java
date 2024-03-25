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
        return "10^" + String.valueOf(getExpValue());
    }
}
