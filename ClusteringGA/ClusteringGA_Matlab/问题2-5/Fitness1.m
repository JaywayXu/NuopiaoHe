%Fitness1函数适合问题2到问题5
%适应度计算时，Fit_List1、Fit_List2中保存两点之间的代价是不包括两点的，
%所以计算过各点之间的代价总和之后，f1要加上路径的长度，f2要加上路径中路口是红色区域的个数
%f3为路径经过路口的个数
function [f1,f2,f3] = Fitness1(length,chrom,RedInter,Fit_List1,Fit_List2)
redinter = 0;
f1 = 0;
f2 = 0;
for i = 1:1:length-1
    index1 = abs(chrom(i));
    index2 = abs(chrom(i+1));
    f1 = f1 + Fit_List1(index1,index2);
    f2 = f2 + Fit_List2(index1,index2);
    if ismember(chrom(i),RedInter)
        redinter = redinter + 1;
    end
    
end
if ismember(chrom(length),RedInter)
    redinter = redinter + 1;
end
f1 = f1 + length;
f2 = f2 + redinter;
f3 = size(find(chrom>0),2);
