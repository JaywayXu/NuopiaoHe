%���췽ʽ���ڻ��������ϵ�һ���㵽��ɫ��֮���ʼ��һ��λ��
%�ҵ��ýڵ�������Ե����·�ڼ��ϣ��ҵ���ͬ�ڵ�ǰ�����е�·�ڼ��뵱���е��У���ʹ�ó�ʼ���ķ�ʽ����
function [chrom2,length2] = Mutation1(chrom,Relation,yellow,goal)
yellow_num=size(yellow,1);
if yellow_num==1
    y=find(chrom==yellow);
elseif yellow_num==2
    a=find(chrom==yellow(1));
    b=find(chrom==yellow(2));
    if a>b
        y=b;
    else
        y=a;
    end
end
chrom1 = chrom(1:y);%��һ���㵽��ɫ���·��
length1 = y;%��һ���㵽��ɫ���·������
randnum = unidrnd(length1-3)+1;%ȥͷβ�Լ������ڶ����ڵ�
chrom2 = chrom1(1:randnum);
length2 = randnum;
candidate = Relation(:,3:end);
candidate(find(candidate==goal))=0;
n = abs(chrom1(randnum)) - 1;
next = chrom1(randnum+1);
m = find(candidate(n,:) == next);
candidate(n,m) = 0;
while(chrom2(length2)~=chrom1(y))
    index = abs(chrom2(length2)) - 1;%�ҵ��õ��ھ����ж�Ӧ���У��Ӷ������ҵ���ϵ·��
    cannum = sum(candidate(index,:)~=0);%·���е�ǰ��û���߹��ĺ�ѡ·����
    if(cannum == 0)%����ǰ��ĺ�ѡ·�ڶ��Ѿ�ʹ�ù�ʱ
        chrom2(end) = [];
        length2 = length2 - 1;%����ǰ���·�����Ƴ�
    else
        if(ismember(chrom1(y),candidate(index,:)))
            chrom2 = [chrom2,chrom1(y)];
            length2 = length2 + 1;
        else
            randnum = round(rand(1)*(cannum-1))+1;%����Ӻ�ѡ·����ѡ��һ��
            canindex = find(candidate(index,:)~=0);%�ҳ�û����ѡ����·�ڣ���Ϊ0�����к�
            can = candidate(index,canindex);%�ҵ��кŶ�Ӧ��·�ں�
            new = can(randnum);%new��¼ѡ�����ĵ����
            if(ismember(new,chrom2))
                candidate(index,canindex(randnum)) = 0;%�����ѡ���ĵ��Ѿ����ڣ��򽫸õ���0��������������
            else
                chrom2 = [chrom2,new];
                length2 = length2 +1 ;
                candidate(index,canindex(randnum)) = 0;
            end
        end
    end
end
candidate1=Relation(:,3:end);
while(chrom2(length2)~=goal)
    index = abs(chrom2(length2)) - 1;%�ҵ��õ��ھ����ж�Ӧ���У��Ӷ������ҵ���ϵ·��
    cannum = sum(candidate1(index,:)~=0);%·���е�ǰ��û���߹��ĺ�ѡ·����
    if(cannum == 0)%����ǰ��ĺ�ѡ·�ڶ��Ѿ�ʹ�ù�ʱ
        chrom2(end) = [];
        length2 = length2 - 1;%����ǰ���·�����Ƴ�
    else
        if(ismember(goal,candidate1(index,:))) % ismember(a,b)�ǿ�����a�е����ǲ��Ǿ���b�еĳ�Ա
            chrom2 = [chrom2,goal];
            length2 = length2 + 1;
        else
            randnum = round(rand(1)*(cannum-1))+1;%����Ӻ�ѡ·����ѡ��һ��
            canindex = find(candidate1(index,:)~=0);%�ҳ�û����ѡ����·�ڣ���Ϊ0�����к�
            can = candidate1(index,canindex);%�ҵ��кŶ�Ӧ��·�ں�
            new = can(randnum);%new��¼ѡ�����ĵ����
            if(ismember(new,chrom2))
                candidate1(index,canindex(randnum)) = 0;%�����ѡ���ĵ��Ѿ����ڣ��򽫸õ���0��������������
            else
                chrom2 = [chrom2,new];
                length2 = length2 +1 ;
                candidate1(index,canindex(randnum)) = 0;
            end
        end
    end
end




