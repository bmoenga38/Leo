import java.io.*;

//Code Adjustments
//
//If threeBlock.txt is a text file and needs to be converted to a binary file,
// you can write a utility to convert it. Here's an example utility method:


//Run the Utility
//
//        Compile and run the utility to convert threeBlock.txt to threeBlock.bin:
//
//        javac TextToBinaryConverter.java
//        java TextToBinaryConverter
//
//        javac Heapsort.java Buffer.java BufferPool.java ExternalHeapSort.java Stat.java
//        java Heapsort threeBlock.bin 3 statistics.txt
//


public class TextToBinaryConverter {
    public static void convert(String inputFileName, String outputFileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
             DataOutputStream out = new DataOutputStream(new FileOutputStream(outputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    short key = Short.parseShort(parts[0]);
                    short value = Short.parseShort(parts[1]);
                    out.writeShort(key);
                    out.writeShort(value);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        convert("threeBlock.txt", "threeBlock.bin");
    }
}
