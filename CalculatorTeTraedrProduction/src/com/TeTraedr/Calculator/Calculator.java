package com.TeTraedr.Calculator;

import java.util.Scanner;

public class Calculator {
    private String example = "";


    public float test2() {
        System.out.println("Введіть приклад з двома значеннями");
        Scanner in = new Scanner(System.in);
        String example = in.nextLine();

        return split(example);
    }


    public float test4() {
        Scanner in = new Scanner(System.in);
        if (example.length() == 0) {
            System.out.println("Введіть приклад");
            //   example="115-14+-4/2--11*11";
            example = in.nextLine();
            System.out.println(example);
        }
        return gear4(example);
    }

    public float test5() {
        Scanner in = new Scanner(System.in);
        if (example.length() == 0) {
            System.out.println("Введіть приклад");
            example = "(2*((3+6+1)+(2+2)))*4+(2+5+-4*4)";
            //example = in.nextLine();
            System.out.println(example);
            example = checkExample(example);

        }
        return gear5(example);
    }

    private float split(String example) {
        Help_Methods hlp = new Help_Methods();
        char[] array = example.toCharArray();
        String f, s;
        int pos = -1;

        for (int i = 1; i < example.length(); i++) {
            if (hlp.checkConditionSign(array[i])) {
                pos = i;
                break;
            }
        }

        if (pos == -1) return -1;

        f=example.substring(0,pos);
        s=example.substring(pos+1);

        return new Operators().compute(Float.parseFloat(f), Float.parseFloat(s), array[pos]);
    }

    private int[] countSign(String str) {
        Help_Methods hlp = new Help_Methods();
        char[] chars = str.toCharArray();

        int count = 0;
        for (int i = 1, p = i - 1; i < str.length(); i++, p++) {
            if ((hlp.checkConditionSign(chars[i])) && (!hlp.checkConditionSign(chars[p]))) {
                count++;
            }
        }
        int[] array = new int[count];

        for (int i = 1, p = i - 1, c = 0; c < array.length; i++, p++) {
            if ((hlp.checkConditionSign(chars[i])) && (!hlp.checkConditionSign(chars[p]))) {
                array[c] = i;
                c++;
            }
        }
        return array;
    }

    private float[] arrFromNumber(int[] array, String example) {
        Help_Methods hlp = new Help_Methods();
        char[] ex = example.toCharArray();
        float[] countNumber = new float[array.length + 1];
        String number;
        for (int i = 0; i <= array.length; i++) {
            if (i == 0) {//for first number
                number = "";
                for (int j = 0; j < array[i]; j++) {
                    if (ex[j] != ')' && ex[j] != '(')
                        number += ex[j];
                }
            } else if (array.length >= 2) {//if is more than two number in example
                if (i == array.length) {
                    number = "";
                    for (int j = (array[i - 1] + 1); j < example.length(); j++) {
                        if (ex[j] != ')' && ex[j] != '(')
                            number += ex[j];
                    }
                } else {
                    number = "";
                    for (int j = (array[i - 1] + 1); j < array[i]; j++) {
                        if (ex[j] != ')' && ex[j] != '(')
                            number += ex[j];
                    }
                }
            } else {//if is only two number on example
                number = "";
                for (int j = array[i - 1] + 1; j < example.length(); j++) {
                    number += ex[j];
                }
            }
            countNumber[i] = Float.parseFloat(number);//   System.out.println("Число -->"+countNumber[i]);
        }
        return countNumber;
    }


    private float gear4(String example) {
        int[] array = countSign(example);
        float[] arrayNum = arrFromNumber(array, example);
        char[] arraySign = new Help_Methods().giveSign(array, example);
        if (arraySign.length <= 1) return split(example);
        Priority_Of_Processing piop = new Priority_Of_Processing(arrayNum, arraySign);
        return piop.priority();
    }


    private float gear5(String example) {
        Help_Methods hlp = new Help_Methods();
        int[] array = countSign(example);
        float[] arrayNum = arrFromNumber(array, example);
        char[] arraySign = hlp.giveSign(array, example);
        Priority_Of_Processing piop = new Priority_Of_Processing(arrayNum, arraySign);
        if (arraySign.length == 1) return piop.priority();
        return piop.result(example);
    }


    private float result(float[] arrayNum, char[] arraySign) {
        float result = 0;
        for (int i = 0, n = 1; i < arrayNum.length - 1; i++, n++) {
            if (i == 0) result = new Operators().compute(arrayNum[i], arrayNum[n], arraySign[i]);
            else result = new Operators().compute(result, arrayNum[n], arraySign[i]);
        }
        return result;
    }

    private String checkExample(String op) {
        Help_Methods hlp = new Help_Methods();
        boolean isBrackets = false;
        boolean isCountSign = false;


        char[] carray = op.toCharArray();
        if ((carray[0] == '(' && carray[op.length() - 1] == ')') &&
                (((int[]) hlp.Brackets(example)[0]).length == ((int[]) hlp.Brackets(example)[1]).length) && ((int[]) hlp.Brackets(example)[0]).length == 1) {
            op = hlp.cutStr(op, 0, op.length() - 1);
            System.out.println(op);

        }
    
        if (((int[]) hlp.Brackets(op)[0]).length == ((int[]) hlp.Brackets(op)[1]).length) isBrackets = true;
        if (arrFromNumber(countSign(op), op).length == countSign(op).length + 1) isCountSign = true;

        if ((isBrackets) && (isCountSign)) {
            //all okay
            System.out.println("isCountSign-->" + isCountSign);
            System.out.println("isBrackets-->" + isBrackets);
        } else throw new ArithmeticException();

        return op;
    }

    public float gear_1(float[] arrayN, char[] arrayC) {
        return result(arrayN, arrayC);
    }

    public float gear_2(String op) {
        return gear4(op);
    }

}