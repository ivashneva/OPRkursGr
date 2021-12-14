package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Regression {


    public static final int LINEAR = 0;
    public static final int EXP1 = 1;
    public static final int HALFLOG = 2;


    public String readFromFile(int typeregression) {
        ArrayList<String> list = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        Scanner scan = null;
        String nameFile = "";
        switch (typeregression) {
            case LINEAR:

                //  nameFile = "Линейная_регрессия2.txt";
                nameFile = "123.txt"; //https://math.semestr.ru/corel/student.php

                break;
            case EXP1:

                nameFile = "Экспоненциальная1.txt";
                break;
            case HALFLOG:

                nameFile = "Полулогарифмическая.txt";
                break;
            default:

                break;
        }


        try {
            scan = new Scanner(new File(nameFile));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                list.add(line);
                sb.append(line + "\n");
            }
            scan.close();
        } catch (
                FileNotFoundException e) {


        }


        return sb.toString();

    }


    public Resultmy regression(int typeregression, ArrayList<String> list) {
        Resultmy resultmy = new Resultmy();


        try {
            double sumx = 0;
            double sumxx = 0;
            double sumy = 0;
            double sumyy = 0;
            double sumxy = 0;
            double x = 0, y = 0;
            double b = 0, a = 0;
            double rr = 0;

            int n = list.size();

            for (int i = 0; i < n; i++) {
                String line = list.get(i);//берем линию
                String[] bbuf = line.split(" ");
                try {
                    x = (Float.parseFloat(bbuf[0]));//преобраз в число и кладем в х
                } catch (NumberFormatException e) {
                    resultmy.message = "Ошибка преобразования переменной " + bbuf[0];
                    resultmy.error = 1;
                    e.printStackTrace();
                }

                try {
                    y = (Float.parseFloat(bbuf[1]));//преобраз в число и кладем в у
                } catch (NumberFormatException e) {
                    resultmy.message = "Ошибка преобразования переменной " + bbuf[1];
                    resultmy.error = 2;
                    e.printStackTrace();
                }


                switch (typeregression) {
                    case LINEAR:


                        break;
                    case EXP1:
                        y = (double) Math.log(y);
                        break;
                    case HALFLOG:
                        x = (double) Math.log(x);
                        break;


                    default:

                        break;
                }


                sumx += x;
                sumxx += x * x;
                sumy += y;
                sumyy += y * y;
                sumxy += x * y;

                b = (sumxy - (sumx) * (sumy) / list.size()) / (sumxx - (sumx) * (sumx) / list.size());
                a = sumy / list.size() - b * (sumx / list.size());
                rr = (sumxy - (sumx * sumy) / list.size()) * (sumxy - (sumx * sumy) / list.size()) /
                        ((sumxx - ((sumx) * (sumx)) / list.size()) * (sumyy - ((sumy) * (sumy)) / list.size()));
                System.out.println(" " + x + "  " + y + "  " + x * x + "  " + y * y + "  " + x * y);


            }

            double sumDy2 = 0;
            for (int j = 0; j < n; j++) {
                String line1 = list.get(j);//берем линию
                String[] bbuf = line1.split(" ");
                x = (Float.parseFloat(bbuf[0]));//преобраз в число и кладем в х
                y = (Float.parseFloat(bbuf[1]));//преобраз в число и кладем в у
                double yrasch = calculately(x, b, a, typeregression);
                ItemMy itemMy = new ItemMy();
                itemMy.x = x;
                itemMy.y = y;
                itemMy.yrasch = yrasch;
                resultmy.itemMyArrayList.add(itemMy);
                double dy = yrasch - y;
                sumDy2 = sumDy2 + dy * dy;
                System.out.println("------x равно " + x + "  " + "y равно " + y + "  " + "y рассчетное равно " + yrasch);
            }


            double xсреднее = sumx / n;
            double yсреднее = sumy / n;
            double S2x = sumxx / n - xсреднее * xсреднее;
            double S2y = sumyy / n - yсреднее * yсреднее;

            double sxсреднеквадратическое_отклонение = (double) Math.sqrt(S2x); //Среднеквадратическое отклонение
            double syСреднеквадратическое_отклонение = (double) Math.sqrt(S2y); //Среднеквадратическое отклонение


//Несмещенной оценкой дисперсии возмущений является величина
            int m = 1;
            double s2y_несмещенная_оценка_дисперсии = sumDy2 / (n - m - 1);
            double sy_стандартная_ошибка_оценки = (double) Math.sqrt(s2y_несмещенная_оценка_дисперсии); // стандартная ошибка оценки (стандартная ошибка регрессии).

            double sa_стандартное_отклонение_случайной_величины_a = sy_стандартная_ошибка_оценки * Math.sqrt(sumxx) / (n * sxсреднеквадратическое_отклонение);
            double sb_стандартное_отклонение_случайной_величины_b = sy_стандартная_ошибка_оценки / (Math.sqrt(n) * sxсреднеквадратическое_отклонение);

            double tb = b / sb_стандартное_отклонение_случайной_величины_b;
            double ta = a / sa_стандартное_отклонение_случайной_величины_a;


            double fisher=     rr*(n-2)/(1-rr) ;                //https://math.semestr.ru/corel/fisher.php

            //tкрит (n-m-1;α/2) = (48;0.025) = 2.009
            double tкрит = 2.01; //взято из таблицы Стьюдента на уровне значимости α=0.05 (выбираем сами) и количество измерений n-2
            System.out.println("Сумма x равна" + " " + sumx + "  | " + "Сумма y равна" + " " + sumy + "  | " + "Сумма х в квадрате равен " + " " + sumxx + "  | " + "Сумма y в квадрате равен " + " " + sumyy + " |  " + "х*y равно " + sumxy);

            System.out.println("Коэффициет a равен" + " " + b + " | " + "Коэффициент b равен" + " " + a);
            System.out.println("Величина rr равна" + " " + rr);

            System.out.println("Xсреднее=" + xсреднее + " Yсреднее=" + yсреднее);
            System.out.println("S2x=" + S2x + " S2y=" + S2y);
            System.out.println("sumDy2=" + sumDy2);
            System.out.println("s2y=" + s2y_несмещенная_оценка_дисперсии + " sy=" + sy_стандартная_ошибка_оценки);
            System.out.println("sa_стандартное_отклонение_случайной_величины_a=" + sa_стандартное_отклонение_случайной_величины_a);
            System.out.println("sb_стандартное_отклонение_случайной_величины_b=" + sb_стандартное_отклонение_случайной_величины_b);
            //http://old.exponenta.ru/educat/referat/XIkonkurs/student5/tabt-st.pdf

            System.out.println("t_b=" + tb);
            System.out.println("t_a=" + ta);


            if (ta > tкрит) {
                System.out.println(" Поскольку " + ta + " > " + tкрит + ", то статистическая значимость коэффициента регрессии a подтверждается");
            } else {
                System.out.println(" Поскольку " + ta + " > " + tкрит + ", то статистическая значимость коэффициента регрессии a НЕ подтверждается");
            }
            if (tb > tкрит) {
                System.out.println(" Поскольку " + tb + " > " + tкрит + ", то статистическая значимость коэффициента регрессии b подтверждается");
            } else {
                System.out.println(" Поскольку " + tb + " > " + tкрит + ", то статистическая значимость коэффициента регрессии b НЕ подтверждается");
            }


            resultmy.setTa(ta);
            resultmy.setTb(tb);
            resultmy.setTкрит(tкрит);
            resultmy.setAlpha(0.05);
            resultmy.setA(b);
            resultmy.setB(a);
            resultmy.setRr(rr);
            resultmy.setFisher(fisher);

            resultmy.setKoefKorr(Math.sqrt(rr));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return resultmy;
    }

    private double calculately(double x, double a, double b, int typeregression) {
        double y = 0;
        switch (typeregression) {
            case LINEAR:
                y = a * x + b;
                break;
            case EXP1:
                b = (double) Math.exp(b);
                y = (double) (b * Math.exp(a * x));
                break;
            case HALFLOG:
                y = (double) (b + a * Math.log(x));
                break;
            default:
                break;
        }

        return y;
    }
//    https://cyberpedia.su/17x19ca3.html
}

