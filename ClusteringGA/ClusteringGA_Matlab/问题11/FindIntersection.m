%找到Map矩阵中的路口和拐角，输出一个大小等于Map的矩阵，矩阵中路口为1，拐角为-1，其余为0
function Intersection_Map = FindIntersection(X)
Intersection_Map = zeros(size(X,1),size(X,2));
for i = 2:1:(size(X,1)-1)
    for j = 2:1:(size(X,2)-1)
        if(X(i,j)==0)
            front = 0;
            back = 0;
            left = 0;
            right = 0;
            if(X(i,j+1) == 0)
                front = 1;
            end
            if(X(i,j-1) == 0)
                back = 1;
            end
            if(X(i-1,j) == 0)
                left = 1;
            end
            if(X(i+1,j) == 0)
                right = 1;
            end
            if((front+back+left+right) >= 3)
                Intersection_Map(i,j) = 1;
            end
            if((front + back) == 1 && (left + right) == 1)
                Intersection_Map(i,j) = -1;
            end
        end
    end
end
            
        
            
            