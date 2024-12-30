function POP_NS = NeighborSearch2(POP,Relation,F,Fit_List1,InterNum,yellow,goal)
N = size(POP,2);
for i = 1:1:N
    chrom = POP(i).c;
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
    chrom1=chrom(1:y);
    length1=y;
    randnum1 = unidrnd(length1-3)+1;%ȥͷβ�Լ������ڶ����ڵ�
    n = abs(chrom1(randnum1)) - 1;
    caninter = Relation(n,3:end);
    next = chrom(randnum1+1);%�����һ����
    pre = chrom(randnum1-1);%ǰ���һ����
    nextindex = find(caninter == next);
    caninter(nextindex) = 0;
    preindex = find(caninter == pre);
    caninter(preindex) = 0;
    newnum = find(caninter ~= 0);
    newnumsize = size(newnum,2);
    %Ҫ����newnumsize>=1���ж�����
    if newnumsize >= 1
        for j=1:1:newnumsize
            newchrom = chrom1(1:randnum1);
            newlength = randnum1;
            newchrom = [newchrom,caninter(newnum(j))];
            newlength = newlength+1;
            candidate = Relation(:,3:end);
            candidate(find(candidate==goal))=0;
            while(newchrom(newlength)~=chrom1(y))
                index = abs(newchrom(newlength)) - 1;
                cannum = sum(candidate(index,:)~=0);
                if(cannum == 0)%����ǰ��ĺ�ѡ·�ڶ��Ѿ�ʹ�ù�ʱ
                    newchrom(end) = [];
                    newlength = newlength - 1;%����ǰ���·�����Ƴ�
                else
                    if(ismember(chrom1(y),candidate(index,:)))
                        newchrom = [newchrom,chrom1(y)];
                        newlength = newlength + 1;
                    else
                        randnum = round(rand(1)*(cannum-1))+1;
                        canindex = find(candidate(index,:)~=0);
                        can = candidate(index,canindex);
                        new = can(randnum);
                        if(ismember(new,newchrom))
                            candidate(index,canindex(randnum)) = 0;
                        else
                            newchrom = [newchrom,new];
                            newlength = newlength +1 ;
                            candidate(index,canindex(randnum)) = 0;
                        end
                    end
                end
            end
            candidate1=Relation(:,3:end);
            while(newchrom(newlength)~=goal)
                index = abs(newchrom(newlength)) - 1;%�ҵ��õ��ھ����ж�Ӧ���У��Ӷ������ҵ���ϵ·��
                cannum = sum(candidate1(index,:)~=0);%·���е�ǰ��û���߹��ĺ�ѡ·����
                if(cannum == 0)%����ǰ��ĺ�ѡ·�ڶ��Ѿ�ʹ�ù�ʱ
                    newchrom(end) = [];
                    newlength = newlength - 1;%����ǰ���·�����Ƴ�
                else
                    if(ismember(goal,candidate1(index,:))) % ismember(a,b)�ǿ�����a�е����ǲ��Ǿ���b�еĳ�Ա
                        newchrom = [newchrom,goal];
                        newlength = newlength + 1;
                    else
                        randnum = round(rand(1)*(cannum-1))+1;%����Ӻ�ѡ·����ѡ��һ��
                        canindex = find(candidate1(index,:)~=0);%�ҳ�û����ѡ����·�ڣ���Ϊ0�����к�
                        can = candidate1(index,canindex);%�ҵ��кŶ�Ӧ��·�ں�
                        new = can(randnum);%new��¼ѡ�����ĵ����
                        if(ismember(new,newchrom))
                            candidate1(index,canindex(randnum)) = 0;%�����ѡ���ĵ��Ѿ����ڣ��򽫸õ���0��������������
                        else
                            newchrom = [newchrom,new];
                            newlength = newlength +1 ;
                            candidate1(index,canindex(randnum)) = 0;
                        end
                    end
                end
            end
            popns(j).c = newchrom;
            popns(j).l = newlength;
%             [f1,f2] = Fitness2(popns(j).l,popns(j).c,F,Fit_List1,InterNum);
            [f1,f2,f3] = Fitness3(popns(j).l,popns(j).c,F,Fit_List1,InterNum);
%             [f1,f2,f3,f4] = Fitness4(popns(j).l,popns(j).c,F,Fit_List1,InterNum);
%             [f1,f2,f3,f4,f5] = Fitness5(popns(j).l,popns(j).c,F,Fit_List1,InterNum);
%             [f1,f2,f3,f4,f5,f6,f7] = Fitness6(popns(j).l,popns(j).c,F,Fit_List1,InterNum);
            popns(j).f1 = f1;
            popns(j).f2 = f2;
            popns(j).f3 = f3;
%             popns(j).f4 = f4;
%             popns(j).f5 = f5;
%             popns(j).f6 = f6;
%             popns(j).f7 = f7;
        end
        popns(newnumsize+1).c = POP(i).c;
        popns(newnumsize+1).l = POP(i).l;
        popns(newnumsize+1).f1 = POP(i).fitness1;
        popns(newnumsize+1).f2 = POP(i).fitness2;
        popns(newnumsize+1).f3 = POP(i).fitness3;
%         popns(newnumsize+1).f4 = POP(i).fitness4;
%         popns(newnumsize+1).f5 = POP(i).fitness5;
%         popns(newnumsize+1).f6 = POP(i).fitness6;
%         popns(newnumsize+1).f7 = POP(i).fitness7;
        Pobjs = [popns.f1;popns.f2;popns.f3]';
%         Pobjs = [popns.f1;popns.f2]';
        [FrontNo,MaxFNo] = NDSort(Pobjs,newnumsize+1);
        pindex = find(FrontNo == 1);
        if size(pindex,2) >1
            randnum2 = unidrnd(size(pindex,2));
            choose = pindex(randnum2);
            POP_NS(i).c = popns(choose).c;
            POP_NS(i).l = popns(choose).l;
            POP_NS(i).fitness1 = popns(choose).f1;
            POP_NS(i).fitness2 = popns(choose).f2;
            POP_NS(i).fitness3 = popns(choose).f3;
%             POP_NS(i).fitness4 = popns(choose).f4;
%             POP_NS(i).fitness5 = popns(choose).f5;
%             POP_NS(i).fitness6 = popns(choose).f6;
%             POP_NS(i).fitness7 = popns(choose).f7;
        else
            POP_NS(i).c = popns(pindex).c;
            POP_NS(i).l = popns(pindex).l;
            POP_NS(i).fitness1 = popns(pindex).f1;
            POP_NS(i).fitness2 = popns(pindex).f2;
            POP_NS(i).fitness3 = popns(pindex).f3;
%             POP_NS(i).fitness4 = popns(pindex).f4;
%             POP_NS(i).fitness5 = popns(pindex).f5;
%             POP_NS(i).fitness6 = popns(pindex).f6;
%             POP_NS(i).fitness7 = popns(pindex).f7;
        end
    else
        POP_NS(i).c = POP(i).c;
        POP_NS(i).l =  POP(i).l;
        POP_NS(i).fitness1 =  POP(i).fitness1;
        POP_NS(i).fitness2 =  POP(i).fitness2;
        POP_NS(i).fitness3 =  POP(i).fitness3;
%         POP_NS(i).fitness4 =  POP(i).fitness4;
%         POP_NS(i).fitness5 =  POP(i).fitness5;
%         POP_NS(i).fitness6 =  POP(i).fitness6;
%         POP_NS(i).fitness7 =  POP(i).fitness7;
    end
end