function [chrom,length] = Initialization1(Relation,start,yellow,goal)
candidate = Relation(:,3:end); %candidate����Relation�п�ѡ��ϵ�㲿��
candidate(find(candidate==goal))=0;
% yellow_num=size(yellow,1);
% rand1=unidrnd(yellow_num);
chrom = start;%�Ƚ���ʼ�����·����
length = 1;
while(chrom(length)~=yellow)
    index = abs(chrom(length)) - 1;%�ҵ��õ��ھ����ж�Ӧ���У��Ӷ������ҵ���ϵ·��
    cannum = sum(candidate(index,:)~=0);%·���е�ǰ��û���߹��ĺ�ѡ·����
    if(cannum == 0)%����ǰ��ĺ�ѡ·�ڶ��Ѿ�ʹ�ù�ʱ
        chrom(end) = [];
        length = length - 1;%����ǰ���·�����Ƴ�
    else
        if(ismember(yellow,candidate(index,:))) %ismember(a,b)�ǿ�����a�е����ǲ��Ǿ���b�еĳ�Ա
            chrom = [chrom,yellow];
            length = length + 1;
        else
            randnum = round(rand(1)*(cannum-1))+1;%����Ӻ�ѡ·����ѡ��һ��
            canindex = find(candidate(index,:)~=0);%�ҳ�û����ѡ����·�ڣ���Ϊ0�����к�
            can = candidate(index,canindex);%�ҵ��кŶ�Ӧ��·�ں�
            new = can(randnum);%new��¼ѡ�����ĵ����
            if(ismember(new,chrom))
                candidate(index,canindex(randnum)) = 0;%�����ѡ���ĵ��Ѿ����ڣ��򽫸õ���0��������������
            else
                chrom = [chrom,new];
                length = length +1 ;
                candidate(index,canindex(randnum)) = 0;
            end
        end
    end
end
candidate1=Relation(:,3:end);
while(chrom(length)~=goal)
    index = abs(chrom(length)) - 1;%�ҵ��õ��ھ����ж�Ӧ���У��Ӷ������ҵ���ϵ·��
    cannum = sum(candidate1(index,:)~=0);%·���е�ǰ��û���߹��ĺ�ѡ·����
    if(cannum == 0)%����ǰ��ĺ�ѡ·�ڶ��Ѿ�ʹ�ù�ʱ
        chrom(end) = [];
        length = length - 1;%����ǰ���·�����Ƴ�
    else
        if(ismember(goal,candidate1(index,:))) % ismember(a,b)�ǿ�����a�е����ǲ��Ǿ���b�еĳ�Ա
            chrom = [chrom,goal];
            length = length + 1;
        else
            randnum = round(rand(1)*(cannum-1))+1;%����Ӻ�ѡ·����ѡ��һ��
            canindex = find(candidate1(index,:)~=0);%�ҳ�û����ѡ����·�ڣ���Ϊ0�����к�
            can = candidate1(index,canindex);%�ҵ��кŶ�Ӧ��·�ں�
            new = can(randnum);%new��¼ѡ�����ĵ����
            if(ismember(new,chrom))
                candidate1(index,canindex(randnum)) = 0;%�����ѡ���ĵ��Ѿ����ڣ��򽫸õ���0��������������
            else
                chrom = [chrom,new];
                length = length +1 ;
                candidate1(index,canindex(randnum)) = 0;
            end
        end
    end
end

    
    
    
