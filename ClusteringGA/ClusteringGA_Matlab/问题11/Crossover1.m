%���溯���������ҵ�������λ֮�����ͬԪ�أ����ܲ�Ψһ����Ϊ��ʼ����������ϵ�� 
function [cchrom1,clength1,cchrom2,clength2] = Crossover1(pchrom1,plength1,pchrom2,plength2,yellow)
yellow_num = size(yellow,1);
if yellow_num==1
a=find(pchrom1==yellow(1));
b=find(pchrom2==yellow(1));
elseif yellow_num==2
    a1=find(pchrom1==yellow(1));
    a2=find(pchrom1==yellow(2));
    if a1>a2
        a=a2;
    else
        a=a1;
    end
    b1=find(pchrom2==yellow(1));
    b2=find(pchrom2==yellow(2));
    if b1>b2
        b=b2;
    else
        b=b1;
    end
end
[samecode,ia,ib] = intersect(pchrom1(2:a-1),pchrom2(2:b-1));%�ӵڶ�������ɫ��ǰһ��֮���Ҵ��ڵ���ͬ��
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
