import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * 程序入口
 */

class DrawSee extends JFrame {

    private static final int sx = 50;//游戏区域10*10方块的起始横坐标
    private static final int sy = 50;//游戏区域10*10方块的起始纵坐标
    private static final int w = 10;//每个小方格的边长
    private static final int rw = 400;//游戏区域10*10方块的边长


    private Graphics jg;

//    private Color rectColor = new Color(0xf5f5f5);
    private Color rectColor = new Color(255,255,255);
    /**
     * DrawSee构造方法
     */
    public DrawSee(int[][] map) throws IOException {
        Container p = getContentPane();
        setBounds(0, 0, 1000, 1000);//1000,1000
        setVisible(true);
        p.setBackground(rectColor);
        setLayout(null);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取专门用于在窗口界面上绘图的对象
        jg = this.getGraphics();

        // 绘制游戏区域
//        paintComponents(jg);
//        paintAreas(jg,map);
    }

    //这里需要使用一个重构的方法，对11和12这种具有必经点的问题进行单独考虑
    public void paintAreas(int[][] cmap, List<Integer> cpath, boolean visibleCrossPoint, int pronum) {
        Graphics g = this.jg;
        int[][] map = cmap;
        try {
            //g.setColor(Color.yellow);g.fillRect(20,70,20,30);
            g.setColor(Color.black);
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == 0) {
                        paintAreaOne(g, Color.LIGHT_GRAY, i, j);
                    }
                }
            }
            //这里默认画黑线，太丑了，这里使用紫色的线条
            paintPath(cpath, Color.cyan);//black
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == 2) {
                        paintAreaOne(g, Color.blue, i, j);
                    } else if (map[i][j] == 5) {
                        paintAreaOne(g, Color.red, i, j);
                    } else if (map[i][j] == 9) {
                        paintAreaOne(g, Color.green, i, j);
                    }
                    if (visibleCrossPoint && map[i][j] == 3) {
                        paintAreaOne(g, Color.yellow, i, j);
                    }
                }
            }
            if (pronum == 11)
                paintAreaOne(g, Color.yellow, 10, 21);
            if (pronum == 12) {
//                paintAreaOne(g, Color.yellow, 18, 10);
//                paintAreaOne(g, Color.yellow, 10, 17);
            }
//
//            BufferedImage image=new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
//            paint(image.getGraphics());
//            ImageIO.write(image,"PNG",new File("output.png"));
//

            //激活这两句话会使左上角和右下角出现两个点
//            paintAreaOne(g,Color.black,2,2);
//            paintAreaOne(g,Color.black,map.length - 3,map[0].length - 3);
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }

    public void paintAreas_1(int[][] cmap, List<Integer> cpath, boolean visibleCrossPoint,int num_pic) {
        Graphics g = this.jg;
        int[][] map = cmap;
        try {
            //g.setColor(Color.yellow);g.fillRect(20,70,20,30);
            g.setColor(Color.black);
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == 0) {
                        paintAreaOne(g, Color.LIGHT_GRAY, i, j);
                    }
                }
            }
            //这里默认画黑线，太丑了，这里使用紫色的线条
            paintPath(cpath, Color.cyan);//black
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == 2) {
                        paintAreaOne(g, Color.blue, i, j);
                    } else if (map[i][j] == 5) {
                        paintAreaOne(g, Color.red, i, j);
                    } else if (map[i][j] == 9) {
                        paintAreaOne(g, Color.green, i, j);
                    }
                    if (visibleCrossPoint && map[i][j] == 3) {
                        paintAreaOne(g, Color.yellow, i, j);
                    }
                }
            }

//            BufferedImage image=new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
//            paint(image.getGraphics());
//            ImageIO.write(image,"PNG",new File(String.valueOf(num_pic)+"output.png"));
            //激活这两句话会使左上角和右下角出现两个点
//            paintAreaOne(g,Color.black,2,2);
//            paintAreaOne(g,Color.black,map.length - 3,map[0].length - 3);
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }

    public void paintAreaOne(Graphics g, Color color, int i, int j) {
        g.setColor(color);
        g.fillRect(50 + w * j, 50 + w * i, w, w);
//        g.setColor(Color.yellow);
//        g.drawString("1",50 + w*j,50 + w*(i + 1));
    }

    public void paintAreas2(int[][] cmap, int[][] keys, List<Integer> cpath1, List<Integer> cpath2, boolean visibleCrossPoint) {
        Graphics g = this.jg;
        int[][] map = cmap;
        try {
            //g.setColor(Color.yellow);g.fillRect(20,70,20,30);
            g.setColor(Color.black);
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == 0) {
                        paintAreaOne(g, Color.LIGHT_GRAY, i, j);
                    }
                }
            }

            for (int i = 0; i < keys.length; i++) {
                paintAreaOne(g, Color.black, keys[i][0], keys[i][1]);
            }
            paintPath(cpath1, Color.yellow);
            paintPath(cpath2, Color.blue);
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == 2) {
                        paintAreaOne(g, Color.blue, i, j);
                    } else if (map[i][j] == 5) {
                        paintAreaOne(g, Color.red, i, j);
                    } else if (map[i][j] == 9) {
                        paintAreaOne(g, Color.green, i, j);
                    }
                    if (visibleCrossPoint && map[i][j] == 3) {
                        paintAreaOne(g, Color.yellow, i, j);
                    }
                }
            }

            //这里是人为加上的点，但是这不是关键点后得到的关键点
//            paintAreaOne(g, Color.black, 7, 23);
//            paintAreaOne(g, Color.black, 15, 37);
//            paintAreaOne(g, Color.black, 20, 17);
//            paintAreaOne(g, Color.black, 27, 24);
//            paintAreaOne(g, Color.black, 32, 8);
//            paintAreaOne(g, Color.black, 32, 21);
//            paintAreaOne(g, Color.black, 33, 25);
//            paintAreaOne(g, Color.black, 33, 29);
//            paintAreaOne(g, Color.black, 35, 37);
//            paintAreaOne(g, Color.black, 38, 23);
//            paintAreaOne(g, Color.black, 42, 10);
//            paintAreaOne(g, Color.black, 42, 40);

            paintAreaOne(g, Color.red, 38, 32);
            paintAreaOne(g, Color.red, 15, 20);
            paintAreaOne(g, Color.red, 10, 28);
            paintAreaOne(g, Color.red, 30, 10);
            paintAreaOne(g, Color.yellow, 27, 12);
            paintAreaOne(g, Color.yellow, 20, 28);
//            paintAreaOne(g,Color.black,2,2);
//            paintAreaOne(g,Color.black,map.length - 3,map[0].length - 3);
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }

    public void paintAreaC(Graphics g, Color color, int i, int j) {
        g.setColor(color);
        g.fillRect(50 + w * j, 50 + w * i, w, w);
//        g.setColor(Color.yellow);
//        g.drawString("1",50 + w*j,50 + w*(i + 1));
    }

    public void test(List<SubPath> subPathList) {
        for (SubPath subPath : subPathList) {
            int key = subPath.getKey();
            int i = key / 1000;
            int j = key % 1000;
            paintAreaOne(jg, Color.yellow, i, j);
        }

    }

    public void paintPath(List<Integer> cpath, Color color) {
        int[][] path = new int[cpath.size()][2];
        for (int i = 0; i < cpath.size(); i++) {
            path[i][0] = cpath.get(i) / 1000;
            path[i][1] = cpath.get(i) % 1000;
        }

        for (int i = 0; i < path.length - 1; i++) {

            //如果画黑颜色的点阵图就是这种

            if (path[i][1] == path[i + 1][1]) {
                int min = Math.min(path[i][0], path[i + 1][0]);
                int max = Math.max(path[i][0], path[i + 1][0]);
                for (int j = min; j <= max; j++) {
                    paintAreaOne(jg, color, j, path[i][1]);
                }
            } else {
                int min = Math.min(path[i][1], path[i + 1][1]);
                int max = Math.max(path[i][1], path[i + 1][1]);
                for (int j = min; j <= max; j++) {
                    paintAreaOne(jg, color, path[i][0], j);
                }
            }


            //如果是画黑颜色的线图就是这种
//            jg.setColor(color);
//            jg.drawLine(50 + w*path[i][1] + 5,50 + path[i][0]*w + 5, 50 + w*path[i + 1][1] + 5, 50 + w*path[i + 1][0] + 5);
        }
    }

    public void paintComponents(Graphics g) {
        try {

            // 设置线条颜色为红色
            g.setColor(Color.black);

            // 绘制外层矩形框
            g.drawRect(sx, sy, rw, rw);

            /* 绘制水平10个，垂直10个方格。
             * 即水平方向9条线，垂直方向9条线，
             * 外围四周4条线已经画过了，不需要再画。
             * 同时内部64个方格填写数字。
             */
            for (int i = 1; i < 40; i++) {
                // 绘制第i条竖直线
                g.drawLine(sx + (i * w), sy, sx + (i * w), sy + rw);

                // 绘制第i条水平线
                g.drawLine(sx, sy + (i * w), sx + rw, sy + (i * w));

                // 填写第i行从第1个方格到第8个方格里面的数字（方格序号从0开始）
                for (int j = 0; j < 40; j++) {
                    //drawString(g, j, i);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}