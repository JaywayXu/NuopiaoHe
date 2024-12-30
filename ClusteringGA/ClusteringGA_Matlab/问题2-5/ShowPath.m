function ShowPath(Map,chrom,length,InterNum,START_x,START_y,GOAL_x,GOAL_y)
% Map = Map';
path =[];
for i = 1:1:length-1
    index1 = abs(chrom(i))-1;
    index2 = abs(chrom(i+1))-1;
    row1 = InterNum(index1,2);
    col1 = InterNum(index1,3);
    row2 = InterNum(index2,2);
    col2 = InterNum(index2,3);
    l = abs(row1 - row2) + abs(col1 - col2);
    if row1 == row2
        if col1 < col2
            for j = 0:1:l
                path = [path;[row1,col1+j]];
            end
        else
            for j = 0:1:l
                path = [path;[row1,col1-j]];
            end
        end
    else
        if row1 < row2
            for j = 0:1:l
                path = [path;[row1+j,col1]];
            end
        else
            for j = 0:1:l
                path = [path;[row2+j,col1]];
            end
        end
    end
end

% for i = 1:1:length
%     index = abs(chrom(i))-1;
%     path = [path;InterNum(index,2:3)];
% end
% path(:,[1,2])=path(:,[2,1]);

k=5; % Enlarge the map by 3 times
for i=1:size(Map,1)
    for j=1:size(Map,2)
        s=Map(i,j);
        ss=ones(k,k);
        ss=s*ss;
        sss(i,j)={ss};
    end
end
for i=1:size(Map,1)
    for j=1:size(Map,2)
        big((i-1)*k+1:i*k,(j-1)*k+1:j*k)=cell2mat(sss(i,j));
    end
end
imshow(big)
hold on
for i=1:size(path,1) % show red areas
    x=path(i,1);
    y=path(i,2);
    s1=[x,y];
    s2=[x-1,y];
    s3=[x-1,y-1];
    s4=[x,y-1];
    s=[s1;s2;s3;s4];
    xx=s(:,1)*k+0.5;
    yy=s(:,2)*k+0.5;
    h=fill(xx,yy,'y','linewidth',0.1);
    set(h,'edgealpha',0,'facealpha',1) ;
end 

text(START_x*k-0.5*k+0.5-3,START_y*k-0.5*k+0.5+k,'START','Color','blue','FontSize',5); % show 'START'
text(GOAL_x*k-0.5*k+0.5-2,GOAL_y*k-0.5*k+0.5-k,'GOAL','Color','blue','FontSize',5); % show 'GOAL'
scatter(START_x*k-0.5*k+0.5,START_y*k-0.5*k+0.5,'MarkerEdgeColor',[0 0 1],'MarkerFaceColor',[0 0 1], 'LineWidth',1); % show the blue dot for marking 'START'
scatter(GOAL_x*k-0.5*k+0.5,GOAL_y*k-0.5*k+0.5,'MarkerEdgeColor',[0 1 0],'MarkerFaceColor',[0 1 0], 'LineWidth',1); % show the green dot for marking 'GOAL'
