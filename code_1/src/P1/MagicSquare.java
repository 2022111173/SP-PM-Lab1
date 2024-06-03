package P1;

import java.io.*;
import java.util.ArrayList;

public class MagicSquare {

    public static boolean isLegalMagicSquare(String fileName)  {
        int rows = 0;
        ArrayList<String> stringArrayList = new ArrayList<>();
        String string;
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\P1\\txt\\" + fileName))) {
            while ((string = reader.readLine()) != null) {  //按行读入
                stringArrayList.add(string);
                rows++;
                if (string.contains(".") || string.contains("-")) { //检测输入为正整数
                    System.out.print("输入的数应全部为正整数");
                    return false;
                } else if (string.contains(" ")) {                  //检测是否用\t分割
                    System.out.print("应使用\\t分割数字");
                    return false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int[][] matrix = new int[rows][rows];
        String[] strings;
        int i,j;
        for( i = 0 ; i < rows ; i++){
            strings = stringArrayList.get(i).split("\t");     //分割每一行得到每个数
            if (rows != strings.length) {                           //行列数不相等或不为矩阵
                System.out.print("输入数据不为方阵，不符合定义");
                return false;
            }
            for( j = 0 ; j < rows ; j++){
                matrix[i][j] = Integer.parseInt(strings[j]);
            }
        }
        int addition = 0;
        int sum = 0;
        for( i = 0 ; i < rows ; i++ ){
            addition += matrix[i][i];
        }
        for( i = 0 ; i < rows ; i++ ){
            sum += matrix[rows-1-i][i];
        }
        if(addition != sum){
            return false;
        }
        sum = 0;
        for( i = 0 ; i < rows ; i++ ){
            for( j = 0 ; j < rows ; j++){
                sum += matrix[i][j];
            }
            if(addition != sum){
                return false;
            }
            sum = 0;
        }
        for( i = 0 ; i < rows ; i++ ){
            for( j = 0 ; j < rows ; j++){
                sum += matrix[j][i];
            }
            if(addition != sum){
                return false;
            }
            sum = 0;
        }
        return true;
    }

    public static boolean generateMagicSquare(int n) {
        //该函数只能生成正偶数阶幻方
        if((n <= 0)||(n % 2 == 0)) {
            System.out.println("请输入正奇数以构造幻方");
            return false;
        }
        //初始化方阵，并设置初始填充位置，从i=1开始填充int row = 0, col = n / 2即中间列的第一行
        int[][] magic = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;

        for (i = 1; i <= square; i++) {     //填充所有格子
            magic[row][col] = i;    //填充数字i
            if (i % n == 0)         //已斜向填充过一轮即右上格元素已填充
                row++;              //行数加一开始斜向填充下一组数据
            else {
                if (row == 0)       //填充格在第一行
                    row = n - 1;    //改为最后一行
                else
                    row--;          //行数减一
                if (col == (n - 1)) //填充格在 最后一列
                    col = 0;        //改为第一列
                else
                    col++;          //列数加一
            }
        }

        //将生成幻方写入6.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\P1\\txt\\6.txt"))) {
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++)
                    writer.write(magic[i][j] + "\t");
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public static void main(String[] args)  {
        System.out.println("1.txt："+isLegalMagicSquare("1.txt"));
        System.out.println("2.txt："+isLegalMagicSquare("2.txt"));
        System.out.println("3.txt："+isLegalMagicSquare("3.txt"));
        System.out.println("4.txt："+isLegalMagicSquare("4.txt"));
        System.out.println("5.txt："+isLegalMagicSquare("5.txt"));
        if(generateMagicSquare(11))
            System.out.println("6.txt："+isLegalMagicSquare("6.txt"));
    }
}