%Fit_List1存放两个路口间的路径长度（不包括两点），例Fit_List1[abs(n),abs(m)]=5,表示路口n到路口m之间的路径长度为5
function [Fit_List1,Fit_List2,Fit_List3] = Fit_calculate1(InterNum,Relation)
Fit_List1 = zeros(size(InterNum,1)+1,size(InterNum,1)+1);
Fit_List1(2:end,1) = InterNum(:,1);
Fit_List1(1,2:end) = InterNum(:,1);
Fit_List2 = Fit_List1;
Fit_List3 = Fit_List1;
for i=1:1:size(InterNum,1)
    dot1 = InterNum(i,1);
    x1 = InterNum(i,2);
    y1 = InterNum(i,3);
    for j=1:1:Relation(i,2)
        dot2 = Relation(i,j+2);
        index = abs(dot2) - 1;
        x2 = InterNum(index,2);
        y2 = InterNum(index,3);
        length = abs(x1 - x2) + abs(y1 - y2) - 1;
        path = [];
        if(x1 == x2)
            path(:,1) = x1*ones(length,1);
            if(y1 > y2)
                path(:,2) = (y2+1):1:(y1-1);
            else
                path(:,2) = (y1+1):1:(y2-1);
            end
        end
        if(y1 == y2)
            path(:,2) = y1*ones(length,1);
            if(x1 > x2)
                path(:,1) = (x2+1):1:(x1-1);
            else
                path(:,1) = (x1+1):1:(x2-1);
            end
        end
%         
%         rednum = 0;
%         for k=1:1:length
%             if(ismember(path(k,:),Red_areas,'rows'))
%                 rednum = rednum + 1;
%             end
%         end
        
        Fit_List1(abs(dot1),abs(dot2)) = length;
%         Fit_List2(abs(dot1),abs(dot2)) = rednum;
    end
end