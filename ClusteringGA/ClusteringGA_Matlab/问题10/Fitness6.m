%Fitness6��Ӧ������10
%%InterNum��СΪ���ս���+·������*3�ľ��󣬾����һ�д�ű��룬�����д�ź�������
%f1Ϊ����·���ĳ���
%f2Ϊ��ͨ��·�����ۻ�F1ֵ��F1ֵ��������������
%f3Ϊ��ͨ��·�����ۻ�F2ֵ��F2ֵ���������𽥼�С
%f4Ϊ��ͨ��·�����ۻ�F3ֵ��F3ֵ���ϵ���������
%f5Ϊ��ͨ��·�����ۻ�F4ֵ��F4ֵ���ϵ���������
%f6Ϊ��ͨ��·�����ۻ�F5ֵ��F5ֵ������������
%f7Ϊ��ͨ��·�����ۻ�F6ֵ��F6ֵ�������𽥼�С
%��Ӧ�ȼ���ʱ��Fit_List1�б�������֮��Ĵ����ǲ���������ģ�
%���Լ��������֮��Ĵ����ܺ�֮��f1Ҫ����·���ĳ���
function [f1,f2,f3,f4,f5,f6,f7]=Fitness6(length,chrom,F,Fit_List1,InterNum)
f1=0;
f2=0;
f3=0;
f4=0;
f5=0;
f6=0;
f7=0;
F1=0;
F2=0;
F3=0;
F4=0;
F5=0;
F6=0;
for i=1:1:length-1
    index1 = abs(chrom(i));
    index2 = abs(chrom(i+1));
    f1 = f1 + Fit_List1(index1,index2);
    row=find(InterNum(:,1)==chrom(i));%�ҵ�������InterNum�е�������
    x=InterNum(row,2);%�����Ӧ�ĺ�����
    y=InterNum(row,3);%�����Ӧ��������
    z=find(F(:,1)==x & F(:,2)==y);%�ҵ�������F��������
    Fa=F(z,3);%ȡ�����Ӧ��Fֵ
    Fb=F(z,4);
    Fc=F(z,5);
    Fd=F(z,6);
    Fe=F(z,7);
    Ff=F(z,8);
    F1=F1+Fa;
    F2=F2+Fb;
    F3=F3+Fc;
    F4=F4+Fd;
    F5=F5+Fe;
    F6=F6+Ff;
end
row1=find(InterNum(:,1)==chrom(length));%�ҵ�·���յ�
x1=InterNum(row1,2);
y1=InterNum(row1,3);
z1=find(F(:,1)==x1&F(:,2)==y1);
F11=F(z1,3);
F22=F(z1,4);
F33=F(z1,5);
F44=F(z1,6);
F55=F(z1,7);
F66=F(z1,8);
f1=f1+length;
f2=f2+F1+F11;
f3=f3+F2+F22;
f4=f4+F3+F33;
f5=f5+F4+F44;
f6=f6+F5+F55;
f7=f7+F6+F66;