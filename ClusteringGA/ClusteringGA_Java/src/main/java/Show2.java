import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class Show2 {

    XYSeries data;
    XYSeriesCollection dataset;
    JFreeChart freeChart;
    ChartPanel chartPanel;
    JFrame frame;
    public Show2(){
        //设置散点图数据集
        //设置第一个
        this.data = new XYSeries("point");
        //添加到数据集
        dataset = new XYSeriesCollection();
        dataset.addSeries(data);

        //实现简单的散点图，设置基本的数据
        freeChart = ChartFactory.createScatterPlot(
                "数据散点图",// 图表标题
                "Category",//y轴方向数据标签
                "Score",//x轴方向数据标签
                dataset,//数据集，即要显示在图表上的数据
                PlotOrientation.VERTICAL,//设置方向
                true,//是否显示图例
                true,//是否显示提示
                false//是否生成URL连接
        );


        //以面板显示
        chartPanel = new ChartPanel(freeChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 400));

        //创建一个主窗口来显示面板
        frame = new JFrame("散点图");
        frame.setLocation(500, 400);
        frame.setSize(600, 500);
        //将主窗口的内容面板设置为图表面板
        frame.setContentPane(chartPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public  void update(int[][] newData) {
        this.data.clear();
        for (int i = 0; i < 50; i++) {
            data.add(newData[i][0],newData[i][1]);
        }

        dataset.removeAllSeries();
        dataset.addSeries(data);

        JFreeChart freeChart = ChartFactory.createScatterPlot(
                "result",// 图表标题
                "length",//y轴方向数据标签
                "blocks",//x轴方向数据标签
                dataset,//数据集，即要显示在图表上的数据
                PlotOrientation.VERTICAL,//设置方向
                true,//是否显示图例
                true,//是否显示提示
                false//是否生成URL连接
        );
        chartPanel.setChart(freeChart);
        frame.repaint();
        frame.revalidate();
    }

}