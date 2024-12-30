import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {
        InitData initData = new InitData("src/main/resources/data/Problem_9.mat");
        int[][] map = MapInfo.genMapInfo(initData);

        int start_x = initData.START_x;
        int start_y = initData.START_y;
        int goal_x = initData.GOAL_x;
        int goal_y = initData.GOAL_y;

        //获得交叉路口位置
        Point pointSet = new Point(initData);
        int[][] points =  pointSet.points;

        //额外目标数
        int objectsNum =  pointSet.objectives.size();
        pointSet.genDistanceMatrix2();

        //计算出有向图
        HashMap<Integer, List<SubPath>> distanceMap = pointSet.distanceMap;

        List<Path> paths = new ArrayList<>();
        Path path1 = new Path();
//        path1.path =  Arrays.asList(40010, 40005,45005,45032,35032,35035,15035);//交叉前
//        path1.path =  Arrays.asList(20020,20030);//用于子路径的解释

        //path1.path =  Arrays.asList(40010,40005,45005,45032,40032,40040,10040,10032,15032, 15035);//交叉后
        //path1.path =  Arrays.asList(40010, 27010,27015,32015,32017,35017,35032,27032,27040,22040,22035,15035);//变异前
        //path1.path =  Arrays.asList(40010, 27010,27015,32015,32017,35017,35032);//变异前
        //path1.path =  Arrays.asList(35032,27032,27040,22040,22035,15035);//变异后
        Path path2 = new Path();
        //path2.path =  Arrays.asList(40010,40032, 40040,10040,10032,15032, 15035);//交叉前
//        path2.path =  Arrays.asList(22020,22030);//用于子路径的解释
        //path2.path =  Arrays.asList(40010,40032,35032,35035,15035);//交叉后
        //path2.path =  Arrays.asList(35032,15032, 15035);//变异前
        //path2.path = Arrays.asList(40010,40032 ,35032);//变异后

        //path.path =  Arrays.asList(35005, 32005,30005,18005, 12005, 12010,12017,15017,15022,15025);
        //path.path =  Arrays.asList(35005, 18005,18015,15015,15025);
        DrawSee drawSee = new DrawSee(map);
        drawSee.paintAreas2(map,points,path1.path,path2.path,false);

    }


    public static int ma(String[] args) throws IOException {
        InitData initData = new InitData("data/Problem_10.mat");
        int[][] d = initData.objectives.get(1);
        for (int i = 0; i < initData.map.length; i++) {
            System.out.println(Arrays.toString(initData.map[i]));
        }
        for (int i = 0; i < d.length; i++) {
            System.out.println(Arrays.toString(d[i]));
        }
        for (int i = 0; i < initData.map.length; i++) {
            for (int j = 0; j < initData.map[i].length; j++) {
                if (initData.map[i][j] == 0 && d[i][j] == 0){
                    System.out.println(i+","+j);
                    System.out.println("error:" + initData.map[i][j] + "," + d[i][j]);
                }else if(initData.map[i][j] == 1 && d[i][j] > 0){
                    System.out.println(i+","+j);
                    System.out.println("error:" + initData.map[i][j] + "," + d[i][j]);
                }
            }
        }
        System.out.println(d[6][11]);
        System.out.println("success");
        return 1;
    }

}
