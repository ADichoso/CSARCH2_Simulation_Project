import java.util.Scanner;

public class binary32converter
{
    static Scanner sc;
    public static void main(String[] args)
    {
        sc = new Scanner(System.in);

        System.out.println("Put d for decimal mode and b for binary mode:");
        String sInput = sc.nextLine();

        if(sInput.equals("d"))
            decimalToBinary32();
        else if(sInput.equals("b"))
            binaryToBinary32();
        
    }

    public static void decimalToBinary32()
    {
        System.out.println("Input a floating point number (With Optional Exponent) to convert to binary 32! (Ex: 65.0x10^3)");
        
        //Step 1: Get user input
        String sInput = sc.nextLine();
        System.out.println("Converting: " + sInput);

        RealNumber realNumber = new RealNumber(sInput);
        
        System.out.println("================================");
        System.out.println("Original Number Value: " + realNumber.getOriginalValue());
        
        System.out.println("================================");
        System.out.println("Is Number Positive? " + realNumber.getSign());

        System.out.println("================================");
        System.out.println("Base: " + realNumber.getBaseString());
        System.out.println("Exponent:" + realNumber.getExpString());

        System.out.println("================================");
        System.out.println("Base Whole: " + realNumber.getWholePart());
        System.out.println("Base Fraction: " + realNumber.getFractionalPart());

        System.out.println("================================");
        System.out.println("Exp Power: " + realNumber.getExpValue());

        System.out.println("================================");
        System.out.println("Denormalized Number Value: " + realNumber.getDenormalizedValue());

        //CONVERSION STARTS HERE
        
        System.out.println("=================DECIMAL TO BINARY CONVERSION=================");
        BinaryNumber binaryNumber = BinaryNumber.valueOf(realNumber);
        
        System.out.println("================================");
        System.out.println("Is Number Positive? " + binaryNumber.getSign());

        System.out.println("================================");
        System.out.println("Base Whole Number Binary: " + binaryNumber.getWholePart());

        System.out.println("================================");
        System.out.println("Base Fractional Number Binary: " + binaryNumber.getFractionalPart());

        //Step 9: Construct the denormalized binary number (xxxx.yyyy)
        System.out.println("================================");
        System.out.println("Denormalized Binary Number: " + binaryNumber.getOriginalValue());
        
        System.out.println("================================");
        System.out.println("Normalized Binary Number: " + binaryNumber.getNormalizedValue());

        System.out.println("================================");

        conversion(binaryNumber);
    }

    public static void binaryToBinary32()
    {
        System.out.println("Input a binary floating point number (With Optional Exponent) to convert to binary 32! (Ex: 101101.011x2^6)");
        
        //Step 1: Get user input
        String sInput = sc.nextLine();
        System.out.println("Converting: " + sInput);

        BinaryNumber binaryNumber = new BinaryNumber(sInput);
        
        System.out.println("================================");
        System.out.println("Original Number Value: " + binaryNumber.getOriginalValue());
        
        System.out.println("================================");
        System.out.println("Is Number Positive? " + binaryNumber.getSign());

        System.out.println("================================");
        System.out.println("Base: " + binaryNumber.getBaseString());
        System.out.println("Exponent:" + binaryNumber.getExpString());

        System.out.println("================================");
        System.out.println("Base Whole: " + binaryNumber.getWholePart());
        System.out.println("Base Fraction: " + binaryNumber.getFractionalPart());

        System.out.println("================================");
        System.out.println("Exp Power: " + binaryNumber.getExpValue());

        System.out.println("================================");
        System.out.println("Normalized Number Value: " + binaryNumber.getNormalizedValue());

        System.out.println("================================");
        System.out.println("Denormalized Number Value: " + binaryNumber.getDenormalizedValue());

        conversion(binaryNumber);
    }

    public static void conversion(BinaryNumber binaryNumber)
    {
        System.out.println("=================BINARY TO BINARY32 CONVERSION=================");

        Binary32Number binary32Number = new Binary32Number(binaryNumber);
        System.out.println("Original Value: " + binaryNumber.getOriginalValue());
        System.out.println("Normalized Value: " + binaryNumber.getNormalizedValue());
        System.out.println("Sign: " + binary32Number.getSignString());
        System.out.println("Exponent: " + binary32Number.getExponentString());
        System.out.println("Mantissa: " + binary32Number.getMantissaString());
        System.out.println("Binary Representation: 0b" + binary32Number.toString());
        System.out.println("Hex Representation: 0x" + binary32Number.toStringHex());
        System.out.println("Case: " + binary32Number.getCase());
        binary32Number.outputToFile(binaryNumber, binary32Number);
    }
}
