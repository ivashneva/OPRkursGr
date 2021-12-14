package com.company;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;


//https://coderlessons.com/tutorials/java-tekhnologii/nauchitsia-kachatsia/swing-kratkoe-rukovodstvo
public class RegressionAll {
    private static final String ПРОЧИТАТЬ_ИЗ_ФАЙЛА = "1";
    private static final String РАССЧИТАТЬ = "2";
    private static final String ОТОБРАЗИТЬ_ГРАФИК = "3";
    private static final String TITLE = "Графики исходных и расчетных данных";
    private JFrame mainFrame;

    private JLabel statusLabel;
    private JLabel statusLabelRR;
    private JLabel statusLabelTa;
    private JLabel statusLabelTb;
    private JPanel controlPanel0;
    private JPanel controlPanel1;
    private JPanel controlPanel111;
    private JPanel controlPanel2;
    private int index = 0;
    private JTextArea area1Text;
    private Regression regression = new Regression();
    private Resultmy resultmy;

    public RegressionAll() {

    }

    private int showWarningMessage() {
        String[] buttonLabels = new String[]{"Да", "Нет"};
        String defaultOption = buttonLabels[0];
        Icon icon = null;

        return JOptionPane.showOptionDialog(mainFrame,
                "Вы действительно хотите выйти?",
                "Внимание!",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                icon,
                buttonLabels,
                defaultOption);
    }


    public void run() {
        mainFrame = new JFrame("Рассчет регресии");
        mainFrame.setSize(1000, 800);
      //  mainFrame.setLayout(new GridLayout(1,10));


        statusLabel = new JLabel("", JLabel.LEFT);
        statusLabelRR = new JLabel("", JLabel.LEFT);
        statusLabelTa = new JLabel("", JLabel.LEFT);
        statusLabelTb = new JLabel("", JLabel.LEFT);
        //   statusLabel.setSize(350, 100);

//        mainFrame.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                showWarningMessage();
//            }
//        });


        controlPanel0 = new JPanel();
        controlPanel111 = new JPanel();
        controlPanel1 = new JPanel();
        controlPanel2 = new JPanel();
        controlPanel0.setLayout(new BoxLayout(controlPanel0, BoxLayout.Y_AXIS));

        controlPanel1.setLayout(new BoxLayout(controlPanel1, BoxLayout.X_AXIS));


        controlPanel2.setLayout(new GridLayout(4,4)); //https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html#box
        controlPanel0.add(statusLabel);
        controlPanel0.add(statusLabelRR);
        controlPanel0.add(statusLabelTa);
        controlPanel0.add(statusLabelTb);
        controlPanel0.add(controlPanel111);
        controlPanel0.add(controlPanel1);
        mainFrame.add(controlPanel0);

        statusLabel.setText("введите данные вручную, или прочитайте из файла");


        // Cоздание многострочных полей
        area1Text = new JTextArea("Поле для ввода данных.\n" +
                "В строчке через пробел\nвводятся X и Y", 5, 5);

        // Шрифт и табуляция
        area1Text.setFont(new Font("Dialog", Font.PLAIN, 14));
        area1Text.setTabSize(10);


        controlPanel1.add(new JScrollPane(area1Text));
        controlPanel1.add(controlPanel2);

        JButton jButtonReadFromFile = new JButton("Прочитать из файла");
        JButton jButtonCalcul = new JButton("Рассчитать");
        JButton cancelButton = new JButton("Отобразить график");


        jButtonReadFromFile.setActionCommand(ПРОЧИТАТЬ_ИЗ_ФАЙЛА);
        jButtonCalcul.setActionCommand(РАССЧИТАТЬ);
        cancelButton.setActionCommand(ОТОБРАЗИТЬ_ГРАФИК);

        jButtonReadFromFile.addActionListener(new ButtonClickListener());
        jButtonCalcul.addActionListener(new ButtonClickListener());
        cancelButton.addActionListener(new ButtonClickListener());


        String[] items = {
                "Линейная"
//                "Экспотенциальная"
                ,
                "Полулогарифмическая"
        };
        JComboBox editComboBox = new JComboBox(items);
        editComboBox.setEditable(true);

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                index = editComboBox.getSelectedIndex();
                //        area1Text.setText("");
//                readFromFile();
            }
        };


        editComboBox.addActionListener(actionListener);


        controlPanel2.add(editComboBox);
        controlPanel2.add(jButtonReadFromFile);
        controlPanel2.add(jButtonCalcul);
        controlPanel2.add(cancelButton);
        RefineryUtilities.centerFrameOnScreen(mainFrame);
        mainFrame.setVisible(true);
    }


    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals(ПРОЧИТАТЬ_ИЗ_ФАЙЛА)) {
                readFromFile();

            }
            if (command.equals(РАССЧИТАТЬ)) {

                String[] strings = area1Text.getText().split("\n");

                ArrayList<String> list = new ArrayList<String>(Arrays.asList(strings));

                resultmy = regression.regression(index, list);
                // Resultmy ch = regression.regression(Regression.EXP);
                System.out.println(resultmy);
                //System.out.println(ch);
                statusLabel.setText(resultmy.showResult());

                double ta = resultmy.getTa();
                double tb = resultmy.getTb();
                double tкрит = resultmy.getTкрит();
                double alpha = resultmy.getAlpha();

                statusLabelRR.setText("<html>Коэффициент детерминации   <p style=\"color:red;\">" + resultmy.getRr() + "</p>" +
                        "<br>Коэффициент корреляции   <p style=\"color:red;\">" + resultmy.getKoefKorr() + "</p>" +
                        "<br>Критерий Фишера   <p style=\"color:red;\">" + resultmy.getFisher() + "</p>" +
                        "</html>");


                if (ta > tкрит) {
                    statusLabelTa.setText("<html>Поскольку для  коэффициента a критерий Стьюдента   <p style=\"color:red;\">" + ta + " > " + tкрит + "(табличное значение)</p>то статистическая значимость коэффициента регрессии подтверждается</html>");
                } else {
                    statusLabelTa.setText("<html>Поскольку для  коэффициента a критерий Стьюдента   <p style=\"color:red;\">" + ta + " < " + tкрит + "(табличное значение)</p>то статистическая значимость коэффициента регрессии не подтверждается</html>");
                }
                if (tb > tкрит) {
                    statusLabelTb.setText("<html>Поскольку для  коэффициента b критерий Стьюдента   <p style=\"color:red;\">" + tb + " > " + tкрит + "(табличное значение)</p>то статистическая значимость коэффициента регрессии подтверждается</html>");
                } else {
                    statusLabelTb.setText("<html>Поскольку для  коэффициента b критерий Стьюдента   <p style=\"color:red;\">" + tb + " < " + tкрит + "(табличное значение)</p>то статистическая значимость коэффициента регрессии не подтверждается</html>");
                }


            }
            if (command.equals(ОТОБРАЗИТЬ_ГРАФИК)) {
                {
                    //   statusLabel.setText("ОТОБРАЗИТЬ ГРАФИК");
                    showGraphic(resultmy);
                }
            }
        }
    }


    private void showGraphic(Resultmy resultmy) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame(TITLE);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                XYSeriesCollection xySeriesCollection = new XYSeriesCollection();

                XYSeries расчетные_данные = new XYSeries("Расчетные данные");
                XYSeries исходные_данные = new XYSeries("Исходные данные");
                for (int i = 0; i < resultmy.itemMyArrayList.size(); i++) {
                    ItemMy itemMy = resultmy.itemMyArrayList.get(i);
                    расчетные_данные.add(itemMy.x, itemMy.yrasch);
                    исходные_данные.add(itemMy.x, itemMy.y);
                }
                xySeriesCollection.addSeries(расчетные_данные);
                xySeriesCollection.addSeries(исходные_данные);


                JFreeChart chart = ChartFactory.createXYLineChart(
                        TITLE, null, null, xySeriesCollection,
                        PlotOrientation.VERTICAL, true, true, false);

                final ChartPanel chartPanel = new ChartPanel(chart);
                final XYPlot plot = chart.getXYPlot();

                XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                renderer.setSeriesLinesVisible(0, true);
                renderer.setSeriesShapesVisible(0, true);
                renderer.setSeriesLinesVisible(1, false);
                renderer.setSeriesShapesVisible(1, true);
                plot.setRenderer(renderer);


                plot.getRenderer().setSeriesPaint(2, new Color(255, 224, 64));

                XYPointerAnnotation pointer1;
                pointer1 = new XYPointerAnnotation(" Normal 1 ", -2.15, 0.65, Math.PI);
                pointer1.setBaseRadius(35.0);
                pointer1.setTipRadius(10.0);
                pointer1.setPaint(Color.yellow);
                pointer1.setBackgroundPaint(Color.red);
                pointer1.setFont(new Font("SansSerif", Font.PLAIN, 12));
                pointer1.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
                plot.addAnnotation(pointer1);

                XYTextAnnotation annotation = new XYTextAnnotation("  График  ", 2.5, 100.18);
                annotation.setBackgroundPaint(new Color(255, 224, 64));
                annotation.setFont(new Font("SansSerif", Font.PLAIN, 9));


                frame.setContentPane(chartPanel);

                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                frame.setResizable(false);
            }
        });
    }


    private void readFromFile() {

        String s = regression.readFromFile(index);
        if (s.length() != 0) {
            area1Text.setText(s);
        } else {
            area1Text.setText("");
            statusLabel.setText("ошибка чтения, введите данные вручную");
        }
    }
}