import utilities.HuffmanFileUtils;

import java.io.File;
import java.util.Scanner;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("           ___  ___                     __   ___   /  __   __         __   __   ___  __   __     __    \n" +
                "|__| |  | |__  |__   |\\/|  /\\  |\\ |    |  \\ |__   /  /  ` /  \\  |\\/| |__) |__) |__  /__` /__` | /  \\ |\\ | \n" +
                "|  | \\__/ |    |     |  | /~~\\ | \\|    |__/ |___ /   \\__, \\__/  |  | |    |  \\ |___ .__/ .__/ | \\__/ | \\| \n" +
                "                                                                                                                    ");

        System.out.println("Please select an option : \n\t[0] : Compress File \n\t[1] : Compress Folder \n\t[2] : Decompress Huffman File \n\t[3] : Exit    ");
        Scanner scanner = new Scanner(System.in);
        int i=9;
        while(i != 3)
        {
            i= scanner.nextInt();

            long end,start;

            switch (i){
                case 0:
                    System.out.println("Enter Path of File: ");
                    String s = scanner.next();
                    System.out.println("Compressing ... ");
                    start  = System.currentTimeMillis();
                    HuffmanFileUtils.compressFile(new File(s));
                    end = System.currentTimeMillis();
                    System.out.printf("Execution Time:     %d milliseconds\n", end-start );
                    System.out.println("Done!");
                    break;
                case 1:
                    System.out.println("Enter Directory of Folder: ");
                    String s1 =scanner.next();
                    System.out.println("Compressing ... ");
                    start  = System.currentTimeMillis();
                    HuffmanFileUtils.compressFolder(s1);
                    end = System.currentTimeMillis();
                    System.out.printf("Execution Time:     %d milliseconds\n",end-start);
                    System.out.println("Done!");
                    break;
                case 2:
                    System.out.println("Enter Path of Huffman File: ");
                    String s2 =scanner.next();
                    System.out.println("Decompressing ... ");
                    HuffmanFileUtils.decompressionFromHuffmanFile(new File(s2));
                    System.out.println("Done!");
                    break;
                case 3:
                    continue;
                default:
                    System.out.println("Invalid Input, select options [0-3]");
                    break;
            }
            System.out.println("Please Select Another Option.");
        }
    }
}
