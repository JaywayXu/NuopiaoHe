%���췽ʽ���ڻ��������������ʼ��һ��λ�ã�������ͷβ�ڵ㣬�Լ������ڶ����ڵ㣬
%%��Ϊ��ʼ����ʽ��ԭ��,����յ��ڵ����ڶ����ڵ����ͨ���ֱ��ѡ���յ㣩
%�ҵ��ýڵ�������Ե����·�ڼ��ϣ��ҵ���ͬ�ڵ�ǰ�����е�·�ڼ��뵱���е��У�
%%��ʹ�ó�ʼ���ķ�ʽ����
function [chrom1,length1] = Mutation(chrom,length,Relation,goal)
randnum = unidrnd(length-3)+1;%ȥͷβ�Լ������ڶ����ڵ�
chrom1 = chrom(1:randnum);
length1 = randnum;
candidate = Relation(:,3:end);
n = abs(chrom(randnum)) - 1;
next = chrom(randnum+1);
m = find(candidate(n,:) == next);
candidate(n,m) = 0;
while(chrom1(length1)~=goal)
    index = abs(chrom1(length1)) - 1;%�ҵ��õ��ھ����ж�Ӧ���У��Ӷ������ҵ���ϵ·��
    cannum = sum(candidate(index,:)~=0);%·���е�ǰ��û���߹��ĺ�ѡ·����
    if(cannum == 0)%����ǰ��ĺ�ѡ·�ڶ��Ѿ�ʹ�ù�ʱ
        chrom1(end) = [];
        length1 = length1 - 1;%����ǰ���·�����Ƴ�
    else
        if(ismember(goal,candidate(index,:)))
            chrom1 = [chrom1,goal];
            length1 = length1 + 1;
        else
            randnum = round(rand(1)*(cannum-1))+1;%����Ӻ�ѡ·����ѡ��һ��
            canindex = find(candidate(index,:)~=0);%�ҳ�û����ѡ����·�ڣ���Ϊ0�����к�
            can = candidate(index,canindex);%�ҵ��кŶ�Ӧ��·�ں�
            new = can(randnum);%new��¼ѡ�����ĵ����
            if(ismember(new,chrom1))
                candidate(index,canindex(randnum)) = 0;%�����ѡ���ĵ��Ѿ����ڣ��򽫸õ���0��������������
            else
                chrom1 = [chrom1,new];
                length1 = length1 +1 ;
                candidate(index,canindex(randnum)) = 0;
            end
        end
    end
end