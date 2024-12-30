function [FrontNo,MaxFront,nCompare,time] = A_ENS(PopObj,nSort)
% A-ENS
% Approximate non-dominated sorting for evolutionary many-objective
% optimization

% Copyright 2016-2017 Ye Tian

    tic
    [N,M]    = size(PopObj);
    FrontNo  = inf(1,N);
    MaxFront = 0;
    nCompare = ceil(N*log2(N));
    [PopObj,rank] = sortrows(PopObj);
    PopObj        = PopObj ./ repmat(max(PopObj,[],1),N,1);
    [minvalue,minloc] = min(PopObj(:,2:end),[],2);
    [maxvalue,maxloc] = max(PopObj(:,2:end),[],2);
    minloc    = minloc + 1;
    maxloc    = maxloc + 1;
    meanvalue = mean(PopObj(:,2:end),2);
    nCompare  = nCompare + N*(M-1);
    while sum(FrontNo<inf) < min(nSort,N)
        MaxFront = MaxFront + 1;
        for i = 1 : N
            if FrontNo(i) == inf
                dominated = false;
                for j = i-1 : -1 : 1
                    if FrontNo(j) == MaxFront
                        if minvalue(i) < PopObj(j,minloc(i))
                            dominated = false;
                            nCompare  = nCompare + 1;
                        elseif maxvalue(j) > PopObj(i,maxloc(j))
                            dominated = false;
                            nCompare  = nCompare + 2;
                        elseif meanvalue(i) < meanvalue(j)
                            dominated = false;
                            nCompare  = nCompare + 3;
                        else
                            dominated = true;
                            nCompare  = nCompare + 3;
                        end
                        if dominated
                            break;
                        end
                    end
                end
                if ~dominated
                    FrontNo(i) = MaxFront;
                end
            end
        end
    end
    FrontNo(rank) = FrontNo;
    time = toc;
end