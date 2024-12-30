import java.io.IOException;

//此方法将起始点信息和拥堵点信息融合到地图中，其中起始点信息标记为2，结束点信息标记为9，而地图中红点的拥堵信息标记为5
public class MapInfo {
    //至于为什么地图上x坐标对应数组中的y索引，而地图上的y坐标对应数组中的x索引，这是因为数组是按照行来存储的，而行对应的索引在地图中用来表示列。
    public static int[][] genMapInfo(InitData initData) throws IOException {
        initData.map[initData.START_y][initData.START_x] = 2;
        initData.map[initData.GOAL_y][initData.GOAL_x] = 9;


        //判断是否有拥堵点，如果有拥堵点，就将拥堵点标记为5
        if (initData.red_areas != null){
            for(int[] area: initData.red_areas){
                initData.map[area[1]][area[0]] = 5;
            }
        }

        //判断是否是必经点，如果有必经点，就将必经点标记为6
//        if (initData.nece_points != null){
//            for(int[] area: initData.nece_points){
//                initData.map[area[1]][area[0]] = 6;
//            }
//        }

        return initData.map;
    }


}
