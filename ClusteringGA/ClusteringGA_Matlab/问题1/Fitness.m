%Fitness��Ӧ������1
%��Ӧ�ȼ���ʱ��Fit_List1��Fit_List2�б�������֮��Ĵ����ǲ���������ģ�
%���Լ��������֮��Ĵ����ܺ�֮��f1Ҫ����·���ĳ��ȣ�f2Ҫ����·����·���Ǻ�ɫ����ĸ���
function [f1,f2] = Fitness(length,chrom,RedInter,Fit_List1,Fit_List2)
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

