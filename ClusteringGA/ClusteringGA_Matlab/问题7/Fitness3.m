%Fitness3��Ӧ������7
%%InterNum��СΪ���ս���+·������*3�ľ��󣬾����һ�д�ű��룬�����д�ź�������
%f1Ϊ����·���ĳ���
%f2Ϊ��ͨ��·�����ۻ�F1ֵ��F1ֵ��������������
%f3Ϊ��ͨ��·�����ۻ�F2ֵ��F2ֵ���������𽥼�С
%��Ӧ�ȼ���ʱ��Fit_List1�б�������֮��Ĵ����ǲ���������ģ�
%���Լ��������֮��Ĵ����ܺ�֮��f1Ҫ����·���ĳ���
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
    row=find(InterNum(:,1)==chrom(i));%�ҵ�������InterNum�е�������
    x=InterNum(row,2);%�����Ӧ�ĺ�����
    y=InterNum(row,3);%�����Ӧ��������
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

    
