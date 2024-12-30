%Fitness3适应于问题7
%%InterNum大小为（拐角数+路口数）*3的矩阵，矩阵第一列存放编码，二三列存放横纵坐标
%f1为可行路径的长度
%f2为可通过路径的累积F1值，F1值从外向内逐渐增大
%f3为可通过路径的累积F2值，F2值从外向内逐渐减小
%适应度计算时，Fit_List1中保存两点之间的代价是不包括两点的，
%所以计算过各点之间的代价总和之后，f1要加上路径的长度
function [f1,f2,f3]=Fitness3(length,chrom,F,Fit_List1,InterNum)
f1=0;
f2=0;
f3=0;
F1=0;
F2=0;
for i=1:1:length-1
    index1 = abs(chrom(i));
    index2 = abs(chrom(i+1));
    f1 = f1 + Fit_List1(index1,index2);
    row=find(InterNum(:,1)==chrom(i));%找到编码在InterNum中的所在行
    x=InterNum(row,2);%编码对应的横坐标
    y=InterNum(row,3);%编码对应的纵坐标
    z=find(F(:,1)==x & F(:,2)==y);
    Fa=F(z,3);
    Fb=F(z,4);
    F1=F1+Fa;
    F2=F2+Fb;
end
row1=find(InterNum(:,1)==chrom(length));
x1=InterNum(row1,2);
y1=InterNum(row1,3);
z1=find(F(:,1)==x1&F(:,2)==y1);
F11=F(z1,3);
F22=F(z1,4);
f1=f1+length;
f2=f2+F1+F11;
f3=f3+F2+F22;

    
