%Fitness5适应于问题9
%%InterNum大小为（拐角数+路口数）*3的矩阵，矩阵第一列存放编码，二三列存放横纵坐标
%f1为可行路径的长度
%f2为可通过路径的累积F1值，F1值从外向内逐渐增大
%f3为可通过路径的累积F2值，F2值从外向内逐渐减小
%f4为可通过路径的累积F3值，F3值从上到下逐渐增大
%f5为可通过路径的累积F4值，F4值从上到下逐渐增大
%适应度计算时，Fit_List1中保存两点之间的代价是不包括两点的，
%所以计算过各点之间的代价总和之后，f1要加上路径的长度
function [f1,f2,f3,f4,f5]=Fitness5(length,chrom,F,Fit_List1,InterNum)
f1=0;
f2=0;
f3=0;
f4=0;
f5=0;
F1=0;
F2=0;
F3=0;
F4=0;
for i=1:1:length-1
    index1 = abs(chrom(i));
    index2 = abs(chrom(i+1));
    f1 = f1 + Fit_List1(index1,index2);
    row=find(InterNum(:,1)==chrom(i));%找到编码在InterNum中的所在行
    x=InterNum(row,2);%编码对应的横坐标
    y=InterNum(row,3);%编码对应的纵坐标
    z=find(F(:,1)==x & F(:,2)==y);%找到坐标在F中所在行
    Fa=F(z,3);%取坐标对应的F值
    Fb=F(z,4);
    Fc=F(z,5);
    Fd=F(z,6);
    F1=F1+Fa;
    F2=F2+Fb;
    F3=F3+Fc;
    F4=F4+Fd;
end
row1=find(InterNum(:,1)==chrom(length));%找到路径终点
x1=InterNum(row1,2);
y1=InterNum(row1,3);
z1=find(F(:,1)==x1&F(:,2)==y1);
F11=F(z1,3);
F22=F(z1,4);
F33=F(z1,5);
F44=F(z1,6);
f1=f1+length;
f2=f2+F1+F11;
f3=f3+F2+F22;
f4=f4+F3+F33;
f5=f5+F4+F44;