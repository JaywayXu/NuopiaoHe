//import sun.misc.PostVMInitHook;//正文中找不到使用的地方

import java.io.IOException;
import java.util.*;
import java.io.*;
import java.util.ArrayList;


public class Start {


    public static int[] startValue;
    public static int objectsNum;
    //种群大小
    public static int INITSIZE = 100;
    //迭代次数-世代数
    public static int LOOP = 100;//100


    public static int Slices = 100;//一共有多少图片的切片
    public static int slice_loop;

    public static int[][] find_solutions;
    public static int question_num = 3;
    public static int Independent_runs = 1;//独立运行的次数


    //问题1-4,6-8   种群大小为100 迭代次数为100 可求解
    //问题5   种群大小为1500 迭代次数为1000 可求解
    //问题9   种群大小为200 迭代次数为100 可求解
    //问题10   种群大小为2000 迭代次数为500 可求解
    //问题12   种群大小为2000 迭代次数为200可求解
    //问题101  是问题1去掉了(18,27)这个点
    public static int pic_num = 22;//表示画出图形的数量
    //1-9,2-24,3-13,4-9,5-24,6-5,7-16,8-48,9-105,10-1280,11-4,12-22

    public static void main(String[] args) throws IOException, InterruptedException {
        int QUESTION_NUM = question_num;//QUESTION_NUM表示问题的索引
        if (QUESTION_NUM == 5) {
            INITSIZE = 1500;//1500
            LOOP = 1000;//1000
        } else if (QUESTION_NUM == 10) {
            INITSIZE = 2000;//2000
            LOOP = 500;//500
        } else if (QUESTION_NUM == 9) {
            INITSIZE = 200;//200
            LOOP = 100;//100
        } else if (QUESTION_NUM == 12) {
            INITSIZE = 2000;//2000
            LOOP = 200;//200
        } else if (QUESTION_NUM == 101) {
            //101问题实在1问题中改的
            INITSIZE = 100;//100
            LOOP = 100;//100
        }
        slice_loop = LOOP / Slices;
        find_solutions = new int[Independent_runs][Slices];
        //count计算的是时间，用于计算多次重复运行耗时
        long count = 0;
        //i这里是表示重复随机运行的次数
        for (int i = 1; i <= Independent_runs; i++) {
            System.out.println("################第" + (i) + "轮结果##################");
            //这里使用start程序开始进行，传入问题索引，是否画出来，是否打印出来
            long cur = Start.start(QUESTION_NUM, false, true, i);//QUESTION_NUM,true,false
            System.out.println("####耗时：" + cur);
            count += cur;
        }
        System.out.println("------------算法复杂度结果如下--------------");
        System.out.println("----T0:58ms");
        System.out.println("----T" + QUESTION_NUM + "/T0:" + (double) count / 58);
//        for (int i = 0; i < find_solutions.length; i++) {
//            for (int j = 0; j < find_solutions[0].length; j++) {
//                System.out.println("i:    " + i + "         " + "j:   " + j + "              " + "num_find:    " + find_solutions[i][j]);
//            }
//        }//end for
    }//end main


    //测试问题编号


    public static long start(int QUESTION_NUM, boolean isPaint, boolean isPrint, int times) throws IOException, InterruptedException {

        long startMs = System.currentTimeMillis();

        //获取mat文件资源，生成地图相关信息
        InitData initData = new InitData("src/main/resources/data/Problem_" + QUESTION_NUM + ".mat");

        //标记地图上的可行部分和拥堵点信息赋值
        int[][] map = MapInfo.genMapInfo(initData);

        List<Path> paths = null;

        //problem11
        List<Path> paths_1 = null;
        List<Path> paths_2 = null;

        //problem12
        List<Path> paths_sy1 = null;
        List<Path> paths_sy2 = null;
        List<Path> paths_y1y2 = null;
        List<Path> paths_y2y1 = null;
        List<Path> paths_y1e = null;
        List<Path> paths_y2e = null;
        //Show2 show = new Show2();

        //question_num <= 10 start
        if(question_num <= 10) {
            int start_x = initData.START_x;
            int start_y = initData.START_y;
            int goal_x = initData.GOAL_x;
            int goal_y = initData.GOAL_y;

            //获得交叉路口位置
            Point pointSet = new Point(initData);

            //额外目标数
            objectsNum = pointSet.objectives.size();
            pointSet.genDistanceMatrix2();

            //计算出有向图
            HashMap<Integer, List<SubPath>> distanceMap = pointSet.distanceMap;

            //获得初始种群
            List<Path> Ini_P = new ArrayList<>();//表示初始种群
            Set<Path> Non_repeating_sets = new HashSet<>();//不重复的集合
            startValue = new int[objectsNum];


            if (objectsNum > 0) {
                startValue = initData.startValue;
            }
            //整个种群的初始化操作
            for (int i = 0; i < INITSIZE; i++) {
                //目前其中保持的都是可行解
                Path path = new Path(start_x, start_y, goal_x, goal_y, distanceMap);
                if (!Non_repeating_sets.contains(path)) {
                    Ini_P.add(path);
                    Non_repeating_sets.add(path);//
                }
            }
            //System.out.println("!!!"+pathList.size());
            paths = Ini_P;
            Random random = new Random();
            //这里才是主循环
            paths = GA.ga(INITSIZE,LOOP,QUESTION_NUM,random,paths,distanceMap,Non_repeating_sets);
        }

        //question_num =11(分治) start
        if(question_num == 11) {
            int start_x = initData.START_x;
            int start_y = initData.START_y;
            int goal_x = initData.GOAL_x;
            int goal_y = initData.GOAL_y;
            int yellow_x_1 = initData.YELLOW_x_1;
            int yellow_y_1 = initData.YELLOW_y_1;

            //获得交叉路口位置
            Point pointSet = new Point(initData);

            //额外目标数
            objectsNum = pointSet.objectives.size();
            pointSet.genDistanceMatrix2();

            //计算出有向图
            HashMap<Integer, List<SubPath>> distanceMap = pointSet.distanceMap;

            //获得初始种群
            List<Path> Ini_P1 = new ArrayList<>();//表示初始种群,且为起始点-必经点
            List<Path> Ini_P2 = new ArrayList<>();//必经点-终点
            Set<Path> Non_repeating_sets = new HashSet<>();//不重复的集合
            startValue = new int[objectsNum];
            if (objectsNum > 0) {
                startValue = initData.startValue;
            }

            //整个种群的初始化操作
            for (int i = 0; i < INITSIZE; i++) {
                //目前其中保持的都是可行解
                //首先读取path1为从起点到必经点
                Path path1 = new Path(start_x, start_y, yellow_x_1, yellow_y_1, distanceMap);
                if (!Non_repeating_sets.contains(path1)) {
                    Ini_P1.add(path1);
                    Non_repeating_sets.add(path1);//
                }
                //其次读取path2为必经点到终点
                Path path2 = new Path(yellow_x_1, yellow_y_1, goal_x, goal_y, distanceMap);
                if (!Non_repeating_sets.contains(path2)) {
                    Ini_P2.add(path2);
                    Non_repeating_sets.add(path2);//
                }
            }

            //System.out.println("!!!"+pathList.size());
            paths_1 = Ini_P1;
            paths_2 = Ini_P2;

            Random random = new Random();

            //这里才是主循环
            //首先对第一条线路paths_1进行操作
            paths_1 = GA.ga(INITSIZE,LOOP,QUESTION_NUM,random,paths_1,distanceMap,Non_repeating_sets);

            //其次对第二条线路paths_2进行操作
            paths_2 = GA.ga(INITSIZE,LOOP,QUESTION_NUM,random,paths_2,distanceMap,Non_repeating_sets);

            //最后将两条路径合并paths=paths_sy1+paths_2,并重制其余属性
            paths = MergePath.mergePaths_pro11(paths_1,paths_2,distanceMap);

            paths = EnviromentalSelection.sort(QUESTION_NUM, paths, INITSIZE);
            paths = EnviromentalSelection.sort(QUESTION_NUM, paths, -1);
        }

        //question_num =12(分治) start
        if(question_num == 12) {
            int start_x = initData.START_x;
            int start_y = initData.START_y;
            int goal_x = initData.GOAL_x;
            int goal_y = initData.GOAL_y;
            int yellow_x_1 = initData.YELLOW_x_1;
            int yellow_y_1 = initData.YELLOW_y_1;
            int yellow_x_2 = initData.YELLOW_x_2;
            int yellow_y_2 = initData.YELLOW_y_2;

            //获得交叉路口位置
            Point pointSet = new Point(initData);

            //额外目标数
            objectsNum = pointSet.objectives.size();
            pointSet.genDistanceMatrix2();

            //计算出有向图
            HashMap<Integer, List<SubPath>> distanceMap = pointSet.distanceMap;

            //获得初始种群
            List<Path> Ini_P_SY1 = new ArrayList<>();//表示初始种群,且为起始点-必经点1
            List<Path> Ini_P_SY2 = new ArrayList<>();//起点-必经点2
            List<Path> Ini_P_Y1Y2 = new ArrayList<>();//必经点1-必经点2
            List<Path> Ini_P_Y2Y1 = new ArrayList<>();//必经点2-必经点1
            List<Path> Ini_P_Y1E = new ArrayList<>();//必经点1-终点
            List<Path> Ini_P_Y2E = new ArrayList<>();//必经点2-终点
            Set<Path> Non_repeating_sets = new HashSet<>();//不重复的集合
            startValue = new int[objectsNum];
            if (objectsNum > 0) {
                startValue = initData.startValue;
            }

            //整个种群的初始化操作
            for (int i = 0; i < INITSIZE; i++) {
                //目前其中保持的都是可行解
                //首先读取path_SY1为从起点到必经点1
                Path path_SY1 = new Path(start_x, start_y, yellow_x_1, yellow_y_1, distanceMap);
                if (!Non_repeating_sets.contains(path_SY1)) {
                    Ini_P_SY1.add(path_SY1);
                    Non_repeating_sets.add(path_SY1);
                }
                //path_SY2为从起点到必经点2
                Path path_SY2 = new Path(start_x, start_y, yellow_x_2, yellow_y_2, distanceMap);
                if (!Non_repeating_sets.contains(path_SY2)) {
                    Ini_P_SY2.add(path_SY2);
                    Non_repeating_sets.add(path_SY2);
                }
                //path_Y1Y2为必经点1到必经点2
                Path path_Y1Y2 = new Path(yellow_x_1, yellow_y_1, yellow_x_2, yellow_y_2, distanceMap);
                if (!Non_repeating_sets.contains(path_Y1Y2)) {
                    Ini_P_Y1Y2.add(path_Y1Y2);
                    Non_repeating_sets.add(path_Y1Y2);
                }
                //path_Y2Y1为必经点2到必经点1
                Path path_Y2Y1 = new Path(yellow_x_2, yellow_y_2, yellow_x_1, yellow_y_1, distanceMap);
                if (!Non_repeating_sets.contains(path_Y2Y1)) {
                    Ini_P_Y2Y1.add(path_Y2Y1);
                    Non_repeating_sets.add(path_Y2Y1);
                }
                //最path_Y1E为必经点1到终点
                Path path_Y1E = new Path(yellow_x_1, yellow_y_1, goal_x, goal_y, distanceMap);
                if (!Non_repeating_sets.contains(path_Y1E)) {
                    Ini_P_Y1E.add(path_Y1E);
                    Non_repeating_sets.add(path_Y1E);//
                }
                //path_Y2E为必经点2到终点
                Path path_Y2E = new Path(yellow_x_2, yellow_y_2, goal_x, goal_y, distanceMap);
                if (!Non_repeating_sets.contains(path_Y2E)) {
                    Ini_P_Y2E.add(path_Y2E);
                    Non_repeating_sets.add(path_Y2E);
                }
            }

            //System.out.println("!!!"+pathList.size());
            paths_sy1 = Ini_P_SY1;//起点-Y1
            paths_sy2 = Ini_P_SY2;//起点-Y2
            paths_y1y2 = Ini_P_Y1Y2;//Y1-Y2
            paths_y2y1 = Ini_P_Y2Y1;//Y2-Y1
            paths_y1e = Ini_P_Y1E;//Y1-终点
            paths_y2e = Ini_P_Y2E;//Y2-终点

            Random random = new Random();

            //这里才是主循环
            //对线路paths_sy1 起点-Y1
            paths_sy1 = GA.ga(INITSIZE,LOOP,QUESTION_NUM,random,paths_sy1,distanceMap,Non_repeating_sets);
            //对线路paths_sy2 起点-Y2
            paths_sy2 = GA.ga(INITSIZE,LOOP,QUESTION_NUM,random,paths_sy2,distanceMap,Non_repeating_sets);
            //对线路paths_y1y2 Y1-Y2
            paths_y1y2 = GA.ga(INITSIZE,LOOP,QUESTION_NUM,random,paths_y1y2,distanceMap,Non_repeating_sets);
            //对线路paths_y2y1 Y2-Y1
            paths_y2y1 = GA.ga(INITSIZE,LOOP,QUESTION_NUM,random,paths_y2y1,distanceMap,Non_repeating_sets);
            //对线路paths_y1e Y1-E
            paths_y1e = GA.ga(INITSIZE,LOOP,QUESTION_NUM,random,paths_y1e,distanceMap,Non_repeating_sets);
            //对线路paths_y2e Y2-E
            paths_y2e = GA.ga(INITSIZE,LOOP,QUESTION_NUM,random,paths_y2e,distanceMap,Non_repeating_sets);

            //合并paths_1=paths_sy1+paths_y1y2+paths_y2e,并重制其余属性
            paths_1 = MergePath.mergePaths_pro12(paths_sy1,paths_y1y2,paths_y2e,distanceMap,Non_repeating_sets);
            //合并paths_2=paths_sy2+paths_y2y1+paths_y1e,并重制其余属性
            paths_2 = MergePath.mergePaths_pro12(paths_sy2,paths_y2y1,paths_y1e,distanceMap,Non_repeating_sets);

            paths = new ArrayList<>(paths_1);
            paths.addAll(paths_2);

            paths = EnviromentalSelection.sort(QUESTION_NUM, paths, INITSIZE);
            paths = EnviromentalSelection.sort(QUESTION_NUM, paths, -1);

        }


        //结束时间
        long endMs = System.currentTimeMillis();

        //是否打印路线
        if (isPrint) {
            paths.forEach(System.out::println);
        }
        //将目标值输出到csv文件中
        write_obj_to_csv(QUESTION_NUM, paths, "./"+"Problem_OBJ" +QUESTION_NUM+"Times"+Integer.toString(times) + ".csv");
        write_findnumofPS_to_csv("./"+"Problem_num_ofPS"+QUESTION_NUM+".csv");

        System.out.println(paths.size());
        for (int i = 0; i < paths.size(); i++) {
            for (int j = i + 1; j < paths.size(); j++) {
                if (check(paths.get(i).path, paths.get(j).path)) {
                    System.out.println("有重复路径！！！！");
                }
            }
        }
        System.out.println("无重复路径！！！！");
        System.out.println(paths.size());

        List<List<Integer>> lists = new ArrayList<>();
        for (Path path : paths) {
            lists.add(path.path);
        }
        //特殊编码的问题使用大数的代码
        if (QUESTION_NUM <= 12) {
            //测试结果正确率
            //这里使用excel和pareto
            int num_exist_pareto = Excel.test(QUESTION_NUM, lists);
        }

        //是否画出路线
        if (isPaint) {
            //画出图形的数量
            for (int i = 0; i < paths.size(); i++) {

                DrawSee drawSee = new DrawSee(map);
                if (question_num == 11 || question_num == 12) {
                    //因为原有的函数不能够画出11和12问题的必经点， 因此重构这个函数进行重画
                    drawSee.paintAreas(map, paths.get(i).path, false, question_num);

                } else
                    drawSee.paintAreas_1(map, paths.get(i).path, false, i);
//                drawSee.paintPath(paths.get(i).path);
            }
        }
        return endMs - startMs;
    }

    public static boolean check(List<Integer> path1, List<Integer> path2) {
        if (path1.size() != path2.size())
            return false;
        for (int i = 0; i < path1.size(); i++) {
            if (!path1.get(i).equals(path2.get(i))) {
                return false;
            }
        }
        return true;
    }

    //将找到的全局最优解的数目打印成csv函数
    public static void write_findnumofPS_to_csv( String path) {
        try {
            System.out.println("print num_of_PS is ok!!");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));


            //按列进行索引，这里的i是列索引
            for (int i = 0; i < find_solutions[0].length; i++) {
                out.write(Integer.toString(slice_loop * (i + 1)));
                out.write(",");
                for (int j = 0; j < find_solutions.length; j++) {
                    out.write(Integer.toString(find_solutions[j][i]));
                    //如果没有到行末尾
                    if (j != find_solutions.length - 1) {
                        out.write(",");
                    }else    out.newLine();
                }
            }

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //将目标值打印成csv函数
    public static void write_obj_to_csv(int pro_num, List<Path> paths, String path) {
        try {
            System.out.println("print csv is ok!!");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
            if (pro_num == 12||pro_num==7) {
                out.write("Distance");
                out.write(",");
                out.write("Obj1");
                out.write(",");
                out.write("obj2");
                out.newLine();
                //问题12的目标函数
                for (int i = 0; i < paths.size(); i++) {
                    out.write(Integer.toString(paths.get(i).distance));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[0] / 10.0));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[1] / 10.0));
                    out.newLine();
                }
            }//end for pro12 or pro7

            if (pro_num==1){
                out.write("Distance");
                out.write(",");
                out.write("Congestion");
                out.newLine();
                //问题1的目标函数
                for (int i = 0; i < paths.size(); i++) {
                    out.write(Integer.toString(paths.get(i).distance));
                    out.write(",");
                    out.write(Integer.toString(paths.get(i).block));
                    out.newLine();
                }
            }//end for pro1

            if(2<=pro_num&&pro_num<=5){
                out.write("Distance");
                out.write(",");
                out.write("Congestion");
                out.write(",");
                out.write("intersection");
                out.newLine();
                //问题2-5的目标函数
                for (int i = 0; i < paths.size(); i++) {
                    out.write(Integer.toString(paths.get(i).distance));
                    out.write(",");
                    out.write(Integer.toString(paths.get(i).block));
                    out.write(",");
                    out.write(Integer.toString(paths.get(i).cross));
                    out.newLine();
                }
            }//end for pro2-5

            if (pro_num == 6) {
                out.write("Distance");
                out.write(",");
                out.write("obj1");
                out.newLine();
                //问题6的目标函数
                for (int i = 0; i < paths.size(); i++) {
                    out.write(Integer.toString(paths.get(i).distance));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[0] / 10.0));
                    out.newLine();
                }
            }//end for pro6

            if (pro_num == 8) {
                out.write("Distance");
                out.write(",");
                out.write("Obj1");
                out.write(",");
                out.write("Obj2");
                out.write(",");
                out.write("Obj3");
                out.newLine();
                //问题8的目标函数
                for (int i = 0; i < paths.size(); i++) {
                    out.write(Integer.toString(paths.get(i).distance));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[0] / 10.0));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[1] / 10.0));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[2] / 10.0));
                    out.newLine();
                }
            }//end for pro8

            if (pro_num == 9) {
                out.write("Distance");
                out.write(",");
                out.write("Obj1");
                out.write(",");
                out.write("Obj2");
                out.write(",");
                out.write("Obj3");
                out.write(",");
                out.write("Obj4");
                out.newLine();
                //问题9的目标函数
                for (int i = 0; i < paths.size(); i++) {
                    out.write(Integer.toString(paths.get(i).distance));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[0] / 10.0));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[1] / 10.0));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[2] / 10.0));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[3] / 10.0));
                    out.newLine();
                }
            }//end for pro9

            if (pro_num == 10) {
                out.write("Distance");
                out.write(",");
                out.write("Obj1");
                out.write(",");
                out.write("Obj2");
                out.write(",");
                out.write("Obj3");
                out.write(",");
                out.write("Obj4");
                out.write(",");
                out.write("Obj5");
                out.write(",");
                out.write("Obj6");
                out.newLine();
                //问题10的目标函数
                for (int i = 0; i < paths.size(); i++) {
                    out.write(Integer.toString(paths.get(i).distance));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[0] / 10.0));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[1] / 10.0));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[2] / 10.0));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[3] / 10.0));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[4] / 10.0));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[5] / 10.0));
                    out.newLine();
                }
            }//end for pro10

            if (pro_num == 11) {
                out.write("Distance");
                out.write(",");
                out.write("Obj1");
                out.newLine();
                //问题11的目标函数
                for (int i = 0; i < paths.size(); i++) {
                    out.write(Integer.toString(paths.get(i).distance));
                    out.write(",");
                    out.write(Double.toString(paths.get(i).objectives[0] / 10.0));
                    out.newLine();
                }
            }//end for pro11

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
