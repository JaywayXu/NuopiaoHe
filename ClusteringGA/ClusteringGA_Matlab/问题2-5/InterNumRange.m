%ΪInterSection������·�ں͹սǱ��
%InterNumΪ�յ�����·�����ܺ�*3�ľ��󣬵�һ��Ϊ��ţ��ڶ������зֱ�Ϊ��Map�ж�Ӧ�ĺᡢ������
%Map2��Map�У��սǺ�·�����ڵ�����Ӧ�ı�ţ����������ϵ�ͼ�����Ӧ��
function [InterNum,Map2] = InterNumRange(InterSection,Map)
k=2;
num = sum(sum(InterSection~=0));
InterNum = zeros(num,3);
Map2 = Map;
for i=1:1:size(InterSection,1)
    for j=1:1:size(InterSection,1)
        if(InterSection(i,j)~=0)
            InterNum(k,1)=k*InterSection(i,j);
            Map2(i,j)=k*InterSection(i,j);
            InterNum(k,2)=j;
            InterNum(k,3)=i;
            k=k+1;
        end
    end
end
InterNum(1,:)=[];