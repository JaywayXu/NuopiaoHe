function Relation = InterLink(InterNum,Map2)
Relation = zeros(size(InterNum,1),6);
Relation(:,1) = InterNum(:,1);
for i = 1:1:size(InterNum,1)
    lie = InterNum(i,2);
    hang = InterNum(i,3);
    % n计算关联的路口数个数
    n = 0;
    % location定位Relation矩阵第三列，从第三列开始放入关联的路口编号
    location = 3;
    for j = (lie-1):-1:1
        if(Map2(hang,j) == 1)
            break;
        end
        if(Map2(hang,j) ~= 0)
            n = n + 1;
            Relation(i,location) = Map2(hang,j);
            location = location + 1;
            break;
        end
    end
    for j = (lie+1):1:size(Map2,1)
        if(Map2(hang,j) == 1)
            break;
        end
        if(Map2(hang,j) ~= 0)
            n = n + 1;
            Relation(i,location) = Map2(hang,j);
            location = location + 1;
            break;
        end
    end
    for j = (hang-1):-1:1
        if(Map2(j,lie) == 1)
            break;
        end
        if(Map2(j,lie) ~= 0)
            n = n + 1;
            Relation(i,location) = Map2(j,lie);
            location = location + 1;
            break;
        end
    end    
    for j = (hang+1):1:size(Map2,1)
        if(Map2(j,lie) == 1)
            break;
        end
        if(Map2(j,lie) ~= 0)
            n = n + 1;
            Relation(i,location) = Map2(j,lie);
            location = location + 1;
            break;
        end
    end  
    Relation(i,2) = n;
end