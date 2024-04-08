# CSARCH 2 Simulation Project
## Team Members
1. Aaron Dichoso
2. Luis Razon
3. Merrick Malong
   
## Background
This github project contains a Java GUI Application that simulates the conversion of a decimal or binary number (with exponent values) to its proper IEEE 754 Single Precision Floating Point Format.
The Github application also handles special edge cases that might be encountered upon conversion due to the limitations of the format.
The output of the conversion is shown in the application, with an option to save the results into a .txt file.

## How to Run
1. Compile ConverterGUI.java normally using `javac ConverterGUI.java` in the directory alongside the other files.
2. Run using `java ConverterGUI`.
3. Alternatively, the program can launched using the `Binary32Converter.jar` file.
4. If the program does not run, it is due to the classes being compiled in a higher class version that the one
   in the system supports. To fix this, update the Java JDK to the latest version available.

## Test Cases
### Case 1. Input has Invalid Format - Decimal
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/71b24ac7-a06e-41ea-85a1-e7e920b46b27)


### Case 2. Input has Invalid Format - Binary
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/00787fe1-8569-4460-9adf-ce8358f97606)

### Case 3. Input has no Base Selected
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/f6255241-678d-45a9-8329-c74f9303e495)

### Case 4. Valid Decimal Input Given - Positive Case
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/f9bd85a6-f40c-4579-9537-a4e4e8a25ad7)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/f3b0ae48-3924-4443-ac52-15fb57f162bc)

### Case 5. Valid Binary Input Given - Positive Case
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/ee2ac2ff-ce39-41e7-a110-342421c26e99)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/7223573b-b06c-414e-8881-9c64a4dfbf30)

### Case 6. Valid Decimal Input Given - Negative Case
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/65d5c7f5-2703-4460-afcf-22f7a8b2692e)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/00e28cf2-2399-402c-a3cb-9f2ff34e9f67)

### Case 7. Valid Binary Input Given - Negative Case
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/dd02bb65-14e8-4bb6-8624-01378a003f13)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/ed679e9b-c080-4920-bca0-bb92fd0b077e)

### Case 8. Valid Decimal Input Given - Positive 0 Case
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/6f24adeb-84b0-41b8-8c21-a95d30634267)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/711e884f-b48f-4da2-8964-13bba6f809a9)

### Case 9. Valid Binary Input Given - Positive 0 Case
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/32c9d4d5-a2f3-43fa-8e02-fdcb3f954687)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/52401603-9d22-4ada-8a07-dc874f0c4bb7)

### Case 10. Valid Decimal Input Given - Negative 0 Case
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/0001e7f7-fd9c-40ce-addc-19e038abff0d)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/850920ee-8af9-41a9-b332-67e43dbf7af4)

### Case 11. Valid Binary Input Given - Negative 0 Case
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/2ecec3ba-c07a-4130-9f76-04a291c299ad)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/e7eff5a2-969a-446e-8a4c-4b8d6621d53d)

### Case 12. Valid Decimal Input Given - Positive Infinity Case
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/865021e6-033e-49f2-8fbb-ed959eb36587)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/f66401df-dd1d-4785-8f45-886b76b75780)

### Case 13. Valid Binary Input Given - Positive Infinity Case
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/617c95af-a7ae-4b86-b8f7-bb0e816b4a1e)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/53db9dd3-18dd-4724-8296-d0a8eebfcac4)

### Case 14. Valid Decimal Input Given - Negative Infinity Case
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/bb116da4-2a05-402e-98ae-a10f96f124c4)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/4f233ee4-5c0c-40e8-9a51-c4c65b0b28ac)

### Case 15. Valid Binary Input Given - Negative Infinity Case
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/59be183e-123e-4ac6-b72b-d07584f5f4bf)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/cc8af317-89ef-4402-a04e-1a3f9ac73bd9)

### Case 16. Valid Decimal Input Given - Denormalized Case
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/95cbbd70-e384-442a-86bc-e6703d53268e)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/5ad47396-22b9-4194-b8a8-7ec093e4ee76)
   
### Case 17. Valid Binary Input Given - Denormalized Case 
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/a1763352-8602-4e43-be10-6df390a96d5f)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/08d62ebc-e572-4529-85ce-b0c7c09ac88e)

### Case 18. Valid Decimal Input Given - Save to Output File
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/1eb5aaf7-f1ab-482c-b73d-31d037aa660a)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/7df1acd9-fcad-4a44-86c9-5445a916a49c)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/476624d9-d982-413c-b5ff-c59a5c489851)

### Case 19. Valid Binary Input Given - Save to Output File
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/e774e3cb-d92d-43c1-a1a2-0ef4e8145667)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/4df7c3f2-36a9-41c9-a10a-46ae05f9829d)
![image](https://github.com/ADichoso/CSARCH2_Simulation_Project/assets/39649018/f45c87c3-14c8-4844-8a85-67edcd80f0f9)


