import java.util.*;
public class ClusteringGACrossover {

    // 交叉：当聚类中只有一个个体时不进行交叉操作，故只针对聚类个数大于等于2的
    public static List<Integer> Crossover(List<Integer> Ppath1,List<Integer> Ppath2){
//        System.out.println("-----父代个体1"+Ppath1);
//        System.out.println("-----父代个体2"+Ppath2);
        List<Integer> Cpath = new ArrayList<>();
        Cpath = crossover(Ppath1,Ppath2);
//        System.out.println("-----子代个体"+Cpath+"-----");
        return Cpath;
    }

    // 交叉操作
    public static List<Integer> crossover(List<Integer> Ppath1,List<Integer> Ppath2) {
        List<Integer> Cpath = new ArrayList<>();
        int Pnum = Ppath1.size();
        List<Integer> Ppath1_copy = new ArrayList<>(Ppath1); // 用来判断两条路径长度是否一致，不一致则修改_copy
        List<Integer> Ppath2_copy = new ArrayList<>(Ppath2);// 可以避免修改到原始路径，以至于影响交叉
        // 将每个子代个体长度转换为与最长的子代个体长度一致，不够补零
        if (Ppath1.size() > Ppath2.size()) {
            for(int i = Ppath2.size();i < Ppath1.size(); i++){
                Ppath2_copy.add(i,0);
            }
            Pnum = Ppath1.size();
        } else if (Ppath2.size() > Ppath1.size()) {
            for(int i = Ppath1.size();i < Ppath2.size(); i++){
                Ppath1_copy.add(i,0);
            }
            Pnum = Ppath2.size();
        }
//        int[] samecode = new int[Pnum]; // 存放两个个体相同元素
//        int[] ia = new int[Pnum]; // 存放samecode中的元素在Ppath1中的位置索引
//        int[] ib = new int[Pnum]; // 存放samecode中的元素在Ppath2中的位置索引
        List<Integer> samecode = new ArrayList<>(); // 存放两个个体相同元素
        List<Integer> ia = new ArrayList<>(); // 存放samecode中的元素在Ppath1中的位置索引
        List<Integer> ib = new ArrayList<>(); // 存放samecode中的元素在Ppath2中的位置索引
        // 查找 Ppath1和 Ppath2 在第2个到倒数第2个位置 的相同元素：
        for (int i = 1; i < Pnum - 1; i++) {
            int scnum = 0; // 计数器
            int ianum = 0;
            int ibnum = 0;
            for (int j = 1; j < Pnum - 1; j++) {
                int Ppath1num = Ppath1_copy.get(i);
                int Ppath2num = Ppath2_copy.get(j);
                if (Ppath1num == Ppath2num) {
//                    samecode[scnum] = Ppath1_copy.get(i); // 记录两个个体相同元素
//                    ia[ianum] = i; // 记录相同元素在Ppath1中的索引
//                    ib[ibnum] = j; // 记录相同元素在Ppath2中的索引
                    samecode.add(Ppath1_copy.get(i)); // 记录两个个体相同元素
                    ia.add(i); // 记录相同元素在Ppath1中的索引
                    ib.add(j); // 记录相同元素在Ppath2中的索引
                }
                scnum++;
                ianum++;
                ibnum++;
            }
        }
        // 判断samecode是否为空
        if (samecode.size() == 0) { // 若为空，则不进行交叉操作
            Cpath = Ppath1;
        } else { // 若不为空，则进行交叉操作
            // 在samecode中，随机选择一个位置的元素作为交叉点
            int n = samecode.size(); // 获取 samecode 的列数
            Random random = new Random(); // 生成 1 到 samecode 列数(n)的均匀随机整数
            int randnum = random.nextInt(n); // 生成范围为 [1, n]
            // 交叉：Cpath为Ppath1的前半部分+Ppath2的后半部分
            for (int i = 0; i < ia.get(randnum); i++) {
                Cpath.add(Ppath1.get(i));
            }
            for (int i = ib.get(randnum); i < Ppath2.size(); i++) {
                Cpath.add(Ppath2.get(i));
            }
        }
//        System.out.println("-----交叉后"+Cpath);
        // 检查子代路径是否有重复元素(只适用于1-10没有黄点问题)
        Set<Integer> Cpath_uni = new HashSet<>();
        for(int i : Cpath){
            Cpath_uni.add(i); // 使用HashSet去重
        }
        // 若不等则进入调整阶段
        if (Cpath_uni.size() != Cpath.size()) {
            Cpath = Adjustment(Cpath);
        }
//        System.out.println("-----调整后"+Cpath);
        return Cpath;
    }

    // 路径重复调整操作
    public static List<Integer> Adjustment(List<Integer> Cpath) {
        // 使用栈模拟保留结果
        List<Integer> result = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        // 保留第一个元素
        result.add(Cpath.get(0));
        // 遍历中间元素（排除第一个和最后一个）
        for (int i = 1; i < Cpath.size() - 1; i++) {
            int current = Cpath.get(i);
            if (result.contains(current)) {
                // 如果当前节点重复，移除中间路径
                while (!result.isEmpty() && result.get(result.size() - 1) != current) {
                    result.remove(result.size() - 1);
                }
            } else {
                seen.add(current);
                result.add(current);
            }
        }
        // 保留最后一个元素
        result.add(Cpath.get(Cpath.size() - 1));
        // 更新 Cpath
        Cpath.clear();
        Cpath.addAll(result);
        return Cpath;
//      思路：[1234251]
//      1.初始化：result = [1], seen = {}。
//		2.不在 seen 中，加入 result 和 seen：result = [1, 2], seen = {2}。
//      3.不在 seen 中，加入 result 和 seen：result = [1, 2, 3], seen = {2, 3}。
//	    4.不在 seen 中，加入 result 和 seen：result = [1, 2, 3, 4], seen = {2, 3, 4}。
//		2.已在 seen 中，触发重复处理逻辑：从 result 中移除 4, 3，直到保留第一个 2, 结果：result = [1, 2]。
//		5.不在 seen 中，加入 result 和 seen：result = [1, 2, 5], seen = {2, 5}。
//      1.保留最后一个元素 1，结果：result = [1, 2, 5, 1]。
    }
}
