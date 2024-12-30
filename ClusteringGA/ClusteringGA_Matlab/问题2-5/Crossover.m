%���溯���������ҵ�������λ֮�����ͬԪ�أ����ܲ�Ψһ����Ϊ��ʼ����������ϵ�� 
function [cchrom1,clength1,cchrom2,clength2] = Crossover(pchrom1,plength1,pchrom2,plength2)
[samecode,ia,ib] = intersect(pchrom1(2:plength1-1),pchrom2(2:plength2-1));%�ӵڶ����������ڶ���
%intersect������samecode����ia,ib�Ľ�����ia��ib���ص��ǽ������������ָ��
if isempty(samecode) %�ж�samecode�Ƿ�Ϊ��
    cchrom1 = pchrom1;
    clength1 = plength1;
    cchrom2 = pchrom2;
    clength2 = plength2;
else
    randnum = unidrnd(size(samecode,2));  %����һ����ɢ�����������
    cchrom1 = pchrom1(1:ia(randnum));
    cchrom1 = [cchrom1,pchrom2(ib(randnum)+1:end)];
    cchrom2 = pchrom2(1:ib(randnum));
    cchrom2 = [cchrom2,pchrom1(ia(randnum)+1:end)];
    clength1 = size(cchrom1,2);
    clength2 = size(cchrom2,2);
end
%�Ӵ�����֮��Ҫ�ж��Ӵ����Ƿ������ͬ��·��
temp1 = unique(cchrom1);%unique��ɾ���������ظ���Ԫ��
temp2 = unique(cchrom2);
if size(temp1,2) < clength1
    [cchrom1,clength1] = Adjustment(cchrom1,clength1);
end
if size(temp2,2) < clength2
    [cchrom2,clength2] = Adjustment(cchrom2,clength2);
end