function Population = InitPop(N)

global start_point end_point solutioninfo turn_point FunObj

empty.decs = [];
empty.objs = [];

Population = repmat(empty,1,N);

for i=1:N
    
    o_flag = 0;
    
    while o_flag==0
        cur_point = start_point;
        decs = cur_point;
        
        o_flag = 1;
        while cur_point ~= end_point
            pick = randperm(solutioninfo.upper(cur_point),solutioninfo.upper(cur_point));
            
            flag = 0;
            for j=1:numel(pick)
                next_point =  solutioninfo.point{cur_point}(pick(j));
                if numel(find(decs==next_point)) == 0 %没有经过这个点
                    decs = [decs next_point];
                    cur_point = next_point;
                    flag = 1;
                    break;
                end
            end
            if flag == 0 %说明重复了
                o_flag = 0;
                break;
            end
        end
    end
    Population(i).decs = decs;
end
    
Population = FunObj(Population);