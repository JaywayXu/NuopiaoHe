import java.util.*;
import java.util.stream.Collectors;

public class Path {

    List<Integer> path;
    int distance;
    int block;
    int cross;//交叉点的个数，即所在路线出现了交叉
    int[] objectives;

    public Path() {
        path = new ArrayList<>();
        distance = 0;
        block = 0;
        cross = 0;
    }

    public Path(int startX, int startY, int goalX, int goalY, HashMap<Integer, List<SubPath>> distanceMap) {
        List<Integer> path = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        int preKey = startY * 1000 + startX;//这是一种点的标记方法，这里表示第Y行第X个点，这里标记的是起始点，即记录下起始点的坐标
        path.add(preKey);
        set.add(preKey);
        objectives = new int[Start.objectsNum];
        Arrays.fill(objectives, 0);
        int goalKey = goalY * 1000 + goalX;//标记下终点的坐标
        Random r = new Random();
        boolean flag = true;

        while (flag) {
            while (preKey != goalKey) {//如果没有到达目标结点
                //distanceMap中包含了所有路口即交叉点，其可以向上下左右延伸的数量，这里的路径最大值肯定是4，
                // 因为一个路口上下左右四个方向会到下一个路口

                List<SubPath> test1 = distanceMap.get(preKey);//获取当前交叉点即路口的所有SubPath
                // 注意distanceMap是一个HashMap，这里的int类型preKey对应着一个ArrayList
                //这个ArrayList是一个SubPath类型的ArrayList,其中有Key,distance,block和obj这几个属性。
                //其中Key连接的是preKey,preKey是有向线段的起点，Key是有向线段的终点
                //例如在problem中，初始化第一次preKey30010对应着问题一的起点，其有两个SubPath，其中的key分别是25010和30012，分别是起点向上和向右方向的线段。
                // stream().filter函数能够对list中的结果进行筛选，其中filter中为条件正则表达式，表示subPath需要满足的条件是set中不包含当前subPath中的路径
                //所以说path和set都是已经走过的路，那么区别是什么呢？
                //经过debug来看，总体来说没有什么区别，也许只是数据结构形式不一样~
                List<SubPath> subPaths = distanceMap.get(preKey).stream().filter(subPath ->
                        !set.contains(subPath.getKey())
                ).collect(Collectors.toList());
                if (subPaths.size() == 0) {
                    break;
                }
                //从可行的路径中随机选择一条
                SubPath subPath = subPaths.get(r.nextInt(subPaths.size()));
                preKey = subPath.getKey();//更新preKey
                path.add(preKey);//前进，将新节点作为preKey，添加到path中
                set.add(preKey);
            }//end while (preKey != goalKey)

            //如果经历了上述这些以后找不到一条路径，能够达到目标点，则重新将起点赋值
            if (preKey != goalKey) {
                preKey = startY * 1000 + startX;
                path.clear();
                path.add(preKey);
                set.clear();
                set.add(preKey);
            } else {
                break;
            }//end if
        }//end while flag

        //因此，此时path中存储的必是一个经过遍历以后的可行解

        Path newPath = Path.calculatePath(path, distanceMap);
        this.path = newPath.path;
        this.cross = newPath.cross;
        this.distance = newPath.distance;
        this.block = newPath.block;
        this.objectives = newPath.objectives;
    }

    //根据路径计算各项指标
    public static Path calculatePath(List<Integer> path, HashMap<Integer, List<SubPath>> distanceMap) {
        Path newPath = new Path();
        newPath.path = path;
        newPath.objectives = new int[Start.objectsNum];
        newPath.distance = 1;
        //path.size()-1指的是最后一个点，如果是可行解，一般最后的点指的是这段路程的终点
        //其实我觉得这里有点奇怪啊，交叉点的数量，直接算path中的数量不就行了吗，path不就是由交叉点构成的吗
        //其实上面的想法是不正确的因为path中不仅包括交叉点还包括拥堵点，所以path中的节点数比交叉点掠多
        newPath.cross = distanceMap.get(path.get(path.size() - 1)).size() > 2 ? 1 : 0;//找到终点对应的交叉路口的数量，如果终点本身是个交叉路口赋值1
        for (int i = 0; i < newPath.objectives.length; i++) {
            newPath.objectives[i] = Start.startValue[i];
        }

        for (int i = 0; i < path.size() - 1; i++) {
            //找path中交叉路口的数量
            if (distanceMap.get(path.get(i)).size() > 2) {
                newPath.cross++;
            }//end if

            int finalI = i;
            //在distanceMap子有向图中查找path.get(i)即path中第i个结点所对应的信息
            //对于distanceMap.get(path.get(i))中的每一个subpath,key中存储的都是path.get(i)可能的下一个节点，以及distance和block
            //这里的item是distanceMap.get(path.get(i))中每个元素的简写，

            distanceMap.get(path.get(i)).forEach(item -> {
                if (item.getKey() == path.get(finalI + 1)) {
                    newPath.distance += item.getDistance();
                    newPath.block += item.getBlock();
                    for (int j = 0; j < item.getObjectives().length; j++) {
                        newPath.objectives[j] += item.getObjectives()[j];
                    }
                }
            });
        }//end for path.size
        //System.out.println(newPath.objectives[0]);
        return newPath;
    }


    @Override
    public String toString() {
        return "Path{" +
                "path=" + path +
                ", distance=" + distance +
                ", block=" + block +
                ", cross=" + cross +
                ", objectives=" + Arrays.toString(objectives) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path)) return false;
        Path path1 = (Path) o;
        if (path1.path.size() != this.path.size()) {
            return false;
        }
        for (int i = 0; i < this.path.size(); i++) {
            if (!path1.path.get(i).equals(this.path.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int code = 0;
        for (int i = 0; i < this.path.size(); i++) {
            code += (this.path.get(i) * i) % 10007;
        }
        return code;
    }

    public List<Integer> getPath() {
        return path;
    }

}
