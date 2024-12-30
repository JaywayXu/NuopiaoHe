import javax.lang.model.type.ArrayType;
import java.util.*;
////第二选择策略
//public class SecSelection {
//    public static List<Path> secselection(List<Path> res,int num,int question_num){
//        while (res.size() > num) {
//            //计算CDmax
//            double CDmax = 0;
//            for (int i = 0; i < res.size(); i++) {
//                for (int j = i + 1; j < res.size(); j++) {
//                    double CDij = calCDij(res.get(i), res.get(j));
//                    if (CDij > CDmax) {
//                        CDmax = CDij;
//                    }
//                }
//            }
//            //计算每个解的f
//            double[] fitness = new double[res.size()];
//            for (int i = 0; i < res.size(); i++) {
//                fitness[i] = calf(i, CDmax, res,question_num);
//            }
//            // 找出具有最大适应度的解
//            int maxIndex = 0;
//            for (int i = 1; i < fitness.length; i++) {
//                if (fitness[i] > fitness[maxIndex]) {
//                    maxIndex = i;
//                }
//            }
//
//            // 删除具有最大适应度的解
//            res.remove(maxIndex);
//        }
//        return res;
//    }
//    //计算公共点数量
//    private static int calcompoint(Path Path1,Path Path2) {
//        int count = 0;
//        List<Integer> path1 = new ArrayList<>(Path1.getPath());
//        List<Integer> path2 = new ArrayList<>(Path2.getPath());
//        for (Integer p1 : path1) {
//            if (path2.contains(p1)) {
//                count++;
//            }
//        }
//        return count;
//    }
//    //计算CDij
//    private static double calCDij(Path Path1,Path Path2){
//        double Li = (double)Path1.distance;//路径1的长度
//        double Lj = (double)Path2.distance;//路径2的长度
//        int Lsame = calcompoint(Path1,Path2);
//        return (2.0 * Lsame) / (Lj + Li);
//    }
//    //计算f
//    private static double calf(int i,double CDmax,List<Path> res,int question_num){
//        double sumCD = 0;
//        int N = 100;
//        int QUESTION_NUM = question_num;//QUESTION_NUM表示问题的索引
//
//        if (QUESTION_NUM == 5) {
//            N = 1500;//1500
//        } else if (QUESTION_NUM == 10) {
//            N = 2000;//2000
//        } else if (QUESTION_NUM == 9) {
//            N = 200;//200
//        } else if (QUESTION_NUM == 12) {
//            N = 2000;//2000
//        } else if (QUESTION_NUM == 101) {
//            //101问题实在1问题中改的
//            N = 100;//100
//        }
//
//        for (int j = 0; j <= N; j++) {
//            if (i != j) {
//                sumCD = sumCD + calCDij(res.get(i), res.get(j)) / CDmax;
//            }
//        }
//        // f = sumCDij / CDmax / N
//        return sumCD / N;
//    }
//}
import java.util.*;

// 第二选择策略优化版
public class SecSelection {
//     res当前路径列表
//     num需要保留的路径数量
//     question_num 问题编号
//     return筛选后的路径列表
    public static List<Path> secselection(List<Path> res, int num, int question_num) {
        if (res.size() <= num) {
            return res;
        }
        // 根据问题编号设置N的值
        int N = getQuestionNum(question_num);

        // 预先计算所有路径对的CDij值，并存储在一个二维数组中
        double[][] cdMatrix = calculateCDMatrix(res);

        // 计算CDmax
        double CDmax = findCDmax(cdMatrix);

        // 计算每个路径的适应度
        double[] fitness = calculateFitness(res, cdMatrix, CDmax, N);

        // 反复移除适应度最高的路径，直到达到目标数量
        while (res.size() > num) {
            int maxIndex = findMaxFitnessIndex(fitness);
            res.remove(maxIndex);
            // 同时移除cdMatrix中对应的行和列
            cdMatrix = removePathFromCDMatrix(cdMatrix, maxIndex);
            // 重新计算CDmax和适应度
            CDmax = findCDmax(cdMatrix);
            fitness = calculateFitness(res, cdMatrix, CDmax, N);
        }

        return res;
    }

//    根据问题编号获取N的值。
    private static int getQuestionNum(int question_num) {
        switch (question_num) {
            case 5:
                return 1500;
            case 9:
                return 200;
            case 10:
                return 2000;
            case 12:
                return 2000;
            case 101:
                return 100;
            default:
                return 100;
        }
    }

//    计算所有路径对的CDij值，存储在二维数组中。
    private static double[][] calculateCDMatrix(List<Path> res) {
        int size = res.size();
        double[][] cdMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                double cdij = calCDij(res.get(i), res.get(j));
                cdMatrix[i][j] = cdij;
                cdMatrix[j][i] = cdij; // 对称填充
            }
        }
        return cdMatrix;
    }

//     查找CDmax，即CDij的最大值。
//     cdMatrix CDij值矩阵
    private static double findCDmax(double[][] cdMatrix) {
        double CDmax = 0;
        for (double[] row : cdMatrix) {
            for (double cdij : row) {
                if (cdij > CDmax) {
                    CDmax = cdij;
                }
            }
        }
        return CDmax;
    }


//     计算所有路径的适应度值。
    private static double[] calculateFitness(List<Path> res, double[][] cdMatrix, double CDmax, int N) {
        int size = res.size();
        double[] fitness = new double[size];
        for (int i = 0; i < size; i++) {
            double sumCDchange = 0;
            double sumCD = 0;
            for (int j = 0; j < size && j <= N; j++) {
                    sumCDchange += cdMatrix[i][j];
            }
            sumCD = sumCDchange / CDmax;
            fitness[i] = sumCD / N;
        }
        return fitness;
    }

//     查找适应度数组中最大值的索引。
    private static int findMaxFitnessIndex(double[] fitness) {
        int maxIndex = 0;
        for (int i = 1; i < fitness.length; i++) {
            if (fitness[i] > fitness[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

//     移除CD矩阵中指定索引的行和列
//     index    需要移除的路径索引
    private static double[][] removePathFromCDMatrix(double[][] cdMatrix, int index) {
        int size = cdMatrix.length - 1;
        double[][] newMatrix = new double[size][size];
        int newI = 0;
        for (int i = 0; i < cdMatrix.length; i++) {
            if (i == index) continue;
            int newJ = 0;
            for (int j = 0; j < cdMatrix[i].length; j++) {
                if (j == index) continue;
                newMatrix[newI][newJ] = cdMatrix[i][j];
                newJ++;
            }
            newI++;
        }
        return newMatrix;
    }

//     计算两个路径之间的CDij值
    private static double calCDij(Path path1, Path path2) {
        double Li = (double) path1.distance; // 路径1的长度
        double Lj = (double) path2.distance; // 路径2的长度
        int Lsame = calcompoint(path1, path2);
        return (2.0 * Lsame) / (Lj + Li);
    }


//    计算两个路径的公共点数量
    private static int calcompoint(Path path1, Path path2) {
        List<Integer> list1 = path1.getPath();
        List<Integer> list2 = path2.getPath();
        Set<Integer> set2 = new HashSet<>(list2); // 使用HashSet优化查找
        int count = 0;
        for (Integer p1 : list1) {
            if (set2.contains(p1)) {
                count++;
            }
        }
        return count;
    }
}