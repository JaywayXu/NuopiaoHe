function showIntersection = showIntersection(Map,Intersection_Map)

neg=sum(sum(Intersection_Map==1));
intersection=zeros(neg,2);
[row,col]=find(Intersection_Map==1);
intersection=[col,row];


pos=sum(sum(Intersection_Map==-1));
corner=zeros(pos,2);
[row,col]=find(Intersection_Map==-1);
corner=[col,row];



k=3; % Enlarge the map by 3 times
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

for i=1:size(intersection,1) % show intersection
    x=intersection(i,1);
    y=intersection(i,2);
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
hold on
for i=1:size(corner,1) % show intersection
    x=corner(i,1);
    y=corner(i,2);
    s1=[x,y];
    s2=[x-1,y];
    s3=[x-1,y-1];
    s4=[x,y-1];
    s=[s1;s2;s3;s4];
    xx=s(:,1)*k+0.5;
    yy=s(:,2)*k+0.5;
    h=fill(xx,yy,'b','linewidth',0.1);
    set(h,'edgealpha',0,'facealpha',1) ;
end 