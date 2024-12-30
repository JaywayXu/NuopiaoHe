import java.util.*;

public class Point {

    public int[][] points; //交叉路口集合
    public HashMap<Integer, List<SubPath>> distanceMap; //有向图
    public int[][] map;
    public Set<Integer> pointSet;
    public List<int[][]> objectives;

    //初始化整个种群
    public Point(InitData initData) {
        pointSet = new HashSet<>();//HashSet基于HashMap来实现，是一个不允许有重复元素的组合
        this.map = initData.map;
        this.objectives = initData.objectives;

        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {//map.length表示行，也就是这里的Y坐标
            for (int j = 0; j < map[i].length; j++) {
                //将交叉点填充到list数组列表中

                //java中&&条件表示必须同时满足，并且只要前面的不是真的，后面的都不会是真的
                //对于路口的判断
                if (map[i][j] != 1//该点可以通过
                        && (map[i - 1][j] != 1 || map[i + 1][j] != 1)//路口点的判断
                        && (map[i][j - 1] != 1 || map[i][j + 1] != 1)
                ) {
                    pointSet.add(i * 1000 + j);//这里没有使用二维数组来表示一个点而是使用一个数字编码的形式
                    list.add(new int[]{i, j});//这个列表是一个数组列表，每一个列表代表一个二维数组，
                }

                //对于路口还应该判断，其是否是必经点，如果必经点不在路口上，就将必经点也添加进去
                if (map[i][j] == 6)//表示其是一个必经点
                {
                    //如果必经点不是一个路口，就把它重新加上
                    if (!pointSet.contains(i * 1000 + j) ) {
                        pointSet.add(i * 1000 + j);
                        list.add(new int[]{i, j});
                    }
                }
            }
        }
        this.points = new int[list.size()][];
        list.toArray(this.points);
    }


    //生成最近交叉路口有向图
    public void genDistanceMatrix2() {
        distanceMap = new HashMap<>();
        //
        for (int i = 0; i < points.length; i++) {
            int key = points[i][0] * 1000 + points[i][1];//将点表示为一个独特的数字标记。
            List<SubPath> list = findJoin(points[i][0], points[i][1]);//传出的是一个subpath的list组合
            if (list.size() >= 2) {
                distanceMap.put(key, list);
            }
        }
    }

    //传入一个点的y坐标和x坐标
    //对应于Construction of the reduction edges操作
    private List<SubPath> findJoin(int i, int j) {
        List<SubPath> list = new ArrayList<>();
        //向上
        int x = i + 1, y = j;//注意：这里的x表示的是y坐标而y表示的是x坐标
        int block = 0;

        int[] values1 = new int[objectives.size()];//values1中记录的应该是目标值
        while (map[x][y] != 1) {//一直找到边界的不可行点
            for (int k = 0; k < values1.length; k++) {
                values1[k] += objectives.get(k)[x][y];//此为赋值操作，values1[k]=0
            }
            if (map[x][y] == 5)//5是拥堵点标记，如果是拥堵点则block++
                block++;
            if (pointSet.contains(x * 1000 + y)) {
                //每个点维持一个subpath的list列表，其中保存了另一个节点的key,与此点的distance,block数量，目标函数
                list.add(new SubPath(x * 1000 + y, x - i, block, values1));
                break;
            }
            x++;//向上进行索引
        }
        //向下
        x = i - 1;
        y = j;
        block = 0;
        int[] values2 = new int[objectives.size()];
        while (map[x][y] != 1) {
            for (int k = 0; k < values2.length; k++) {
                values2[k] += objectives.get(k)[x][y];
            }
            if (map[x][y] == 5)
                block++;
            if (pointSet.contains(x * 1000 + y)) {
                list.add(new SubPath(x * 1000 + y, i - x, block, values2));
                break;
            }
            x--;
        }
        //向左
        x = i;
        y = j + 1;
        block = 0;
        int[] values3 = new int[objectives.size()];

        while (map[x][y] != 1) {
            for (int k = 0; k < values3.length; k++) {
                values3[k] += objectives.get(k)[x][y];
            }
            if (map[x][y] == 5)
                block++;
            if (pointSet.contains(x * 1000 + y)) {
                list.add(new SubPath(x * 1000 + y, y - j, block, values3));
                break;
            }
            y++;
        }
        //向右
        x = i;
        y = j - 1;
        block = 0;
        int[] values4 = new int[objectives.size()];

        while (map[x][y] != 1) {
            for (int k = 0; k < values4.length; k++) {
                values4[k] += objectives.get(k)[x][y];
            }
            if (map[x][y] == 5)
                block++;
            if (pointSet.contains(x * 1000 + y)) {
                list.add(new SubPath(x * 1000 + y, j - y, block, values4));
                break;
            }
            y--;
        }
        return list;
    }

}
