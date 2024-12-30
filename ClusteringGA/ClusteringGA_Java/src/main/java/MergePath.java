import java.util.*;

public class MergePath {
    public static List<Path> mergePaths_pro11(List<Path> path1,List<Path> path2,HashMap<Integer, List<SubPath>> distanceMap) {
        //对于problem11，只经过一个必经点，有两条路径需要合成
        List<Path> paths = new ArrayList<>();//paths为合并后的最终种群
        List<Integer>  mergepath= new ArrayList<>();//mergepath用来暂时储存路径合并
        for (int i = 0; i < path1.size(); i++) {
            //将两路径进行合并
            mergepath = new ArrayList<>(path1.get(i).path);
            mergepath.addAll(path2.get(0).path);
            //采用LinkedHashSet数据类型在保住数据顺序的情况下，删除重复的元素
            Set<Integer> unique = new LinkedHashSet<>(mergepath);
            mergepath = new ArrayList<>(unique);
            //重新计算合并的路径的相关属性
            Path newpath = Path.calculatePath(mergepath, distanceMap);
            //将全新的路径及其新计算的相关属性加入最终种群
            paths.add(newpath);
        }
        return paths;
    }

    public static List<Path> mergePaths_pro12(List<Path> path1,List<Path> path2,List<Path> path3,
                                              HashMap<Integer, List<SubPath>> distanceMap,Set<Path> Non_repeating_sets) {
        //对于problem12，经过两个必经点，有三条路径需要合成
        //problem12在合成后可能会有重复路径，需检查重复性并删除
        List<Path> paths = new ArrayList<>();// 创建最终结果的路径集合
        // 合并前两条路径
        for (Path p1 : path1) {
            for (Path p2 : path2) {
                List<Integer> mergepath = new ArrayList<>(p1.path);
                mergepath.addAll(p2.path);
                // 暂存合并后的路径，以便后续操作
                List<Integer> mergepathCopy = new ArrayList<>(mergepath);
                // 合并第三条路径
                for (Path p3 : path3) {
                    List<Integer> finalMergepath = new ArrayList<>(mergepathCopy);
                    finalMergepath.addAll(p3.path);
                    // 使用 LinkedHashSet 去重并保持顺序
                    //List<Integer> uniquePath = new ArrayList<>(new LinkedHashSet<>(finalMergepath));
                    //去除必经点重复
                    List<Integer> uniquePath = removeyp(finalMergepath);
                    // 计算合并后的路径属性
                    Path newPath = Path.calculatePath(uniquePath, distanceMap);
                    if (!Non_repeating_sets.contains(newPath)) {
                        // 添加到最终路径集合中
                        paths.add(newPath);
                        Non_repeating_sets.add(newPath);
                    }
                }
            }
        }
        return paths;
    }

    private static List<Integer> removeyp(List<Integer> path) {
        List<Integer> result = new ArrayList<>();
        Integer previous = null;
        for (Integer current : path) {
            if (!current.equals(previous)) {  // 如果当前元素与上一个元素不同，添加到结果
                result.add(current);
            }
            previous = current;
        }
        return result;
    }

}
